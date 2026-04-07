package com.pixgateway.backend.repository;

import com.pixgateway.backend.domain.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, UUID> {
    // Aqui o Spring já te dá: save(), findAll(), findById(), delete()...
}