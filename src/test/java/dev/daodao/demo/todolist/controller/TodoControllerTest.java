package dev.daodao.demo.todolist.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.daodao.demo.todolist.TodoListApplicationTest;
import dev.daodao.demo.todolist.constant.TodoConstant;
import dev.daodao.demo.todolist.model.Todo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
class TodoControllerTest extends TodoListApplicationTest {

    @Resource
    private JdbcClient jdbcClient;

    private Long id;
    private final String name = UUID.randomUUID().toString();
    private final Boolean done = false;

    @BeforeEach
    void setUp() {
        id = 1L;
        Map<String, Object> map = query(name);
        String sql;
        if (map.isEmpty()) {
            sql = """
                    INSERT INTO `todo_list`.`todo` (`name`, `done`) VALUES (:name, :done);
                    """;
            int update = jdbcClient.sql(sql)
                    .param("name", name)
                    .param("done", done)
                    .update();
            assert update == 1;
            map = query(name);
        }
        id = (Long) map.get("id");
    }

    private Map<String, Object> query(String name) {
        String sql = """
                SELECT * FROM `todo_list`.`todo` WHERE `name` = :name;
                """;
        List<Map<String, Object>> list = jdbcClient.sql(sql)
                .param("name", name)
                .query()
                .listOfRows();
        if (list.isEmpty()) {
            return Map.of();
        } else {
            return list.getFirst();
        }
    }

    @Test
    void all() {
        String uri = TodoConstant.TODO;
        HttpMethod method = HttpMethod.GET;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TypeReference<List<Todo>> typeReference = new TypeReference<>() {
        };
        List<Todo> all = mockMvc(uri, method, headers, null, typeReference);
        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());
        Todo todo = all.getFirst();
        Assertions.assertNotNull(todo);
        Assertions.assertEquals(id, todo.getId());
        Assertions.assertEquals(name, todo.getName());
        Assertions.assertEquals(done, todo.getDone());
    }

    @Test
    void get() {
        String uri = TodoConstant.TODO + "/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TypeReference<Todo> typeReference = new TypeReference<>() {
        };
        Todo todo = mockMvc(uri, method, headers, null, typeReference);
        Assertions.assertNotNull(todo);
        Assertions.assertEquals(id, todo.getId());
        Assertions.assertEquals(name, todo.getName());
        Assertions.assertEquals(done, todo.getDone());
    }

    @Test
    void add() {
        String uri = TodoConstant.TODO;
        HttpMethod method = HttpMethod.POST;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Todo todo = new Todo();
        todo.setName(UUID.randomUUID().toString());
        todo.setDone(false);
        TypeReference<Todo> typeReference = new TypeReference<>() {
        };
        Todo add = mockMvc(uri, method, headers, todo, typeReference);
        Assertions.assertNotNull(add);
        Assertions.assertNotNull(add.getId());
        Assertions.assertEquals(todo.getName(), add.getName());
        Assertions.assertEquals(todo.getDone(), add.getDone());
    }

    @Test
    void put() {
        String uri = TodoConstant.TODO + "/" + id;
        HttpMethod method = HttpMethod.PUT;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Todo todo = new Todo();
        todo.setName(UUID.randomUUID().toString());
        todo.setDone(true);
        TypeReference<Todo> typeReference = new TypeReference<>() {
        };
        Todo put = mockMvc(uri, method, headers, todo, typeReference);
        Assertions.assertNotNull(put);
        Assertions.assertEquals(id, put.getId());
        Assertions.assertEquals(todo.getName(), put.getName());
        Assertions.assertEquals(todo.getDone(), put.getDone());
    }

    @Test
    void del() {
        String uri = TodoConstant.TODO + "/" + id;
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        mockMvc(uri, method, headers, null, null);
    }

    @Test
    void clear() {
        String uri = TodoConstant.TODO;
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        mockMvc(uri, method, headers, null, null);
    }

}