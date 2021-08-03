package io.gesianeleal.gg.bankgg.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.gesianeleal.gg.bankgg.dataaccess.model.Account;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccountId(Integer accountId);
    Optional<Account> findByDocumentNumber(Long documentNumber);
}
