package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import com.test.TUdipsaiApi.Service.HistorialCambiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class HistorialCambiosController {

    @Autowired
    private HistorialCambiosService historialCambiosService;


}
