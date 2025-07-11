// Ubicado en src/main/java/com/UnFoldCR/repository/FacturaRepository.java

package com.UnFold.repository;

import com.UnFold.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}