package com.websystique.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.websystique.api.configuration.WebMystiqueRestMvcConfiguration;
import com.websystique.api.controllers.UserController;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {WebmystiqueApplication.class, WebMystiqueRestMvcConfiguration.class})
@Transactional
@WebAppConfiguration
public class OrderAPITests {
    protected MockMvc mockMvc;
	public static final Logger logger = LoggerFactory.getLogger(OrderAPITests.class);

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    
    @Before
    public final void initMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	

    @Test
	public void ordersListAPIAvailable() throws Exception {
    	mockMvc.perform(
            	get("/api/orders")
            ).andExpect(status().isNoContent());    			        
    }

    @Test
	public void shouldBeAbleToGetNewlyCreatedOrder() throws Exception {
    	String userJson ="{\"age\":33,\"name\":\"tahir\",\"salary\":445}";		
		MvcResult result = mockMvc.perform(
            	post("/api/users").content(userJson).contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated()).andReturn();
		String location = result.getResponse().getHeader("Location");
		logger.info("Got location " + location);
		String[] parts = location.split("/");
		String userId = parts[parts.length-1];
    	String orderJson ="{\"userId\":"+ userId +",\"productId\":\"1\",\"status\":\"PENDING\",\"totalValue\":99}";
		mockMvc.perform(
            	post("/api/orders").content(orderJson).contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());
    	MvcResult getOrderResult = mockMvc.perform(
            	get("/api/orders/1")
            ).andExpect(status().isOk()).andReturn();
    	logger.info("Got order result " + getOrderResult);
    }
    
    
}
