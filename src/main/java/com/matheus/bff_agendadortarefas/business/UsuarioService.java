package com.matheus.bff_agendadortarefas.business;

import com.matheus.bff_agendadortarefas.business.dto.in.EnderecoDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.in.LoginRequest;
import com.matheus.bff_agendadortarefas.business.dto.in.TelefoneDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.in.UsuarioDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.matheus.bff_agendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.matheus.bff_agendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.matheus.bff_agendadortarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient usuarioClient;


    public UsuarioDTOResponse salvaUsuario(UsuarioDTORequest usuarioDTO) {
        return usuarioClient.salvaUsuario(usuarioDTO);
    }

    public String loginUsuario(LoginRequest dto) {
        return usuarioClient.login(dto);
    }


    public UsuarioDTOResponse buscaUsuarioPorEmail(String email, String token) {
        return usuarioClient.buscaUsuarioPorEmail(email, token);
    }

    public void deletaUsuarioPorEmail(String email, String token) {
        usuarioClient.deletaUsuarioPorEmail(email, token);
    }

    public UsuarioDTOResponse atualizaDadosUsuario(String token, UsuarioDTORequest dto) {
        return usuarioClient.atualizaDadosUsuario(dto, token);
    }

    public EnderecoDTOResponse atualizaEndereco(Long enderecoId, EnderecoDTORequest enderecoDTO, String token) {

        return usuarioClient.atualizaEndereco(enderecoDTO, enderecoId, token);

    }

    public TelefoneDTOResponse atualizaTelefone(Long telefoneId, TelefoneDTORequest telefoneDTO, String token) {

        return usuarioClient.atualizaTelefone(telefoneDTO, telefoneId, token);

    }

    public EnderecoDTOResponse cadastraEndereco(String token, EnderecoDTORequest dto) {
        return usuarioClient.cadastraEnderecoUsuario(dto, token);

    }

    public TelefoneDTOResponse cadastraTelefone(String token, TelefoneDTORequest dto) {
        return usuarioClient.cadastraTelefone(dto, token);
    }

}
