package com.ykh.shorteningurl;

import com.ykh.Utils.UrlUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Ignore
class UnitTests {

    @Autowired
    UrlRepository urlRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void UrlEncodingTest() {
        int seq = 999999999;
        assertEquals(UrlUtils.encoding(seq), "Pq3pFB");
    }

    @Test
    void UrlDecodingTest() {
        String shortUrl = "Pq3pFB";
        assertEquals(UrlUtils.decoding(shortUrl), 999999999);
    }

    @Test
    void insertTest() {

        String originUrl = "https://www.daum.net";

        UrlEntity urlEntity = urlRepository.findByOriginUrl(originUrl);

        if(urlEntity == null) {
            urlEntity = new UrlEntity().buildWithUrl(originUrl);
            urlRepository.save(urlEntity);
        }

        Optional<UrlEntity> urlEntity2 = urlRepository.findById(urlEntity.getSeq());
        assertEquals(urlEntity2.get().getOriginUrl(), "https://www.daum.net");
    }
}
