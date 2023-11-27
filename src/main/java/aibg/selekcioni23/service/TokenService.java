package aibg.selekcioni23.service;

import io.jsonwebtoken.Claims;

public interface TokenService {
    String generate(Claims claims);
    Claims parseToken(String jwt);
}
