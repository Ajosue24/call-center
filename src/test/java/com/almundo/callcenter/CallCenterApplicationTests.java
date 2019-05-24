package com.almundo.callcenter;

import com.almundo.callcenter.component.Dispatcher;
import com.almundo.callcenter.component.DispatcherTest;
import com.almundo.callcenter.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallCenterApplicationTests{

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    public void contextLoads() throws Exception {
        MvcResult result = mockMvc.perform(get("/demo").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(resultContent, Response.class);
        Assert.assertTrue(response.isStatus() == Boolean.TRUE);


    }


    /**
     * Atributo responsable que se ejecuten las tareas
     */
    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * Clase que gestiona las llamadas
     */
    @Autowired
    DispatcherTest dispatcher;


    @Test
    public void executeAsynchronously() {
        for (int i = 0; i <11 ; i++) {
            taskExecutor.execute(() -> {
                dispatcher.dispatchCall();

            });
        }
    }

}


