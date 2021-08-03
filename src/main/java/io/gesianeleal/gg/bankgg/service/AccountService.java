package io.gesianeleal.gg.bankgg.service;

import io.gesianeleal.gg.bankgg.dataaccess.model.Account;
import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import io.gesianeleal.gg.bankgg.dataaccess.repository.AccountRepository;
import io.gesianeleal.gg.bankgg.dataaccess.repository.TransactionRepository;
import io.gesianeleal.gg.bankgg.error.BusinessException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByAccountId(int id) {
        return accountRepository.findByAccountId(id);
    }

    public Account save(Account account) {
        log.info("Service>>>>" + account.toString());
        var findAccount = accountRepository.findByDocumentNumber(account.getDocumentNumber());
        if (findAccount.isPresent()) {
            throw new BusinessException("Account with this number alread exists!");
        }
        return accountRepository.save(account);
    }

    public Account update(Account account) {
        var findAccount = accountRepository.findById(account.getAccountId());
        if (!findAccount.isPresent()) {
            throw new BusinessException("Account not exists!");
        }
        return accountRepository.save(account);
    }

    public void delete(Integer accountId) {
        Optional<Account> findAccount = accountRepository.findById(accountId);
        if (findAccount == null) {
            throw new BusinessException("Account not found!");
        }
        Pageable paging = PageRequest.of(1, 10);
        Page<Transaction> findTransaction = transactionRepository.findByAccountId(findAccount.get().getAccountId(), paging);
        if (!findTransaction.isEmpty()) {
            throw new BusinessException("Account has transactions!");
        }
        accountRepository.deleteById(accountId);
    }
}
