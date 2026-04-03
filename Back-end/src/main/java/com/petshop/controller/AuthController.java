package com.petshop.controller;

import com.petshop.dto.LoginRequest;
import com.petshop.dto.LoginResponse;
import com.petshop.dto.RegisterRequest;
import com.petshop.dto.RegisterResponse;
import com.petshop.entity.Usuario;
import com.petshop.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.petshop.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getSenha()
                    );

            authenticationManager.authenticate(authToken);

            UserDetails userDetails = usuarioService.buscarPorEmail(request.getEmail());
            String jwtToken = jwtService.gerarToken(userDetails);

            LoginResponse response = new LoginResponse(
                    "Login realizado com sucesso",
                    request.getEmail(),
                    jwtToken
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos.");
        }
    }
}