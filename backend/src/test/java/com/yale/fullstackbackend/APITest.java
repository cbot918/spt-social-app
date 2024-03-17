package com.yale.fullstackbackend;

import com.yale.fullstackbackend.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
public class APITest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        // 將 mockMvc 在webApplicationContext環境中執行
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    /**
     * 測試初始化 Token API內容是否正常運作
     * @throws Exception
     */
    @Test
    public void checkGenerateJwtOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/jwt/generate"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 確認有沒有回傳 token欄位
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }
    /**
     * 測試認證Token API
     *
     * @throws Exception
     */
    @Test
    public void checkAuthToken() throws Exception {
        String checkValue = "王小明";
        String headerToekn = jwtUtil.generateToken(null, checkValue);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/jwt/auth")
                                .header("authorization", headerToekn)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 正常情況 status 回傳 true
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
                // 實際 sub 要與 Token內的 sub 相同
                .andExpect(MockMvcResultMatchers.jsonPath("$.sub").value(checkValue));
    }
}


