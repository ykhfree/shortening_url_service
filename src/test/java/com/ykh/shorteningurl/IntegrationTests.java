package com.ykh.shorteningurl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Ignore
public class IntegrationTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UrlRepository urlRepository;

    @Test
    public void convertUrlTest() throws Exception {

        //given
        String originUrl = "https://www.naver.com";
        UrlEntity urlEntity = new UrlEntity().buildWithUrl(originUrl);

        //when
        ResultActions resultActions = convertUrlByAPI(urlEntity.getOriginUrl());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true));
    }

    private ResultActions convertUrlByAPI(String originUrl) throws Exception {
        return mvc.perform(post("/service/convertUrl")
                .param("originUrl", originUrl))
                .andDo(print());
    }
}
