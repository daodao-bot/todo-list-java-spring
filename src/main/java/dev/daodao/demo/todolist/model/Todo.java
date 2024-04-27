package dev.daodao.demo.todolist.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Schema(name = "Todo", description = "Todo model")
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(schema = "todo_list", name = "todo")
public class Todo {

    @Schema(name = "id", description = "Todo id", example = "1")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "name", description = "Todo name", example = "Todo")
    @Column(name = "name")
    private String name;

    @Schema(name = "done", description = "Todo done", example = "false")
    @Column(name = "done")
    private Boolean done;

}
