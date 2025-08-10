package com.example.foro.domain.topico;

public record DatosActualizarTopico(
    String titulo,
    String mensaje,
    Long cursoId
) {}