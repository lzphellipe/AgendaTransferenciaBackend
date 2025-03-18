package com.tokiomarine.transferencias.service;

import com.tokiomarine.transferencias.model.Transferencia;
import com.tokiomarine.transferencias.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;

    public TransferenciaService(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    public Transferencia agendarTransferencia(Transferencia transferencia) {
        // Calcula a taxa com base na data de transferência
        BigDecimal taxa = calcularTaxa(transferencia.getDataTransferencia(), transferencia.getValorTransferencia());
        transferencia.setTaxa(taxa);

        // Define a data de agendamento como hoje
        transferencia.setDataAgendamento(LocalDate.now());

        // Salva a transferência no banco de dados
        return transferenciaRepository.save(transferencia);
    }

    BigDecimal calcularTaxa(LocalDate dataTransferencia, BigDecimal valorTransferencia) {
        LocalDate hoje = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(hoje, dataTransferencia);

        if (dias == 0) {
            return new BigDecimal("3.00").add(valorTransferencia.multiply(new BigDecimal("0.025")));
        } else if (dias >= 1 && dias <= 10) {
            return new BigDecimal("12.00");
        } else if (dias >= 11 && dias <= 20) {
            return valorTransferencia.multiply(new BigDecimal("0.082"));
        } else if (dias >= 21 && dias <= 30) {
            return valorTransferencia.multiply(new BigDecimal("0.069"));
        } else if (dias >= 31 && dias <= 40) {
            return valorTransferencia.multiply(new BigDecimal("0.047"));
        } else if (dias >= 41 && dias <= 50) {
            return valorTransferencia.multiply(new BigDecimal("0.017"));
        } else {
            throw new IllegalArgumentException("Não há taxa aplicável para a data de transferência informada.");
        }
    }

    public List<Transferencia> listarTransferencias() {
        return transferenciaRepository.findAll();
    }
}