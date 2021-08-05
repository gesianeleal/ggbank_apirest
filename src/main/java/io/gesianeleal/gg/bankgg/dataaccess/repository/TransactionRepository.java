package io.gesianeleal.gg.bankgg.dataaccess.repository;

import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findByTransactionId(Integer id);
    
    @Cacheable("transactionsAccount")
    Page<Transaction> findByAccountId(Integer id, Pageable paging);
    
    @Cacheable("transactionsAccount")
    List<Transaction> findByAccountId(Integer id);

}
