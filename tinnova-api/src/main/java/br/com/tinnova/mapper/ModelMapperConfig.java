package br.com.tinnova.mapper;

import br.com.tinnova.dto.VeiculoDtoResponse;
import br.com.tinnova.entity.Veiculo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(VeiculoDtoResponse.class, Veiculo.class)
				.addMappings(mp -> mp.skip(Veiculo::setId))
				.addMappings(mp -> mp.skip(Veiculo::setCreated));
		return mapper;
	}

}