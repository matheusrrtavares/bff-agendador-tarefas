package com.matheus.bff_agendadortarefas.controller;

import com.matheus.bff_agendadortarefas.business.TarefaService;
import com.matheus.bff_agendadortarefas.business.dto.in.TarefaDTORequest;
import com.matheus.bff_agendadortarefas.business.dto.out.TarefaDTOResponse;
import com.matheus.bff_agendadortarefas.infrastructure.enums.StatusNotificacao;
import com.matheus.bff_agendadortarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuário")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefaService service;

    @PostMapping
    @Operation(summary = "Salva Tarefa", description = "Salva uma nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "500 ", description = "Erro de servidor")
    public ResponseEntity<TarefaDTOResponse> salvaTarefa(@RequestBody TarefaDTORequest dto,
                                                         @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(service.salvarTarefa(dto, token));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca Tarefas", description = "Busca tarefas cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso")
    @ApiResponse(responseCode = "500 ", description = "Erro de servidor")
    public ResponseEntity<List<TarefaDTORequest>> buscaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(service.buscaTarefasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Busca Lista de Tarefas",
            description = "Busca lista de tarefas por email de usuário")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso")
    @ApiResponse(responseCode = "500 ", description = "Erro de servidor")
    public ResponseEntity<List<TarefaDTOResponse>> buscaTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(service.buscaTarefasPorEmail(token));

    }

    @DeleteMapping
    @Operation(summary = "Delete Tarefas por Id",
            description = "Deleta tarefas cadastradas por Id")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso")
    @ApiResponse(responseCode = "500 ", description = "Erro de servidor")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader(name = "Authorization", required = false) String token) {
        service.deletaTarefaPorId(id, token);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera status da tarefa",
            description = "Altera status de tarefa cadastrada")
    @ApiResponse(responseCode = "200", description = "Status de tarefa alterado com sucesso")
    @ApiResponse(responseCode = "500 ", description = "Erro de servidor")
    public ResponseEntity<TarefaDTOResponse> atualizaStatusTarefa(@RequestParam("status") StatusNotificacao status,
                                                                 @RequestParam("id") String id,
                                                                 @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(service.alteraStatusTarefa(status, id, token));
    }

    @PutMapping
    @Operation(summary = "Altera dados de tarefa",
            description = "Altera dados de tarefa cadastrada")
    @ApiResponse(responseCode = "200", description = "Tarefa alterada com sucesso")
    @ApiResponse(responseCode = "500 ", description = "Erro de servidor")
    public ResponseEntity<TarefaDTOResponse> updateTarefas(@RequestBody TarefaDTORequest dto,
                                                          @RequestParam("id") String id,
                                                          @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(service.updateTarefas(dto, id, token));
    }

}
