/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : seezoon-stack

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 22/04/2021 15:53:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

# if utf8mb4_0900_ai_ci  not support (less than 8),pls replace utf8mb4_0900_ai_ci to utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `seezoon-stack` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
use `seezoon-stack`;
-- ----------------------------
-- Table structure for sys_demo
-- ----------------------------
DROP TABLE IF EXISTS `sys_demo`;
CREATE TABLE `sys_demo` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `input_text` varchar(50) NOT NULL COMMENT '文本',
  `input_select` varchar(10) NOT NULL COMMENT '下拉',
  `input_radio` varchar(10) NOT NULL COMMENT '单选',
  `input_checkbox` varchar(10) DEFAULT NULL COMMENT '复选',
  `input_textarea` text COMMENT '文本域',
  `input_date` date NOT NULL COMMENT '日期',
  `input_zhengshu` int DEFAULT NULL COMMENT '整数',
  `input_xiaoshu` double DEFAULT NULL COMMENT '小数',
  `rich_text` mediumtext COMMENT '富文本',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `file` varchar(255) DEFAULT NULL COMMENT '文件',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `input_text` (`input_text`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `input_select` (`input_select`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生成案例';

-- ----------------------------
-- Records of sys_demo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` int NOT NULL COMMENT '父部门根节点插入0',
  `parent_ids` varchar(255) NOT NULL COMMENT '父节点路径',
  `name` varchar(100) NOT NULL COMMENT '部门名称',
  `sort` int NOT NULL COMMENT '排序',
  `contact_user` varchar(50) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id_and_ name` (`parent_id`,`name`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(50) NOT NULL COMMENT '字典类型',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int NOT NULL COMMENT '排序',
  `status` tinyint NOT NULL COMMENT '状态',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `type` (`type`) USING BTREE,
  KEY `create_date` (`create_time`),
  KEY `create_by` (`create_by`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `content_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件类型',
  `file_size` int NOT NULL COMMENT '文件大小B',
  `relative_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '相对路径',
  `create_by` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` int NOT NULL,
  `update_time` datetime NOT NULL,
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `relative_path` (`relative_path`),
  KEY `create_time` (`create_time`),
  KEY `name` (`name`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文件';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_gen
-- ----------------------------
DROP TABLE IF EXISTS `sys_gen`;
CREATE TABLE `sys_gen` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(32) NOT NULL COMMENT '表名',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名',
  `module_name` varchar(10) NOT NULL COMMENT '模块名',
  `function_name` varchar(20) NOT NULL COMMENT '功能模块',
  `template` tinyint(1) NOT NULL COMMENT '生成模板',
  `class_name` varchar(50) NOT NULL COMMENT '类名',
  `columns` text NOT NULL COMMENT '字段信息',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `table_name` (`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成';

-- ----------------------------
-- Records of sys_gen
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int NOT NULL COMMENT '用户ID，无id则为-1',
  `user_name` varchar(50) NOT NULL COMMENT '账号',
  `result` tinyint(1) NOT NULL COMMENT '登录结果0:成功;10:账号不存在;20:密码错误;30:已禁用;40:账户已锁定;45:IP已锁定;50:未知失败',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `ip` varchar(16) NOT NULL COMMENT 'IP地址',
  `area` varchar(20) DEFAULT NULL COMMENT '登录地区',
  `user_agent` varchar(1000) NOT NULL COMMENT '用户代理',
  `device_name` varchar(100) DEFAULT NULL COMMENT '设备名称',
  `browser_name` varchar(100) DEFAULT NULL COMMENT '浏览器名称',
  PRIMARY KEY (`id`),
  KEY `ip` (`ip`) USING BTREE,
  KEY `login_time` (`login_time`),
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登录日志';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int NOT NULL COMMENT '上级',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int NOT NULL COMMENT '排序',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `target` varchar(20) DEFAULT NULL COMMENT '目标位置main,_blank',
  `type` tinyint NOT NULL COMMENT '1:目录2:菜单3:按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint NOT NULL COMMENT '状态1.启用,0.停用',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`) USING BTREE,
  KEY `parent_ids` (`parent_ids`(191)),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (6, 0, '/0/', '系统管理', 10, NULL, NULL, 1, 'ion:settings-outline', 1, NULL, 1, '2021-03-03 23:49:58', 1, '2021-04-16 23:59:01', '');
INSERT INTO `sys_menu` VALUES (7, 6, '/0/6/', '部门管理', 10, '/sys/dept', 'main', 2, 'clarity:organization-line', 1, 'sys:dept:query', 1, '2021-03-04 00:55:33', 1, '2021-03-06 00:35:13', NULL);
INSERT INTO `sys_menu` VALUES (8, 0, '/0/', '首页', 9, '/sys/home', 'main', 2, 'bx:bx-home', 1, NULL, 1, '2021-03-04 01:08:27', 1, '2021-03-04 01:08:27', '必须存在');
INSERT INTO `sys_menu` VALUES (9, 6, '/0/6/', '用户管理', 1000, '/sys/user', 'main', 2, 'ant-design:user-switch-outlined', 1, 'sys:user:query', 1, '2021-03-04 22:25:41', 1, '2021-03-06 00:34:49', NULL);
INSERT INTO `sys_menu` VALUES (10, 6, '/0/6/', '角色管理', 1010, '/sys/role', 'main', 2, 'carbon:user-role', 1, 'sys:role:query', 1, '2021-03-04 22:26:30', 1, '2021-03-06 00:52:45', NULL);
INSERT INTO `sys_menu` VALUES (11, 6, '/0/6/', '菜单管理', 1019, '/sys/menu', 'main', 2, 'ic:sharp-menu-book', 1, 'sys:menu:query', 1, '2021-03-04 22:27:15', 1, '2021-03-06 00:52:54', NULL);
INSERT INTO `sys_menu` VALUES (12, 6, '/0/6/', '系统参数', 1020, '/sys/param', 'main', 2, 'zmdi:code-setting', 1, 'sys:param:query', 1, '2021-03-04 22:27:45', 1, '2021-03-06 00:53:03', NULL);
INSERT INTO `sys_menu` VALUES (13, 6, '/0/6/', '系统字典', 1030, '/sys/dict', 'main', 2, 'raphael:books', 1, 'sys:dict:query', 1, '2021-03-04 22:29:43', 1, '2021-03-06 00:53:14', NULL);
INSERT INTO `sys_menu` VALUES (14, 6, '/0/6/', '文件管理', 1040, '/sys/file', 'main', 2, 'akar-icons:file', 1, 'sys:file:query', 1, '2021-03-04 22:30:25', 1, '2021-03-06 00:53:22', NULL);
INSERT INTO `sys_menu` VALUES (17, 7, '/0/6/7/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:dept:save', 1, '2021-03-07 23:51:47', 1, '2021-03-08 00:42:09', NULL);
INSERT INTO `sys_menu` VALUES (18, 7, '/0/6/7/', '修改', 1010, NULL, 'main', 3, NULL, 1, 'sys:dept:update', 1, '2021-03-07 23:52:14', 1, '2021-03-07 23:52:14', NULL);
INSERT INTO `sys_menu` VALUES (19, 7, '/0/6/7/', '删除', 1020, NULL, 'main', 3, NULL, 1, 'sys:dept:delete', 1, '2021-03-07 23:52:50', 1, '2021-03-07 23:52:50', NULL);
INSERT INTO `sys_menu` VALUES (20, 9, '/0/6/9/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:user:save', 1, '2021-03-07 23:54:06', 1, '2021-03-07 23:54:06', NULL);
INSERT INTO `sys_menu` VALUES (21, 9, '/0/6/9/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:user:update', 1, '2021-03-07 23:54:25', 1, '2021-03-07 23:57:30', NULL);
INSERT INTO `sys_menu` VALUES (22, 9, '/0/6/9/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:user:delete', 1, '2021-03-07 23:54:47', 1, '2021-03-07 23:54:47', NULL);
INSERT INTO `sys_menu` VALUES (25, 10, '/0/6/10/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:role:save', 1, '2021-03-07 23:58:04', 1, '2021-03-07 23:58:04', NULL);
INSERT INTO `sys_menu` VALUES (26, 10, '/0/6/10/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:role:update', 1, '2021-03-07 23:58:27', 1, '2021-03-07 23:58:27', NULL);
INSERT INTO `sys_menu` VALUES (27, 10, '/0/6/10/', '分配', 1030, NULL, 'main', 3, NULL, 1, 'sys:role:assign', 1, '2021-03-07 23:59:34', 1, '2021-03-07 23:59:34', NULL);
INSERT INTO `sys_menu` VALUES (28, 10, '/0/6/10/', '删除', 1040, NULL, 'main', 3, NULL, 1, 'sys:role:delete', 1, '2021-03-07 23:59:58', 1, '2021-03-07 23:59:58', NULL);
INSERT INTO `sys_menu` VALUES (29, 11, '/0/6/11/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:menu:save', 1, '2021-03-08 00:00:42', 1, '2021-03-08 00:00:42', NULL);
INSERT INTO `sys_menu` VALUES (30, 11, '/0/6/11/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:menu:update', 1, '2021-03-08 00:01:08', 1, '2021-03-08 00:01:08', NULL);
INSERT INTO `sys_menu` VALUES (31, 11, '/0/6/11/', '删除', 1030, NULL, 'main', 3, NULL, 0, 'sys:menu:delete', 1, '2021-03-08 00:01:33', 1, '2021-03-08 01:07:31', NULL);
INSERT INTO `sys_menu` VALUES (32, 12, '/0/6/12/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:param:save', 1, '2021-03-08 00:02:00', 1, '2021-03-08 00:02:00', NULL);
INSERT INTO `sys_menu` VALUES (33, 12, '/0/6/12/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:param:update', 1, '2021-03-08 00:02:29', 1, '2021-03-08 00:02:29', NULL);
INSERT INTO `sys_menu` VALUES (34, 12, '/0/6/12/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:param:delete', 1, '2021-03-08 00:02:53', 1, '2021-03-08 00:02:59', NULL);
INSERT INTO `sys_menu` VALUES (35, 13, '/0/6/13/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:dict:save', 1, '2021-03-08 00:03:25', 1, '2021-03-08 00:03:25', NULL);
INSERT INTO `sys_menu` VALUES (36, 13, '/0/6/13/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:dict:update', 1, '2021-03-08 00:03:45', 1, '2021-03-08 00:04:16', NULL);
INSERT INTO `sys_menu` VALUES (37, 13, '/0/6/13/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:dict:delete', 1, '2021-03-08 00:04:09', 1, '2021-03-08 00:04:09', NULL);
INSERT INTO `sys_menu` VALUES (38, 14, '/0/6/14/', '上传', 1000, NULL, 'main', 3, NULL, 1, 'sys:file:upload', 1, '2021-03-08 00:05:18', 1, '2021-03-15 23:40:12', NULL);
INSERT INTO `sys_menu` VALUES (39, 14, '/0/6/14/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:file:delete', 1, '2021-03-08 00:05:40', 1, '2021-03-08 00:05:40', NULL);
INSERT INTO `sys_menu` VALUES (40, 14, '/0/6/14/', '下载', 1020, NULL, 'main', 3, NULL, 1, 'sys:file:download', 1, '2021-03-08 00:06:28', 1, '2021-03-08 00:06:28', NULL);
INSERT INTO `sys_menu` VALUES (42, 0, '/0/', '生成案例', 1000, '/sys/demo', 'main', 2, 'ant-design:tags-filled', 1, 'sys:demo:query', 1, '2021-03-16 01:20:53', 1, '2021-03-16 01:21:21', NULL);
INSERT INTO `sys_menu` VALUES (43, 42, '/0/42/', '添加', 1010, NULL, 'main', 3, NULL, 1, 'sys:demo:save', 1, '2021-03-16 01:21:51', 1, '2021-03-16 01:21:51', NULL);
INSERT INTO `sys_menu` VALUES (44, 42, '/0/42/', '修改', 1010, NULL, 'main', 3, NULL, 1, 'sys:demo:update', 1, '2021-03-16 01:22:10', 1, '2021-03-16 01:22:10', NULL);
INSERT INTO `sys_menu` VALUES (45, 42, '/0/42/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:demo:delete', 1, '2021-03-16 01:22:34', 1, '2021-03-16 01:22:34', NULL);
INSERT INTO `sys_menu` VALUES (46, 6, '/0/6/', '代码生成', 1060, '/sys/gen', 'main', 2, 'ant-design:block-outlined', 1, 'sys:gen:query', 1, '2021-03-30 00:06:57', 1, '2021-04-04 00:20:15', NULL);
INSERT INTO `sys_menu` VALUES (67, 46, '/0/46/', '添加', 1010, NULL, 'main', 3, NULL, 1, 'sys:gen:save', 1, '2021-03-30 00:06:57', 1, '2021-03-30 00:10:10', NULL);
INSERT INTO `sys_menu` VALUES (68, 46, '/0/46/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:gen:update', 1, '2021-03-30 00:06:57', 1, '2021-03-30 00:10:10', NULL);
INSERT INTO `sys_menu` VALUES (69, 46, '/0/46/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:gen:delete', 1, '2021-03-30 00:06:57', 1, '2021-04-08 00:29:56', NULL);
INSERT INTO `sys_menu` VALUES (70, 46, '/0/6/46/', '生成', 1050, NULL, 'main', 3, 'ant-design:bug-outlined', 1, 'sys:gen:generate', 1, '2021-04-09 02:16:17', 1, '2021-04-09 02:16:17', NULL);
INSERT INTO `sys_menu` VALUES (71, 6, '/0/6/', '登录日志', 1100, '/sys/login_log', 'main', 2, 'ant-design:clock-circle-filled', 1, 'sys:login_log:query', 1, '2021-04-18 01:37:34', 1, '2021-04-19 22:55:24', NULL);
INSERT INTO `sys_menu` VALUES (74, 71, '/0/71/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:login_log:delete', 1, '2021-04-18 01:37:34', 1, '2021-04-20 00:39:26', NULL);
INSERT INTO `sys_menu` VALUES (75, 9, '/0/6/9/', '解锁', 900, NULL, 'main', 3, NULL, 1, 'sys:user:unlock', 1, '2021-04-21 22:35:04', 1, '2021-04-21 22:35:04', NULL);
INSERT INTO `sys_menu` VALUES (76, 6, '/0/6/', '接口文档', 1120, 'http://localhost:3100/api/doc.html', '_blank', 2, 'ant-design:file-text-filled', 1, NULL, 1, '2021-04-22 01:16:32', 1, '2021-04-22 01:17:23', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `param_key` varchar(50) NOT NULL COMMENT '键',
  `param_value` varchar(100) NOT NULL COMMENT '值',
  `status` tinyint NOT NULL COMMENT '状态',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统参数';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `data_scope` tinyint NOT NULL COMMENT '数据范围:0:全部，1：本部门，2：本部门及以下，3.下级部门，4.指定部门，5.本人',
  `status` tinyint NOT NULL COMMENT '0停用1启用',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `data_scope` (`data_scope`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int NOT NULL COMMENT '角色编号',
  `menu_id` int NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `dept_id` int DEFAULT NULL COMMENT '部门',
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `mobile` varchar(20) NOT NULL COMMENT '手机',
  `photo` varchar(100) DEFAULT NULL COMMENT '头像',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `open_id` varchar(50) DEFAULT NULL COMMENT 'openId',
  `union_id` varchar(50) DEFAULT NULL COMMENT 'union_id',
  `status` tinyint NOT NULL COMMENT '状态1：正常，0：禁用',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `open_id` (`open_id`),
  UNIQUE KEY `union_id` (`union_id`),
  KEY `dept_id` (`dept_id`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, NULL, 'admin', '$2a$10$GOB1Kw.6noZYJ1i7BzwbvezlRafUoWqlSILIzXvBYeZURrKFdG2aO', '系统管理员', '132****3372', NULL , '734839030@qq.com', NULL, NULL, 1, 1, '2021-01-19 00:57:42', 1, '2021-04-16 02:11:55', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int NOT NULL COMMENT '用户编号',
  `role_id` int NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
