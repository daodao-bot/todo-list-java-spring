package dev.daodao.demo.todolist.api;

import dev.daodao.demo.todolist.constant.TodoConstant;
import dev.daodao.demo.todolist.model.Todo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;

@Tag(name = "TodoApi", description = "Todo API")
@HttpExchange(url = TodoConstant.API)
public interface TodoApi {

    @Operation(summary = "Get all todos")
    @GetExchange(url = TodoConstant.TODO)
    List<Todo> all();

    @Operation(summary = "Get todo by id")
    @GetExchange(url = TodoConstant.TODO + "/{id}")
    Todo get(@PathVariable("id") Long id);

    @Operation(summary = "Add todo")
    @PostExchange(url = TodoConstant.TODO)
    Todo add(@RequestBody Todo todo);

    @Operation(summary = "Put todo by id")
    @PutExchange(url = TodoConstant.TODO + "/{id}")
    Todo put(@PathVariable("id") Long id, @RequestBody Todo todo);

    @Operation(summary = "Delete todo by id")
    @DeleteExchange(url = TodoConstant.TODO + "/{id}")
    void del(@PathVariable("id") Long id);

}
