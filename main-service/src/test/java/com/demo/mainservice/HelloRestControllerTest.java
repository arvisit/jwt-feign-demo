package com.demo.mainservice;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.demo.mainservice.security.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 8480)
class HelloRestControllerTest {

    private static final String USERS_VALIDATE_URL = "/users/validate";
    private static final String HELLO_ADMINISTRATOR_URL = "/hello/administrator";
    private static final String HELLO_JOURNALIST_URL = "/hello/journalist";
    private static final String HELLO_USER_URL = "/hello/user";

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200_whenRequestToAdministratorAuthorityEndpointWithValidAuthorities() throws Exception {
        UserDto user = new UserDto("user", List.of("ROLE_ADMINISTRATOR"));
        wireMockResponse(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                HELLO_ADMINISTRATOR_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturn200_whenRequestToJournalistAuthorityEndpointWithValidAuthorities() throws Exception {
        UserDto user = new UserDto("user", List.of("ROLE_JOURNALIST"));
        wireMockResponse(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                HELLO_JOURNALIST_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturn200_whenRequestToUserAuthorityEndpointWithValidAuthorities() throws Exception {
        UserDto user = new UserDto("user", List.of("ROLE_USER"));
        wireMockResponse(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                HELLO_USER_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturn403_whenRequestToJournalistAuthorityEndpointWithInvalidAuthorities() throws Exception {
        UserDto user = new UserDto("user", List.of("ROLE_USER"));
        wireMockResponse(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                HELLO_JOURNALIST_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    private void wireMockResponse(UserDto user) throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(user);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(USERS_VALIDATE_URL))
                .willReturn(WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(body)));
    }

}
