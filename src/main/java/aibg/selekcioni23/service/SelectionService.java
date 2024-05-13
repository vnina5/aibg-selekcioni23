package aibg.selekcioni23.service;

import aibg.selekcioni23.domain.User;
import aibg.selekcioni23.dto.DTO;
import aibg.selekcioni23.dto.JoinRequestDTO;
import aibg.selekcioni23.dto.LoginRequestDTO;
import aibg.selekcioni23.dto.ResultRequestDTO;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.List;

public interface SelectionService {

    DTO login(LoginRequestDTO dto) throws IOException;
    DTO join (String token) throws IOException;
    DTO result(ResultRequestDTO dto, String token) throws IOException;

    List<User> getUsers();
    User findUser(Claims claims);
}
