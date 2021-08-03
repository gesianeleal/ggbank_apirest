package io.gesianeleal.gg.bankgg.controller.dto;

import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import io.gesianeleal.gg.bankgg.dataaccess.repository.AccountRepository;
import io.gesianeleal.gg.bankgg.dataaccess.repository.OperationTypeRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@ToString
public class DetailTransactionDTO {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    OperationTypeRepository operationTypeRepository;

    private int transactionId;
    private Long accountDocumentNumber;
    private String descriptionOperationtype;
    private BigDecimal amount;
    private Date eventDatetime;

    public DetailTransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.accountDocumentNumber = accountRepository.getById(transaction.getAccountId()).getDocumentNumber();
        this.descriptionOperationtype = operationTypeRepository.getById(transaction.getOperationtypeId()).getDescription();
        this.amount = transaction.getAmount();
        this.eventDatetime = transaction.getEventDatetime();       
    }

    public static List<DetailTransactionDTO> converter(List<Transaction> transactions) {
        return transactions.stream().map(DetailTransactionDTO::new).collect(Collectors.toList());
    }
}
