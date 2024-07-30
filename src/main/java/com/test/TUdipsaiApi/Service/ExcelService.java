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

    public void savePatientsFromExcel(MultipartFile file) throws IOException {
        List<Paciente> pacientes = new ArrayList<>();
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
                    System.out.println("ID duplicado encontrado: " + id);
                    paciente.setId(null); // ID se asignará automáticamente
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
            paciente.setImagen(null); // Suponiendo que no se sube imagen por Excel
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

            // Asignar InstitucionEducativa por id
            Cell institucionCell = row.getCell(21);
            if (institucionCell != null && institucionCell.getCellType() != CellType.BLANK) {
                int institucionId = (int) getCellValueAsNumeric(institucionCell);
                Optional<InstitucionEducativa> institucion = institucionEducativaRepositorio.findById(institucionId);
                institucion.ifPresent(paciente::setInstitucionEducativa);
            }

            // Asignar Jornada por id
            Cell jornadaCell = row.getCell(22);
            if (jornadaCell != null && jornadaCell.getCellType() != CellType.BLANK) {
                int jornadaId = (int) getCellValueAsNumeric(jornadaCell);
                Optional<Jornada> jornada = jornadaRepositorio.findById(jornadaId);
                jornada.ifPresent(paciente::setJornada);
            }

            paciente.setDetalleDiscapacidad(getCellValueAsString(row.getCell(23)));
            paciente.setPorcentajeDiscapacidad((int) getCellValueAsNumeric(row.getCell(24)));
            paciente.setTipoDiscapacidad(getCellValueAsString(row.getCell(25)));
            paciente.setPerteneceAProyecto(getCellValueAsBoolean(row.getCell(26)));

            // Asignar Sede por id
            Cell sedeCell = row.getCell(30);
            if (sedeCell != null && sedeCell.getCellType() != CellType.BLANK) {
                int sedeId = (int) getCellValueAsNumeric(sedeCell);
                Optional<Sede> sede = sedeRepositorio.findById(sedeId);
                sede.ifPresent(paciente::setSede);
            }

            System.out.println("Paciente a insertar: " + paciente);

            pacientes.add(paciente);
        }

        workbook.close();
        pacienteRepository.saveAll(pacientes);

        System.out.println("Pacientes guardados: " + pacientes.size());
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
}
