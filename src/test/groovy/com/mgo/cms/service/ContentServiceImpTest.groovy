package com.raigeek.cos.service

import com.raigeek.cos.vo.LabelVO
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class ContentServiceImpTest  {
    def service = new ContentServiceImpl()

    def labels = [
            [key: "hello", value: "world_en_US"],
            [key: "dashboard", value: "dashboard"]
    ]

    def html = "<h1>HTML!</h1>"
    def image = new byte[100]
    def list = new LabelVO();
    def label1 = [
            key          : "hello",
            value        : "world_en_US",

    ] as LabelVO

    def label2 = [
            key          : "dashboard_send_money",
            value        : "Localized Send Money",

    ] as LabelVO

    URI testUri = new URI();

    @Before
    void setup()  {
        def restTemplate = [
                exchange  : {String url, HttpMethod httpMethod, HttpEntity<String> requestEntity, Class<String> s, Object... uriVariables ->
                    new ResponseEntity(html,HttpStatus.FOUND)
                }
        ] as RestTemplate

        service.restTemplate=restTemplate;

    }

    @Test
    void testGetLabelHappyPath() {
        WrappedResponse<List<LabelVO>> label = service.getLabels(new Locale("en", "US","FBANK"))
        assert label.getResponse().size() == 1
    }

    @Test
    void testHtmlHappyPath() {
        def response = service.getTextContent("contentId",new Locale("en", "US","FBANK"))
        assert response.getResponse() == html
    }

}