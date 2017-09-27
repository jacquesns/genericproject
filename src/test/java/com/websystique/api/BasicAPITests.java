package com.websystique.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.websystique.api.configuration.WebMystiqueRestMvcConfiguration;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {WebmystiqueApplication.class, WebMystiqueRestMvcConfiguration.class})
@Transactional
@WebAppConfiguration
public class BasicAPITests {
    protected MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    
    @Before
    public final void initMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
    @Test
	public void usersListAPIAvailable() throws Exception {
    	mockMvc.perform(
            	get("/api/users")
            ).andExpect(status().isOk())
    			.andExpect(content().contentType("application/hal+json;charset=UTF-8"));        
    }

    @Test
	public void ordersListAPIAvailable() throws Exception {
    	mockMvc.perform(
            	get("/api/orders")
            ).andExpect(status().isOk())
    			.andExpect(content().contentType("application/hal+json;charset=UTF-8"));        
    }

    @Test
	public void productsListAPIAvailable() throws Exception {
    	mockMvc.perform(
            	get("/api/products")
            ).andExpect(status().isOk())
    			.andExpect(content().contentType("application/hal+json;charset=UTF-8"));        
    }

    @Test
	public void addAndGetUser() throws Exception {
    	String userJson ="{\"age\":33,\"name\":\"tahir\",\"salary\":445}";
		mockMvc.perform(
            	post("/api/users").content(userJson)
            ).andExpect(status().isCreated());
    	mockMvc.perform(
            	get("/api/users/1")
            ).andExpect(status().isOk())
    			.andExpect(content().contentType("application/hal+json;charset=UTF-8"));        

    }

    @Test
	public void addAndGetProduct() throws Exception {
    	String userJson ="{\"description\":\"test\",\"name\":\"tahir\",\"price\":99}";
		mockMvc.perform(
            	post("/api/products").content(userJson)
            ).andExpect(status().isCreated());
    	mockMvc.perform(
            	get("/api/products/1")
            ).andExpect(status().isOk())
    			.andExpect(content().contentType("application/hal+json;charset=UTF-8"));        

    }

}
