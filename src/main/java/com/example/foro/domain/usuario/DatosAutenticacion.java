package com.example.foro.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacion(
    @NotBlank
    String correoElectronico,
    
    @NotBlank
    String contrasena
) {}