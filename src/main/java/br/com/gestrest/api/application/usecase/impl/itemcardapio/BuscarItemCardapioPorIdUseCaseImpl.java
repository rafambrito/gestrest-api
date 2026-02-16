package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.BuscarItemCardapioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscarItemCardapioPorIdUseCaseImpl implements BuscarItemCardapioPorIdUseCase {

	private final ItemCardapioRepositoryPort repository;

	@Override
	public ItemCardapio buscarPorId(Long id) {
		return repository.buscarPorId(id).orElseThrow(() -> new RuntimeException("Item não encontrado"));
	}
}
