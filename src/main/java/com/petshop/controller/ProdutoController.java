package com.petshop.controller;

import com.petshop.entity.Produto;
import com.petshop.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(
            summary = "Criar produto",
            description = "Cria um novo produto. Campos obrigatórios: nome, preco e estoque."
    )
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto criado = produtoService.criar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
            summary = "Listar produtos",
            description = "Retorna todos os produtos cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto pelo ID. Se não existir, retorna 404."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza parcialmente um produto. Apenas campos preenchidos são alterados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        produtoService.atualizar(id, produto);
        return ResponseEntity.ok("Produto de id " + id + " alterado com sucesso.");
    }


    @Operation(
            summary = "Deletar produto",
            description = "Remove um produto pelo ID. Se não existir, retorna 404."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.ok("Produto de id " + id + " deletado com sucesso.");
    }
}

