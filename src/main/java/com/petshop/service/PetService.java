package com.petshop.service;

import com.petshop.dto.PetResponseDTO;
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

    public Pet buscarEntidadePorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado."));
    }

    public PetResponseDTO buscarPorId(Long id) {
        Pet pet = buscarEntidadePorId(id);
        return converterParaResponseDTO(pet);
    }

    public Pet criar(Pet pet) {
        validarCriacao(pet);
        return petRepository.save(pet);
    }

    public Pet atualizar(Long id, Pet dados) {
        Pet existente = buscarEntidadePorId(id);

        existente.setNome(dados.getNome());
        existente.setEspecie(dados.getEspecie());
        existente.setRaca(dados.getRaca());
        existente.setPorte(dados.getPorte());
        existente.setIdade(dados.getIdade());
        existente.setSexo(dados.getSexo());
        existente.setPeso(dados.getPeso());
        existente.setObservacao(dados.getObservacao());
        existente.setCliente(dados.getCliente());

        return petRepository.save(existente);
    }

    public void deletar(Long id) {
        Pet existente = buscarEntidadePorId(id);
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

    private PetResponseDTO converterParaResponseDTO(Pet pet) {
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setNome(pet.getNome());
        dto.setEspecie(pet.getEspecie());
        dto.setRaca(pet.getRaca());
        dto.setPorte(pet.getPorte());
        dto.setIdade(pet.getIdade());
        dto.setSexo(pet.getSexo());
        dto.setPeso(pet.getPeso());
        dto.setObservacao(pet.getObservacao());

        if (pet.getCliente() != null) {
            dto.setClienteId(pet.getCliente().getId());
            dto.setClienteNome(pet.getCliente().getNome());
        }

        return dto;
    }
}