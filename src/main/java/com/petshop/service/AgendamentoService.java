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

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Agendamento não encontrado. id=" + id));
    }

    public Agendamento atualizar(Long id, Agendamento dadosNovos) {
        Agendamento existente = buscarPorId(id);

        if (dadosNovos.getPreco() != null) {
            if (dadosNovos.getPreco().signum() <= 0) {
                throw new DadosInvalidosException("Preço do agendamento deve ser maior que zero.");
            }
            existente.setPreco(dadosNovos.getPreco());
        }

        if (dadosNovos.getStatus() != null && !dadosNovos.getStatus().isBlank()) {
            existente.setStatus(dadosNovos.getStatus());
        }

        if (dadosNovos.getObservacao() != null) {
            existente.setObservacao(dadosNovos.getObservacao());
        }

        if (dadosNovos.getData() != null) {
            existente.setData(dadosNovos.getData());
        }

        if (dadosNovos.getPet() != null && dadosNovos.getPet().getId() != null) {
            Long petId = dadosNovos.getPet().getId();
            Pet pet = petRepository.findById(petId)
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado. id=" + petId));
            existente.setPet(pet);
        }

        if (dadosNovos.getCliente() != null && dadosNovos.getCliente().getId() != null) {
            Long clienteId = dadosNovos.getCliente().getId();
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado. id=" + clienteId));
            existente.setCliente(cliente);
        }

        if (dadosNovos.getServico() != null && dadosNovos.getServico().getId() != null) {
            Long servicoId = dadosNovos.getServico().getId();
            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + servicoId));
            existente.setServico(servico);
        }

        return agendamentoRepository.save(existente);
    }

    public void deletar(Long id) {
        Agendamento existente = buscarPorId(id);
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