package br.com.tinnova.service;

import br.com.tinnova.dto.VeiculoDto;
import br.com.tinnova.dto.VeiculoDtoResponse;
import br.com.tinnova.entity.Veiculo;
import br.com.tinnova.enums.MarcaEnum;

public final class VeiculoBuilder {

    public static Veiculo veiculoUno() {
        return Veiculo.builder()
            .id(1L)
            .veiculo("Uno")
            .ano(1995)
            .marca(MarcaEnum.Fiat)
            .vendido(false)
            .descricao("1.0 gasolina").build();
    }

    public static Veiculo veiculoGol() {
        return Veiculo.builder()
            .id(1L)
            .veiculo("Gol")
            .ano(1998)
            .marca(MarcaEnum.Volkswagen)
            .vendido(false)
            .descricao("1.6 gasolina").build();
    }

    public static VeiculoDtoResponse veiculoDtoResponseUno() {
        return VeiculoDtoResponse.builder()
                .id(1L)
                .veiculo("Uno")
                .ano(1995)
                .marca(MarcaEnum.Fiat)
                .vendido(false)
                .descricao("1.0 gasolina").build();
    }

    public static VeiculoDtoResponse veiculoDtoResponseGol() {
        return VeiculoDtoResponse.builder()
                .id(1L)
                .veiculo("Gol")
                .ano(1998)
                .marca(MarcaEnum.Volkswagen)
                .vendido(false)
                .descricao("1.6 gasolina").build();
    }

    public static VeiculoDto veiculoDtoOpala() {
        return VeiculoDto.builder()
                .veiculo("Opala")
                .ano(1975)
                .marca(MarcaEnum.Chevrolet)
                .vendido(true)
                .descricao("2.0 gasolina").build();
    }

    public static Veiculo veiculoOpala() {
        return Veiculo.builder()
                .id(1L)
                .veiculo("Opala")
                .ano(1975)
                .marca(MarcaEnum.Chevrolet)
                .vendido(true)
                .descricao("2.0 gasolina").build();
    }

    public static VeiculoDtoResponse veiculoDtoResponseOpala() {
        return VeiculoDtoResponse.builder()
                .id(1L)
                .veiculo("Opala")
                .ano(1975)
                .marca(MarcaEnum.Chevrolet)
                .vendido(true)
                .descricao("2.0 gasolina").build();
    }

}
