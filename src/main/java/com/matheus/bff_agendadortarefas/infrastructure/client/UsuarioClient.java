package com.matheus.bff_agendadortarefas.infrastructure.client;

import com.matheus.bff_agendadortarefas.business.dto.in.EnderecoDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.in.LoginRequestDTO;
import com.matheus.bff_agendadortarefas.business.dto.in.TelefoneDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.in.UsuarioDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.matheus.bff_agendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.matheus.bff_agendadortarefas.business.dto.out.UsuarioDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping
    UsuarioDTOResponse buscaUsuarioPorEmail(@RequestParam("email") String email,
                                            @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTOResponse salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO);

    @PostMapping("/login")
    String login(@RequestBody LoginRequestDTO usuarioDTO);

    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization") String token);

    @PutMapping
    UsuarioDTOResponse atualizaDadosUsuario(@RequestBody UsuarioDTORequest dto,
                                            @RequestHeader("Authorization") String token);

    @PutMapping("/endereco")
    EnderecoDTOResponse atualizaEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization") String token);

    @PutMapping("/telefone")
    TelefoneDTOResponse atualizaTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization") String token);

    @PostMapping("/endereco")
    EnderecoDTOResponse cadastraEnderecoUsuario(@RequestBody EnderecoDTORequest enderecoDTO,
                                        @RequestHeader("Authorization") String token);

    @PostMapping("/telefone")
    TelefoneDTOResponse cadastraTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                         @RequestHeader("Authorization") String token);

}
