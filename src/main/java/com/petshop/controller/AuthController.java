package com.petshop.controller;

import com.petshop.dto.RegisterRequest;
import com.petshop.dto.RegisterResponse;
import com.petshop.entity.Usuario;
import com.petshop.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        Usuario usuario = usuarioService.registrarCliente(request);

        RegisterResponse response = new RegisterResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}