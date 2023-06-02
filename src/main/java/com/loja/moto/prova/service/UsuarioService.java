package com.loja.moto.prova.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.loja.moto.prova.model.Usuario;
import com.loja.moto.prova.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
    UsuarioRepository repository;
    BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder encoder){
        this.repository = repository;
    }

    public void save(Usuario u){
        u.setSenha(encoder.encode(u.getSenha()));
        this.repository.save(u);
    }

    public void delete(Integer id){
        this.repository.deleteById(id);
    }

    public Usuario findById(Integer id){
        Optional<Usuario> u = this.repository.findById(id);

        if(u.isPresent()){
            return u.get();
        }else{
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }

    public List<Usuario> findAll(){
        List<Usuario> u = this.repository.findAll();

        return u;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> u = this.repository.findUsuarioByUsername(username);
        if(u.isPresent())
            return u.get();
        else
            throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
}
