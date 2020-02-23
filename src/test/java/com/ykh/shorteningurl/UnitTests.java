package com.ykh.shorteningurl;

import com.ykh.Utils.UrlUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
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
        int seq1 = 999999999;
        int seq2 = 1245;
        int seq3 = 78848;
        assertEquals(UrlUtils.encoding(seq1), "Pq3pFB");
        assertEquals(UrlUtils.encoding(seq2), "FU");
        assertEquals(UrlUtils.encoding(seq3), "ufU");
    }

    @Test
    void UrlDecodingTest() {
        String shortUrl1 = "Pq3pFB";
        String shortUrl2 = "FU";
        String shortUrl3 = "ufU";
        assertEquals(UrlUtils.decoding(shortUrl1), 999999999);
        assertEquals(UrlUtils.decoding(shortUrl2), 1245);
        assertEquals(UrlUtils.decoding(shortUrl3), 78848);
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

        assertThat(urlEntity2).isNotEmpty();
        assertEquals(urlEntity2.get().getOriginUrl(), "https://www.daum.net");
    }
}
