package com.raigeek.cos.service;

import com.raigeek.cos.vo.LabelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${igx.url}")
    private String url;

    private static final Logger LOG = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    public WrappedResponse<List<LabelVO>> getLabels(@NotNull Locale locale) {

      WrappedResponse<List<LabelVO>> wrappedResponse = new com.raigeek.cos.service.WrappedResponse<>(HttpStatus.OK);
       //TODO.....Get the URL, make the call and build the response here from
       //cache =this.cacheManager.getCache();
/*
        Cache cache = this.cacheManager.getCache("labels");
        System.out.println("Cache Ka Naam aa gya: " + cache.getName());
        System.out.println("Cache Ki Value: " + cache.get("A").get().toString() );
*/

        LabelVO lVo =  new LabelVO();
        lVo.setKey("test");
        lVo.setValue("testValue");
        List a1 = new ArrayList<LabelVO>();
        a1.add(lVo);
            //return a1;
        wrappedResponse.setResponse(a1);
        return wrappedResponse;
    }

   /**
     * Returns a byte array based on the specified contentId. Returns null if the key is not found.
     *
     * @param contentId The content ID used to return the file
     * @return A byte array containing the file
     */
    public com.raigeek.cos.service.WrappedResponse<byte[]> getBinaryContent(@NotNull String contentId, @NotNull Locale locale) {
        com.raigeek.cos.service.WrappedResponse<byte[]> wrappedResponse = new com.raigeek.cos.service.WrappedResponse<>(HttpStatus.OK);
        //TODO.....Get the URL (igx.url in YML ) and build the response here from
        final String uri = "http://dummyUrl.com";
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, byte[].class, "1");

        if (!isError(responseEntity.getStatusCode())) {
            byte[] responseEntityBody = responseEntity.getBody();
            wrappedResponse.setResponse(responseEntityBody);
            wrappedResponse.setStatus(responseEntity.getStatusCode());
            //Conv to list and add it in response
        }
        //TODO.....Get the URL and build the response here from
        return wrappedResponse;
    }

   /**
     * Returns a text based on the specified contentId. Returns null if the key is not found.
     *
     * @param contentId The content ID used to return the file
     * @return  WrappedResponse
     */
    public com.raigeek.cos.service.WrappedResponse<String> getTextContent(@NotNull String contentId, @NotNull Locale locale) {
        com.raigeek.cos.service.WrappedResponse<String> wrappedResponse = new com.raigeek.cos.service.WrappedResponse<>(HttpStatus.OK);
        //*****ToDo Build the URL here correctly
        final String uri = "http://localhost:8081/greeting";
        ResponseEntity<String> responseEntity=null;

        try {
             responseEntity = getHttp(uri);
            if (!isError(responseEntity.getStatusCode())) {
                wrappedResponse.setResponse(responseEntity.getBody());
                //Build The correct response here based on the caching
            }
        } catch (Exception e) {
            LOG.error("Error occurred retrieving text content from Inginux for content Id: " + contentId
                   + e.getMessage());
            wrappedResponse.setStatus(responseEntity.getStatusCode());
            return wrappedResponse;
        }
        return wrappedResponse;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

    private ResponseEntity<String> getHttp(String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        return responseEntity;
    }

    @PostConstruct
    public void initCache() throws Exception {
        Cache cache = this.cacheManager.getCache("labels");
        cache.put("PahlaLable", "UskiValue");
        cache.put("A","B");
        System.out.println("Cache Ki Value: " + cache.get("PahlaLable").toString());
        System.out.println("Cache Ki Value: " + cache.getName());
    }



}