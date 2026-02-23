package com.petshop.controller;

import com.petshop.entity.Servico;
import com.petshop.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @Operation(
            summary = "Criar serviço",
            description = "Cria um novo serviço para um pet. Campos obrigatórios: nome, preco, status e pet (pet.id)."
    )
    @PostMapping
    public ResponseEntity<Servico> criar(@RequestBody Servico servico) {
        Servico criado = servicoService.criar(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
            summary = "Listar serviços",
            description = "Retorna todos os serviços cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @Operation(
            summary = "Buscar serviço por ID",
            description = "Retorna um serviço pelo ID. Se não existir, retorna 404."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar serviço",
            description = "Atualiza parcialmente um serviço. Apenas campos preenchidos serão alterados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Servico servico) {
        servicoService.atualizar(id, servico);
        return ResponseEntity.ok("Serviço de id " + id + " alterado com sucesso.");
    }

    @Operation(
            summary = "Deletar serviço",
            description = "Remove um serviço pelo ID. Se não existir, retorna 404."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.ok("Serviço de id " + id + " deletado com sucesso.");
    }
}
