package com.almundo.callcenter.service;



import com.almundo.callcenter.entity.Roles;
import com.almundo.callcenter.entity.Users;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 Clase faker que me genera empleados para el call-center asi lograr visuarlizar de una mejor manera Cantidad de empleados

 */
@Service
public class GenerateDataFaker {

    @Getter
    @Setter
    /**
     * Atributo que almacena roles predefinidos por el negocio
     */
    private List<Roles> rolesList = new ArrayList<>();

    @Getter
    @Setter
    /**
     * Atributo que contiene empleados activos durante la jornada laboral
     */
    private List<Users> employees = new ArrayList<>();

    /**
     * Log para trazas y debugs
     */
    private static final Logger LOG = LoggerFactory.getLogger(GenerateDataFaker.class);


    /**
     * Metodo semilla que genera roles
     */
    public void seedRoles(){
        LOG.debug("inicio metodo seedRoles");
        this.rolesList.add(new Roles(1, "OPERADOR", "usuario de primer nivel"));
        this.rolesList.add(new Roles(2, "SUPERVISOR", "usuario de segundo nivel"));
        this.rolesList.add(new Roles(3, "DIRECTOR", "usuario de tercer nivel"));
        LOG.debug("fin metodo seedRoles");
    }

    /**
     * Metodo semilla que almacena usuarios laborando actualmente
     */
    public void seedUsers() {
        LOG.debug("inicio metodo seedUsers");
        int cantOperator = 6;
        int cantSupervisor = 2;
        int cantDirector = 2;

        for (int i = 0; i < this.rolesList.size(); i++) {
            switch (i) {
                case 0:
                    employees.addAll(generateRamdonUsers(cantOperator, i));
                    break;
                case 1:
                    employees.addAll(generateRamdonUsers(cantSupervisor, i));
                    break;
                case 2:
                    employees.addAll(generateRamdonUsers(cantDirector, i));
                    break;
            }
        }
        LOG.debug("Fin metodo seedUsers");
    }

    /**
     * Con el uso de una libreria faker se generan usuarios ramdon para su uso dentro del call-center
     * @param numbers cantidad de usuarios para un determinado rol
     * @param role    rol del negocio al cual pertenece nuestro usuario
     * @return
     */
    private List<Users> generateRamdonUsers(int numbers, int role) {
        LOG.debug("Inicio metodo generateRamdonUsers");
        List<Users> users = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            Faker faker = new Faker();
            users.add(new Users(i + 1, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), rolesList.get(role)));
        }
        LOG.debug("Fin metodo generateRamdonUsers");
        return users;
    }


    /**
     * Obtiene los usuarios empleados conectados o laborando actualmente en el call-center
     * @param name obtiene el nombre de rol
     * @return una lista de usuarios activos para un rol determinado
     */
    public List<Users> getEmployees(String name){
        return new ArrayList<>(employees.stream()
                .filter(x -> x.getRoleId().getName().equalsIgnoreCase(name)).collect(Collectors.toList()));
    }


}