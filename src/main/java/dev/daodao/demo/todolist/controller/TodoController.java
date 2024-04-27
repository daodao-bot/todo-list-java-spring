package dev.daodao.demo.todolist.controller;

import dev.daodao.demo.todolist.api.TodoApi;
import dev.daodao.demo.todolist.model.Todo;
import dev.daodao.demo.todolist.service.TodoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController implements TodoApi {

    @Resource
    private TodoService todoService;

    @Override
    public List<Todo> all() {
        return todoService.all();
    }

    @Override
    public Todo get(Long id) {
        return todoService.get(id);
    }

    @Override
    public Todo add(Todo todo) {
        return todoService.add(todo);
    }

    @Override
    public Todo put(Long id, Todo todo) {
        return todoService.put(id, todo);
    }

    @Override
    public void del(Long id) {
        todoService.del(id);
    }

}
