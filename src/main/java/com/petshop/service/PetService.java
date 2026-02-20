package com.petshop.service;

import com.petshop.entity.Pet;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> listar() {
        return petRepository.findAll();
    }

    public Pet buscarPorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado."));
    }

    public Pet criar(Pet pet) {
        validarCriacao(pet);
        return petRepository.save(pet);
    }

    public Pet atualizar(Long id, Pet dados) {
        Pet existente = buscarPorId(id);

        existente.setNome(dados.getNome());
        existente.setEspecie(dados.getEspecie());
        existente.setRaca(dados.getRaca());
        existente.setPorte(dados.getPorte());
        existente.setCliente(dados.getCliente());

        return petRepository.save(existente);
    }

    public void deletar(Long id) {
        Pet existente = buscarPorId(id);
        petRepository.delete(existente);
    }

    private void validarCriacao(Pet pet) {
        if (pet.getNome() == null || pet.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome é obrigatório.");
        }
        if (pet.getCliente() == null || pet.getCliente().getId() == null) {
            throw new DadosInvalidosException("Cliente (id) é obrigatório.");
        }
    }
}