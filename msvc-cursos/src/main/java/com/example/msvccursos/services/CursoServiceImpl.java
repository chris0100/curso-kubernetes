package com.example.msvccursos.services;

import com.example.msvccursos.clients.UsuarioClientRest;
import com.example.msvccursos.models.Usuario;
import com.example.msvccursos.models.entity.Curso;
import com.example.msvccursos.models.entity.CursoUsuario;
import com.example.msvccursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {


    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest client;


    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            System.out.println(o.toString());
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = CursoUsuario.builder()
                    .usuarioId(usuarioMsvc.getId())
                    .build();

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioNuevoMsvc = client.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = CursoUsuario.builder()
                    .usuarioId(usuarioNuevoMsvc.getId())
                    .build();

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = CursoUsuario.builder()
                    .usuarioId(usuarioMsvc.getId())
                    .build();

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Curso> porIdConUsuario(Long id) {
        Optional<Curso> o = repository.findById(id);
        if (o.isPresent()){
            Curso curso = o.get();
            if (!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList();
                List<Usuario> usuarios = client.obtenerAlumnosPorCurso(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuario(id);
    }
}
