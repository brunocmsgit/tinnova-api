package br.com.tinnova.controller;

import br.com.tinnova.dto.VeiculoDecadaDto;
import br.com.tinnova.dto.VeiculoDto;
import br.com.tinnova.dto.VeiculoDtoResponse;
import br.com.tinnova.dto.VeiculoMarcaDto;
import br.com.tinnova.enums.MarcaEnum;
import br.com.tinnova.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @GetMapping
    public ResponseEntity<List<VeiculoDtoResponse>> listar() {
        List<VeiculoDtoResponse> veiculos = service.listarVeiculos();
        if (!veiculos.isEmpty()) {
            for (VeiculoDtoResponse veiculo: veiculos) {
                veiculo.add(linkTo(methodOn(VeiculoController.class).detalhar(veiculo.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(veiculos);
    }

    @GetMapping("/vendidos")
    public ResponseEntity<List<VeiculoDtoResponse>> listarVendidos() {
        List<VeiculoDtoResponse> veiculos = service.listarVendidos();
        if (!veiculos.isEmpty()) {
            for (VeiculoDtoResponse veiculo: veiculos) {
                veiculo.add(linkTo(methodOn(VeiculoController.class).detalhar(veiculo.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(veiculos);
    }

    @GetMapping("/nao-vendidos")
    public ResponseEntity<List<VeiculoDtoResponse>> listarNaoVendidos() {
        List<VeiculoDtoResponse> veiculos = service.listarNaoVendidos();
        if (!veiculos.isEmpty()) {
            for (VeiculoDtoResponse veiculo: veiculos) {
                veiculo.add(linkTo(methodOn(VeiculoController.class).detalhar(veiculo.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(veiculos);
    }

    @GetMapping("/decada")
    public ResponseEntity<List<VeiculoDecadaDto>> listarPorDecadas() {
        return ResponseEntity.ok().body(service.listarVeiculosPorDecada());
    }

    @GetMapping("/marca")
    public ResponseEntity<List<VeiculoMarcaDto>> listarPorMarcas() {
        return ResponseEntity.ok().body(service.listarVeiculosPorMarca());
    }

    @GetMapping("/ultima-semana")
    public ResponseEntity<List<VeiculoDtoResponse>> listarUltimaSemana() {
        List<VeiculoDtoResponse> veiculos = service.listarUltimaSemana();
        if (!veiculos.isEmpty()) {
            for (VeiculoDtoResponse veiculo: veiculos) {
                veiculo.add(linkTo(methodOn(VeiculoController.class).detalhar(veiculo.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(veiculos);
    }

    @GetMapping("/marca-ano")
    public ResponseEntity<List<VeiculoDtoResponse>> listarPorMarcaAno(@RequestParam MarcaEnum marca, Integer ano) {
        List<VeiculoDtoResponse> veiculos = service.listarPorMarcaAno(marca, ano);
        if (!veiculos.isEmpty()) {
            for (VeiculoDtoResponse veiculo: veiculos) {
                veiculo.add(linkTo(methodOn(VeiculoController.class).detalhar(veiculo.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(veiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDtoResponse> detalhar(@PathVariable Long id) {
        Optional<VeiculoDtoResponse> veiculo = service.findById(id);
        veiculo.get().add(linkTo(methodOn(VeiculoController.class).listar()).withRel("listaVeiculos"));
        return ResponseEntity.ok().body(veiculo.get());
    }

    @PostMapping
    public ResponseEntity<VeiculoDtoResponse> novo(@RequestBody @Valid VeiculoDto veiculoDto){
        VeiculoDtoResponse veiculo = service.salvar(veiculoDto);

        veiculo.add(linkTo(methodOn(VeiculoController.class)
                .detalhar(veiculo.getId())).withSelfRel());

        return ResponseEntity.created(linkTo(methodOn(VeiculoController.class)
                .detalhar(veiculo.getId())).toUri()).body(veiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDtoResponse> alterar(@PathVariable Long id, @RequestBody @Valid VeiculoDto veiculo){
        return ResponseEntity.ok(service.atualizar(id, veiculo));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VeiculoDtoResponse> alterarPatch(@PathVariable Long id, @RequestBody VeiculoDto veiculo){
        return ResponseEntity.ok(service.atualizarPatch(id, veiculo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
