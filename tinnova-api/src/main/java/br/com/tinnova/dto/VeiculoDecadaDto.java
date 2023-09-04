package br.com.tinnova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VeiculoDecadaDto {

    private Integer decada;
    private Long quantidade;

}
