package com.matheus.bff_agendadortarefas.business;


import com.matheus.bff_agendadortarefas.business.dto.out.ViaCepDTOResponse;
import com.matheus.bff_agendadortarefas.infrastructure.client.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    public final ViaCepClient viaCepClient;

    public ViaCepDTOResponse buscaDadosEndereco(String cep){

        return viaCepClient.buscaDadosEndereco(processarCep(cep));
    }

    private String processarCep(String cep){

        if(cep == null){
            throw new IllegalArgumentException("O CEP não pode ser nulo");
        }

        String cepFormatado = cep.replace(" ", "").replace("-", "");

        if(!cepFormatado.matches("\\d{8}")){
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 dígitos");
        };

        return cepFormatado;

    }

}
