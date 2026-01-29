package com.allanfelipe.aprendendospring.business;

import com.allanfelipe.aprendendospring.infrastructure.entity.Usuario;
import com.allanfelipe.aprendendospring.infrastructure.excptions.ConflictException;
import com.allanfelipe.aprendendospring.infrastructure.excptions.ResourceNotFoundException;
import com.allanfelipe.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvaUsuario(Usuario usuario){
        try{
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        }catch (ConflictException e){
            throw new ConflictException("Email ja cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void emailExiste(String email){
        try{
            boolean exite = verificaEmailExistente(email);
            if(exite){
                throw new ConflictException("Email ja exitente: " + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email ja cadastrado:" + e.getCause());
        }
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com o email: " + email
                ));
    }
    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
