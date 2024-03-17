package com.yale.fullstackbackend;

import com.yale.fullstackbackend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@ActiveProfiles("test")
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void handleToken() {
        // 配置文件
        Map<String, Object> claims = new HashMap<>();
        claims.put("created_by", "jtest");

        // 測試是否為 String
        String jwtToken = jwtUtil.generateToken(claims, "Java Programer");
        assertTrue(jwtToken instanceof String);

        // 測試 Token是否逾期或異常
        Boolean tokenIsValidated = jwtUtil.validateToken(jwtToken);
        assertTrue(tokenIsValidated);

        // 測試 解析不符合 Token 格式的字串
        assertThrows(MalformedJwtException.class, () -> {
            jwtUtil.validateToken("不符合Token的情況");
        });
    }

    @Test
    void tokenData() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("founder", "jtest");
        String jwtToken = jwtUtil.generateToken(claims, "tokenData");
        Claims payload = jwtUtil.parseToken(jwtToken);

        // 驗證 founder 欄位資料
        String founder = payload.get("founder", String.class);
        assertEquals("jtest", founder);

        // 驗證 sub 資料
        String sub = payload.get("sub", String.class);
        assertEquals("tokenData", sub);
    }
}
