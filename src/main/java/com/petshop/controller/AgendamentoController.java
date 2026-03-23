package com.petshop.controller;

import com.petshop.dto.AgendamentoRequestDTO;
import com.petshop.dto.AgendamentoResponseDTO;
import com.petshop.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Operation(
            summary = "Criar agendamento",
            description = "Cria um agendamento para um pet. Campos obrigatórios: petId, clienteId, servicoId, preco e status."
    )
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.criar(dto));
    }

    @Operation(
            summary = "Listar agendamentos",
            description = "Retorna todos os agendamentos cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @Operation(
            summary = "Buscar agendamento por ID",
            description = "Retorna um agendamento pelo ID. Se não existir, retorna 404."
    )
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar agendamento",
            description = "Atualiza parcialmente um agendamento. Apenas campos preenchidos serão alterados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, dto));
    }

    @Operation(
            summary = "Deletar agendamento",
            description = "Remove um agendamento pelo ID. Se não existir, retorna 404."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.ok("Agendamento de id " + id + " deletado com sucesso.");
    }
}