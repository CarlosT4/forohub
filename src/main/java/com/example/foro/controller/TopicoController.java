package com.example.foro.controller;

import com.example.foro.domain.curso.CursoRepository;
import com.example.foro.domain.topico.*;
import com.example.foro.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    
    @Autowired
    private TopicoRepository topicoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrar(@RequestBody @Valid DatosRegistroTopico datos,
                                                          UriComponentsBuilder uriComponentsBuilder) {
        
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().build();
        }
        
        var autor = usuarioRepository.getReferenceById(datos.autorId());
        var curso = cursoRepository.getReferenceById(datos.cursoId());
        
        var topico = new Topico(datos, autor, curso);
        topicoRepository.save(topico);
        
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        
        return ResponseEntity.created(uri).body(new DatosRespuestaTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listar(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio) {
        
        Page<Topico> topicos;
        
        if (curso != null && anio != null) {
            topicos = topicoRepository.findByCursoNombreAndAnio(curso, anio, paginacion);
        } else {
            topicos = topicoRepository.findAllByOrderByFechaCreacionAsc(paginacion);
        }
        
        return ResponseEntity.ok(topicos.map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detallar(@PathVariable Long id) {
        var topico = topicoRepository.findById(id);
        
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizar(@PathVariable Long id,
                                                           @RequestBody @Valid DatosActualizarTopico datos) {
        
        var topicoOpt = topicoRepository.findById(id);
        
        if (topicoOpt.isPresent()) {
            var topico = topicoOpt.get();
            var curso = datos.cursoId() != null ? cursoRepository.getReferenceById(datos.cursoId()) : null;
            
            topico.actualizar(datos, curso);
            
            return ResponseEntity.ok(new DatosRespuestaTopico(topico));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        var topicoOpt = topicoRepository.findById(id);
        
        if (topicoOpt.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}