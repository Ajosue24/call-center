package com.almundo.callcenter.service;

import com.almundo.callcenter.component.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsynchronousService {


    /**
     * Atributo responsable que se ejecuten las tareas
     */
    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * Clase que gestiona las llamadas
     */
    @Autowired
    Dispatcher dispatcher;


    /**
     * Metodo que ejecutara de manera asincrona las llamadas
     */
    public void executeAsynchronously() {
        taskExecutor.execute(() -> {
            dispatcher.dispatchCall();
        });
    }
}
