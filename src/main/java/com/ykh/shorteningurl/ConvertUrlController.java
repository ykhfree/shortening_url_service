package com.ykh.shorteningurl;

import com.google.gson.Gson;
import com.ykh.Utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Controller
public class ConvertUrlController {

    @Autowired
    UrlRepository urlRepository;

    @Value("${server.port}")
    private int serverPort;

    /**
     * 원본 URL을 축약URL로 변경하는 화면 호출
     * @return
     */
    @GetMapping("/service/convertUrl")
    public String convertUrl() {

        return "convertUrl";
    }

    /**
     * 원본 URL을 축약URL로 변경하는 API
     * @param originUrl
     * @return
     */
    @PostMapping("/service/convertUrl")
    public ResponseEntity<String> converingtUrl(@RequestParam("originUrl") String originUrl) throws UnknownHostException {

        ResultVO resultVO = new ResultVO();
        Gson gson = new Gson();
        String prefixUrl = serverPort + "/";

        InetAddress ip = InetAddress.getLocalHost();
        prefixUrl = "http://" + ip.getHostAddress() + ":" + prefixUrl;

        try {

            if (StringUtils.isEmpty(originUrl)) {
                resultVO.setSuccess(false);
                resultVO.setResultMsg("urlEmpty");
            } else {

                //originUrl이 DB에 있는지 확인
                UrlEntity urlEntity = urlRepository.findByOriginUrl(originUrl);

                if (urlEntity == null) {
                    urlEntity = new UrlEntity().buildWithUrl(originUrl);
                    urlRepository.save(urlEntity);
                } else {
                    long count = urlEntity.getCount();
                    urlEntity.setCount(++count);

                    urlRepository.save(urlEntity);
                }

                resultVO.setSuccess(true);
                resultVO.setResultMsg(prefixUrl + UrlUtils.encoding(urlEntity.getSeq()));
            }

            String json = gson.toJson(resultVO);

            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * shortening url 리스트 확인
     * @param model
     * @return
     */
    @GetMapping("/service/urlList")
    public String urlList(Model model) {

        List<UrlEntity> urlEntities = urlRepository.findAll();

        model.addAttribute("urlEntities", urlEntities);

        return "urlList";
    }

    /**
     * 축약URL 호출 시 원본URL로 리다이렉트
     * @param request
     * @param response
     * @param encodeSeq
     * @return
     */
    @GetMapping("/{encodeSeq}")
    @ResponseBody
    public ResponseEntity<String> redirectOriginUrl(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    @PathVariable("encodeSeq") String encodeSeq) {

        int seq = UrlUtils.decoding(encodeSeq);

        Optional<UrlEntity> urlEntity = urlRepository.findById(seq);

        if(urlEntity.isPresent()) {
            try {
                response.sendRedirect(urlEntity.get().getOriginUrl());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("The address move failed....", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("The address does not exist...", HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
