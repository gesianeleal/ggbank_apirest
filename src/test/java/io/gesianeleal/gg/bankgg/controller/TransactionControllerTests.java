package io.gesianeleal.gg.bankgg.controller;

import io.gesianeleal.gg.bankgg.JsonUtil;
import io.gesianeleal.gg.bankgg.controller.dto.PageDTO;
import io.gesianeleal.gg.bankgg.dataaccess.model.Transaction;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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
public class TransactionControllerTests {

    private Transaction transaction;

    @Autowired
    private MockMvc mockMvc;

    private Date now;

    @Before
    public void setup() {
        now = new Timestamp(System.currentTimeMillis());
        transaction = new Transaction(1, 1, 2, BigDecimal.ONE, now);
    }

    @Test
    public void createTransaction_PostMethod() throws Exception {
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(transaction)))
                .andExpect(status().isOk());
    }

    @Test
    public void listAllTransaction_whenGetMethod() throws Exception {
        PageDTO pageDTO = new PageDTO(0, 1, 45);
        mockMvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(pageDTO)))
                .andExpect(status().isOk());
    }
}
