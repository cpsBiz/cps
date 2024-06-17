package com.mobcomms.cgvSupport.controller;

import com.mobcomms.common.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {

    @Value("${jwt.secret}")
    private String secretKey;

    @Operation(
            summary = "Get data",
            description = "Retrieve data with authorization header")
    @GetMapping("")
    public ResponseEntity tryJwt(
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Bearer token", required = true)
            @RequestHeader("Authorization") String authorization)
    {
        JWTUtils jwtUtil = new JWTUtils(secretKey);
        String token = jwtUtil.createToken("user@enliple.com", "admin");
        log.info("Generated Token: {}", token);

        if (jwtUtil.validateToken(token)) {
            log.info("Token is valid");
            log.info("Extracted Username: {}", jwtUtil.extractUsername(token));
            log.info("Payload : {}", jwtUtil.parseToken(token));
        } else {
            log.error("Token is invalid");
        }
        return ResponseEntity.ok().build();
    }
}
