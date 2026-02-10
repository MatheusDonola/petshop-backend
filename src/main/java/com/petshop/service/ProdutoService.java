package com.petshop.service;

import com.petshop.entity.Produto;
import com.petshop.exception.DadosInvalidosException;
import com.petshop.exception.RegistroNaoEncontradoException;
import com.petshop.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criar(Produto produto) {
        validarCriacao(produto);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado. id=" + id));
    }

    public Produto atualizar(Long id, Produto dadosNovos) {
        Produto existente = buscarPorId(id);

        if (dadosNovos.getNome() != null) {
            existente.setNome(dadosNovos.getNome());
        }

        if (dadosNovos.getDescricao() != null) {
            existente.setDescricao(dadosNovos.getDescricao());
        }

        if (dadosNovos.getMarca() != null) {
            existente.setMarca(dadosNovos.getMarca());
        }

        if (dadosNovos.getPreco() != null) {
            if (dadosNovos.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
                throw new DadosInvalidosException("Preço deve ser maior que 0.");
            }
            existente.setPreco(dadosNovos.getPreco());
        }

        if (dadosNovos.getEstoque() != null) {
            if (dadosNovos.getEstoque() < 0) {
                throw new DadosInvalidosException("Estoque não pode ser negativo.");
            }
            existente.setEstoque(dadosNovos.getEstoque());
        }

        return produtoRepository.save(existente);
    }

    public void deletar(Long id) {
        Produto existente = buscarPorId(id);
        produtoRepository.delete(existente);
    }

    private void validarCriacao(Produto produto) {
        if (produto == null) {
            throw new DadosInvalidosException("Produto não pode ser nulo.");
        }
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome é obrigatório.");
        }
        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("Preço é obrigatório e deve ser maior que 0.");
        }
        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            throw new DadosInvalidosException("Estoque é obrigatório e não pode ser negativo.");
        }
    }
}
