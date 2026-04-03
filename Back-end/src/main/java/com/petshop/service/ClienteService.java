package com.petshop.service;

import com.petshop.entity.Cliente;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import com.petshop.dto.ClienteRequestDTO;
import com.petshop.dto.ClienteResponseDTO;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        cliente.setEndereco(dto.getEndereco());

        validarCriacao(cliente);

        Cliente salvo = clienteRepository.save(cliente);
        return converterParaResponseDTO(salvo);
    }

    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    public Cliente buscarEntidadePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado. id=" + id));
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = buscarEntidadePorId(id);
        return converterParaResponseDTO(cliente);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente existente = buscarEntidadePorId(id);

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            existente.setNome(dto.getNome());
        }

        if (dto.getTelefone() != null && !dto.getTelefone().isBlank()) {
            existente.setTelefone(dto.getTelefone());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            existente.setEmail(dto.getEmail());
        }

        if (dto.getEndereco() != null && !dto.getEndereco().isBlank()) {
            existente.setEndereco(dto.getEndereco());
        }

        Cliente atualizado = clienteRepository.save(existente);
        return converterParaResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        Cliente existente = buscarEntidadePorId(id);
        clienteRepository.delete(existente);
    }


    private void validarCriacao(Cliente cliente) {
        if (cliente == null) {
            throw new DadosInvalidosException("Cliente não pode ser nulo.");
        }
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome é obrigatório.");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            throw new DadosInvalidosException("Telefone é obrigatório.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new DadosInvalidosException("Email é obrigatório.");
        }
    }

    private ClienteResponseDTO converterParaResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());
        dto.setEmail(cliente.getEmail());
        dto.setEndereco(cliente.getEndereco());
        return dto;
    }
}
