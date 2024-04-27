CREATE SCHEMA IF NOT EXISTS `todo_list`;

CREATE TABLE IF NOT EXISTS `todo_list`.`todo`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` VARCHAR(255) NOT NULL COMMENT 'Name',
    `done` BOOLEAN      NOT NULL DEFAULT FALSE COMMENT 'Done',
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
);
