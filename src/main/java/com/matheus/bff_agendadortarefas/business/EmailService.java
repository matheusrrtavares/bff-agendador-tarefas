package com.matheus.bff_agendadortarefas.business;

import com.matheus.bff_agendadortarefas.business.dto.in.TarefaDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.out.TarefaDTOResponse;
import com.matheus.bff_agendadortarefas.infrastructure.client.NotificacaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final NotificacaoClient notificacaoClient;

    public void enviarEmail(TarefaDTOResponse dto){
        notificacaoClient.enviarEmail(dto);
    }

}
