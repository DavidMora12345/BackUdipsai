package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private PacienteRepositorio pacienteRepository;

    public void savePatientsFromExcel(MultipartFile file) throws IOException {
        List<Paciente> pacientes = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Saltar la primera fila (cabecera)
                continue;
            }

            Paciente paciente = new Paciente();

            paciente.setCedula(getCellValueAsString(row.getCell(0)));
            paciente.setNombresApellidos(getCellValueAsString(row.getCell(1)));
            paciente.setEdad(getCellValueAsString(row.getCell(2)));
            paciente.setDomicilio(getCellValueAsString(row.getCell(3)));
            paciente.setCiudad(getCellValueAsString(row.getCell(4)));
            paciente.setTelefono(getCellValueAsString(row.getCell(5)));
            paciente.setCelular(getCellValueAsString(row.getCell(6)));
            paciente.setInstitucionEducativa(getCellValueAsString(row.getCell(7)));
            paciente.setTipoInstitucion(getCellValueAsString(row.getCell(8)));
            paciente.setProyecto(getCellValueAsString(row.getCell(9)));
            paciente.setJornada(getCellValueAsString(row.getCell(10)));
            paciente.setDireccionInstitucion(getCellValueAsString(row.getCell(11)));
            paciente.setAnioEducacion(getCellValueAsString(row.getCell(12)));
            paciente.setParalelo(getCellValueAsString(row.getCell(13)));
            paciente.setPerteneceInclusion(getCellValueAsString(row.getCell(14)));
            paciente.setTieneDiscapacidad(getCellValueAsString(row.getCell(15)));
            paciente.setPortadorCarnet(getCellValueAsBoolean(row.getCell(16)));
            paciente.setDiagnostico(getCellValueAsString(row.getCell(17)));
            paciente.setMotivoConsulta(getCellValueAsString(row.getCell(18)));
            paciente.setObservaciones(getCellValueAsString(row.getCell(19)));

            try {
                paciente.setFechaApertura(dateFormat.parse(getCellValueAsString(row.getCell(20))));
                paciente.setFechaNacimiento(dateFormat.parse(getCellValueAsString(row.getCell(21))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            paciente.setPacienteEstado((int) row.getCell(22).getNumericCellValue());

            // Establecer el campo de imagen como null
            paciente.setImagen(null);

            pacientes.add(paciente);
        }

        workbook.close();

        pacienteRepository.saveAll(pacientes);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return null;
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
}
