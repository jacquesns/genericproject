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
public class ProductAPITests {
    protected MockMvc mockMvc;
	public static final Logger logger = LoggerFactory.getLogger(ProductAPITests.class);

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    
    @Before
    public final void initMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
    @Test
	public void productsListAPIAvailable() throws Exception {
    	mockMvc.perform(
            	get("/api/products")
            ).andExpect(status().isNoContent());        
    }

    @Test
	public void shouldBeAbleToGetNewlyCreatedProduct() throws Exception {
    	String userJson ="{\"description\":\"test\",\"name\":\"tahir\",\"price\":99}";
		mockMvc.perform(
            	post("/api/products").content(userJson).contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());
    	mockMvc.perform(
            	get("/api/products/1")
            ).andExpect(status().isOk());        
    }
    
}
