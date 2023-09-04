package br.com.tinnova.repository;

import br.com.tinnova.dto.VeiculoDecadaDto;
import br.com.tinnova.dto.VeiculoMarcaDto;
import br.com.tinnova.entity.Veiculo;
import br.com.tinnova.enums.MarcaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findByVendidoTrue();

    List<Veiculo> findByVendidoFalse();

    List<Veiculo> findByCreatedBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Veiculo> findByMarcaAndAno(MarcaEnum marca, Integer ano);

    @Query("""
        SELECT new br.com.tinnova.dto.VeiculoDecadaDto(FLOOR(v.ano / 10) * 10 as decada, COUNT(*) as quantidade)
            FROM Veiculo v
            GROUP BY decada
            ORDER BY decada asc""")
    List<VeiculoDecadaDto> findVeiculosPorDecada();

    @Query("""
        SELECT new br.com.tinnova.dto.VeiculoMarcaDto(v.marca as marca, COUNT(*) as quantidade)
            FROM Veiculo v
            GROUP BY marca
            ORDER BY marca asc""")
     List<VeiculoMarcaDto> findVeiculosPorMarca();

}
