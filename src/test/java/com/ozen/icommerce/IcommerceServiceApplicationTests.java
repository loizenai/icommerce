package com.ozen.icommerce;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ozen.icommerce.dto.card.CardItem;
import com.ozen.icommerce.dto.pagination.Pagination;
import com.ozen.icommerce.dto.request.filter.FilterRequest;
import com.ozen.icommerce.dto.request.login.SignUp;
import com.ozen.icommerce.enums.Color;
import com.ozen.icommerce.enums.RoleName;
import com.ozen.icommerce.security.jwt.JwtProvider;
import com.ozen.icommerce.service.init.InitialService;
import com.ozen.icommerce.service.signup.SignUpService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class IcommerceServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

	@Autowired
	private JwtProvider jwtProvider;
    
    @Autowired
    private InitialService initialService;
    
	@Autowired
	SignUpService signUpService;
	
	private static boolean setUpIsDone = false;
    
    @BeforeEach
    void setup(){
    	doSetup();
    }
	
    @Test
    @WithMockUser(username="jane_johnson",roles={"USER"})
    void testcase_CardItems() throws Exception{
    	do_Testcase_CardItem();
    }
    
    @Test
    void testcase_Filter() throws Exception{
    	doTestcase_Filter();
    }
    
    private void doSetup() {
    	if (setUpIsDone) {
    		return;
    	}
    	
    	// 1.0 Sign-Up User
    	final var user = SignUp.builder()
    						.email("jane_johnson@icommerce.com")
    						.username("jane_johnson")
    						.firstname("Jane")
    						.lastname("Johnson")
    						.role(Set.of(RoleName.USER.value()))
    						.password("123")
    						.build();
    	
    	signUpService.signUp(user);
    	
    	// 1.1 Sign-Up Admin
    	final var admin = SignUp.builder()
				.email("admin@icommerce.com")
				.username("jack_smith")
				.firstname("Jack")
				.lastname("Smith")
				.role(Set.of(RoleName.ADMIN.value()))
				.password("123")
				.build();

    	signUpService.signUp(admin);
    	
    	// 2. Initial Data
    	initialService.init();
    	
    	setUpIsDone = true;
    }
    
    private void do_Testcase_CardItem() throws Exception{
    	// 1. Add New Card Item
    	final var cardItem_001 = CardItem.builder()
    		.productId(Long.valueOf(1))
    		.quantity(10)
    		.color(Color.WHITE)
    		.build();
    	
    	final var token = jwtProvider.generateJwtToken("jane_johnson");
    	
        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/card/add")
                		.header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cardItem_001)));
        
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
        
        // 1.1 >>> Add More Item
    	final var cardItem_002 = CardItem.builder()
    		.productId(Long.valueOf(2))
    		.quantity(33)
    		.color(Color.BLUE)
    		.build();
    	
        response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/card/add")
                		.header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cardItem_002)));
        
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
        
        // 2. >>> Retrieve All Adding Card Items
        response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/card/all")
                		.header("Authorization", "Bearer " + token));
        
        response.andExpect(status().isOk())
        			.andDo(print());
        
        var content = response.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        JSONArray data = jsonObject.getJSONArray("data");
        
        assertEquals(data.length(), 2);
        
        // 3. >>> Modify and retrieve an Card Items
    	final var modify_cardItem_002 = CardItem.builder()
        		.id(Long.valueOf(2))
        		.productId(Long.valueOf(2))
        		.quantity(22)
        		.color(Color.RED)
        		.build();
    	
    	response = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/card/modify")
                		.header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(modify_cardItem_002)));
        
        response.andExpect(status().isOk())
			.andDo(print());
        
        // 4. >>> Retrieve an Items after Modified
        response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/card/retrieve/2")
                		.header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(modify_cardItem_002)));
        
        response.andExpect(status().isOk())
			.andDo(print());

        // >>> Check Modify Data
        content = response.andReturn().getResponse().getContentAsString();
        log.info("Content = {}", content);
        
        jsonObject = new JSONObject(content);
        var cardItemData = jsonObject.getJSONObject("data");

        Gson gson = new Gson();
        CardItem cardItem = gson.fromJson(cardItemData.toString(), CardItem.class);
        
        System.out.println(cardItem);
        
        assertEquals(cardItem.getQuantity().intValue(), 22);
        assertEquals(cardItem.getColor().value(), Color.RED.value());
        
        // 5. >>> Delete Card Item By ID
        response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/card/remove/2")
                		.header("Authorization", "Bearer " + token));
        
        response.andExpect(status().isOk())
			.andDo(print());

        // 5.1 >>> Try again-retrieve Deleted-Card
        response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/card/retrieve/2")
                		.header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(modify_cardItem_002)));
        
        response.andExpect(status().isNotFound())
			.andDo(print());
        
        // 6 >>> Delete All
        response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/card/remove/all")
                		.header("Authorization", "Bearer " + token));
        
        response.andExpect(status().isOk())
			.andDo(print());
        
        // 6.1 >>> Retrieve All After Deleted All
        response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/card/all")
                		.header("Authorization", "Bearer " + token));
        
        response.andExpect(status().isOk())
        			.andDo(print());
        
        content = response.andReturn().getResponse().getContentAsString();
        jsonObject = new JSONObject(content);
        data = jsonObject.getJSONArray("data");
        
        assertEquals(data.length(), 0);
    }
    
    private void doTestcase_Filter() throws Exception {
    	// 1. Build Filter Request with Ginger & Smart brand
    	var filter = FilterRequest.builder()
    		.brand("Ginger & Smart")
    		.category("JEANS")
    		.color(Color.RED.value())
    		.priceMin(Double.valueOf(0))
    		.priceMax(Double.valueOf(1000))
    		.page(Pagination.builder()
    					.size(10)
    					.page(0)
    					.build())
    		.build();
    	
        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filter)));
        
        // >>> Then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
        
        // >>> Verify Body of Response
        var content = response.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        JSONObject data = jsonObject.getJSONObject("data");
        assertEquals(data.getInt("total_items"), 1);
        
        
    	// 2. Build Filter Request - with FORWARD brand
    	filter = FilterRequest.builder()
    		.brand("FORWARD")
    		.page(Pagination.builder()
    					.size(10)
    					.page(0)
    					.build())
    		.build();
    	
        response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filter)));
        
        // >>> Then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
        
        // >>> Verify Body of Response
        content = response.andReturn().getResponse().getContentAsString();
        jsonObject = new JSONObject(content);
        data = jsonObject.getJSONObject("data");
        assertEquals(data.getInt("total_items"), 2);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
