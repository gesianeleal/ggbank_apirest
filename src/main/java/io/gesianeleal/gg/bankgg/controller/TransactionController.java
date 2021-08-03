package io.gesianeleal.gg.bankgg.controller;

import io.gesianeleal.gg.bankgg.controller.dto.PageDTO;
import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import io.gesianeleal.gg.bankgg.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/transactions")
@Api(value = "API REST Transaction")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService service;

    @ApiOperation(value = "Return list of Transaction")
    @GetMapping
    public ResponseEntity<List<Transaction>> listTransactions(@RequestBody PageDTO pageDTO) {
        log.info("Return Transactions");
        Pageable paging = PageRequest.of(pageDTO.getPage(), pageDTO.getQtd(), Sort.by("accountId").descending().and(Sort.by("eventDatetime")));
        Page<Transaction> listPage;
        if (pageDTO.getAccountId() > 0) {
            listPage = service.findByAccountId(paging, pageDTO.getAccountId());
            return ResponseEntity.ok(listPage.getContent());
        }
        listPage = service.findAll(paging);
        return ResponseEntity.ok(listPage.toList());
    }
    
    @ApiOperation(value = "Save a new transaction")
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Transaction transaction) {
        log.info("saveTransaction: " + transaction.toString());
        Transaction newTransaction = service.save(transaction);
        if (newTransaction != null) {
            log.info("savedTransaction: " + newTransaction.toString());
//            var teste = new DetailTransactionDTO(newTransaction);
//            log.info(teste.toString());
            return ResponseEntity.ok(newTransaction);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete a Transaction Account")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = "transactionsAccount", allEntries = true)
    public ResponseEntity<String> delete(@PathVariable("id") @Min(1) int id) {
        log.info("Delete a Transaction Account");
        Transaction transaction = service.getTransaction(id);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
