package dev.daodao.demo.todolist.runner;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchemaRunner implements ApplicationRunner {

    @Resource
    private JdbcClient jdbcClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String text = new String(new ClassPathResource("schema/todo_list.sql").getInputStream().readAllBytes());
        String[] split = text.split(";");
        for (String sql : split) {
            if (sql.isBlank()) {
                continue;
            }
            log.info("sql: {}", sql);
            int update = jdbcClient.sql(sql).update();
            assert update >= 0;
        }
    }

}
