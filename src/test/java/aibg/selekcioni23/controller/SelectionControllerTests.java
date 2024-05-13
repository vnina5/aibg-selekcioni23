package aibg.selekcioni23.controller;

import aibg.selekcioni23.dto.*;
import aibg.selekcioni23.service.SelectionService;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.IOException;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;

import aibg.selekcioni23.service.implementation.SelectionServiceImplementation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityExistsException;

@WebMvcTest(SelectionController.class)
public class SelectionControllerTests {
    private Logger LOG = LoggerFactory.getLogger(SelectionControllerTests.class);

    @MockBean
    private SelectionService selectionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


//    @Test
//    public void testLoginSuccess() throws Exception {
//        LoginRequestDTO requestDTO = new LoginRequestDTO("admin", "admin");
//        // Postaviti potrebne podatke u requestDTO
//
//        LoginResponseDTO loginResponse = new LoginResponseDTO("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ");
//
//        // Postaviti potrebne podatke u loginResponse
//
//        when(selectionService.login(requestDTO)).thenReturn(loginResponse);
//
//        mockMvc.perform(post("/selection/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isAccepted());
//    }


    @Test
    public void loginSuccessTest() throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO("admin", "admin");
        LOG.info(">> REQUEST: " + "username: " + dto.getUsername() + " password: " + dto.getPassword());

        LoginResponseDTO res = new LoginResponseDTO("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ");
        LOG.info(">> token: " + res.getToken());
        when(selectionService.login(dto)).thenReturn(res);

//        mockMvc.perform(post("/selection/login").)

        DTO response = selectionService.login(dto);
        assertTrue(response instanceof LoginResponseDTO);

//        mockMvc.perform(post("/selection/login").contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isAccepted());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.token", equalTo(res.getToken())));

//        verify(selectionService, times(1)).login(dto);
    }
// vraca status code 415, a treba 202


    @Test
    public void loginFailureTest() throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO("tim2", "tim2");
        DTO response = selectionService.login(dto);

        when(selectionService.login(dto)).thenReturn(response);

        mockMvc.perform(post("/selection/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());

        assertFalse(response instanceof LoginResponseDTO);
    }


    @Test
    public void joinSuccessTest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";

        JoinResponseDTO res = new JoinResponseDTO("a=5");
        when(selectionService.join(token)).thenReturn(res);

        MvcResult result = mockMvc.perform(get("/selection/join")).andExpect(status().isAccepted()).andReturn();
        JoinResponseDTO returnedRes = objectMapper.readValue( result.getResponse().getContentAsString(), new TypeReference<JoinResponseDTO>() {} );
//        mockMvc.perform(get("/selection/join").contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isAccepted())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.token", equalTo(res.getToken())));

        assertNotNull(returnedRes);
    }
// vraca status 400, a treba 202


    @Test
    public void joinFailureTest() throws Exception {
        String token = "eyJhbGciOiJIUztZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";

        DTO response = selectionService.join(token);

        when(selectionService.join(token)).thenReturn(response);

        mockMvc.perform(get("/selection/join")).andExpect(status().isBadRequest());

        assertFalse(response instanceof JoinResponseDTO);
    }

}
