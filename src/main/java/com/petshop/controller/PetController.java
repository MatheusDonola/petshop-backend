package com.petshop.controller;

import com.petshop.entity.Pet;
import com.petshop.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> listar() {
        return petService.listar();
    }

    @GetMapping("/{id}")
    public Pet buscarPorId(@PathVariable Long id) {
        return petService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet criar(@RequestBody Pet pet) {
        return petService.criar(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Pet pet) {
        petService.atualizar(id, pet);
        return ResponseEntity.ok("Pet de id " + id + " alterado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.ok("Pet de id " + id + " deletado com sucesso.");
    }
}