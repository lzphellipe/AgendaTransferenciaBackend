package com.tokiomarine.transferencias.repository;

import com.tokiomarine.transferencias.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}