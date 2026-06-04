package com.matheus.bff_agendadortarefas.infrastructure.client;

import com.matheus.bff_agendadortarefas.business.dto.in.TarefaDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.out.TarefaDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface NotificacaoClient {

    @PostMapping
    void enviarEmail(@RequestBody TarefaDTOResponse dto);

}
