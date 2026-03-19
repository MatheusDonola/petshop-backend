package com.petshop.service;

import com.petshop.entity.Pet;
import com.petshop.entity.Agendamento;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.PetRepository;
import com.petshop.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final ServicoRepository servicoRepository;
    private final PetRepository petRepository;

    public AgendamentoService(ServicoRepository servicoRepository, PetRepository petRepository) {
        this.servicoRepository = servicoRepository;
        this.petRepository = petRepository;
    }

    public Agendamento criar(Agendamento agendamento) {
        if (agendamento == null) {
            throw new DadosInvalidosException("Serviço não pode ser nulo.");
        }

        if (agendamento.getNome() == null || agendamento.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome do serviço é obrigatório.");
        }

        if (agendamento.getPreco() == null || agendamento.getPreco().signum() <= 0) {
            throw new DadosInvalidosException("Preço do serviço deve ser maior que zero.");
        }

        if (agendamento.getStatus() == null || agendamento.getStatus().isBlank()) {
            throw new DadosInvalidosException("Status do serviço é obrigatório.");
        }

        if (agendamento.getPet() == null || agendamento.getPet().getId() == null) {
            throw new DadosInvalidosException("Pet é obrigatório (informe o pet.id).");
        }

        Long petId = agendamento.getPet().getId();
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado. id=" + petId));
        agendamento.setPet(pet);

        if (agendamento.getData() == null) {
            agendamento.setData(java.time.LocalDateTime.now());
        }

        return servicoRepository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return servicoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + id));
    }

    public Agendamento atualizar(Long id, Agendamento dadosNovos) {

        Agendamento existente = buscarPorId(id);

        if (dadosNovos.getNome() != null && !dadosNovos.getNome().isBlank()) {
            existente.setNome(dadosNovos.getNome());
        }

        if (dadosNovos.getPreco() != null) {
            if (dadosNovos.getPreco().signum() <= 0) {
                throw new DadosInvalidosException("Preço do serviço deve ser maior que zero.");
            }
            existente.setPreco(dadosNovos.getPreco());
        }

        if (dadosNovos.getStatus() != null && !dadosNovos.getStatus().isBlank()) {
            existente.setStatus(dadosNovos.getStatus());
        }

        if (dadosNovos.getObservacao() != null && !dadosNovos.getObservacao().isBlank()) {
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

        return servicoRepository.save(existente);
    }

    public void deletar(Long id) {
        Agendamento existente = buscarPorId(id);
        servicoRepository.delete(existente);
    }
}
