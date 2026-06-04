package com.matheus.bff_agendadortarefas.business;

import com.matheus.bff_agendadortarefas.business.dto.in.LoginRequestDTO;
import com.matheus.bff_agendadortarefas.business.dto.out.TarefaDTOResponse;
import com.matheus.bff_agendadortarefas.infrastructure.enums.StatusNotificacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {

    private final TarefaService tarefaService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {
        String token = login(converterParaLoginRequestDTO());
        log.info("Iniciado a busca por Tarefas");
        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);

        List<TarefaDTOResponse> listaTarefas = tarefaService.buscaTarefasPorPeriodo(horaAtual, horaFutura, token);
        log.info("Tarefas encontradas: " + listaTarefas);

        listaTarefas.forEach(tarefa -> {
            emailService.enviarEmail(tarefa);
            log.info("Email enviado");
            tarefaService.alteraStatusTarefa(StatusNotificacao.NOTIFICADO, tarefa.getId(), token);
        });
        log.info("Finalizado a busca e notificação de tarefas");

    }

    public String login(LoginRequestDTO dto) {
        return usuarioService.loginUsuario(dto);
    }

    public LoginRequestDTO converterParaLoginRequestDTO() {
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }

}
