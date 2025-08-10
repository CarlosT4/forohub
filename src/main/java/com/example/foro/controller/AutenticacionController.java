package com.example.foro.controller;

import com.example.foro.domain.usuario.DatosAutenticacion;
import com.example.foro.domain.usuario.Usuario;
import com.example.foro.infra.security.DatosJWTToken;
import com.example.foro.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWTToken> autenticar(@RequestBody @Valid DatosAutenticacion datos) {
        var authToken = new UsernamePasswordAuthenticationToken(datos.correoElectronico(), datos.contrasena());
        var authentication = authenticationManager.authenticate(authToken);
        
        var token = tokenService.generarToken((Usuario) authentication.getPrincipal());
        
        return ResponseEntity.ok(new DatosJWTToken(token));
    }
}