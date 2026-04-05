package com.petshop.service;

import com.petshop.dto.ProdutoRequestDTO;
import com.petshop.dto.ProdutoResponseDTO;
import com.petshop.entity.Produto;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        validar(dto);

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setMarca(dto.getMarca());
        produto.setDescricao(dto.getDescricao());
        produto.setEstoque(dto.getEstoque());
        produto.setPreco(dto.getPreco());
        produto.setImagemUrl(dto.getImagemUrl());

        Produto salvo = produtoRepository.save(produto);
        return toResponseDTO(salvo);
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado. id=" + id));

        return toResponseDTO(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado. id=" + id));

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            existente.setNome(dto.getNome());
        }

        if (dto.getMarca() != null) {
            existente.setMarca(dto.getMarca());
        }

        if (dto.getDescricao() != null) {
            existente.setDescricao(dto.getDescricao());
        }

        if (dto.getEstoque() != null) {
            if (dto.getEstoque() < 0) {
                throw new DadosInvalidosException("Estoque não pode ser negativo.");
            }
            existente.setEstoque(dto.getEstoque());
        }

        if (dto.getPreco() != null) {
            if (dto.getPreco().signum() <= 0) {
                throw new DadosInvalidosException("Preço deve ser maior que zero.");
            }
            existente.setPreco(dto.getPreco());
        }

        if (dto.getImagemUrl() != null) {
            existente.setImagemUrl(dto.getImagemUrl());
        }

        Produto atualizado = produtoRepository.save(existente);
        return toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado. id=" + id));

        produtoRepository.delete(produto);
    }

    private void validar(ProdutoRequestDTO dto) {
        if (dto == null) {
            throw new DadosInvalidosException("Produto não pode ser nulo.");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome do produto é obrigatório.");
        }

        if (dto.getEstoque() == null || dto.getEstoque() < 0) {
            throw new DadosInvalidosException("Estoque deve ser zero ou maior.");
        }

        if (dto.getPreco() == null || dto.getPreco().signum() <= 0) {
            throw new DadosInvalidosException("Preço deve ser maior que zero.");
        }
    }

    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setMarca(produto.getMarca());
        dto.setDescricao(produto.getDescricao());
        dto.setEstoque(produto.getEstoque());
        dto.setPreco(produto.getPreco());
        dto.setImagemUrl(produto.getImagemUrl());
        return dto;
    }
}