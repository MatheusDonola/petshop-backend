package com.petshop.controller;

import com.petshop.dto.ClienteRequestDTO;
import com.petshop.dto.ClienteResponseDTO;
import com.petshop.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
            summary = "Criar cliente",
            description = "Cria um novo cliente. Campos obrigatórios: nome, telefone e email."
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO criado = clienteService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
            summary = "Listar clientes",
            description = "Retorna todos os clientes cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna um cliente pelo ID. Se não existir retorna 404."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza parcialmente um cliente. Apenas campos preenchidos são alterados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.atualizar(id, dto));
    }

    @Operation(
            summary = "Deletar cliente",
            description = "Remove um cliente pelo ID. Se não existir, retorna 404."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.ok("Cliente de id " + id + " deletado com sucesso.");
    }
}
