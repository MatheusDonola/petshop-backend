package com.petshop.service;

import com.petshop.dto.ServicoRequestDTO;
import com.petshop.dto.ServicoResponseDTO;
import com.petshop.entity.Servico;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public ServicoResponseDTO criar(ServicoRequestDTO dto) {
        validar(dto);

        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setPrecoBase(dto.getPrecoBase());
        servico.setDuracaoMinutos(dto.getDuracaoMinutos());
        servico.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        Servico salvo = servicoRepository.save(servico);
        return toResponseDTO(salvo);
    }

    public List<ServicoResponseDTO> listarTodos() {
        return servicoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + id));

        return toResponseDTO(servico);
    }

    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico existente = servicoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + id));

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            existente.setNome(dto.getNome());
        }

        if (dto.getDescricao() != null) {
            existente.setDescricao(dto.getDescricao());
        }

        if (dto.getPrecoBase() != null) {
            if (dto.getPrecoBase().signum() <= 0) {
                throw new DadosInvalidosException("Preço base deve ser maior que zero.");
            }
            existente.setPrecoBase(dto.getPrecoBase());
        }

        if (dto.getDuracaoMinutos() != null) {
            if (dto.getDuracaoMinutos() <= 0) {
                throw new DadosInvalidosException("Duração deve ser maior que zero.");
            }
            existente.setDuracaoMinutos(dto.getDuracaoMinutos());
        }

        if (dto.getAtivo() != null) {
            existente.setAtivo(dto.getAtivo());
        }

        Servico atualizado = servicoRepository.save(existente);
        return toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + id));

        servicoRepository.delete(servico);
    }

    private void validar(ServicoRequestDTO dto) {
        if (dto == null) {
            throw new DadosInvalidosException("Serviço não pode ser nulo.");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome do serviço é obrigatório.");
        }

        if (dto.getPrecoBase() == null || dto.getPrecoBase().signum() <= 0) {
            throw new DadosInvalidosException("Preço base deve ser maior que zero.");
        }

        if (dto.getDuracaoMinutos() == null || dto.getDuracaoMinutos() <= 0) {
            throw new DadosInvalidosException("Duração em minutos deve ser maior que zero.");
        }
    }

    private ServicoResponseDTO toResponseDTO(Servico servico) {
        ServicoResponseDTO dto = new ServicoResponseDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setDescricao(servico.getDescricao());
        dto.setPrecoBase(servico.getPrecoBase());
        dto.setDuracaoMinutos(servico.getDuracaoMinutos());
        dto.setAtivo(servico.getAtivo());
        return dto;
    }
}