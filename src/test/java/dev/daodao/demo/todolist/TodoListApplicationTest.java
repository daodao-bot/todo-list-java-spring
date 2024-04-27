package dev.daodao.demo.todolist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.daodao.demo.todolist.constant.TodoConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

@Slf4j
@Rollback
@Transactional
@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoListApplicationTest {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    public <I, O> O mockMvc(String uri, HttpMethod method, HttpHeaders headers, I request, TypeReference<O> typeReference) {
        uri = "/" + TodoConstant.API + "/" + uri;

        String requestBody;
        if (null == request) {
            requestBody = "";
        } else {
            try {
                requestBody = objectMapper.writeValueAsString(request);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        String responseBody;
        try {
            responseBody = mockMvc.perform(MockMvcRequestBuilders.request(method, uri)
                            .headers(headers)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(Charset.defaultCharset());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (responseBody.isEmpty()) {
            return null;
        }
        O response;
        try {
            response = objectMapper.readValue(responseBody, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

}