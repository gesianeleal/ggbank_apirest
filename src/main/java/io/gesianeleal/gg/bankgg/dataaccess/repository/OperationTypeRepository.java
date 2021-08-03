package io.gesianeleal.gg.bankgg.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.gesianeleal.gg.bankgg.dataaccess.model.OperationType;

public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
