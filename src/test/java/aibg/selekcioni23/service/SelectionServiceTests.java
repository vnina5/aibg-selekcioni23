package aibg.selekcioni23.service;

import aibg.selekcioni23.domain.User;
import aibg.selekcioni23.dto.*;
import aibg.selekcioni23.logic.Assignment;
import aibg.selekcioni23.logic.LogicClass;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SelectionServiceTests {

    @Autowired
    private SelectionService selectionService;

    @MockBean
    private TokenService tokenService;

    private LogicClass logicClass = new LogicClass();

    @Test
    public void loginSuccessTest() throws IOException {
        LoginRequestDTO dto = new LoginRequestDTO("admin", "admin");

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";

        LoginResponseDTO resExpected = new LoginResponseDTO(token);

        Claims claims = Jwts.claims();
        claims.put("username", dto.getUsername());
        claims.put("password", dto.getPassword());

        when(tokenService.generate(claims)).thenReturn(token);

        LoginResponseDTO resActual = (LoginResponseDTO) selectionService.login(dto);
        assertNotNull(resActual);
        assertEquals(resExpected.getToken(), resActual.getToken());
    }


    @Test
    public void loginFailureTest() throws IOException {
        LoginRequestDTO dto = new LoginRequestDTO("tim45", "tim45");
        Claims claims = Jwts.claims();
        claims.put("username", dto.getUsername());
        claims.put("password", dto.getPassword());

        ErrorResponseDTO res = new ErrorResponseDTO("Tim sa username-om: " + dto.getUsername() + " i password-om: " + dto.getPassword() + " ne postoji.");

        DTO resActual = selectionService.login(dto);

        assertEquals(res.getMessage(), ((ErrorResponseDTO) resActual).getMessage());

//        assertThrows(ErrorResponseDTO.class, () -> {
//            selectionService.login(dto);
//        });
    }


    @Test
    public void joinSuccessTest() throws IOException {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";

        Claims claims = Jwts.claims();
        claims.put("username", "admin");
        claims.put("password", "admin");

        User user = new User("admin", "admin");

        when(tokenService.parseToken(token)).thenReturn(claims);
//        when(selectionService.findUser(claims)).thenReturn(user);
//        when(logicClass.getAss(user)).thenReturn(ass);

//        String assignment = logicClass.getAss(user);
//        assertNotNull(assignment);
//        assertNotNull(user.getAssignment());

        JoinResponseDTO resActual = (JoinResponseDTO) selectionService.join(token);
        assertNotNull(resActual);
//        assertEquals(resActual.getAssignment(), logicClass.getAss(user));
    }


    @Test
    public void resultSuccessTest() throws IOException {
        ResultRequestDTO dto = new ResultRequestDTO(747);

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiJ9.IZxtCnuIsZOrPFLeJ_zPCS6ixNaJsAyd69Y1JwW5cDE1R0fzCH9JBRX9mj11Ez4NfP_7WPvzo2ouJrbNaCODyQ";
        Claims claims = Jwts.claims();
        claims.put("username", "admin");
        claims.put("password", "admin");
        when(tokenService.parseToken(token)).thenReturn(claims);

        User user = new User("admin", "admin");
        user.setAssignment(new Assignment(64, 5, 25,20,8));
        user.setResult(dto.getResult());

        logicClass.calculateTrueResult(user);
        assertNotEquals(user.getTrueResult(), 0);

        String message = "Hvala Vam što ste se prijavili za AIBG i što ste uradili selekcioni zadatak! Očekujte rezultate selekcije u narednih nekoliko dana";
        JoinResponseDTO joinRes = (JoinResponseDTO) selectionService.join(token);  // mora da ne bi Assignment bio null
        ResultResponseDTO resActual = (ResultResponseDTO) selectionService.result(dto, token);

        assertNotNull(resActual);
        assertEquals(resActual.getMessage(), message);

    }


}
