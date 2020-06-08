CREATE TABLE tb_user(
user_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户主键',
username VARCHAR(33) NOT NULL COMMENT '用户名',
password VARCHAR(100) NOT NULL COMMENT '密码',
create_time TIMESTAMP NOT NULL COMMENT '账户创建时间',
login_time TIMESTAMP NOT NULL COMMENT '登陆时间',
PRIMARY KEY (user_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';