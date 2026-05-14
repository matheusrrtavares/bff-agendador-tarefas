package com.matheus.bff_agendadortarefas.business;

import com.matheus.bff_agendadortarefas.business.dto.in.TarefaDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.out.TarefaDTOResponse;
import com.matheus.bff_agendadortarefas.infrastructure.client.TarefasClient;
import com.matheus.bff_agendadortarefas.infrastructure.enums.StatusNotificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasClient tarefasClient;

    public TarefaDTOResponse salvarTarefa(TarefaDTORequest dto, String token) {

        return tarefasClient.salvaTarefa(dto, token);
    }

    public List<TarefaDTORequest> buscaTarefasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token) {

        return tarefasClient.buscaTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefaDTOResponse> buscaTarefasPorEmail(String token) {
        return tarefasClient.buscaTarefasPorEmail(token);
    }

    public void deletaTarefaPorId(String id, String token) {
         tarefasClient.deletaTarefaPorId(id, token);
    }

    public TarefaDTOResponse alteraStatusTarefa(StatusNotificacao statusNotificacao, String id, String token) {

        return tarefasClient.atualizaStatusTarefa(statusNotificacao, id, token);

    }

    public TarefaDTOResponse updateTarefas(TarefaDTORequest dto, String id, String token) {

        return tarefasClient.updateTarefas(dto, id, token);

    }

}
