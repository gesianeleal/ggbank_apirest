package io.gesianeleal.gg.bankgg.controller;

import io.gesianeleal.gg.bankgg.JsonUtil;
import io.gesianeleal.gg.bankgg.dataaccess.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTests {

    private Account account;
            
    @Autowired
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        account = new Account(1, 12345678900L);        
    }

    @Test
    public void listAccountById_whenGetMethod() throws Exception {
        mockMvc.perform(get("/accounts/" + account.getAccountId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("documentNumber", is(account.getDocumentNumber())));
    }

    @Test
    public void createAccount_whenDocument_alreadExists_PostMethod() throws Exception {
        Account newaccount = new Account(90, 3871419);
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(newaccount)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_throw_exception_when_account_doesnt_exist() throws Exception {
        mockMvc.perform(get("/accounts/8979879")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listAllAccounts_whenGetMethod() throws Exception {
        mockMvc.perform(get("/accounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].documentNumber", is(account.getDocumentNumber())));
    }
}
