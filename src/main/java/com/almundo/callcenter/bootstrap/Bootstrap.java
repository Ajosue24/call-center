package com.almundo.callcenter.bootstrap;

import com.almundo.callcenter.service.GenerateDataFaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap implements InitializingBean {


    /**
     * Logs del sistema
     */
    private final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    GenerateDataFaker generateDataFaker;


    /**
     * Metodo que al iniciar el servidor creara instancias de usuarios del sistema
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        LOG.debug("inicio metodo afterPropertiesSet");
        generateDataFaker.seedRoles();
        generateDataFaker.seedUsers();
        generateDataFaker.getRolesList();
        generateDataFaker.getEmployees();
        LOG.debug("fin metodo afterPropertiesSet");
    }


}
