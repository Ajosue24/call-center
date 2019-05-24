package com.almundo.callcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {

    /**
     * Bean que ejecuta tareas en el grupo de subprocesos
     * se le asigna un nucleo inicial con tama;o de diez,
     * aca se le asignan el espacio en memoria que puede almacenar por tanto al ser diez
     * dejara en cola los las otras solicitudes ya que es la cantidad de data que se puede procesar
     * @return objeto tipo ThreadPoolTaskExecutor
     */
    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setThreadNamePrefix("threads for calls");
        executor.initialize();
        return executor;
    }
}