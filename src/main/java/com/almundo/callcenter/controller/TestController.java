package com.almundo.callcenter.controller;


import com.almundo.callcenter.component.Dispatcher;
import com.almundo.callcenter.response.Response;
import com.almundo.callcenter.service.AsynchronousService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Controller para experiencia de usuario
 */
@RestController
@RequestMapping(value = "/demo", produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
public class TestController {

    /**
     * instancia de metodo que llamara a nuestro servicio asincrono
     */
    @Autowired
    private AsynchronousService asynchronousService;


    /**
     * Log para trazas y debugs
     */
    private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);


    /**
     * Metodo que dispara diez llamadas simultaneamente
     *
     * @return OK
     */
    @GetMapping
    public Response executeAsync() {
        LOG.debug("inicio metodo executeAsync");
        for (int i = 0; i < 10; i++) {
            asynchronousService.executeAsynchronously();
        }
        LOG.debug("fin metodo executeAsync");
        return new Response("OK con 10 llamadas",Boolean.TRUE) ;
    }

    /**
     * Metodo que ejecuta las llamadas solicitadas
     *
     * @param id cantidad de llamadas a ingresar
     * @return OK
     */
    @GetMapping(value = "/{id}")
    public Response executeAsyncMultipleId(@PathVariable("id") Integer id) {
        LOG.debug("inicio metodo executeAsyncMultipleId");
        for (int i = 0; i < id; i++) {
            Integer p = new Random().ints(5, 11).findFirst().getAsInt();
            asynchronousService.executeAsynchronously();
        }

        LOG.debug("fin metodo executeAsyncMultipleId");
        return new Response("OK Whith " + id + " calls",Boolean.TRUE);
    }
}
