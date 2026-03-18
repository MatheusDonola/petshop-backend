package com.petshop.service;

import com.petshop.dto.PetRequestDTO;
import com.petshop.dto.PetResponseDTO;
import com.petshop.entity.Cliente;
import com.petshop.entity.Pet;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.ClienteRepository;
import com.petshop.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final ClienteRepository clienteRepository;

    public PetService(PetRepository petRepository, ClienteRepository clienteRepository) {
        this.petRepository = petRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<PetResponseDTO> listar() {
        return petRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    public Pet buscarEntidadePorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado."));
    }

    public PetResponseDTO buscarPorId(Long id) {
        Pet pet = buscarEntidadePorId(id);
        return converterParaResponseDTO(pet);
    }

    public PetResponseDTO criar(PetRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));

        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setPorte(dto.getPorte());
        pet.setIdade(dto.getIdade());
        pet.setSexo(dto.getSexo());
        pet.setPeso(dto.getPeso());
        pet.setObservacao(dto.getObservacao());
        pet.setCliente(cliente);

        validarCriacao(pet);

        Pet salvo = petRepository.save(pet);
        return converterParaResponseDTO(salvo);
    }

    public PetResponseDTO atualizar(Long id, PetRequestDTO dto) {

        Pet existente = buscarEntidadePorId(id);

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));

        existente.setNome(dto.getNome());
        existente.setEspecie(dto.getEspecie());
        existente.setRaca(dto.getRaca());
        existente.setPorte(dto.getPorte());
        existente.setIdade(dto.getIdade());
        existente.setSexo(dto.getSexo());
        existente.setPeso(dto.getPeso());
        existente.setObservacao(dto.getObservacao());
        existente.setCliente(cliente);

        Pet atualizado = petRepository.save(existente);

        return converterParaResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        Pet existente = buscarEntidadePorId(id);
        petRepository.delete(existente);
    }

    private void validarCriacao(Pet pet) {
        if (pet.getNome() == null || pet.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome é obrigatório.");
        }
        if (pet.getEspecie() == null || pet.getEspecie().isBlank()) {
            throw new DadosInvalidosException("Espécie é obrigatória.");
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