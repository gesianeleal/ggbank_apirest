package io.gesianeleal.gg.bankgg.service;

import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import io.gesianeleal.gg.bankgg.dataaccess.repository.TransactionRepository;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
public class TransactionServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Date now;

    @Before
    public void setup() {
        now = new Timestamp(System.currentTimeMillis());
    }

    @Test
    public void save_whenTransaction_valueShouldPositive() {
        Transaction newTransaction = new Transaction(1, 1, 4, BigDecimal.ONE, now);

        when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(newTransaction);

        Transaction created = transactionService.save(newTransaction);

        assertThat(created.getAmount()).isGreaterThanOrEqualTo(BigDecimal.ONE);
        verify(transactionRepository).save(created);
    }

    @Test
    public void save_whenTransaction_valueShouldNegative() {
        Transaction newTransaction = new Transaction(1, 1, 2, BigDecimal.ONE, now);

        when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(newTransaction);

        Transaction created = transactionService.save(newTransaction);

        assertThat(created.getAmount()).isLessThan(BigDecimal.ONE);
        verify(transactionRepository).save(created);
    }

    /*@Test
    public void delete_whenGivenId_shouldDeleteTransaction_ifFound() {
        when(accountRepository.findById(account.getTransactionId())).thenReturn(Optional.of(account));

        accountService.delete(account.getTransactionId());
        verify(accountRepository).deleteById(account.getTransactionId());
    }

    @Test(expected = BusinessException.class)
    public void delete_should_throw_exception_when_account_doesnt_exist() {
        given(accountRepository.findById(anyInt())).willReturn(Optional.ofNullable(null));
        accountService.delete(account.getTransactionId());
    } 
     */
}
