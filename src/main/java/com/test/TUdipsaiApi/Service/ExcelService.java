package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.*;
import com.test.TUdipsaiApi.Repository.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar para transacciones
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelService {

    @Autowired
    private PacienteRepositorio pacienteRepository;

    @Autowired
    private InstitucionEducativaRepositorio institucionEducativaRepositorio;

    @Autowired
    private JornadaRepositorio jornadaRepositorio;

    @Autowired
    private SedeRepositorio sedeRepositorio;

    @Transactional // Aseguramos que la operación sea atómica
    public List<String> savePatientsFromExcel(MultipartFile file) throws IOException {
        List<Paciente> pacientes = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        List<String> warnings = new ArrayList<>(); // Lista para advertencias
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            messages.add("Error al leer el archivo Excel. Asegúrese de que el archivo sea válido.");
            return messages;
        }

        Sheet sheet = workbook.getSheetAt(0);

        int rowNum = 0; // Para llevar un registro de la fila actual

        for (Row row : sheet) {
            rowNum++;
            if (row.getRowNum() == 0) {
                continue; // Saltar la fila de encabezados
            }

            Paciente paciente = new Paciente();
            boolean hasError = false; // Indicador de error en este paciente

            try {
                // Procesar ID
                Cell idCell = row.getCell(0);
                if (idCell == null || idCell.getCellType() == CellType.BLANK) {
                    paciente.setId(null);
                } else {
                    int id = (int) getCellValueAsNumeric(idCell);
                    if (pacienteRepository.findById(id).isPresent()) {
                        warnings.add("Fila " + rowNum + ": ID " + id + " ya existe. Se asignará un nuevo ID.");
                        paciente.setId(null); // Establecer como null para que se genere un nuevo ID
                    } else {
                        paciente.setId(id);
                    }
                }

                paciente.setPacienteEstado(1);

                // Obtener la cédula
                String cedula = getCellValueAsString(row.getCell(6));
                if (cedula == null || cedula.trim().isEmpty()) {
                    warnings.add("Fila " + rowNum + ": La cédula está vacía. Se guardará como null.");
                    paciente.setCedula(null);
                } else {
                    paciente.setCedula(cedula);
                }

                // Procesar la edad
                String edadStr = getCellValueAsString(row.getCell(5));
                paciente.setEdad(parseAge(edadStr, warnings, cedula, rowNum));

                paciente.setFechaApertura(getCellValueAsDate(row.getCell(1), dateFormat));
                paciente.setNombresApellidos(getCellValueAsString(row.getCell(2)));
                paciente.setCiudad(getCellValueAsString(row.getCell(3)));
                paciente.setFechaNacimiento(getCellValueAsDate(row.getCell(4), dateFormat));

                paciente.setDomicilio(getCellValueAsString(row.getCell(7)));
                paciente.setTelefono(getCellValueAsString(row.getCell(8)));
                paciente.setCelular(getCellValueAsString(row.getCell(9)));

                // Crear o asignar Institución Educativa
                String institucionNombre = getCellValueAsString(row.getCell(10));
                String tipoInstitucion = getCellValueAsString(row.getCell(11));
                InstitucionEducativa institucion = getOrCreateInstitucionEducativa(institucionNombre, null, tipoInstitucion);
                paciente.setInstitucionEducativa(institucion);

                // Crear o asignar Jornada
                String jornadaNombre = getCellValueAsString(row.getCell(12));
                Jornada jornada = getOrCreateJornada(jornadaNombre, warnings, rowNum);
                if (jornada == null) {
                    warnings.add("Fila " + rowNum + ": La jornada está vacía. Se guardará como null.");
                    paciente.setJornada(null);
                } else {
                    paciente.setJornada(jornada);
                }

                // Crear o asignar Sede
                String sedeNombre = getCellValueAsString(row.getCell(20));
                Sede sede = getOrCreateSede(sedeNombre);
                paciente.setSede(sede);

                // Verificar si el paciente con la misma cédula ya existe
                List<Paciente> posiblesDuplicados = pacienteRepository.findBySearchCriteria(paciente.getCedula());
                if (!posiblesDuplicados.isEmpty()) {
                    warnings.add("Fila " + rowNum + ": Paciente con cédula " + paciente.getCedula() + " ya está registrado y se ingresó nuevamente como advertencia.");
                }

                pacientes.add(paciente); // Agregar a la lista para guardar posteriormente

            } catch (Exception e) {
                messages.add("Fila " + rowNum + ": Error al procesar el paciente. " + e.getMessage());
                e.printStackTrace();
                continue;
            }
        }

        workbook.close();

        if (!messages.isEmpty()) {
            // Si hay errores críticos, no guardar nada y devolver los mensajes
            messages.add(0, "No se guardó ningún dato debido a errores en el archivo:");
            // Agregar las advertencias al final
            messages.addAll(warnings);
            return messages;
        }

        // Guardar todos los pacientes
        pacienteRepository.saveAll(pacientes);

        messages.add("Se han guardado " + pacientes.size() + " pacientes correctamente.");
        // Agregar las advertencias al final
        messages.addAll(warnings);

        return messages;
    }

    private String parseAge(String ageStr, List<String> warnings, String cedulaPaciente, int rowNum) {
        if (ageStr == null || ageStr.trim().isEmpty()) {
            warnings.add("Fila " + rowNum + ": La edad está vacía para el paciente con cédula: " + cedulaPaciente + ". Se guardará como null.");
            return null; // Establecer edad como nula
        }

        try {
            String[] parts = ageStr.split(" - ");
            int years = 0;
            int months = 0;

            for (String part : parts) {
                if (part.contains("AÑO")) {
                    years = extractNumber(part);
                } else if (part.contains("MES")) {
                    months = extractNumber(part);
                }
            }

            return years + " AÑOS - " + months + " MESES";
        } catch (Exception e) {
            warnings.add("Fila " + rowNum + ": Formato de edad inválido para el paciente con cédula: " + cedulaPaciente + ". Valor recibido: '" + ageStr + "'. Se guardará como null.");
            return null; // Establecer edad como nula
        }
    }

    private int extractNumber(String str) {
        String numberStr = str.replaceAll("\\D+", ""); // Elimina todo lo que no sea un dígito
        try {
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0; // Retorna 0 si no se puede convertir
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Manejar números como cadenas
                DataFormatter formatter = new DataFormatter();
                return formatter.formatCellValue(cell);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        DataFormatter formulaFormatter = new DataFormatter();
                        return formulaFormatter.formatCellValue(cell);
                    case STRING:
                        return cell.getStringCellValue();
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    private double getCellValueAsNumeric(Cell cell) {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private Date getCellValueAsDate(Cell cell, SimpleDateFormat dateFormat) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                // Si no es una fecha, intentar convertir el valor numérico a fecha
                return DateUtil.getJavaDate(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return dateFormat.parse(cell.getStringCellValue());
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    private InstitucionEducativa getOrCreateInstitucionEducativa(String nombre, String direccion, String tipoInstitucion) {
        if (nombre == null || nombre.isEmpty()) {
            return null;
        }
        Optional<InstitucionEducativa> optional = institucionEducativaRepositorio.findByNombreInstitucionIgnoreCase(nombre);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            InstitucionEducativa nuevaInstitucion = new InstitucionEducativa();
            nuevaInstitucion.setNombreInstitucion(nombre);
            nuevaInstitucion.setDireccion(direccion);
            nuevaInstitucion.setTipoInstitucion(tipoInstitucion);
            nuevaInstitucion.setInstitucionEstado(1); // Estado por defecto a 1
            return institucionEducativaRepositorio.save(nuevaInstitucion);
        }
    }

    // Forzar creación de jornada si no existe
    private Jornada getOrCreateJornada(String nombre, List<String> warnings, int rowNum) {
        if (nombre == null || nombre.trim().isEmpty()) {
            warnings.add("Fila " + rowNum + ": El nombre de la jornada está vacío. Se guardará como null.");
            return null;
        }

        // Intentar buscar la jornada por nombre
        Optional<Jornada> optional = jornadaRepositorio.findByNombreJornadaIgnoreCase(nombre);

        // Si la jornada existe, devolverla
        if (optional.isPresent()) {
            return optional.get();
        } else {
            // Si no existe, crearla y devolver la nueva instancia
            Jornada nuevaJornada = new Jornada();
            nuevaJornada.setNombreJornada(nombre);
            nuevaJornada.setEstadoJornada(1); // Estado por defecto

            // Guardar la nueva jornada en la base de datos
            return jornadaRepositorio.save(nuevaJornada);
        }
    }

    private Sede getOrCreateSede(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return null;
        }

        List<Sede> resultados = sedeRepositorio.findByNombreIgnoreCase(nombre);

        if (resultados.isEmpty()) {
            Sede nuevaSede = new Sede();
            nuevaSede.setNombre(nombre);
            nuevaSede.setEstado(1); // Estado por defecto a 1
            return sedeRepositorio.save(nuevaSede);
        } else {
            return resultados.get(0); // Retorna el primero si ya existen
        }
    }
}
