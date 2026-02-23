package com.petshop.controller;

import com.petshop.entity.Cliente;
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
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        Cliente criado = clienteService.criar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
            summary = "Listar clientes",
            description = "Retorna todos os clientes cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna um cliente pelo ID. Se não existir retorna 404."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza parcialmente um cliente. Apenas campos preenchidos são alterados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        clienteService.atualizar(id, cliente);
        return ResponseEntity.ok("Cliente de id " + id + " alterado com sucesso.");
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
