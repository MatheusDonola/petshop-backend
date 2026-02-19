package com.petshop.service;

import com.petshop.entity.Cliente;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente criar(Cliente cliente) {
        validarCriacao(cliente);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado. id=" + id));
    }

    public Cliente atualizar(Long id, Cliente dadosNovos) {
        Cliente existente = buscarPorId(id);

        if (dadosNovos.getNome() != null && !dadosNovos.getNome().isBlank()) {
            existente.setNome(dadosNovos.getNome());
        }

        if (dadosNovos.getTelefone() != null && !dadosNovos.getTelefone().isBlank()) {
            existente.setTelefone(dadosNovos.getTelefone());
        }

        if (dadosNovos.getEmail() != null && !dadosNovos.getEmail().isBlank()) {
            existente.setEmail(dadosNovos.getEmail());
        }

        return clienteRepository.save(existente);
    }

    public void deletar(Long id) {
        Cliente existente = buscarPorId(id);
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
}
