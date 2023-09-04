package br.com.tinnova.entity;

import br.com.tinnova.enums.MarcaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String veiculo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MarcaEnum marca;

    @Min(1850) @Max(2023)
    private Integer ano;

    @NotNull
    private String descricao;

    @NotNull
    private Boolean vendido;

    private LocalDateTime created;

    private LocalDateTime updated;

    @PrePersist
    public void onPrePersist() {
        this.setCreated(LocalDateTime.now());
        this.setUpdated(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdated(LocalDateTime.now());
    }

}
