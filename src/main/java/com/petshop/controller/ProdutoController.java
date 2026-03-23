package com.petshop.controller;

import com.petshop.dto.ProdutoRequestDTO;
import com.petshop.dto.ProdutoResponseDTO;
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
            description = "Cria um produto. Campos obrigatórios: nome, estoque e preco."
    )
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(dto));
    }

    @Operation(
            summary = "Listar produtos",
            description = "Retorna todos os produtos cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto pelo ID. Se não existir, retorna 404."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza parcialmente um produto. Apenas campos preenchidos serão alterados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto));
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