package io.gesianeleal.gg.bankgg.service;

import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import io.gesianeleal.gg.bankgg.dataaccess.repository.TransactionRepository;
import io.gesianeleal.gg.bankgg.error.BusinessException;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {
    
    @Autowired
    TransactionRepository transactionRepository;    

    public Transaction save(Transaction transaction) {
        if (transaction.getOperationtypeId() != 4) {
            transaction.setAmount(transaction.getAmount().negate());
        }
        var now = new Timestamp(System.currentTimeMillis());
        transaction.setEventDatetime(now);
        log.info("service>>>>" + transaction.toString());
        return transactionRepository.save(transaction);
    }

    public Page<Transaction> findAll(Pageable paging) {
        return transactionRepository.findAll(paging);
    }
    
    public Page<Transaction> findByAccountId(Pageable paging, Integer id) {
        return transactionRepository.findByAccountId(id, paging);
    }
    
    public Transaction getTransaction(Integer id) {
        return transactionRepository.getById(id);
    }

    public void delete(int id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (!transaction.isPresent()) {
            throw new BusinessException("Transaction not found!");
        }
        transactionRepository.deleteById(id);
    }
}
