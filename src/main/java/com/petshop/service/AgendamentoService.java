package com.petshop.service;

import com.petshop.dto.AgendamentoRequestDTO;
import com.petshop.dto.AgendamentoResponseDTO;
import com.petshop.entity.Agendamento;
import com.petshop.entity.Cliente;
import com.petshop.entity.Pet;
import com.petshop.entity.Servico;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.AgendamentoRepository;
import com.petshop.repository.ClienteRepository;
import com.petshop.repository.PetRepository;
import com.petshop.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PetRepository petRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            PetRepository petRepository,
            ClienteRepository clienteRepository,
            ServicoRepository servicoRepository
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.petRepository = petRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
    }

    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        if (dto == null) {
            throw new DadosInvalidosException("Agendamento não pode ser nulo.");
        }

        if (dto.getPetId() == null) {
            throw new DadosInvalidosException("Pet é obrigatório.");
        }

        if (dto.getClienteId() == null) {
            throw new DadosInvalidosException("Cliente é obrigatório.");
        }

        if (dto.getServicoId() == null) {
            throw new DadosInvalidosException("Serviço é obrigatório.");
        }

        if (dto.getPreco() == null || dto.getPreco().signum() <= 0) {
            throw new DadosInvalidosException("Preço do agendamento deve ser maior que zero.");
        }

        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new DadosInvalidosException("Status do agendamento é obrigatório.");
        }

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado. id=" + dto.getPetId()));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado. id=" + dto.getClienteId()));

        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + dto.getServicoId()));

        Agendamento agendamento = new Agendamento();
        agendamento.setPet(pet);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setPreco(dto.getPreco());
        agendamento.setStatus(dto.getStatus());
        agendamento.setObservacao(dto.getObservacao());

        if (dto.getData() != null) {
            agendamento.setData(dto.getData());
        } else {
            agendamento.setData(LocalDateTime.now());
        }

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return toResponseDTO(salvo);
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Agendamento não encontrado. id=" + id));

        return toResponseDTO(agendamento);
    }

    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        Agendamento existente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Agendamento não encontrado. id=" + id));

        if (dto.getPetId() != null) {
            Pet pet = petRepository.findById(dto.getPetId())
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado. id=" + dto.getPetId()));
            existente.setPet(pet);
        }

        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado. id=" + dto.getClienteId()));
            existente.setCliente(cliente);
        }

        if (dto.getServicoId() != null) {
            Servico servico = servicoRepository.findById(dto.getServicoId())
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + dto.getServicoId()));
            existente.setServico(servico);
        }

        if (dto.getPreco() != null) {
            if (dto.getPreco().signum() <= 0) {
                throw new DadosInvalidosException("Preço do agendamento deve ser maior que zero.");
            }
            existente.setPreco(dto.getPreco());
        }

        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            existente.setStatus(dto.getStatus());
        }

        if (dto.getObservacao() != null) {
            existente.setObservacao(dto.getObservacao());
        }

        if (dto.getData() != null) {
            existente.setData(dto.getData());
        }

        Agendamento atualizado = agendamentoRepository.save(existente);
        return toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        Agendamento existente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Agendamento não encontrado. id=" + id));

        agendamentoRepository.delete(existente);
    }

    private AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();

        dto.setId(agendamento.getId());

        dto.setPetId(agendamento.getPet().getId());
        dto.setPetNome(agendamento.getPet().getNome());

        dto.setClienteId(agendamento.getCliente().getId());
        dto.setClienteNome(agendamento.getCliente().getNome());

        dto.setServicoId(agendamento.getServico().getId());
        dto.setServicoNome(agendamento.getServico().getNome());

        dto.setPreco(agendamento.getPreco());
        dto.setStatus(agendamento.getStatus());
        dto.setData(agendamento.getData());
        dto.setObservacao(agendamento.getObservacao());

        return dto;
    }
}