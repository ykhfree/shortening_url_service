package com.ykh.shorteningurl;

import com.ykh.Utils.UrlUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShorteningUrlApplicationTests {

    @Autowired
    UrlRepository urlRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void UrlConvertTest() {
        UrlEntity urlEntity = new UrlEntity().buildWithUrl("https://www.nate.com");

        //TODO : 주소가 이미 있는지 확인
        urlRepository.save(urlEntity);

        System.out.println(urlEntity.getOriginUrl() + " ===> " + "http://localhost:8080/" + UrlUtils.encoding(urlEntity.getSeq()));
    }

    @Test
    void UrlEncodingTest() {
        int seq = 999999999;

        System.out.println(UrlUtils.encoding(seq));
    }

    @Test
    void UrlDecodingTest() {
        String shortUrl = "PqD";

        System.out.println(UrlUtils.decoding(shortUrl));
    }

    @Test
    void insertTest() {
        UrlEntity urlEntity = new UrlEntity().buildWithUrl("https://www.daum.net");

        urlRepository.save(urlEntity);

        List<UrlEntity> urlEntities = urlRepository.findAll();

        urlEntities.forEach(T -> {
            System.out.println(T.getSeq());
            System.out.println(T.getOriginUrl());
            System.out.println(T.getInsertDate());
            System.out.println(T.getUpdateDate());
        });
    }

    @Test
    void deleteTest() {

        urlRepository.deleteAll();

        List<UrlEntity> urlEntities = urlRepository.findAll();

        System.out.println(urlEntities.size());

    }

}
