package io.gesianeleal.gg.bankgg.controller.dto;

import io.gesianeleal.gg.bankgg.dataaccess.model.Account;
import io.gesianeleal.gg.bankgg.dataaccess.repository.TransactionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class DetailAccountDTO {
    
    @Autowired
    private TransactionRepository transactionRepository;

    private int accountId;
    private Long documentNumber;
    private List<DetailTransactionDTO> transactions;

    public DetailAccountDTO(Account account) {
        this.accountId = account.getAccountId();
        this.documentNumber = account.getDocumentNumber();
        
        this.transactions = new ArrayList<>();
        this.transactions.addAll(transactionRepository.findByAccountId(accountId).stream().map(DetailTransactionDTO::new).collect(Collectors.toList()));
    }

    public static List<DetailAccountDTO> converter(List<Account> accounts) {
        return accounts.stream().map(DetailAccountDTO::new).collect(Collectors.toList());
    }
}
