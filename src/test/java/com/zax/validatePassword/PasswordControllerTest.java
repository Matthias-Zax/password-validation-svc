package com.zax.validatePassword;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidPassword() throws Exception {
        MvcResult result = mockMvc.perform(get("/checkpassword?password=Valid@!123Paassword1aA"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"valid\"}", content);
    }

    @Test
    public void testInvalidPasswordShortLength() throws Exception {
        MvcResult result = mockMvc.perform(get("/checkpassword?password=Short1!"))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"invalid\"}", content);
    }

    @Test
    public void testInvalidPasswordNoUppercase() throws Exception {
        MvcResult result = mockMvc.perform(get("/checkpassword?password=valid@123paaassword"))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"invalid\"}", content);
    }

    @Test
    public void testInvalidPasswordNoLowercase() throws Exception {
        MvcResult result = mockMvc.perform(get("/checkpassword?password=VALID@123PaaASSWORD"))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"invalid\"}", content);
    }

    @Test
    public void testInvalidPasswordNoDigit() throws Exception {
        MvcResult result = mockMvc.perform(get("/checkpassword?password=Valid@Paaassword"))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"invalid\"}", content);
    }

    @Test
    public void testInvalidPasswordNoSpecialChar() throws Exception {
        MvcResult result = mockMvc.perform(get("/checkpassword?password=Valid123Password"))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"invalid\"}", content);
    }

    @Test
    public void testInvalidPasswordForbiddenWord() throws Exception {
        MvcResult result = mockMvc.perform(get("/checkpassword?password=Password123!@#"))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"invalid\"}", content);
    }


    // You can add more test cases for other scenarios (e.g., missing special char, forbidden words, etc.)
}
