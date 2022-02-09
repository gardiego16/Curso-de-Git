package com.cursoJava.curso.controladores;


import com.cursoJava.curso.dao.UsuarioDao;
import com.cursoJava.curso.modelos.UsuarioModelo;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControl {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private  JWTUtil jwtUtil;

    @RequestMapping(value="api/login", method = RequestMethod.POST)
    public String login(@RequestBody UsuarioModelo usuario){
        UsuarioModelo usuarioLogeado=usuarioDao.verificarEmailPassword(usuario);
        if (usuarioLogeado!=null){
            //Creacion del token
            return jwtUtil.create(String.valueOf(usuarioLogeado.getId()), usuarioLogeado.getEmail());
        }
        else{
            return "Fail";
        }

    }

}
