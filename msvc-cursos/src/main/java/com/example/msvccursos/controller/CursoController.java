package com.example.msvccursos.controller;

import com.example.msvccursos.models.Usuario;
import com.example.msvccursos.models.entity.Curso;
import com.example.msvccursos.services.CursoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CursoController {


    @Autowired
    private CursoService service;


    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        Optional<Curso> curso = service.porIdConUsuario(id);
        return curso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        ResponseEntity<Map<String, String>> errors = ValidateErrors(result);
        if (errors != null) return errors;

        Curso cursoDB = service.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        ResponseEntity<Map<String, String>> errors = ValidateErrors(result);
        if (errors != null) return errors;

        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            System.out.println(usuario.toString());
            o = service.asignarUsuario(usuario, cursoId);
            if (o.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
            }
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.crearUsuario(usuario, cursoId);
            if (o.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
            }
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.eliminarUsuario(usuario, cursoId);
            if (o.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(o.get());
            }
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        service.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }


    private static ResponseEntity<Map<String, String>> ValidateErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        return null;
    }


}
