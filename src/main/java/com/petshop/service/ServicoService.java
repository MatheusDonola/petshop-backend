package com.petshop.service;

import com.petshop.entity.Pet;
import com.petshop.entity.Servico;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.PetRepository;
import com.petshop.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final PetRepository petRepository;

    public ServicoService(ServicoRepository servicoRepository, PetRepository petRepository) {
        this.servicoRepository = servicoRepository;
        this.petRepository = petRepository;
    }

    public Servico criar(Servico servico) {
        if (servico == null) {
            throw new DadosInvalidosException("Serviço não pode ser nulo.");
        }

        if (servico.getNome() == null || servico.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome do serviço é obrigatório.");
        }

        if (servico.getPreco() == null || servico.getPreco().signum() <= 0) {
            throw new DadosInvalidosException("Preço do serviço deve ser maior que zero.");
        }

        if (servico.getStatus() == null || servico.getStatus().isBlank()) {
            throw new DadosInvalidosException("Status do serviço é obrigatório.");
        }

        if (servico.getPet() == null || servico.getPet().getId() == null) {
            throw new DadosInvalidosException("Pet é obrigatório (informe o pet.id).");
        }

        Long petId = servico.getPet().getId();
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pet não encontrado. id=" + petId));
        servico.setPet(pet);

        if (servico.getData() == null) {
            servico.setData(java.time.LocalDateTime.now());
        }

        return servicoRepository.save(servico);
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Serviço não encontrado. id=" + id));
    }

    public Servico atualizar(Long id, Servico dadosNovos) {

        Servico existente = buscarPorId(id);

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
        Servico existente = buscarPorId(id);
        servicoRepository.delete(existente);
    }
}
