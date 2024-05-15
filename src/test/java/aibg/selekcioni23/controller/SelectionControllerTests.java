package aibg.selekcioni23.controller;

import aibg.selekcioni23.dto.*;
import aibg.selekcioni23.service.SelectionService;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
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
    @InjectMocks
    private SelectionController selectionController;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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
        LoginResponseDTO resDto = new LoginResponseDTO("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ");
        LOG.info(">> token: " + resDto.getToken());
        when(selectionService.login(dto)).thenReturn(resDto);

        ResponseEntity<DTO> response = selectionController.login(dto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(resDto, response.getBody());
    }

    @Test
    public void loginFailureTest() throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO("tim54", "tim54");
        ErrorResponseDTO resDto = new ErrorResponseDTO("Token nije uspešno generisan.");
        when(selectionService.login(dto)).thenReturn(resDto);

        ResponseEntity<DTO> response = selectionController.login(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(resDto, response.getBody());
    }


    @Test
    public void joinSuccessTest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";
        JoinResponseDTO resDto = new JoinResponseDTO("a=5");
        when(selectionService.join(token)).thenReturn(resDto);

        ResponseEntity<DTO> response = selectionController.join(token);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(resDto, response.getBody());
    }

    @Test
    public void joinFailureTest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VymmmtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";
        ErrorResponseDTO resDto = new ErrorResponseDTO("Tim ne postoji.");
        when(selectionService.join(token)).thenReturn(resDto);

        ResponseEntity<DTO> response = selectionController.join(token);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(resDto, response.getBody());
    }


    @Test
    public void resultSuccessTest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";
        ResultRequestDTO dto = new ResultRequestDTO(554);
        ResultResponseDTO resDto = new ResultResponseDTO("Hvala Vam što ste se prijavili za AIBG i što ste uradili selekcioni zadatak! Očekujte rezultate selekcije u narednih nekoliko dana");
        when(selectionService.result(dto, token)).thenReturn(resDto);

        ResponseEntity<DTO> response = selectionController.result(dto, token);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(resDto, response.getBody());
    }

    @Test
    public void resultFailureTest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";
        ResultRequestDTO dto = new ResultRequestDTO();
        ErrorResponseDTO resDto = new ErrorResponseDTO("Tim ne postoji.");
        when(selectionService.result(dto, token)).thenReturn(resDto);

        ResponseEntity<DTO> response = selectionController.result(dto, token);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(resDto, response.getBody());
    }
}
