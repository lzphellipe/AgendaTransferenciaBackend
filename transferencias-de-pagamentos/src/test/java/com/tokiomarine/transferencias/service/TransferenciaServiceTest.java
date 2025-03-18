package com.tokiomarine.transferencias.service;

import com.tokiomarine.transferencias.model.Transferencia;
import com.tokiomarine.transferencias.repository.TransferenciaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransferenciaServiceTest {

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @InjectMocks
    private TransferenciaService transferenciaService;

    @Test
    public void testCalcularTaxaParaTransferenciaHoje() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now());
        transferencia.setValorTransferencia(new BigDecimal("100.00"));

        BigDecimal taxa = transferenciaService.calcularTaxa(transferencia.getDataTransferencia(), transferencia.getValorTransferencia());
        assertEquals(new BigDecimal("5.50"), taxa); // 3.00 + (100 * 0.025)
    }

    @Test
    public void testAgendarTransferencia() {
        Transferencia transferencia = new Transferencia();
        transferencia.setContaOrigem("1234567890");
        transferencia.setContaDestino("0987654321");
        transferencia.setValorTransferencia(new BigDecimal("100.00"));
        transferencia.setDataTransferencia(LocalDate.now().plusDays(1));

        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferencia);

        Transferencia result = transferenciaService.agendarTransferencia(transferencia);
        assertNotNull(result);
        assertEquals(new BigDecimal("12.00"), result.getTaxa());
    }
}