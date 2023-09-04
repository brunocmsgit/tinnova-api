package br.com.tinnova.service;

import br.com.tinnova.dto.VeiculoDecadaDto;
import br.com.tinnova.dto.VeiculoDto;
import br.com.tinnova.dto.VeiculoMarcaDto;
import br.com.tinnova.enums.MarcaEnum;
import br.com.tinnova.exception.EntidadeNaoEncontradaException;
import br.com.tinnova.dto.VeiculoDtoResponse;
import br.com.tinnova.entity.Veiculo;
import br.com.tinnova.mapper.VeiculoMapper;
import br.com.tinnova.repository.VeiculoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private ModelMapper mapper;

    public List<VeiculoDtoResponse> listarVeiculos() {
        return repository.findAll().stream()
                .map(veiculo -> mapper.map(veiculo, VeiculoDtoResponse.class))
                .collect(Collectors.toList());
    }

    public List<VeiculoDtoResponse> listarVendidos() {
        return repository.findByVendidoTrue().stream()
                .map(veiculo -> mapper.map(veiculo, VeiculoDtoResponse.class))
                .collect(Collectors.toList());
    }

    public List<VeiculoDtoResponse> listarNaoVendidos() {
        return repository.findByVendidoFalse().stream()
                .map(veiculo -> mapper.map(veiculo, VeiculoDtoResponse.class))
                .collect(Collectors.toList());
    }

    public List<VeiculoDecadaDto> listarVeiculosPorDecada() {
        return repository.findVeiculosPorDecada();
    }

    public List<VeiculoMarcaDto> listarVeiculosPorMarca() {
        return repository.findVeiculosPorMarca();
    }

    public List<VeiculoDtoResponse> listarUltimaSemana() {
        return repository.findByCreatedBetween(
                    LocalDate.now().minusDays(7).atStartOfDay(), LocalDateTime.now())
                .stream()
                .map(veiculo -> mapper.map(veiculo, VeiculoDtoResponse.class))
                .collect(Collectors.toList());
    }

    public List<VeiculoDtoResponse> listarPorMarcaAno(MarcaEnum marca, Integer ano) {
        return repository.findByMarcaAndAno(marca, ano)
                .stream()
                .map(veiculo -> mapper.map(veiculo, VeiculoDtoResponse.class))
                .collect(Collectors.toList());
    }

    public VeiculoDtoResponse salvar(VeiculoDto veiculoDto) {
        Veiculo veiculo = repository.save(mapper.map(veiculoDto, Veiculo.class));
        return mapper.map(veiculo, VeiculoDtoResponse.class);
    }

    public VeiculoDtoResponse atualizar(Long id, VeiculoDto veiculoDto) {

        Optional<Veiculo> veiculo = repository.findById(id);

        if (veiculo.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Veiculo com id %d não encontrado", id));
        }

        mapper.map(veiculoDto, veiculo.get());

        repository.save(veiculo.get());
        return mapper.map(veiculo.get(), VeiculoDtoResponse.class);
    }

    public VeiculoDtoResponse atualizarPatch(Long id, VeiculoDto veiculoDto) {

        Optional<Veiculo> veiculo = repository.findById(id);

        if (veiculo.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Veiculo com id %d não encontrado", id));
        }

        repository.save(new VeiculoMapper().mapDtoToVeiculo(veiculo.get(), veiculoDto));
        return mapper.map(veiculo.get(), VeiculoDtoResponse.class);
    }

    public void remover(Long id) {
        Optional<Veiculo> veiculoOptional = repository.findById(id);

        if (veiculoOptional.isPresent()) {
            repository.delete(veiculoOptional.get());
        } else {
            throw new EntidadeNaoEncontradaException(String.format("Veiculo com id %d não encontrado", id));
        }
    }

    public Optional<VeiculoDtoResponse> findById(Long id) {
        Optional<Veiculo> veiculoOptional = repository.findById(id);
        if (veiculoOptional.isPresent()) {
            return Optional.ofNullable(mapper.map(veiculoOptional.get(), VeiculoDtoResponse.class));
        } else {
            throw new EntidadeNaoEncontradaException(String.format("Veiculo com id %d não encontrado", id));
        }
    }

}
