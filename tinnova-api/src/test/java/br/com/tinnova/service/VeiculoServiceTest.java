package br.com.tinnova.service;

import br.com.tinnova.dto.VeiculoDtoResponse;
import br.com.tinnova.entity.Veiculo;
import br.com.tinnova.enums.MarcaEnum;
import br.com.tinnova.exception.EntidadeNaoEncontradaException;
import br.com.tinnova.mapper.ModelMapperConfig;
import br.com.tinnova.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.com.tinnova.service.VeiculoBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ModelMapperConfig mapperConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarVeiculos() {
        List<Veiculo> veiculos = Arrays.asList(veiculoUno(), veiculoGol());

        // mock do repositorio
        when(veiculoRepository.findAll()).thenReturn(veiculos);

        // mock do modelMapper
        when(modelMapper.map(veiculoUno(), VeiculoDtoResponse.class)).thenReturn(veiculoDtoResponseUno());
        when(modelMapper.map(veiculoGol(), VeiculoDtoResponse.class)).thenReturn(veiculoDtoResponseGol());

        // Chamando service
        List<VeiculoDtoResponse> listaDeVeiculos = veiculoService.listarVeiculos();

        // Verificando os resultados esperados
        assertEquals(2, listaDeVeiculos.size());
        assertEquals("Uno", listaDeVeiculos.get(0).getVeiculo());
        assertEquals(MarcaEnum.Fiat, listaDeVeiculos.get(0).getMarca());
        assertEquals("Gol", listaDeVeiculos.get(1).getVeiculo());
        assertEquals(MarcaEnum.Volkswagen, listaDeVeiculos.get(1).getMarca());

        // Verificando se os metodos foram chamados
        verify(veiculoRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(veiculoUno(), VeiculoDtoResponse.class);
        verify(modelMapper, times(1)).map(veiculoGol(), VeiculoDtoResponse.class);
    }

    @Test
    public void testSalvar() {

        // Configurando comportamento do mock do modelMapper
        when(modelMapper.map(veiculoDtoOpala(), Veiculo.class)).thenReturn(veiculoOpala());
        when(modelMapper.map(veiculoOpala(), VeiculoDtoResponse.class)).thenReturn(veiculoDtoResponseOpala());

        // Configurando comportamento do mock do repositório
        when(veiculoRepository.save(veiculoOpala())).thenReturn(veiculoOpala());

        // Chamando o service
        VeiculoDtoResponse veiculoDtoResponse = veiculoService.salvar(veiculoDtoOpala());

        // Verificando o resultado
        assertEquals(1L, veiculoDtoResponse.getId());
        assertEquals("Opala", veiculoDtoResponse.getVeiculo());
        assertEquals(MarcaEnum.Chevrolet, veiculoDtoResponse.getMarca());

        // Verificando os métodos chamados
        verify(modelMapper, times(1)).map(veiculoDtoOpala(), Veiculo.class);
        verify(modelMapper, times(1)).map(veiculoOpala(), VeiculoDtoResponse.class);
        verify(veiculoRepository, times(1)).save(veiculoOpala());
    }

    @Test
    public void testVeiculoExistente() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculoGol()));

        when(modelMapper.map(veiculoGol(), VeiculoDtoResponse.class)).thenReturn(veiculoDtoResponseGol());

        Optional<VeiculoDtoResponse> veiculoDtoResponseOptional = veiculoService.findById(1L);

        assertTrue(veiculoDtoResponseOptional.isPresent());

        VeiculoDtoResponse veiculoDtoResponse = veiculoDtoResponseOptional.get();
        assertEquals(1L, veiculoDtoResponse.getId());
        assertEquals("Gol", veiculoDtoResponse.getVeiculo());
        assertEquals(MarcaEnum.Volkswagen, veiculoDtoResponse.getMarca());

        verify(veiculoRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(veiculoGol(), VeiculoDtoResponse.class);
    }

    @Test
    public void testAtualizarVeiculoExistente() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculoOpala()));

        when(veiculoRepository.save(veiculoOpala())).thenReturn(veiculoOpala());

        when(modelMapper.map(veiculoOpala(), VeiculoDtoResponse.class)).thenReturn(veiculoDtoResponseOpala());

        VeiculoDtoResponse veiculoDtoResponse = veiculoService.atualizar(1L, veiculoDtoOpala());

        verify(modelMapper, times(1)).map(veiculoOpala(), VeiculoDtoResponse.class);
        verify(veiculoRepository, times(1)).findById(1L);
        verify(veiculoRepository, times(1)).save(veiculoOpala());

        assertEquals(1L, veiculoDtoResponse.getId());
        assertEquals("Opala", veiculoDtoResponse.getVeiculo());
        assertEquals(MarcaEnum.Chevrolet, veiculoDtoResponse.getMarca());
    }

    @Test
    public void testAtualizarPatchVeiculo() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculoOpala()));

        when(veiculoRepository.save(veiculoOpala())).thenReturn(veiculoOpala());

        when(modelMapper.map(veiculoOpala(), VeiculoDtoResponse.class)).thenReturn(veiculoDtoResponseOpala());

        VeiculoDtoResponse veiculoDtoResponse = veiculoService.atualizarPatch(1L, veiculoDtoOpala());

        verify(modelMapper, times(1)).map(veiculoOpala(), VeiculoDtoResponse.class);
        verify(veiculoRepository, times(1)).findById(1L);
        verify(veiculoRepository, times(1)).save(veiculoOpala());

        assertEquals(1L, veiculoDtoResponse.getId());
        assertEquals("Opala", veiculoDtoResponse.getVeiculo());
        assertEquals(MarcaEnum.Chevrolet, veiculoDtoResponse.getMarca());
    }

    @Test
    public void testVeiculoNaoEncontrado() {

        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> veiculoService.findById(1L));

        String mensagem = String.format("Veiculo com id %d não encontrado", 1L);
        String mensagemExeption = exception.getMessage();
        assertEquals(mensagem, mensagemExeption);

        verify(veiculoRepository, times(1)).findById(1L);
        verify(modelMapper, never()).map(any(), eq(VeiculoDtoResponse.class));
    }

    @Test
    public void testRemoverVeiculo() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculoGol()));

        veiculoService.remover(1L);

        verify(veiculoRepository, times(1)).findById(1L);
        verify(veiculoRepository, times(1)).delete(veiculoGol());
    }

}