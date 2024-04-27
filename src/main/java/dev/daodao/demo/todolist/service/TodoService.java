package dev.daodao.demo.todolist.service;

import dev.daodao.demo.todolist.model.Todo;
import dev.daodao.demo.todolist.repository.TodoRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class TodoService {

    @Resource
    private TodoRepository todoRepository;

    public List<Todo> all() {
        return todoRepository.findAll();
    }

    public Todo get(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public Todo add(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo put(Long id, Todo todo) {
        Todo oldTodo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        oldTodo.setName(todo.getName());
        oldTodo.setDone(todo.getDone());
        return todoRepository.save(oldTodo);
    }

    public void del(Long id) {
        boolean b = todoRepository.existsById(id);
        if (!b) {
            throw new RuntimeException("Todo not found");
        }
        todoRepository.deleteById(id);
    }

}
