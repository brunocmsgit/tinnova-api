package br.com.tinnova.dto;

import br.com.tinnova.enums.MarcaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VeiculoMarcaDto {

    private MarcaEnum marca;
    private Long quantidade;

}
