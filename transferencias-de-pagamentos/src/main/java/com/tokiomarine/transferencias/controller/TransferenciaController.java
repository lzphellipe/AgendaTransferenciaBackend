package com.tokiomarine.transferencias.controller;

import com.tokiomarine.transferencias.model.Transferencia;
import com.tokiomarine.transferencias.service.TransferenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/transferencias")
@CrossOrigin(origins = "http://localhost:8081") // Permite requisições do frontend (Vue.js)
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping
    public Transferencia agendarTransferencia(@RequestBody Transferencia transferencia) {
        return transferenciaService.agendarTransferencia(transferencia);
    }

    @GetMapping
    public List<Transferencia> listarTransferencias() {
        return transferenciaService.listarTransferencias();
    }
}