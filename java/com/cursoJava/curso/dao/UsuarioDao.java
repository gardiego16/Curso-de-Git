package com.cursoJava.curso.dao;

import com.cursoJava.curso.modelos.UsuarioModelo;

import java.util.List;

public interface UsuarioDao {
    List<UsuarioModelo> getUsuarios();

    void eliminar(Long id);

    void registrar(UsuarioModelo usuario);

    UsuarioModelo verificarEmailPassword(UsuarioModelo usuario);
}
