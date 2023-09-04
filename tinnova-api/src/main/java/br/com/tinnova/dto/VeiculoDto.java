package br.com.tinnova.dto;

import br.com.tinnova.enums.MarcaEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VeiculoDto {

    @NotNull
    private String veiculo;

    @NotNull
    private MarcaEnum marca;

    @Min(1850) @Max(2023)
    private Integer ano;

    @NotNull
    private String descricao;

    @NotNull
    private Boolean vendido;

}
