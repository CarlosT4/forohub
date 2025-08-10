package com.example.foro.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    Status status,
    String autor,
    String curso,
    String categoria
) {
    public DatosDetalleTopico(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre(),
            topico.getCurso().getCategoria()
        );
    }
}