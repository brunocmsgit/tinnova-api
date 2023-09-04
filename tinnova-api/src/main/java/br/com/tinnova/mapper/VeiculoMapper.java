package br.com.tinnova.mapper;

import br.com.tinnova.dto.VeiculoDto;
import br.com.tinnova.entity.Veiculo;

public class VeiculoMapper {

    public Veiculo mapDtoToVeiculo (Veiculo veiculo, VeiculoDto dto) {
        veiculo.setVeiculo(dto.getVeiculo() != null ? dto.getVeiculo() : veiculo.getVeiculo());
        veiculo.setMarca(dto.getMarca() != null ? dto.getMarca() : veiculo.getMarca());
        veiculo.setAno(dto.getAno() != null ? dto.getAno() : veiculo.getAno());
        veiculo.setDescricao(dto.getDescricao() != null ? dto.getDescricao() : veiculo.getDescricao());
        veiculo.setVendido(dto.getVendido() != null ? dto.getVendido() : veiculo.getVendido());
        return veiculo;
    }

}
