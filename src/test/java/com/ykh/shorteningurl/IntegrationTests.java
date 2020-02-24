package com.ykh.shorteningurl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykh.Utils.UrlUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations ="classpath:/test.properties")
@Transactional
@Ignore
public class IntegrationTests {

    @Autowired
    Environment env;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UrlRepository urlRepository;

    @Test
    public void convertUrlTest() throws Exception {

        //given
        UrlEntity urlEntity = new UrlEntity().buildWithUrl(env.getProperty("test.origin.url"));

        //when #1 (shortening url 생성)
        ResultActions resultActions = convertUrlByAPI(urlEntity.getOriginUrl());

        //then #1 (shortening url 생성)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true));

        urlEntity = urlRepository.findByOriginUrl(env.getProperty("test.origin.url"));

        //when #2 (origin url로 리다이렉트)
        ResultActions resultActionsCall = callShortUrl(urlEntity.getSeq());

        //then #2 (origin url로 리다이렉트)
        resultActionsCall
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", env.getProperty("test.origin.url")));
    }

    private ResultActions convertUrlByAPI(String originUrl) throws Exception {
        return mvc.perform(post("/service/convertUrl")
                .param("originUrl", originUrl))
                .andDo(print());
    }

    private ResultActions callShortUrl(int seq) throws Exception {
        return mvc.perform(get(env.getProperty("test.prefix.url") + UrlUtils.encoding(seq)))
                .andDo(print());
    }
}
