package com.almundo.callcenter.component;


import com.almundo.callcenter.entity.Users;
import com.almundo.callcenter.service.GenerateDataFaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * Clase que administra las llamadas
 */
@Component
@Scope("prototype")
public class Dispatcher {


    /**
     * Atributo que nos permite acceder a los usuarios y roles ya predefinidos
     */
    @Autowired
    private GenerateDataFaker generateDataFaker;
    /**
     * Atributo tipo Queue para tener las colas de las llamadas en curso
     */
    private Queue<Users> progressCall = new PriorityBlockingQueue<>();
    /**
     * Atributo tipo lista que almacena Lista de usuarios del call-center
     */
    private List<Users> usersList = new ArrayList<>();

    /**
     * Log para trazas y debugs
     */
    private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);

    /**
     * Usuario temp
     */
    private Users user;


    /**
     * Metodo que valida los operadores disponibles
     * @return un usario disponible u null cuando todos estan ocupados
     * @throws InterruptedException devolver la excepcion al usuario
     */
    private Users validateOperatorsAvailable(){
        LOG.debug("inicio metodo validateOperatorsAvailable");

        try {
            usersList = generateDataFaker.getEmployees("OPERADOR");

            usersList.removeAll(progressCall);

            if(usersList.isEmpty()){
                //Usuarios de segundo nivel
                usersList = generateDataFaker.getEmployees("SUPERVISOR");
                usersList.removeAll(progressCall);
                if(usersList.isEmpty()){
                    //Usuarios de 3er nivel
                    usersList = generateDataFaker.getEmployees("DIRECTOR");
                    usersList.removeAll(progressCall);
                    if(usersList.isEmpty()){
                        LOG.debug("Fin metodo validateOperatorsAvailable");
                        return null;
                    }else {
                        Users user = getUserRandom();
                        progressCall.add(user);
                        LOG.debug("Fin metodo validateOperatorsAvailable");
                        return user;

                    }
                }else{
                    Users user = getUserRandom();
                    progressCall.add(user);
                    LOG.debug("Fin metodo validateOperatorsAvailable");
                    return user;
                }

            }else{
                Users user = getUserRandom();
                progressCall.add(user);
                LOG.debug("Fin metodo validateOperatorsAvailable");
                return user;
            }

        }catch (Exception e){
            LOG.error("error al buscar operador");
            return null;
        }

    }
    public Users getUserRandom(){
        do{
            if(usersList.isEmpty()){return null;}
            user = usersList.get(new Random().ints(0, usersList.size()).findFirst().getAsInt());
        }while (progressCall.stream().anyMatch(n -> n.getId()==user.getId()));
        progressCall.add(user);
        return user;

    }

    /**
     * Metodo que asigna a las llamadas los usuarios disponibles
     * El metodos es invocado simultaneamente desde varios hilos al mismo tiempo
     *
     */

    public void dispatchCall(){
        LOG.debug("inicio metodo dispatchCall");
        try {
            //validamos que haya usuarios disponibles
            Users users = validateOperatorsAvailable();
            //numero aleatorio entre 5 y 10
            Integer timeCall = new Random().ints(5, 11).findFirst().getAsInt();
            if(users!=null){
                //Duracion de la llamada un tiempo aleatorio entre 5 y 10 segundos
                Thread.sleep(1000 * timeCall);
                LOG.info("usuario " + users.getName() + " " + users.getLastName() + " " +users.getRoleId().getName());
                LOG.info(" duracion llamada "+timeCall);
                progressCall.remove(users);
                LOG.info("usuario " + users.getName() + " " + users.getLastName() + " " +users.getRoleId().getName()+" ya se encuentra disponible");
            }else{
                //no hay operadores disponibles lo dejamos en espera
                Thread.sleep(5 * 1000);
                LOG.info("llamada se deja en espera hasta que un usuario se encuentre disponible");
                dispatchCall();
            }

        } catch (InterruptedException e) {
            LOG.error("Error en el sistema");

        }
        LOG.debug("fin metodo dispatchCall");
    }


}
