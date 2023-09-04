package com.example.msvccursos.clients;

import com.example.msvccursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/api/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/api/")
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/api/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
}
