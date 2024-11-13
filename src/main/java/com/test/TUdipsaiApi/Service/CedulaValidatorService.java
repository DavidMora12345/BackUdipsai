package com.test.TUdipsaiApi.Service;

import org.springframework.stereotype.Service;

@Service
public class CedulaValidatorService {

    public boolean validarCedulaEcuatoriana(String cedula) {
        // Verificar que la cédula tenga 10 dígitos
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        // Verificar que los primeros dos dígitos corresponden a una región válida (1-24)
        int digitoRegion = Integer.parseInt(cedula.substring(0, 2));
        if (digitoRegion < 1 || digitoRegion > 24) {
            return false; // Región no válida
        }

        // Extraer el último dígito de la cédula
        int ultimoDigito = Integer.parseInt(cedula.substring(9, 10));

        // Sumar los pares (dígitos en las posiciones pares)
        int sumaPares = Integer.parseInt(cedula.substring(1, 2)) +
                Integer.parseInt(cedula.substring(3, 4)) +
                Integer.parseInt(cedula.substring(5, 6)) +
                Integer.parseInt(cedula.substring(7, 8));

        // Sumar los impares, multiplicarlos por 2 y si el resultado es mayor a 9 restarle 9
        int sumaImpares = 0;
        sumaImpares += multiplicarPorDosYRestarNueve(Integer.parseInt(cedula.substring(0, 1)));
        sumaImpares += multiplicarPorDosYRestarNueve(Integer.parseInt(cedula.substring(2, 3)));
        sumaImpares += multiplicarPorDosYRestarNueve(Integer.parseInt(cedula.substring(4, 5)));
        sumaImpares += multiplicarPorDosYRestarNueve(Integer.parseInt(cedula.substring(6, 7)));
        sumaImpares += multiplicarPorDosYRestarNueve(Integer.parseInt(cedula.substring(8, 9)));

        // Suma total de pares e impares
        int sumaTotal = sumaPares + sumaImpares;

        // Obtener la decena inmediata superior
        int decenaInmediata = ((sumaTotal / 10) + 1) * 10;

        // Restar la suma total de la decena inmediata para obtener el dígito verificador
        int digitoVerificador = decenaInmediata - sumaTotal;
        if (digitoVerificador == 10) {
            digitoVerificador = 0; // Si es 10, el dígito verificador es 0
        }

        // Comparar el dígito verificador calculado con el último dígito de la cédula
        return digitoVerificador == ultimoDigito;
    }

    private int multiplicarPorDosYRestarNueve(int numero) {
        int resultado = numero * 2;
        return (resultado > 9) ? resultado - 9 : resultado;
    }
}
