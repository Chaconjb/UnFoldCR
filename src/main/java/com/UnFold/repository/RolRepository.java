// Ubicado en src/main/java/com/UnFoldCR/repository/FacturaRepository.java

package com.UnFold.repository;

import com.UnFoldCR.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Factura, Long> {
}