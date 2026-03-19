package com.petshop.controller;

import com.petshop.entity.Agendamento;
import com.petshop.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Operation(
            summary = "Criar serviço",
            description = "Cria um novo serviço para um pet. Campos obrigatórios: nome, preco, status e pet (pet.id)."
    )
    @PostMapping
    public ResponseEntity<Agendamento> criar(@RequestBody Agendamento agendamento) {
        Agendamento criado = agendamentoService.criar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
            summary = "Listar serviços",
            description = "Retorna todos os serviços cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @Operation(
            summary = "Buscar serviço por ID",
            description = "Retorna um serviço pelo ID. Se não existir, retorna 404."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar serviço",
            description = "Atualiza parcialmente um serviço. Apenas campos preenchidos serão alterados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Agendamento agendamento) {
        agendamentoService.atualizar(id, agendamento);
        return ResponseEntity.ok("Serviço de id " + id + " alterado com sucesso.");
    }

    @Operation(
            summary = "Deletar serviço",
            description = "Remove um serviço pelo ID. Se não existir, retorna 404."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.ok("Serviço de id " + id + " deletado com sucesso.");
    }
}
