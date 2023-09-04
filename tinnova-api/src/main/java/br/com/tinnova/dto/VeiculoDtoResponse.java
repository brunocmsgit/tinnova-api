package br.com.tinnova.dto;

import br.com.tinnova.enums.MarcaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoDtoResponse extends RepresentationModel<VeiculoDtoResponse> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String veiculo;

    private MarcaEnum marca;

    private Integer ano;

    private String descricao;

    private Boolean vendido;

    private LocalDateTime created;

    private LocalDateTime updated;

}
