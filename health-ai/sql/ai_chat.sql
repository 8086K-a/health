-- AI 对话会话表
DROP TABLE IF EXISTS `ai_conversation`;
CREATE TABLE `ai_conversation` (
  `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `title`       varchar(100) DEFAULT '新对话'        COMMENT '会话标题',
  `user_id`     bigint(20)   DEFAULT NULL           COMMENT '用户ID',
  `model`       varchar(50)  DEFAULT NULL           COMMENT '使用的模型名称',
  `status`      tinyint(1)   DEFAULT 1              COMMENT '状态：1正常 0删除',
  `create_by`   varchar(64)  DEFAULT ''             COMMENT '创建者',
  `create_time` datetime     DEFAULT NULL           COMMENT '创建时间',
  `update_time` datetime     DEFAULT NULL           COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI对话会话表';

-- AI 对话消息表
DROP TABLE IF EXISTS `ai_message`;
CREATE TABLE `ai_message` (
  `id`              bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `conversation_id` bigint(20)   DEFAULT NULL           COMMENT '会话ID',
  `role`            varchar(20)  DEFAULT NULL           COMMENT '角色：user/assistant',
  `content`         text         DEFAULT NULL           COMMENT '消息内容',
  `tokens`          int(11)      DEFAULT 0              COMMENT 'Token数量',
  `create_time`     datetime     DEFAULT NULL           COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI对话消息表';
