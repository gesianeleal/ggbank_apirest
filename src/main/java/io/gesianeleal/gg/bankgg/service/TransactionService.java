package io.gesianeleal.gg.bankgg.service;

import io.gesianeleal.gg.bankgg.dataaccess.model.Account;
import io.gesianeleal.gg.bankgg.dataaccess.model.OperationType;
import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import io.gesianeleal.gg.bankgg.dataaccess.repository.AccountRepository;
import io.gesianeleal.gg.bankgg.dataaccess.repository.OperationTypeRepository;
import io.gesianeleal.gg.bankgg.dataaccess.repository.TransactionRepository;
import io.gesianeleal.gg.bankgg.error.BusinessException;
import java.sql.Timestamp;
import java.util.List;
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
    
    @Autowired
    AccountRepository accountRepository;    
    
    @Autowired
    OperationTypeRepository operationTypeRepository;    

    public Transaction save(Transaction transaction) {
        Optional<Account> account = accountRepository.findByAccountId(transaction.getAccountId());
        if (!account.isPresent()) {
            throw new BusinessException("Account not exists!");
        }
        
        if (transaction.getOperationtypeId() < 1 || transaction.getOperationtypeId() > 4) {
            throw new BusinessException("Operation Type not exists!");
        }
        
//        OperationType operationType = operationTypeRepository.getById(transaction.getOperationtypeId());
//        if (operationType == null && operationType.getOperationtypeId() != 0) {
//            throw new BusinessException("Operation Type not exists!");
//        }
        
        if (transaction.getOperationtypeId() != 4) {
            transaction.setAmount(transaction.getAmount().negate());
        }
        var now = new Timestamp(System.currentTimeMillis());
        transaction.setEventDatetime(now);
        log.info("service>>>>" + transaction.toString());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    
    public Page<Transaction> findByAccountId(Pageable paging, Integer id) {
        return transactionRepository.findByAccountId(id, paging);
    }
    
    public List<Transaction> findByAccountId(Integer id) {
        return transactionRepository.findByAccountId(id);
    }
    
    public Optional<Transaction> getTransaction(Integer id) {
        return transactionRepository.findByTransactionId(id);
    }

    public void delete(int id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (!transaction.isPresent()) {
            throw new BusinessException("Transaction not found!");
        }
        transactionRepository.deleteById(id);
    }
}
