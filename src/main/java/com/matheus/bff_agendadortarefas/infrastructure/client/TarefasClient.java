package com.matheus.bff_agendadortarefas.infrastructure.client;

import com.matheus.bff_agendadortarefas.business.dto.in.TarefaDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.out.TarefaDTOResponse;
import com.matheus.bff_agendadortarefas.infrastructure.enums.StatusNotificacao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
public interface TarefasClient {

    @PostMapping
    TarefaDTOResponse salvaTarefa(@RequestBody TarefaDTORequest dto,
                                  @RequestHeader("Authorization") String token);

    @GetMapping("/eventos")
    List<TarefaDTOResponse> buscaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader("Authorization") String token);

    @GetMapping
    List<TarefaDTOResponse> buscaTarefasPorEmail(@RequestHeader("Authorization") String token);

    @DeleteMapping
    Void deletaTarefaPorId(@RequestParam("id") String id,
                           @RequestHeader("Authorization") String token);

    @PatchMapping
    TarefaDTOResponse atualizaStatusTarefa(@RequestParam("status") StatusNotificacao status,
                                          @RequestParam("id") String id,
                                          @RequestHeader("Authorization") String token);

    @PutMapping
    TarefaDTOResponse updateTarefas(@RequestBody TarefaDTORequest dto,
                                   @RequestParam("id") String id,
                                   @RequestHeader("Authorization") String token);

}
