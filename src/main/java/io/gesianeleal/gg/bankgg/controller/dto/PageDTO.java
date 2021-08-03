package io.gesianeleal.gg.bankgg.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDTO {
    private Integer accountId;
    private int page;
    private int qtd;
}
