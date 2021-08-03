package io.gesianeleal.gg.bankgg.controller;

import io.gesianeleal.gg.bankgg.controller.dto.DetailAccountDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import io.gesianeleal.gg.bankgg.dataaccess.model.Account;
import io.gesianeleal.gg.bankgg.error.ResourceNotFoundException;
import io.gesianeleal.gg.bankgg.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/accounts")
@Api(value = "API REST Account")
@Slf4j
public class AccountController {

    @Autowired
    AccountService service;

    @ApiOperation(value = "Return list of Accounts")
    @GetMapping
    public ResponseEntity<List<Account>> listAccounts() {
        log.info("List Accounts");
        var list = service.findAll();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Account>>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Detail a account")
    @GetMapping("/{id}")
    public ResponseEntity<Account> listAccount(@PathVariable(value = "id") @Min(1) int id) {
        log.info("List a Account");
        Account account = service.findByAccountId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account " + String.valueOf(id) + " NOT Found!"));

        //return ResponseEntity.ok().body(new DetailAccountDTO(account));
        return ResponseEntity.ok().body(account);
    }

    @ApiOperation(value = "Save a new account")
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Account account) {
        log.info("New Account");
        Account savedAccount = service.save(account);
        if (savedAccount != null) {
            return ResponseEntity.ok(savedAccount);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Update a account")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public ResponseEntity<DetailAccountDTO> update(@PathVariable("id") @Min(1) int id, @Valid @RequestBody Account account) {
        log.info("Update Account");
        Optional<Account> findAccount = service.findByAccountId(id);
        if (!findAccount.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.update(account);
        return ResponseEntity.ok(new DetailAccountDTO(account));
    }

    @ApiOperation(value = "Delete a account")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public ResponseEntity<String> delete(@PathVariable("id") @Min(1) int id) {
        log.info("Delete Account");
        Optional<Account> findAccount = service.findByAccountId(id);
        if (!findAccount.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
