package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.*;
import com.test.TUdipsaiApi.Repository.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<String> savePatientsFromExcel(MultipartFile file) throws IOException {
        List<Paciente> pacientes = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            Paciente paciente = new Paciente();

            Cell idCell = row.getCell(0);
            if (idCell == null || idCell.getCellType() == CellType.BLANK) {
                paciente.setId(null); // ID se asignará automáticamente
            } else {
                int id = (int) getCellValueAsNumeric(idCell);
                if (pacienteRepository.findById(id).isPresent()) {
                    Paciente nuevoPaciente = pacienteRepository.save(paciente); // Guardar paciente para obtener nuevo ID
                    messages.add("ID del paciente repetido. ID repetido: " + id + ", se asignará un nuevo ID. ID nuevo: " + nuevoPaciente.getId());
                    paciente.setId(nuevoPaciente.getId());
                } else {
                    paciente.setId(id);
                }
            }

            paciente.setAnioEducacion(getCellValueAsString(row.getCell(1)));
            paciente.setCedula(getCellValueAsString(row.getCell(2)));
            paciente.setCelular(getCellValueAsString(row.getCell(3)));
            paciente.setCiudad(getCellValueAsString(row.getCell(4)));
            paciente.setDiagnostico(getCellValueAsString(row.getCell(5)));
            paciente.setDomicilio(getCellValueAsString(row.getCell(6)));
            paciente.setEdad(getCellValueAsString(row.getCell(7)));
            paciente.setFechaApertura(getCellValueAsDate(row.getCell(8), dateFormat));
            paciente.setFechaNacimiento(getCellValueAsDate(row.getCell(9), dateFormat));
            paciente.setImagen(null); // Asume que la imagen no se carga desde Excel
            paciente.setMotivoConsulta(getCellValueAsString(row.getCell(11)));
            paciente.setNombresApellidos(getCellValueAsString(row.getCell(12)));
            paciente.setObservaciones(getCellValueAsString(row.getCell(13)));
            paciente.setPacienteEstado((int) getCellValueAsNumeric(row.getCell(14)));
            paciente.setParalelo(getCellValueAsString(row.getCell(15)));
            paciente.setPerteneceInclusion(getCellValueAsString(row.getCell(16)));
            paciente.setPortadorCarnet(getCellValueAsBoolean(row.getCell(17)));
            paciente.setProyecto(getCellValueAsString(row.getCell(18)));
            paciente.setTelefono(getCellValueAsString(row.getCell(19)));
            paciente.setTieneDiscapacidad(getCellValueAsString(row.getCell(20)));

            // Asignar o crear InstitucionEducativa por nombre
            String institucionNombre = getCellValueAsString(row.getCell(21));
            String direccionInstitucion = getCellValueAsString(row.getCell(27));
            String tipoInstitucion = getCellValueAsString(row.getCell(28));
            InstitucionEducativa institucion = getOrCreateInstitucionEducativa(institucionNombre, direccionInstitucion, tipoInstitucion);
            paciente.setInstitucionEducativa(institucion);

            // Asignar o crear Jornada por nombre
            String jornadaNombre = getCellValueAsString(row.getCell(22));
            Jornada jornada = getOrCreateJornada(jornadaNombre);
            paciente.setJornada(jornada);

            paciente.setDetalleDiscapacidad(getCellValueAsString(row.getCell(23)));
            paciente.setPorcentajeDiscapacidad((int) getCellValueAsNumeric(row.getCell(24)));
            paciente.setTipoDiscapacidad(getCellValueAsString(row.getCell(25)));
            paciente.setPerteneceAProyecto(getCellValueAsBoolean(row.getCell(26)));

            // Asignar o crear Sede por nombre (convertir a mayúsculas)
            String sedeNombre = getCellValueAsString(row.getCell(30));
            Sede sede = getOrCreateSede(sedeNombre);
            paciente.setSede(sede);

            pacientes.add(paciente);
        }

        workbook.close();
        pacienteRepository.saveAll(pacientes);

        return messages;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
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

    private Boolean getCellValueAsBoolean(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Boolean.parseBoolean(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue() != 0;
        } else {
            return null;
        }
    }

    private Date getCellValueAsDate(Cell cell, SimpleDateFormat dateFormat) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getDateCellValue();
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

    private Jornada getOrCreateJornada(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return null;
        }
        Optional<Jornada> optional = jornadaRepositorio.findByNombreJornadaIgnoreCase(nombre);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            Jornada nuevaJornada = new Jornada();
            nuevaJornada.setNombreJornada(nombre);
            nuevaJornada.setEstadoJornada(1); // Estado por defecto a 1
            return jornadaRepositorio.save(nuevaJornada);
        }
    }

    private Sede getOrCreateSede(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return null;
        }
        Optional<Sede> optional = sedeRepositorio.findByNombreIgnoreCase(nombre);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            Sede nuevaSede = new Sede();
            nuevaSede.setNombre(nombre);
            return sedeRepositorio.save(nuevaSede);
        }
    }
}
