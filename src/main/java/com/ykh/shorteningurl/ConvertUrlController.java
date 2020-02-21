package com.ykh.shorteningurl;

import com.ykh.Utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ConvertUrlController {

    @Autowired
    UrlRepository urlRepository;

    /**
     * 원본 URL을 축약URL로 변경하는 화면 호출
     * @return
     */
    @RequestMapping(value = "/service/convertUrl", method = RequestMethod.GET)
    public String convertUrl() {
        return "convertUrl";
    }

    /**
     * 축약URL 호출 시 원본URL로 리다이렉트
     * @param encodeSeq
     * @return
     */
    @RequestMapping(value = "/{encodeSeq}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> redirectOriginUrl(HttpServletRequest request, HttpServletResponse response, @PathVariable("encodeSeq") String encodeSeq) {

        int seq = UrlUtils.decoding(encodeSeq);

        Optional<UrlEntity> urlEntity = urlRepository.findById(seq);

        if(urlEntity.isPresent()) {
            try {
                response.sendRedirect(urlEntity.get().getOriginUrl());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<String>("The address move failed....", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<String>("The address does not exist...", HttpStatus.OK);
        }

        return new ResponseEntity<String>("", HttpStatus.OK);
    }
}
