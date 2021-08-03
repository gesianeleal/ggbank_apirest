package io.gesianeleal.gg.bankgg.service;

import io.gesianeleal.gg.bankgg.dataaccess.model.Account;
import io.gesianeleal.gg.bankgg.dataaccess.repository.AccountRepository;
import io.gesianeleal.gg.bankgg.dataaccess.repository.TransactionRepository;
import io.gesianeleal.gg.bankgg.error.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTests {
    
    private Account account;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;
    
    @Before
    public void setup() {
        account = new Account(1, 12345678900L);        
    }

    @Test
    public void save_whenAccount_shouldReturnAccount() {
        Account newAccount = new Account(2, 1231231000);

        when(accountRepository.findByDocumentNumber(account.getDocumentNumber())).thenReturn(Optional.ofNullable(null));
        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(newAccount);

        Account created = accountService.save(newAccount);

        assertThat(created.getDocumentNumber()).isEqualTo(1231231000L);
        verify(accountRepository).save(created);
    }

    @Test(expected = BusinessException.class)
    public void save_whenAccount_shouldReturnTheSameDocumentNumber() {
        Account newAccount = new Account(2, 12345678900L);

        when(accountRepository.findByDocumentNumber(account.getDocumentNumber())).thenReturn(Optional.ofNullable(account));
        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(newAccount);

        Account created = accountService.save(newAccount);

    }

    /*@Test
    public void delete_whenGivenId_shouldDeleteAccount_ifFound() {
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        accountService.delete(account.getAccountId());
        verify(accountRepository).deleteById(account.getAccountId());
    }

    @Test(expected = BusinessException.class)
    public void delete_should_throw_exception_when_account_doesnt_exist() {
        given(accountRepository.findById(anyInt())).willReturn(Optional.ofNullable(null));
        accountService.delete(account.getAccountId());
    }*/

    @Test
    public void findByAccountId_whenGivenId_shouldReturnAccount_ifFound() {
        Optional<Account> optionalAccount = Optional.of(account);
        when(accountRepository.findByAccountId(optionalAccount.get().getAccountId())).thenReturn(optionalAccount);

        Optional<Account> expected = accountService.findByAccountId(optionalAccount.get().getAccountId());

        assertThat(expected).isSameAs(optionalAccount);
        verify(accountRepository).findByAccountId(optionalAccount.get().getAccountId());
    }

    @Test
    public void findByAccountId_should_when_account_doesnt_exist() {
        given(accountRepository.findByAccountId(anyInt())).willReturn(Optional.ofNullable(null));

        var find = accountService.findByAccountId(9898);

        assertThat(find).isEmpty();
    }

    @Test
    public void findAll_shouldReturnAllAccounts() {
        List<Account> accounts = new ArrayList();
        accounts.add(account);
        accounts.add(new Account(2, 1231231000));

        given(accountRepository.findAll()).willReturn(accounts);

        List<Account> expected = accountService.findAll();

        assertEquals(expected, accounts);
        verify(accountRepository).findAll();
    }

/*    @Test
    public void update_whenGivenId_shouldUpdateAccount_ifFound() {
        Account newAccount = new Account(1, 99999);

        given(accountRepository.findById(account.getAccountId())).willReturn(Optional.of(account));
        accountService.update(newAccount);

        verify(accountRepository).save(newAccount);
        verify(accountRepository).findById(account.getAccountId());
    }

    @Test(expected = BusinessException.class)
    public void update_should_throw_exception_when_account_doesnt_exist() {
        Account newAccount = new Account(1, 99999);

        given(accountRepository.findById(account.getAccountId())).willReturn(Optional.of(account));
        accountService.update(newAccount);
        given(accountRepository.findById(anyInt())).willReturn(Optional.ofNullable(null));
        accountService.update(newAccount);
    }*/
}
