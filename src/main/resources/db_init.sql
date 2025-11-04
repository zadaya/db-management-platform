-- 创建系统数据库
CREATE DATABASE IF NOT EXISTS db_platform DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_platform;

-- 用户表
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) COMMENT '用户表';

-- 数据库连接配置表
CREATE TABLE db_connection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '连接名称',
    db_type VARCHAR(20) NOT NULL COMMENT '数据库类型',
    host VARCHAR(100) NOT NULL COMMENT '主机地址',
    port INT NOT NULL COMMENT '端口号',
    database_name VARCHAR(100) COMMENT '数据库名',
    username VARCHAR(100) NOT NULL COMMENT '用户名',
    password VARCHAR(200) NOT NULL COMMENT '密码',
    create_user BIGINT COMMENT '创建用户ID',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) COMMENT '数据库连接配置表';

-- 操作日志表
CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT COMMENT '用户ID',
    operation_type VARCHAR(50) COMMENT '操作类型',
    operation_content TEXT COMMENT '操作内容',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    operation_time DATETIME COMMENT '操作时间'
) COMMENT '操作日志表';

-- SQL执行记录表
CREATE TABLE sql_execution (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT COMMENT '用户ID',
    connection_id BIGINT COMMENT '数据库连接ID',
    sql_content TEXT COMMENT 'SQL内容',
    execution_time DATETIME COMMENT '执行时间',
    execution_result VARCHAR(50) COMMENT '执行结果：success/failure',
    affected_rows INT COMMENT '影响行数',
    execution_time_ms BIGINT COMMENT '执行时间（毫秒）'
) COMMENT 'SQL执行记录表';

-- 插入默认管理员用户（密码：admin123）
INSERT INTO sys_user (username, password, real_name, email, status, create_time, update_time)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV2UiC', '系统管理员', 'admin@dbplatform.com', 1, NOW(), NOW());

-- 插入模拟数据：数据库连接
INSERT INTO db_connection (name, db_type, host, port, database_name, username, password, create_user, create_time, update_time)
VALUES ('本地MySQL', 'mysql', 'localhost', 3306, 'test_db', 'root', 'password', 1, NOW(), NOW());

-- 插入模拟数据：SQL执行记录
INSERT INTO sql_execution (user_id, connection_id, sql_content, execution_time, execution_result, affected_rows, execution_time_ms)
VALUES (1, 1, 'SELECT * FROM users', NOW() - INTERVAL 1 DAY, 'success', 10, 150),
       (1, 1, 'SELECT * FROM orders', NOW() - INTERVAL 2 DAY, 'success', 5, 200),
       (1, 1, 'SELECT * FROM products', NOW() - INTERVAL 3 DAY, 'success', 20, 100),
       (1, 1, 'SELECT * FROM customers', NOW() - INTERVAL 4 DAY, 'success', 15, 300),
       (1, 1, 'SELECT * FROM employees', NOW() - INTERVAL 5 DAY, 'success', 8, 250);
