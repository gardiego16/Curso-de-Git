package com.cursoJava.curso.controladores;

import com.cursoJava.curso.dao.UsuarioDao;
import com.cursoJava.curso.modelos.UsuarioModelo;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioControl {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="api/usuario", method = RequestMethod.GET)
    public List<UsuarioModelo> getUsuario(@RequestHeader(value="Authorization") String token){

        if (!validarToken(token)){
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioId=jwtUtil.getKey(token);
        return usuarioId!=null;
    }

    @RequestMapping(value="api/usuario/{id}", method = RequestMethod.DELETE)
    public void eliminarUsuario(@PathVariable Long id, @RequestHeader(value="Authorization") String token){
        if (validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value="api/usuario", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody UsuarioModelo usuario){
        Argon2 argon2= Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        /*
        argon2.hash(iteraciones,memoria usada, hilos usados, texto a hashear)
         */
        String hash=argon2.hash(1,1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }
}
