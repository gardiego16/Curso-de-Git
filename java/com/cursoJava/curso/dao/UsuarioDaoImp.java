package com.cursoJava.curso.dao;

import com.cursoJava.curso.modelos.UsuarioModelo;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<UsuarioModelo> getUsuarios() {
        String query="FROM UsuarioModelo";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        UsuarioModelo usuario= entityManager.find(UsuarioModelo.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(UsuarioModelo usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public UsuarioModelo verificarEmailPassword(UsuarioModelo usuario) {
        String query="FROM UsuarioModelo WHERE email=:email";
        List<UsuarioModelo> lista=entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        }
        String passwordHash=lista.get(0).getPassword();

        Argon2 argon2= Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHash, usuario.getPassword())){
            return lista.get(0);
        }
        return null;
    }
}
