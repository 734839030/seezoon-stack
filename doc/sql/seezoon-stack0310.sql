/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 50633
 Source Host           : localhost:3306
 Source Schema         : seezoon-stack

 Target Server Type    : MySQL
 Target Server Version : 50633
 File Encoding         : 65001

 Date: 11/03/2021 02:01:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` int(11) NOT NULL COMMENT '父部门根节点插入0',
  `parent_ids` varchar(255) NOT NULL COMMENT '父节点路径',
  `name` varchar(100) NOT NULL COMMENT '部门名称',
  `sort` int(10) NOT NULL COMMENT '排序',
  `contact_user` varchar(50) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `create_by` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id_and_ name` (`parent_id`,`name`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (11, 0, '/0/', '总公司', 10, NULL, NULL, NULL, '2021-01-14 01:03:33', NULL, '2021-03-06 00:28:58', NULL);
INSERT INTO `sys_dept` VALUES (20, 11, '/0/11/', '分公司1', 1000, NULL, NULL, NULL, '2021-03-06 00:29:17', NULL, '2021-03-06 00:29:17', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(50) NOT NULL COMMENT '字典类型',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL COMMENT '排序',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `create_by` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `type` (`type`) USING BTREE,
  KEY `create_date` (`create_time`),
  KEY `create_by` (`create_by`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (2, 'sex', '1', '男', 10, 1, NULL, '2020-12-27 23:19:38', NULL, '2020-12-27 23:19:38', NULL);
INSERT INTO `sys_dict` VALUES (3, 'status', '1', '有效', 9, 1, NULL, '2020-12-28 00:01:00', NULL, '2020-12-31 01:15:53', '11');
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `content_type` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '文件类型',
  `file_size` int(11) NOT NULL COMMENT '文件大小B',
  `relative_path` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '相对路径',
  `create_by` int(32) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_by` int(32) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `relative_path` (`relative_path`),
  KEY `create_time` (`create_time`),
  KEY `name` (`name`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文件';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
INSERT INTO `sys_file` VALUES ('004a8c74e2d74d18b2dd0f3c7f6afe82', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/004a8c74e2d74d18b2dd0f3c7f6afe82.jpg', NULL, '2021-01-20 23:31:20', NULL, '2021-01-20 23:31:20', NULL);
INSERT INTO `sys_file` VALUES ('02bf4b5691c04fe7855b55710124f3fb', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/02bf4b5691c04fe7855b55710124f3fb.jpg', NULL, '2021-01-20 23:15:47', NULL, '2021-01-20 23:15:47', NULL);
INSERT INTO `sys_file` VALUES ('035e25af0db14aaea3da9b5237b4c942', '选型分析V1.2.pptx', 'application/vnd.openxmlformats-officedocument.presentationml.presentation', 212510, '/2021/1/20/035e25af0db14aaea3da9b5237b4c942.pptx', NULL, '2021-01-20 01:54:29', NULL, '2021-01-20 01:54:29', NULL);
INSERT INTO `sys_file` VALUES ('03a2228b3d7b4813b61f98cea334a1ea', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/21/03a2228b3d7b4813b61f98cea334a1ea.jpg', NULL, '2021-01-21 22:25:16', NULL, '2021-01-21 22:25:16', NULL);
INSERT INTO `sys_file` VALUES ('0649b4d1b6be4710a09c85e92ec6bb33', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/0649b4d1b6be4710a09c85e92ec6bb33.jpg', NULL, '2021-01-20 01:09:40', NULL, '2021-01-20 01:09:40', NULL);
INSERT INTO `sys_file` VALUES ('06d29025bd6344558764146f0cabfedf', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/06d29025bd6344558764146f0cabfedf.jpg', NULL, '2021-01-20 23:29:39', NULL, '2021-01-20 23:29:39', NULL);
INSERT INTO `sys_file` VALUES ('0862ee6f9c294725bd46839f9214150f', '1', 'application/octet-stream', 317234, '/2021/1/11/0862ee6f9c294725bd46839f9214150f', NULL, '2021-01-11 00:15:21', NULL, '2021-01-11 00:15:21', NULL);
INSERT INTO `sys_file` VALUES ('08f1cfa67fe4496e822c1a9a314a93eb', '1.jpg', 'image/jpeg', 242908, '/2021/1/21/08f1cfa67fe4496e822c1a9a314a93eb.jpg', NULL, '2021-01-21 22:25:21', NULL, '2021-01-21 22:25:21', NULL);
INSERT INTO `sys_file` VALUES ('092af3d78c144a36a8b8f1c61729a460', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/092af3d78c144a36a8b8f1c61729a460.jpg', NULL, '2021-01-20 01:19:04', NULL, '2021-01-20 01:19:04', NULL);
INSERT INTO `sys_file` VALUES ('0b3af10847314111bbf198a9a006729c', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/0b3af10847314111bbf198a9a006729c.jpg', NULL, '2021-01-20 00:03:43', NULL, '2021-01-20 00:03:43', NULL);
INSERT INTO `sys_file` VALUES ('0bfd55b008684e319c55fe42adf1a416', '1.jpeg', 'image/jpeg', 111413, '/2021/1/20/0bfd55b008684e319c55fe42adf1a416.jpeg', NULL, '2021-01-20 01:44:26', NULL, '2021-01-20 01:44:26', NULL);
INSERT INTO `sys_file` VALUES ('0f09d7457856436487cfa97be234dd52', '1 (1).jpeg', 'image/jpeg', 882, '/2021/1/20/0f09d7457856436487cfa97be234dd52.jpeg', NULL, '2021-01-20 01:43:30', NULL, '2021-01-20 01:43:30', NULL);
INSERT INTO `sys_file` VALUES ('0fcb8bc201d24d9b9003f5059c0115b3', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/0fcb8bc201d24d9b9003f5059c0115b3.jpg', NULL, '2021-01-20 00:02:43', NULL, '2021-01-20 00:02:43', NULL);
INSERT INTO `sys_file` VALUES ('11bc2b48709c434da2a0109ec96b7d55', 'jest.config.js', 'text/javascript', 512, '/2021/1/9/11bc2b48709c434da2a0109ec96b7d55.js', NULL, '2021-01-09 23:17:38', NULL, '2021-01-09 23:17:38', NULL);
INSERT INTO `sys_file` VALUES ('1372d8ae101340dca190fb749cd799f7', '2 (7).jpg', 'image/jpeg', 115420, '/2021/1/22/1372d8ae101340dca190fb749cd799f7.jpg', NULL, '2021-01-22 00:25:03', NULL, '2021-01-22 00:25:03', NULL);
INSERT INTO `sys_file` VALUES ('15abcfe8f03642e198ee3c13d08ee800', '1 (1).jpeg', 'image/jpeg', 882, '/2021/1/20/15abcfe8f03642e198ee3c13d08ee800.jpeg', NULL, '2021-01-20 01:23:06', NULL, '2021-01-20 01:23:06', NULL);
INSERT INTO `sys_file` VALUES ('172fe3e10e4042fe9a180f47415d25df', '001 (1).png', 'image/png', 173918, '/2021/1/19/172fe3e10e4042fe9a180f47415d25df.png', NULL, '2021-01-19 23:24:22', NULL, '2021-01-19 23:24:22', NULL);
INSERT INTO `sys_file` VALUES ('19e127a69de14a608db2ab444e01a30e', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/19e127a69de14a608db2ab444e01a30e.jpg', NULL, '2021-01-20 01:52:45', NULL, '2021-01-20 01:52:45', NULL);
INSERT INTO `sys_file` VALUES ('1a10a7441fa54b46bd630751a4f2211d', 'favicon (1).ico', 'image/vnd.microsoft.icon', 894, '/2021/3/3/1a10a7441fa54b46bd630751a4f2211d.ico', NULL, '2021-03-03 21:48:05', NULL, '2021-03-03 21:48:05', NULL);
INSERT INTO `sys_file` VALUES ('1c53e7459a86485291c62e619d5383bc', 'themePluginConfig.js', 'text/javascript', 2116, '/2021/1/9/1c53e7459a86485291c62e619d5383bc.js', NULL, '2021-01-09 23:24:33', NULL, '2021-01-09 23:24:33', NULL);
INSERT INTO `sys_file` VALUES ('1d210155179947b587a84391f0009eea', '1.jpg', 'image/jpeg', 242908, '/2021/1/22/1d210155179947b587a84391f0009eea.jpg', NULL, '2021-01-22 00:24:50', NULL, '2021-01-22 00:24:50', NULL);
INSERT INTO `sys_file` VALUES ('1d6f64a619d142baa44700932bd833dc', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/1d6f64a619d142baa44700932bd833dc.jpg', NULL, '2021-01-20 22:55:05', NULL, '2021-01-20 22:55:05', NULL);
INSERT INTO `sys_file` VALUES ('1dec8f992cef41c78c7b5c6675ad1938', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/21/1dec8f992cef41c78c7b5c6675ad1938.jpg', NULL, '2021-01-21 02:10:23', NULL, '2021-01-21 02:10:23', NULL);
INSERT INTO `sys_file` VALUES ('21918b2f4d79428eafb9f8ff0c6e5c52', '1.jpg', 'image/jpeg', 242908, '/2021/1/21/21918b2f4d79428eafb9f8ff0c6e5c52.jpg', NULL, '2021-01-21 02:13:45', NULL, '2021-01-21 02:13:45', NULL);
INSERT INTO `sys_file` VALUES ('253f87f3e0e243129f3eafd83eb3998e', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/253f87f3e0e243129f3eafd83eb3998e.jpg', NULL, '2021-01-20 23:23:08', NULL, '2021-01-20 23:23:08', NULL);
INSERT INTO `sys_file` VALUES ('27bc015db0f047079ab0f9fd1c12b753', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/27bc015db0f047079ab0f9fd1c12b753.jpg', NULL, '2021-01-20 23:34:36', NULL, '2021-01-20 23:34:36', NULL);
INSERT INTO `sys_file` VALUES ('2a19e259a00f453a88d62aaf25f906ea', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/21/2a19e259a00f453a88d62aaf25f906ea.jpg', NULL, '2021-01-21 02:10:28', NULL, '2021-01-21 02:10:28', NULL);
INSERT INTO `sys_file` VALUES ('2c8fab90efab44f3b5e62ad807d2f217', '1.jpeg', 'image/jpeg', 111413, '/2021/1/21/2c8fab90efab44f3b5e62ad807d2f217.jpeg', NULL, '2021-01-21 02:14:25', NULL, '2021-01-21 02:14:25', NULL);
INSERT INTO `sys_file` VALUES ('2d2219085dfb4d1681ff01463a312c7e', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/2d2219085dfb4d1681ff01463a312c7e.jpg', NULL, '2021-01-20 23:27:34', NULL, '2021-01-20 23:27:34', NULL);
INSERT INTO `sys_file` VALUES ('2d5d80265841488e974c0abb270938a5', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/2d5d80265841488e974c0abb270938a5.jpg', NULL, '2021-01-20 23:22:17', NULL, '2021-01-20 23:22:17', NULL);
INSERT INTO `sys_file` VALUES ('2d790126cd094a058099888434fde789', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/2d790126cd094a058099888434fde789.jpg', NULL, '2021-01-20 23:28:12', NULL, '2021-01-20 23:28:12', NULL);
INSERT INTO `sys_file` VALUES ('2ee944f125bc4ba49ae6568df5dcb062', '1 (1).jpeg', 'image/jpeg', 882, '/2021/1/20/2ee944f125bc4ba49ae6568df5dcb062.jpeg', NULL, '2021-01-20 01:18:56', NULL, '2021-01-20 01:18:56', NULL);
INSERT INTO `sys_file` VALUES ('314007cabf494305ba9c060e5c559f43', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/314007cabf494305ba9c060e5c559f43.jpg', NULL, '2021-01-20 00:03:15', NULL, '2021-01-20 00:03:15', NULL);
INSERT INTO `sys_file` VALUES ('3610b623ba074d03b95ae60cfe3f4a8a', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/3610b623ba074d03b95ae60cfe3f4a8a.jpg', NULL, '2021-01-20 01:51:06', NULL, '2021-01-20 01:51:06', NULL);
INSERT INTO `sys_file` VALUES ('36aac7f5ee644eb5af255e4305c3134d', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/36aac7f5ee644eb5af255e4305c3134d.jpg', NULL, '2021-01-20 23:28:49', NULL, '2021-01-20 23:28:49', NULL);
INSERT INTO `sys_file` VALUES ('3822095a4a484b4fa577f36549388c42', '1.jpg', 'image/jpeg', 242908, '/2021/1/11/3822095a4a484b4fa577f36549388c42.jpg', NULL, '2021-01-11 00:15:03', NULL, '2021-01-11 00:15:03', NULL);
INSERT INTO `sys_file` VALUES ('39b438f07de84e0987915f89f791bc8b', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/39b438f07de84e0987915f89f791bc8b.jpg', NULL, '2021-01-20 22:49:26', NULL, '2021-01-20 22:49:26', NULL);
INSERT INTO `sys_file` VALUES ('3a4a56bcf5da44afa0fb8575515b6ad9', 'themePluginConfig.js', 'text/javascript', 2116, '/2021/1/10/3a4a56bcf5da44afa0fb8575515b6ad9.js', NULL, '2021-01-10 00:13:32', NULL, '2021-01-10 00:13:32', NULL);
INSERT INTO `sys_file` VALUES ('3b96e4918d024e0ca8c74604cc8225e7', '1.jpg', 'image/jpeg', 242908, '/2021/1/19/3b96e4918d024e0ca8c74604cc8225e7.jpg', NULL, '2021-01-19 23:39:33', NULL, '2021-01-19 23:39:33', NULL);
INSERT INTO `sys_file` VALUES ('3bcc72bc91e54a1f9a37b2542cb35bb0', '2+(8).jpg', 'image/jpeg', 115420, '/2021/3/3/3bcc72bc91e54a1f9a37b2542cb35bb0.jpg', NULL, '2021-03-03 23:28:21', NULL, '2021-03-03 23:28:21', NULL);
INSERT INTO `sys_file` VALUES ('4249bdeaf0164a85877d82e9a0a17499', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/4249bdeaf0164a85877d82e9a0a17499.jpg', NULL, '2021-01-20 01:53:14', NULL, '2021-01-20 01:53:14', NULL);
INSERT INTO `sys_file` VALUES ('42696c1d9be840fa926a8bc13a60281e', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/42696c1d9be840fa926a8bc13a60281e.jpg', NULL, '2021-01-20 01:42:53', NULL, '2021-01-20 01:42:53', NULL);
INSERT INTO `sys_file` VALUES ('448a293c540f4715862ded2704bb1b48', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/448a293c540f4715862ded2704bb1b48.jpg', NULL, '2021-01-20 01:50:11', NULL, '2021-01-20 01:50:11', NULL);
INSERT INTO `sys_file` VALUES ('44e40b7ae8e04e789dbdb11147233faa', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/44e40b7ae8e04e789dbdb11147233faa.jpg', NULL, '2021-01-20 01:52:53', NULL, '2021-01-20 01:52:53', NULL);
INSERT INTO `sys_file` VALUES ('45bef96b74c946d386a7c4e8cbce9357', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/21/45bef96b74c946d386a7c4e8cbce9357.jpg', NULL, '2021-01-21 00:26:17', NULL, '2021-01-21 00:26:17', NULL);
INSERT INTO `sys_file` VALUES ('4650c8f3d95445e3be822e4c1cdada14', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/4650c8f3d95445e3be822e4c1cdada14.jpg', NULL, '2021-01-20 23:14:00', NULL, '2021-01-20 23:14:00', NULL);
INSERT INTO `sys_file` VALUES ('4906f4cef4ee4c88b3019eaf6d0f9caf', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/4906f4cef4ee4c88b3019eaf6d0f9caf.jpg', NULL, '2021-01-20 23:20:56', NULL, '2021-01-20 23:20:56', NULL);
INSERT INTO `sys_file` VALUES ('4ccb07106417427887e40dc2758c244b', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/19/4ccb07106417427887e40dc2758c244b.jpg', NULL, '2021-01-19 23:52:44', NULL, '2021-01-19 23:52:44', NULL);
INSERT INTO `sys_file` VALUES ('4e71546496a04e6881995083abac9aa4', '1.jpg', 'image/jpeg', 242908, '/2021/1/22/4e71546496a04e6881995083abac9aa4.jpg', NULL, '2021-01-22 00:26:34', NULL, '2021-01-22 00:26:34', NULL);
INSERT INTO `sys_file` VALUES ('52c5f64ca7be4c4785348086d9258f44', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/52c5f64ca7be4c4785348086d9258f44.jpg', NULL, '2021-01-20 22:50:12', NULL, '2021-01-20 22:50:12', NULL);
INSERT INTO `sys_file` VALUES ('52da250dff614b3faabf5f891233b20f', '2+(8).jpg', 'image/jpeg', 115420, '/2021/3/11/52da250dff614b3faabf5f891233b20f.jpg', NULL, '2021-03-11 00:52:05', NULL, '2021-03-11 00:52:05', NULL);
INSERT INTO `sys_file` VALUES ('56c7b12417654b2784716ab7af3715af', '11.png', 'image/png', 38181, '/2021/1/22/56c7b12417654b2784716ab7af3715af.png', NULL, '2021-01-22 00:26:43', NULL, '2021-01-22 00:26:43', NULL);
INSERT INTO `sys_file` VALUES ('577193bd325e43d5904e7c73329164d7', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/577193bd325e43d5904e7c73329164d7.jpg', NULL, '2021-01-20 01:46:34', NULL, '2021-01-20 01:46:34', NULL);
INSERT INTO `sys_file` VALUES ('58354e78f706434eb2d7de904a9f8852', '2 (3).jpg', 'image/jpeg', 115420, '/2021/1/20/58354e78f706434eb2d7de904a9f8852.jpg', NULL, '2021-01-20 01:52:49', NULL, '2021-01-20 01:52:49', NULL);
INSERT INTO `sys_file` VALUES ('5a50ac11cc2c42dcb7360b7471f6075d', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/5a50ac11cc2c42dcb7360b7471f6075d.jpg', NULL, '2021-01-20 23:24:42', NULL, '2021-01-20 23:24:42', NULL);
INSERT INTO `sys_file` VALUES ('5db97d7b813e4b40ab7588f06613857f', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/21/5db97d7b813e4b40ab7588f06613857f.jpg', NULL, '2021-01-21 02:16:47', NULL, '2021-01-21 02:16:47', NULL);
INSERT INTO `sys_file` VALUES ('5dcd434519eb4f0498578d39205e1c73', '1.jpeg', 'image/jpeg', 111413, '/2021/1/20/5dcd434519eb4f0498578d39205e1c73.jpeg', NULL, '2021-01-20 23:10:59', NULL, '2021-01-20 23:10:59', NULL);
INSERT INTO `sys_file` VALUES ('613bf391feb1424ba84f78eec2e242e6', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/613bf391feb1424ba84f78eec2e242e6.jpg', NULL, '2021-01-20 01:50:23', NULL, '2021-01-20 01:50:23', NULL);
INSERT INTO `sys_file` VALUES ('62b52640348c4a29a46644a57379b7aa', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/19/62b52640348c4a29a46644a57379b7aa.jpg', NULL, '2021-01-19 23:57:07', NULL, '2021-01-19 23:57:07', NULL);
INSERT INTO `sys_file` VALUES ('630d06bcd6ba4169898a03a0f52dbc77', 'testName.txt', 'text/plain', 12, '/2021/3/3/630d06bcd6ba4169898a03a0f52dbc77.txt', NULL, '2021-03-03 23:16:35', NULL, '2021-03-03 23:16:35', NULL);
INSERT INTO `sys_file` VALUES ('6798bbca0fe54f3e9e4537d2efe3863b', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/6798bbca0fe54f3e9e4537d2efe3863b.jpg', NULL, '2021-01-20 23:28:02', NULL, '2021-01-20 23:28:02', NULL);
INSERT INTO `sys_file` VALUES ('6847245f3a6e483ab000355ce89fcb2b', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/6847245f3a6e483ab000355ce89fcb2b.jpg', NULL, '2021-01-20 01:30:21', NULL, '2021-01-20 01:30:21', NULL);
INSERT INTO `sys_file` VALUES ('698751481dbb445f9f80e33aebf1e1f6', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/19/698751481dbb445f9f80e33aebf1e1f6.jpg', NULL, '2021-01-19 23:25:24', NULL, '2021-01-19 23:25:24', NULL);
INSERT INTO `sys_file` VALUES ('6c14cc0e0e274948b47376ce537b5aa0', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/6c14cc0e0e274948b47376ce537b5aa0.jpg', NULL, '2021-01-20 01:22:27', NULL, '2021-01-20 01:22:27', NULL);
INSERT INTO `sys_file` VALUES ('6cb7362d896f47bd813472db5b656d59', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/6cb7362d896f47bd813472db5b656d59.jpg', NULL, '2021-01-20 01:43:37', NULL, '2021-01-20 01:43:37', NULL);
INSERT INTO `sys_file` VALUES ('6d6b11be5752482287807fa8c745e6f0', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/6d6b11be5752482287807fa8c745e6f0.jpg', NULL, '2021-01-20 01:57:23', NULL, '2021-01-20 01:57:23', NULL);
INSERT INTO `sys_file` VALUES ('6e4d51f5b5934e5eaa29e515037e865f', '1 (1).jpg', 'image/jpeg', 242908, '/2021/3/11/6e4d51f5b5934e5eaa29e515037e865f.jpg', NULL, '2021-03-11 01:02:12', NULL, '2021-03-11 01:02:12', NULL);
INSERT INTO `sys_file` VALUES ('6f36e39f667d4fa181406404de9a58a5', '1.jpeg', 'image/jpeg', 111413, '/2021/1/22/6f36e39f667d4fa181406404de9a58a5.jpeg', NULL, '2021-01-22 01:28:45', NULL, '2021-01-22 01:28:45', NULL);
INSERT INTO `sys_file` VALUES ('70edb0b3220c4a2fabf575fb50b39423', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/19/70edb0b3220c4a2fabf575fb50b39423.jpg', NULL, '2021-01-19 23:23:12', NULL, '2021-01-19 23:23:12', NULL);
INSERT INTO `sys_file` VALUES ('719030a2e7364decb93afb940b2a05f3', '2 (1).jpg', 'image/jpeg', 883, '/2021/1/22/719030a2e7364decb93afb940b2a05f3.jpg', NULL, '2021-01-22 00:24:56', NULL, '2021-01-22 00:24:56', NULL);
INSERT INTO `sys_file` VALUES ('722674931ce246b6bf391f0adae288c9', '2 (3).jpg', 'image/jpeg', 115420, '/2021/1/20/722674931ce246b6bf391f0adae288c9.jpg', NULL, '2021-01-20 01:46:40', NULL, '2021-01-20 01:46:40', NULL);
INSERT INTO `sys_file` VALUES ('72288969e1264d20abe11722123d66ec', '1.jpeg', 'image/jpeg', 111413, '/2021/1/22/72288969e1264d20abe11722123d66ec.jpeg', NULL, '2021-01-22 00:24:32', NULL, '2021-01-22 00:24:32', NULL);
INSERT INTO `sys_file` VALUES ('73aff9b5770740228a385f7c9c2fd101', '2+(8).jpg', 'image/jpeg', 115420, '/2021/3/11/73aff9b5770740228a385f7c9c2fd101.jpg', NULL, '2021-03-11 00:51:09', NULL, '2021-03-11 00:51:09', NULL);
INSERT INTO `sys_file` VALUES ('74ea3515f4fc41f68e9c5a1cf6877d4d', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/74ea3515f4fc41f68e9c5a1cf6877d4d.jpg', NULL, '2021-01-20 01:24:14', NULL, '2021-01-20 01:24:14', NULL);
INSERT INTO `sys_file` VALUES ('7e018773933b44798210f4b9c12158b6', '1.jpeg', 'image/jpeg', 111413, '/2021/1/19/7e018773933b44798210f4b9c12158b6.jpeg', NULL, '2021-01-19 23:59:02', NULL, '2021-01-19 23:59:02', NULL);
INSERT INTO `sys_file` VALUES ('7f043ce3125a449da02aa1421cfd3bcd', '2+(8).jpg', 'image/jpeg', 115420, '/2021/3/11/7f043ce3125a449da02aa1421cfd3bcd.jpg', NULL, '2021-03-11 00:43:18', NULL, '2021-03-11 00:43:18', NULL);
INSERT INTO `sys_file` VALUES ('826c3222b5b246dbae66204fba8757c6', '1.jpg', 'image/jpeg', 242908, '/2021/1/19/826c3222b5b246dbae66204fba8757c6.jpg', NULL, '2021-01-19 23:34:42', NULL, '2021-01-19 23:34:42', NULL);
INSERT INTO `sys_file` VALUES ('83208660c39149f083afd098b4e812e5', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/22/83208660c39149f083afd098b4e812e5.jpg', NULL, '2021-01-22 00:24:21', NULL, '2021-01-22 00:24:21', NULL);
INSERT INTO `sys_file` VALUES ('8454d10632c14d1fbbff333a52c17978', 'testName.txt', 'text/plain', 12, '/2021/3/3/8454d10632c14d1fbbff333a52c17978.txt', NULL, '2021-03-03 21:47:49', NULL, '2021-03-03 21:47:49', NULL);
INSERT INTO `sys_file` VALUES ('86cea642b7054e7b87179d3ffba367d5', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/86cea642b7054e7b87179d3ffba367d5.jpg', NULL, '2021-01-20 01:12:47', NULL, '2021-01-20 01:12:47', NULL);
INSERT INTO `sys_file` VALUES ('86e0aa4653bd415f937a8a6644ab0179', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/19/86e0aa4653bd415f937a8a6644ab0179.jpg', NULL, '2021-01-19 23:58:21', NULL, '2021-01-19 23:58:21', NULL);
INSERT INTO `sys_file` VALUES ('910d208ecf414b66bcf5276e7ee01fa5', '2 (3).jpg', 'image/jpeg', 115420, '/2021/1/20/910d208ecf414b66bcf5276e7ee01fa5.jpg', NULL, '2021-01-20 01:50:07', NULL, '2021-01-20 01:50:07', NULL);
INSERT INTO `sys_file` VALUES ('991f1f24be9141e985ac72f282acfa48', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/22/991f1f24be9141e985ac72f282acfa48.jpg', NULL, '2021-01-22 00:23:27', NULL, '2021-01-22 00:23:27', NULL);
INSERT INTO `sys_file` VALUES ('9bde3f447e28417db28649d5eb347064', 'themePluginConfig.js', 'text/javascript', 2116, '/2021/1/9/9bde3f447e28417db28649d5eb347064.js', NULL, '2021-01-09 23:27:28', NULL, '2021-01-09 23:27:28', NULL);
INSERT INTO `sys_file` VALUES ('9c2edd2cbb79415db94ec77f8da6d9f8', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/9c2edd2cbb79415db94ec77f8da6d9f8.jpg', NULL, '2021-01-20 01:22:06', NULL, '2021-01-20 01:22:06', NULL);
INSERT INTO `sys_file` VALUES ('9ca00bad3c8f4ec1b921d350e15a04ba', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/9ca00bad3c8f4ec1b921d350e15a04ba.jpg', NULL, '2021-01-20 01:48:20', NULL, '2021-01-20 01:48:20', NULL);
INSERT INTO `sys_file` VALUES ('9e14b7cd9ac646e1bb243e3946c956b4', '2+(8).jpg', 'image/jpeg', 115420, '/2021/3/11/9e14b7cd9ac646e1bb243e3946c956b4.jpg', NULL, '2021-03-11 00:44:26', NULL, '2021-03-11 00:44:26', NULL);
INSERT INTO `sys_file` VALUES ('9fb33a064c114820a5a40a3c9a915e49', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/9fb33a064c114820a5a40a3c9a915e49.jpg', NULL, '2021-01-20 23:28:08', NULL, '2021-01-20 23:28:08', NULL);
INSERT INTO `sys_file` VALUES ('9ff342a586a04d4c8bbe5da81198d9fe', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/9ff342a586a04d4c8bbe5da81198d9fe.jpg', NULL, '2021-01-20 01:43:56', NULL, '2021-01-20 01:43:56', NULL);
INSERT INTO `sys_file` VALUES ('a170a579bc5f46ad855042c2774dd553', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/a170a579bc5f46ad855042c2774dd553.jpg', NULL, '2021-01-20 23:27:49', NULL, '2021-01-20 23:27:49', NULL);
INSERT INTO `sys_file` VALUES ('a2316fb65dd946aeb9b59369106e0da0', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/a2316fb65dd946aeb9b59369106e0da0.jpg', NULL, '2021-01-20 01:28:23', NULL, '2021-01-20 01:28:23', NULL);
INSERT INTO `sys_file` VALUES ('a8e738602e714bf3be4fe5cac18dae47', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/a8e738602e714bf3be4fe5cac18dae47.jpg', NULL, '2021-01-20 23:33:59', NULL, '2021-01-20 23:33:59', NULL);
INSERT INTO `sys_file` VALUES ('a8fdd6bc47fb4b3ab5f8d98d6ff4f58f', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/a8fdd6bc47fb4b3ab5f8d98d6ff4f58f.jpg', NULL, '2021-01-20 23:31:55', NULL, '2021-01-20 23:31:55', NULL);
INSERT INTO `sys_file` VALUES ('a9fe81a233a443749d78f785e40e34a0', 'themePluginConfig.js', 'text/javascript', 2116, '/2021/1/9/a9fe81a233a443749d78f785e40e34a0.js', NULL, '2021-01-09 23:25:53', NULL, '2021-01-09 23:25:53', NULL);
INSERT INTO `sys_file` VALUES ('ac75449025ec418fa107e51fff363bb8', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/ac75449025ec418fa107e51fff363bb8.jpg', NULL, '2021-01-20 01:06:07', NULL, '2021-01-20 01:06:07', NULL);
INSERT INTO `sys_file` VALUES ('aea76a643c924b38a69591e4e1122389', '1.jpeg', 'image/jpeg', 111413, '/2021/1/20/aea76a643c924b38a69591e4e1122389.jpeg', NULL, '2021-01-20 01:50:18', NULL, '2021-01-20 01:50:18', NULL);
INSERT INTO `sys_file` VALUES ('af2ed23d8ed74173b66f61bddb579ae2', '2 (8).jpg', 'image/jpeg', 115420, '/2021/1/20/af2ed23d8ed74173b66f61bddb579ae2.jpg', NULL, '2021-01-20 23:30:37', NULL, '2021-01-20 23:30:37', NULL);
INSERT INTO `sys_file` VALUES ('b45f75cf14084f5193baff193f7bf9cb', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/19/b45f75cf14084f5193baff193f7bf9cb.jpg', NULL, '2021-01-19 23:57:57', NULL, '2021-01-19 23:57:57', NULL);
INSERT INTO `sys_file` VALUES ('b5734bb0d25d47749983b4cc957f5b35', '2 (1).jpg', 'image/jpeg', 883, '/2021/1/21/b5734bb0d25d47749983b4cc957f5b35.jpg', NULL, '2021-01-21 02:14:55', NULL, '2021-01-21 02:14:55', NULL);
INSERT INTO `sys_file` VALUES ('b9f8d017394f44e18e3c9db31ae18623', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/b9f8d017394f44e18e3c9db31ae18623.jpg', NULL, '2021-01-20 23:01:38', NULL, '2021-01-20 23:01:38', NULL);
INSERT INTO `sys_file` VALUES ('ba2c706ad5b24c3e8c31e851c8de3196', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/ba2c706ad5b24c3e8c31e851c8de3196.jpg', NULL, '2021-01-20 01:46:29', NULL, '2021-01-20 01:46:29', NULL);
INSERT INTO `sys_file` VALUES ('bbf2494076da42798710ccc21d0b5dbf', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/bbf2494076da42798710ccc21d0b5dbf.jpg', NULL, '2021-01-20 01:22:01', NULL, '2021-01-20 01:22:01', NULL);
INSERT INTO `sys_file` VALUES ('bc8edcfb740c4c42bf6100cf19d6d1a1', '2.jpg', 'image/jpeg', 115420, '/2021/1/11/bc8edcfb740c4c42bf6100cf19d6d1a1.jpg', NULL, '2021-01-11 00:38:43', NULL, '2021-01-11 00:38:43', NULL);
INSERT INTO `sys_file` VALUES ('bdee80aa65864cd5996d9528e2bd05f0', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/bdee80aa65864cd5996d9528e2bd05f0.jpg', NULL, '2021-01-20 01:12:40', NULL, '2021-01-20 01:12:40', NULL);
INSERT INTO `sys_file` VALUES ('be4787d0fcd744b183e883691d049424', '12.jpeg', 'image/jpeg', 31587, '/2021/1/22/be4787d0fcd744b183e883691d049424.jpeg', NULL, '2021-01-22 00:25:19', NULL, '2021-01-22 00:25:19', NULL);
INSERT INTO `sys_file` VALUES ('bf003611b63f4b7fb732f83c7287f153', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/bf003611b63f4b7fb732f83c7287f153.jpg', NULL, '2021-01-20 23:28:25', NULL, '2021-01-20 23:28:25', NULL);
INSERT INTO `sys_file` VALUES ('c1182d8717a941da8d5a90ffe6908d87', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/c1182d8717a941da8d5a90ffe6908d87.jpg', NULL, '2021-01-20 01:50:02', NULL, '2021-01-20 01:50:02', NULL);
INSERT INTO `sys_file` VALUES ('c232341a051b4694aa4c5aa7b4c85fd1', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/19/c232341a051b4694aa4c5aa7b4c85fd1.jpg', NULL, '2021-01-19 23:21:49', NULL, '2021-01-19 23:21:49', NULL);
INSERT INTO `sys_file` VALUES ('c2a2bca126be499e8d08fa8b6815b185', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/c2a2bca126be499e8d08fa8b6815b185.jpg', NULL, '2021-01-20 01:57:29', NULL, '2021-01-20 01:57:29', NULL);
INSERT INTO `sys_file` VALUES ('c30ef74af9884a98a51e68cb78acde00', '1 (1).jpg', 'image/jpeg', 242908, '/2021/1/20/c30ef74af9884a98a51e68cb78acde00.jpg', NULL, '2021-01-20 01:30:05', NULL, '2021-01-20 01:30:05', NULL);
INSERT INTO `sys_file` VALUES ('c329ffcb1720459ea639d75615ce75ef', 'themePluginConfig.js', 'text/javascript', 2116, '/2021/1/9/c329ffcb1720459ea639d75615ce75ef.js', NULL, '2021-01-09 23:59:36', NULL, '2021-01-09 23:59:36', NULL);
INSERT INTO `sys_file` VALUES ('c3b0aa577ff5411fbc3b9445488f256c', '1.jpg', 'image/jpeg', 242908, '/2021/1/11/c3b0aa577ff5411fbc3b9445488f256c.jpg', NULL, '2021-01-11 00:31:47', NULL, '2021-01-11 00:31:47', NULL);
INSERT INTO `sys_file` VALUES ('c565d568efa44995bc889d6e6d2be34c', 'themePluginConfig.js', 'text/javascript', 2116, '/2021/1/10/c565d568efa44995bc889d6e6d2be34c.js', NULL, '2021-01-10 00:21:56', NULL, '2021-01-10 00:21:56', NULL);
INSERT INTO `sys_file` VALUES ('cfe94691eeb14519b00f42ee67933ec8', '2 (3).jpg', 'image/jpeg', 115420, '/2021/1/20/cfe94691eeb14519b00f42ee67933ec8.jpg', NULL, '2021-01-20 01:44:31', NULL, '2021-01-20 01:44:31', NULL);
INSERT INTO `sys_file` VALUES ('d55f9ed747334c29b74d60a2fcff6647', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/21/d55f9ed747334c29b74d60a2fcff6647.jpg', NULL, '2021-01-21 02:11:14', NULL, '2021-01-21 02:11:14', NULL);
INSERT INTO `sys_file` VALUES ('d5c224bd31e741a8b5fad46ce74b8e7a', 'favicon.ico', 'image/vnd.microsoft.icon', 894, '/2021/3/11/d5c224bd31e741a8b5fad46ce74b8e7a.ico', NULL, '2021-03-11 00:54:06', NULL, '2021-03-11 00:54:06', NULL);
INSERT INTO `sys_file` VALUES ('d8e286da2b1d476aa098d621221495c3', 'plugin.config.js', 'text/javascript', 3507, '/2021/1/9/d8e286da2b1d476aa098d621221495c3.js', NULL, '2021-01-09 23:30:10', NULL, '2021-01-09 23:30:10', NULL);
INSERT INTO `sys_file` VALUES ('dde744cc7ede442b9be9c60aeeed2354', '3.jpg', 'image/jpeg', 139720, '/2021/1/20/dde744cc7ede442b9be9c60aeeed2354.jpg', NULL, '2021-01-20 23:30:45', NULL, '2021-01-20 23:30:45', NULL);
INSERT INTO `sys_file` VALUES ('e4d0a06f3b7a4e84b657448f2b15252f', '2.png', 'image/png', 814531, '/2021/1/20/e4d0a06f3b7a4e84b657448f2b15252f.png', NULL, '2021-01-20 01:51:31', NULL, '2021-01-20 01:51:31', NULL);
INSERT INTO `sys_file` VALUES ('e6606a4d9bd64cb7a300b95b7f07e093', '2 (3).jpg', 'image/jpeg', 115420, '/2021/1/20/e6606a4d9bd64cb7a300b95b7f07e093.jpg', NULL, '2021-01-20 01:51:21', NULL, '2021-01-20 01:51:21', NULL);
INSERT INTO `sys_file` VALUES ('eddf46508e6942ee961cdbfe3db1b075', '1.jpg', 'image/jpeg', 242908, '/2021/1/20/eddf46508e6942ee961cdbfe3db1b075.jpg', NULL, '2021-01-20 01:08:38', NULL, '2021-01-20 01:08:38', NULL);
INSERT INTO `sys_file` VALUES ('ee2ca496ed2747ce8e290166054f5270', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/19/ee2ca496ed2747ce8e290166054f5270.jpg', NULL, '2021-01-19 23:20:30', NULL, '2021-01-19 23:20:30', NULL);
INSERT INTO `sys_file` VALUES ('ee8676aff542485e9de672109a1292cd', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/ee8676aff542485e9de672109a1292cd.jpg', NULL, '2021-01-20 23:29:17', NULL, '2021-01-20 23:29:17', NULL);
INSERT INTO `sys_file` VALUES ('ef1218d6b99745e2b4cb9a8697f9ed5c', '001 (1).png', 'image/png', 173918, '/2021/1/20/ef1218d6b99745e2b4cb9a8697f9ed5c.png', NULL, '2021-01-20 01:11:01', NULL, '2021-01-20 01:11:01', NULL);
INSERT INTO `sys_file` VALUES ('efa1d72680ee436db60cc54257ed80b5', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/20/efa1d72680ee436db60cc54257ed80b5.jpg', NULL, '2021-01-20 01:17:41', NULL, '2021-01-20 01:17:41', NULL);
INSERT INTO `sys_file` VALUES ('efcc309e86e848d182b6b49554ce2cb8', '001 (1).png', 'image/png', 173918, '/2021/1/19/efcc309e86e848d182b6b49554ce2cb8.png', NULL, '2021-01-19 23:27:45', NULL, '2021-01-19 23:27:45', NULL);
INSERT INTO `sys_file` VALUES ('f36d612863f649c4a5b56ad624aa03c5', '2 (3).jpg', 'image/jpeg', 115420, '/2021/1/19/f36d612863f649c4a5b56ad624aa03c5.jpg', NULL, '2021-01-19 23:31:01', NULL, '2021-01-19 23:31:01', NULL);
INSERT INTO `sys_file` VALUES ('f4ed5c41ae064b9698d9750ba2a4ce11', '1.jpg', 'image/jpeg', 242908, '/2021/1/12/f4ed5c41ae064b9698d9750ba2a4ce11.jpg', NULL, '2021-01-12 00:13:21', NULL, '2021-01-12 00:13:21', NULL);
INSERT INTO `sys_file` VALUES ('fcc6a5de47b347bc857965748a3becc8', '1 (2).jpg', 'image/jpeg', 242908, '/2021/1/22/fcc6a5de47b347bc857965748a3becc8.jpg', NULL, '2021-01-22 00:50:20', NULL, '2021-01-22 00:50:20', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NOT NULL COMMENT '上级',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int(10) NOT NULL COMMENT '排序',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `target` varchar(20) DEFAULT NULL COMMENT '目标位置main,_blank',
  `type` tinyint(4) NOT NULL COMMENT '1:目录2:菜单3:按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) NOT NULL COMMENT '状态1.启用,0.停用',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(32) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`) USING BTREE,
  KEY `parent_ids` (`parent_ids`(191)),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (6, 0, '/0/', '系统管理', 10, NULL, NULL, 1, 'ion:settings-outline', 1, NULL, NULL, '2021-03-03 23:49:58', NULL, '2021-03-04 00:46:16', '');
INSERT INTO `sys_menu` VALUES (7, 6, '/0/6/', '部门管理', 10, '/sys/dept', 'main', 2, 'clarity:organization-line', 1, 'sys:dept:query', NULL, '2021-03-04 00:55:33', NULL, '2021-03-06 00:35:13', NULL);
INSERT INTO `sys_menu` VALUES (8, 0, '/0/', '首页', 9, '/sys/home', 'main', 2, 'bx:bx-home', 1, NULL, NULL, '2021-03-04 01:08:27', NULL, '2021-03-04 01:08:27', NULL);
INSERT INTO `sys_menu` VALUES (9, 6, '/0/6/', '用户管理', 1000, '/sys/user', 'main', 2, 'ant-design:user-switch-outlined', 1, 'sys:user:query', NULL, '2021-03-04 22:25:41', NULL, '2021-03-06 00:34:49', NULL);
INSERT INTO `sys_menu` VALUES (10, 6, '/0/6/', '角色管理', 1010, '/sys/role', 'main', 2, 'carbon:user-role', 1, 'sys:role:query', NULL, '2021-03-04 22:26:30', NULL, '2021-03-06 00:52:45', NULL);
INSERT INTO `sys_menu` VALUES (11, 6, '/0/6/', '菜单管理', 1019, '/sys/menu', 'main', 2, 'ic:sharp-menu-book', 1, 'sys:menu:query', NULL, '2021-03-04 22:27:15', NULL, '2021-03-06 00:52:54', NULL);
INSERT INTO `sys_menu` VALUES (12, 6, '/0/6/', '系统参数', 1020, '/sys/param', 'main', 2, 'zmdi:code-setting', 1, 'sys:param:query', NULL, '2021-03-04 22:27:45', NULL, '2021-03-06 00:53:03', NULL);
INSERT INTO `sys_menu` VALUES (13, 6, '/0/6/', '系统字典', 1030, '/sys/dict', 'main', 2, 'raphael:books', 1, 'sys:dict:query', NULL, '2021-03-04 22:29:43', NULL, '2021-03-06 00:53:14', NULL);
INSERT INTO `sys_menu` VALUES (14, 6, '/0/6/', '文件管理', 1040, '/sys/file', 'main', 2, 'akar-icons:file', 1, 'sys:file:query', NULL, '2021-03-04 22:30:25', NULL, '2021-03-06 00:53:22', NULL);
INSERT INTO `sys_menu` VALUES (17, 7, '/0/6/7/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:dept:save', NULL, '2021-03-07 23:51:47', NULL, '2021-03-08 00:42:09', NULL);
INSERT INTO `sys_menu` VALUES (18, 7, '/0/6/7/', '修改', 1010, NULL, 'main', 3, NULL, 1, 'sys:dept:update', NULL, '2021-03-07 23:52:14', NULL, '2021-03-07 23:52:14', NULL);
INSERT INTO `sys_menu` VALUES (19, 7, '/0/6/7/', '删除', 1020, NULL, 'main', 3, NULL, 1, 'sys:dept:delete', NULL, '2021-03-07 23:52:50', NULL, '2021-03-07 23:52:50', NULL);
INSERT INTO `sys_menu` VALUES (20, 9, '/0/6/9/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:user:save', NULL, '2021-03-07 23:54:06', NULL, '2021-03-07 23:54:06', NULL);
INSERT INTO `sys_menu` VALUES (21, 9, '/0/6/9/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:user:update', NULL, '2021-03-07 23:54:25', NULL, '2021-03-07 23:57:30', NULL);
INSERT INTO `sys_menu` VALUES (22, 9, '/0/6/9/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:user:delete', NULL, '2021-03-07 23:54:47', NULL, '2021-03-07 23:54:47', NULL);
INSERT INTO `sys_menu` VALUES (25, 10, '/0/6/10/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:role:save', NULL, '2021-03-07 23:58:04', NULL, '2021-03-07 23:58:04', NULL);
INSERT INTO `sys_menu` VALUES (26, 10, '/0/6/10/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:role:update', NULL, '2021-03-07 23:58:27', NULL, '2021-03-07 23:58:27', NULL);
INSERT INTO `sys_menu` VALUES (27, 10, '/0/6/10/', '分配', 1030, NULL, 'main', 3, NULL, 1, 'sys:role:assign', NULL, '2021-03-07 23:59:34', NULL, '2021-03-07 23:59:34', NULL);
INSERT INTO `sys_menu` VALUES (28, 10, '/0/6/10/', '删除', 1040, NULL, 'main', 3, NULL, 1, 'sys:role:delete', NULL, '2021-03-07 23:59:58', NULL, '2021-03-07 23:59:58', NULL);
INSERT INTO `sys_menu` VALUES (29, 11, '/0/6/11/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:menu:save', NULL, '2021-03-08 00:00:42', NULL, '2021-03-08 00:00:42', NULL);
INSERT INTO `sys_menu` VALUES (30, 11, '/0/6/11/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:menu:update', NULL, '2021-03-08 00:01:08', NULL, '2021-03-08 00:01:08', NULL);
INSERT INTO `sys_menu` VALUES (31, 11, '/0/6/11/', '删除', 1030, NULL, 'main', 3, NULL, 0, 'sys:menu:delete', NULL, '2021-03-08 00:01:33', NULL, '2021-03-08 01:07:31', NULL);
INSERT INTO `sys_menu` VALUES (32, 12, '/0/6/12/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:param:save', NULL, '2021-03-08 00:02:00', NULL, '2021-03-08 00:02:00', NULL);
INSERT INTO `sys_menu` VALUES (33, 12, '/0/6/12/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:param:update', NULL, '2021-03-08 00:02:29', NULL, '2021-03-08 00:02:29', NULL);
INSERT INTO `sys_menu` VALUES (34, 12, '/0/6/12/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:param:delete', NULL, '2021-03-08 00:02:53', NULL, '2021-03-08 00:02:59', NULL);
INSERT INTO `sys_menu` VALUES (35, 13, '/0/6/13/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:dict:save', NULL, '2021-03-08 00:03:25', NULL, '2021-03-08 00:03:25', NULL);
INSERT INTO `sys_menu` VALUES (36, 13, '/0/6/13/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:dict:update', NULL, '2021-03-08 00:03:45', NULL, '2021-03-08 00:04:16', NULL);
INSERT INTO `sys_menu` VALUES (37, 13, '/0/6/13/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:dict:delete', NULL, '2021-03-08 00:04:09', NULL, '2021-03-08 00:04:09', NULL);
INSERT INTO `sys_menu` VALUES (38, 14, '/0/6/14/', '上传', 1000, NULL, 'main', 3, NULL, 1, 'sys:file:save', NULL, '2021-03-08 00:05:18', NULL, '2021-03-08 00:05:18', NULL);
INSERT INTO `sys_menu` VALUES (39, 14, '/0/6/14/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:file:delete', NULL, '2021-03-08 00:05:40', NULL, '2021-03-08 00:05:40', NULL);
INSERT INTO `sys_menu` VALUES (40, 14, '/0/6/14/', '下载', 1020, NULL, 'main', 3, NULL, 1, 'sys:file:download', NULL, '2021-03-08 00:06:28', NULL, '2021-03-08 00:06:28', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_copy1
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_copy1`;
CREATE TABLE `sys_menu_copy1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NOT NULL COMMENT '上级',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int(10) NOT NULL COMMENT '排序',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `target` varchar(20) DEFAULT NULL COMMENT '目标位置main,_blank',
  `type` tinyint(4) NOT NULL COMMENT '1:目录2:菜单3:按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) NOT NULL COMMENT '状态1.启用,0.停用',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(32) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`) USING BTREE,
  KEY `parent_ids` (`parent_ids`(191)),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu_copy1
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_copy1` VALUES (6, 0, '/0/', '系统管理', 10, NULL, NULL, 1, 'ion:settings-outline', 1, NULL, NULL, '2021-03-03 23:49:58', NULL, '2021-03-04 00:46:16', '');
INSERT INTO `sys_menu_copy1` VALUES (7, 6, '/0/6/', '部门管理', 10, '/sys/dept', 'main', 2, 'clarity:organization-line', 1, 'sys:dept:query', NULL, '2021-03-04 00:55:33', NULL, '2021-03-06 00:35:13', NULL);
INSERT INTO `sys_menu_copy1` VALUES (8, 0, '/0/', '首页', 9, '/sys/home', 'main', 2, 'bx:bx-home', 1, NULL, NULL, '2021-03-04 01:08:27', NULL, '2021-03-04 01:08:27', NULL);
INSERT INTO `sys_menu_copy1` VALUES (9, 6, '/0/6/', '用户管理', 1000, '/sys/user', 'main', 2, 'ant-design:user-switch-outlined', 1, 'sys:user:query', NULL, '2021-03-04 22:25:41', NULL, '2021-03-06 00:34:49', NULL);
INSERT INTO `sys_menu_copy1` VALUES (10, 6, '/0/6/', '角色管理', 1010, '/sys/role', 'main', 2, 'carbon:user-role', 1, 'sys:role:query', NULL, '2021-03-04 22:26:30', NULL, '2021-03-06 00:52:45', NULL);
INSERT INTO `sys_menu_copy1` VALUES (11, 6, '/0/6/', '菜单管理', 1019, '/sys/menu', 'main', 2, 'ic:sharp-menu-book', 1, 'sys:menu:query', NULL, '2021-03-04 22:27:15', NULL, '2021-03-06 00:52:54', NULL);
INSERT INTO `sys_menu_copy1` VALUES (12, 6, '/0/6/', '系统参数', 1020, '/sys/param', 'main', 2, 'zmdi:code-setting', 1, 'sys:param:query', NULL, '2021-03-04 22:27:45', NULL, '2021-03-06 00:53:03', NULL);
INSERT INTO `sys_menu_copy1` VALUES (13, 6, '/0/6/', '系统字典', 1030, '/sys/dict', 'main', 2, 'raphael:books', 1, 'sys:dict:query', NULL, '2021-03-04 22:29:43', NULL, '2021-03-06 00:53:14', NULL);
INSERT INTO `sys_menu_copy1` VALUES (14, 6, '/0/6/', '文件管理', 1040, '/sys/file', 'main', 2, 'akar-icons:file', 1, 'sys:file:query', NULL, '2021-03-04 22:30:25', NULL, '2021-03-06 00:53:22', NULL);
INSERT INTO `sys_menu_copy1` VALUES (17, 7, '/0/6/7/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:dept:save', NULL, '2021-03-07 23:51:47', NULL, '2021-03-08 00:42:09', NULL);
INSERT INTO `sys_menu_copy1` VALUES (18, 7, '/0/6/7/', '修改', 1010, NULL, 'main', 3, NULL, 1, 'sys:dept:update', NULL, '2021-03-07 23:52:14', NULL, '2021-03-07 23:52:14', NULL);
INSERT INTO `sys_menu_copy1` VALUES (19, 7, '/0/6/7/', '删除', 1020, NULL, 'main', 3, NULL, 1, 'sys:dept:delete', NULL, '2021-03-07 23:52:50', NULL, '2021-03-07 23:52:50', NULL);
INSERT INTO `sys_menu_copy1` VALUES (20, 9, '/0/6/9/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:user:save', NULL, '2021-03-07 23:54:06', NULL, '2021-03-07 23:54:06', NULL);
INSERT INTO `sys_menu_copy1` VALUES (21, 9, '/0/6/9/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:user:update', NULL, '2021-03-07 23:54:25', NULL, '2021-03-07 23:57:30', NULL);
INSERT INTO `sys_menu_copy1` VALUES (22, 9, '/0/6/9/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:user:delete', NULL, '2021-03-07 23:54:47', NULL, '2021-03-07 23:54:47', NULL);
INSERT INTO `sys_menu_copy1` VALUES (25, 10, '/0/6/10/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:role:save', NULL, '2021-03-07 23:58:04', NULL, '2021-03-07 23:58:04', NULL);
INSERT INTO `sys_menu_copy1` VALUES (26, 10, '/0/6/10/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:role:update', NULL, '2021-03-07 23:58:27', NULL, '2021-03-07 23:58:27', NULL);
INSERT INTO `sys_menu_copy1` VALUES (27, 10, '/0/6/10/', '分配', 1030, NULL, 'main', 3, NULL, 1, 'sys:role:assign', NULL, '2021-03-07 23:59:34', NULL, '2021-03-07 23:59:34', NULL);
INSERT INTO `sys_menu_copy1` VALUES (28, 10, '/0/6/10/', '删除', 1040, NULL, 'main', 3, NULL, 1, 'sys:role:delete', NULL, '2021-03-07 23:59:58', NULL, '2021-03-07 23:59:58', NULL);
INSERT INTO `sys_menu_copy1` VALUES (29, 11, '/0/6/11/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:menu:save', NULL, '2021-03-08 00:00:42', NULL, '2021-03-08 00:00:42', NULL);
INSERT INTO `sys_menu_copy1` VALUES (30, 11, '/0/6/11/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:menu:update', NULL, '2021-03-08 00:01:08', NULL, '2021-03-08 00:01:08', NULL);
INSERT INTO `sys_menu_copy1` VALUES (31, 11, '/0/6/11/', '删除', 1030, NULL, 'main', 3, NULL, 0, 'sys:menu:delete', NULL, '2021-03-08 00:01:33', NULL, '2021-03-08 01:07:31', NULL);
INSERT INTO `sys_menu_copy1` VALUES (32, 12, '/0/6/12/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:param:save', NULL, '2021-03-08 00:02:00', NULL, '2021-03-08 00:02:00', NULL);
INSERT INTO `sys_menu_copy1` VALUES (33, 12, '/0/6/12/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:param:update', NULL, '2021-03-08 00:02:29', NULL, '2021-03-08 00:02:29', NULL);
INSERT INTO `sys_menu_copy1` VALUES (34, 12, '/0/6/12/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:param:delete', NULL, '2021-03-08 00:02:53', NULL, '2021-03-08 00:02:59', NULL);
INSERT INTO `sys_menu_copy1` VALUES (35, 13, '/0/6/13/', '添加', 1000, NULL, 'main', 3, NULL, 1, 'sys:dict:save', NULL, '2021-03-08 00:03:25', NULL, '2021-03-08 00:03:25', NULL);
INSERT INTO `sys_menu_copy1` VALUES (36, 13, '/0/6/13/', '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:dict:update', NULL, '2021-03-08 00:03:45', NULL, '2021-03-08 00:04:16', NULL);
INSERT INTO `sys_menu_copy1` VALUES (37, 13, '/0/6/13/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:dict:delete', NULL, '2021-03-08 00:04:09', NULL, '2021-03-08 00:04:09', NULL);
INSERT INTO `sys_menu_copy1` VALUES (38, 14, '/0/6/14/', '上传', 1000, NULL, 'main', 3, NULL, 1, 'sys:file:save', NULL, '2021-03-08 00:05:18', NULL, '2021-03-08 00:05:18', NULL);
INSERT INTO `sys_menu_copy1` VALUES (39, 14, '/0/6/14/', '删除', 1030, NULL, 'main', 3, NULL, 1, 'sys:file:delete', NULL, '2021-03-08 00:05:40', NULL, '2021-03-08 00:05:40', NULL);
INSERT INTO `sys_menu_copy1` VALUES (40, 14, '/0/6/14/', '下载', 1020, NULL, 'main', 3, NULL, 1, 'sys:file:download', NULL, '2021-03-08 00:06:28', NULL, '2021-03-08 00:06:28', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `param_key` varchar(50) NOT NULL COMMENT '键',
  `param_value` varchar(100) NOT NULL COMMENT '值',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `create_by` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
BEGIN;
INSERT INTO `sys_param` VALUES (20, 'aaa', 'key-k1811', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-25 15:20:05', NULL);
INSERT INTO `sys_param` VALUES (21, 'key-n-19', 'key-k19', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:47', NULL);
INSERT INTO `sys_param` VALUES (22, 'key-n-20', 'key-k20', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (23, 'key-n-21', 'key-k21', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (24, 'key-n-22', 'key-k22', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (25, 'key-n-23', 'key-k23', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (26, 'key-n-24', 'key-k24', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (27, 'key-n-25', 'key-k25', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (28, 'key-n-26', 'key-k26', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (29, 'key-n-27', 'key-k27', '1', 0, 1, '2020-12-01 00:09:18', 1, '2020-12-23 17:43:36', NULL);
INSERT INTO `sys_param` VALUES (30, 'key-n-28', 'key-k28', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (31, 'key-n-29', 'key-k29', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (32, 'key-n-30', 'key-k30', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (33, 'key-n-31', 'key-k31', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (34, 'key-n-32', 'key-k32', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (35, 'key-n-33', 'key-k33', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (36, 'key-n-34', 'key-k34', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (37, 'key-n-35', 'key-k35', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (38, 'key-n-36', 'key-k36', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (39, 'key-n-37', 'key-k37', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (40, 'key-n-38', 'key-k38', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (41, 'key-n-39', 'key-k39', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (42, 'key-n-40', 'key-k40', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (43, 'key-n-41', 'key-k41', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (44, 'key-n-42', 'key-k42', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (45, 'key-n-43', 'key-k43', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (48, 'key-n-46', 'key-k46', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (49, 'key-n-47', 'key-k47', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (50, 'key-n-48', 'key-k48', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (51, 'key-n-49', 'key-k49', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (52, 'key-n-50', 'key-k50', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (53, 'key-n-51', 'key-k51', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (54, 'key-n-52', 'key-k52', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (55, 'key-n-53', 'key-k53', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (56, 'key-n-54', 'key-k54', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (57, 'key-n-55', 'key-k55', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (58, 'key-n-56', 'key-k56', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (59, 'key-n-57', 'key-k57', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (60, 'key-n-58', 'key-k58', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (61, 'key-n-59', 'key-k59', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (62, 'key-n-60', 'key-k60', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (63, 'key-n-61', 'key-k61', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (64, 'key-n-62', 'key-k62', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (65, 'key-n-63', 'key-k63', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (66, 'key-n-64', 'key-k64', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (67, 'key-n-65', 'key-k65', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (68, 'key-n-66', 'key-k66', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (69, 'key-n-67', 'key-k67', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (70, 'key-n-68', 'key-k68', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (71, 'key-n-69', 'key-k69', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (72, 'key-n-70', 'key-k70', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (73, 'key-n-71', 'key-k71', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (74, 'key-n-72', 'key-k72', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (75, 'key-n-73', 'key-k73', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (76, 'key-n-74', 'key-k74', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (77, 'key-n-75', 'key-k75', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (78, 'key-n-76', 'key-k76', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (79, 'key-n-77', 'key-k77', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (80, 'key-n-78', 'key-k78', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (81, 'key-n-79', 'key-k79', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (82, 'key-n-80', 'key-k80', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (83, 'key-n-81', 'key-k81', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (84, 'key-n-82', 'key-k82', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (85, 'key-n-83', 'key-k83', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (86, 'key-n-84', 'key-k84', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (87, 'key-n-85', 'key-k85', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (88, 'key-n-86', 'key-k86', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (89, 'key-n-87', 'key-k87', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (90, 'key-n-88', 'key-k88', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (91, 'key-n-89', 'key-k89', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (92, 'key-n-90', 'key-k90', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (93, 'key-n-91', 'key-k91', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (94, 'key-n-92', 'key-k92', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (95, 'key-n-93', 'key-k93', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (96, 'key-n-94', 'key-k94', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (97, 'key-n-95', 'key-k95', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (98, 'key-n-96', 'key-k96', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (99, 'key-n-97', 'key-k97', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (100, 'key-n-98', 'key-k98', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (101, 'key-n-99', 'key-k99', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (102, 'key-n-100', 'key-k100', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (103, 'key-n-101', 'key-k101', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (104, 'key-n-102', 'key-k102', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (105, 'key-n-103', 'key-k103', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (106, 'key-n-104', 'key-k104', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (107, 'key-n-105', 'key-k105', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (108, 'key-n-106', 'key-k106', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (109, 'key-n-107', 'key-k107', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (110, 'key-n-108', 'key-k108', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (111, 'key-n-109', 'key-k109', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (112, 'key-n-110', 'key-k110', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (113, 'key-n-111', 'key-k111', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (114, 'key-n-112', 'key-k112', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (115, 'key-n-113', 'key-k113', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (116, 'key-n-114', 'key-k114', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (117, 'key-n-115', 'key-k115', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (118, 'key-n-116', 'key-k116', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (119, 'key-n-117', 'key-k117', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (120, 'key-n-118', 'key-k118', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (121, 'key-n-119', 'key-k119', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (122, 'key-n-120', 'key-k120', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (123, 'key-n-121', 'key-k121', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (124, 'key-n-122', 'key-k122', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (125, 'key-n-123', 'key-k123', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (126, 'key-n-124', 'key-k124', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (127, 'key-n-125', 'key-k125', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (128, 'key-n-126', 'key-k126', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (129, 'key-n-127', 'key-k127', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (130, 'key-n-128', 'key-k128', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (131, 'key-n-129', 'key-k129', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (132, 'key-n-130', 'key-k130', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (133, 'key-n-131', 'key-k131', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (134, 'key-n-132', 'key-k132', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (135, 'key-n-133', 'key-k133', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (136, 'key-n-134', 'key-k134', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (137, 'key-n-135', 'key-k135', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (138, 'key-n-136', 'key-k136', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (139, 'key-n-137', 'key-k137', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (140, 'key-n-138', 'key-k138', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (141, 'key-n-139', 'key-k139', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (142, 'key-n-140', 'key-k140', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (143, 'key-n-141', 'key-k141', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (144, 'key-n-142', 'key-k142', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (145, 'key-n-143', 'key-k143', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (146, 'key-n-144', 'key-k144', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (147, 'key-n-145', 'key-k145', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (148, 'key-n-146', 'key-k146', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (149, 'key-n-147', 'key-k147', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (150, 'key-n-148', 'key-k148', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (151, 'key-n-149', 'key-k149', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (152, 'key-n-150', 'key-k150', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (153, 'key-n-151', 'key-k151', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (154, 'key-n-152', 'key-k152', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (155, 'key-n-153', 'key-k153', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (156, 'key-n-154', 'key-k154', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (157, 'key-n-155', 'key-k155', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (158, 'key-n-156', 'key-k156', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (159, 'key-n-157', 'key-k157', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:48', NULL);
INSERT INTO `sys_param` VALUES (160, 'key-n-158', 'key-k158', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (161, 'key-n-159', 'key-k159', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (162, 'key-n-160', 'key-k160', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (163, 'key-n-161', 'key-k161', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (164, 'key-n-162', 'key-k162', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (165, 'key-n-163', 'key-k163', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (166, 'key-n-164', 'key-k164', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (167, 'key-n-165', 'key-k165', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (168, 'key-n-166', 'key-k166', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (169, 'key-n-167', 'key-k167', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (170, 'key-n-168', 'key-k168', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (171, 'key-n-169', 'key-k169', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (172, 'key-n-170', 'key-k170', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (173, 'key-n-171', 'key-k171', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (174, 'key-n-172', 'key-k172', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (175, 'key-n-173', 'key-k173', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (176, 'key-n-174', 'key-k174', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (177, 'key-n-175', 'key-k175', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (178, 'key-n-176', 'key-k176', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (179, 'key-n-177', 'key-k177', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (180, 'key-n-178', 'key-k178', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (181, 'key-n-179', 'key-k179', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (182, 'key-n-180', 'key-k180', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (183, 'key-n-181', 'key-k181', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (184, 'key-n-182', 'key-k182', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (185, 'key-n-183', 'key-k183', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (186, 'key-n-184', 'key-k184', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (187, 'key-n-185', 'key-k185', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (188, 'key-n-186', 'key-k186', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (189, 'key-n-187', 'key-k187', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (190, 'key-n-188', 'key-k188', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (191, 'key-n-189', 'key-k189', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (192, 'key-n-190', 'key-k190', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (193, 'key-n-191', 'key-k191', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (194, 'key-n-192', 'key-k192', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (195, 'key-n-193', 'key-k193', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (196, 'key-n-194', 'key-k194', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (197, 'key-n-195', 'key-k195', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (198, 'key-n-196', 'key-k196', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (199, 'key-n-197', 'key-k197', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (200, 'key-n-198', 'key-k198', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (201, 'key-n-199', 'key-k199', '1', 1, 1, '2020-12-01 00:09:18', 1, '2020-12-21 01:55:49', NULL);
INSERT INTO `sys_param` VALUES (203, '黄登峰', '1', '1', 1, NULL, '2020-12-23 12:46:32', NULL, '2020-12-23 12:46:32', NULL);
INSERT INTO `sys_param` VALUES (215, '111', '黄', '11', 1, NULL, '2020-12-23 12:52:29', NULL, '2021-01-15 03:11:25', '111');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `data_scope` tinyint(4) NOT NULL COMMENT '数据范围:0:全部，1：本部门，2：本部门及以下，3.下级部门，4.指定部门，5.本人',
  `status` tinyint(4) NOT NULL COMMENT '0停用1启用',
  `create_by` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(32) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `create_date` (`create_time`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `data_scope` (`data_scope`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (125, '222', '系统管理员', 3, 1, NULL, '2021-01-26 01:19:35', NULL, '2021-03-05 00:05:58', NULL);
INSERT INTO `sys_role` VALUES (126, '333', '管理员222', 4, 1, NULL, '2021-01-26 01:19:51', NULL, '2021-01-27 01:29:27', NULL);
INSERT INTO `sys_role` VALUES (127, '44', '部门管理员', 0, 1, NULL, '2021-01-26 01:22:33', NULL, '2021-03-03 00:21:07', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `dept_id` varchar(32) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`role_id`,`dept_id`),
  KEY `role_id` (`role_id`),
  KEY `dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色部门';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `menu_id` int(11) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (125, 9);
INSERT INTO `sys_role_menu` VALUES (125, 10);
INSERT INTO `sys_role_menu` VALUES (125, 11);
INSERT INTO `sys_role_menu` VALUES (125, 12);
INSERT INTO `sys_role_menu` VALUES (125, 13);
INSERT INTO `sys_role_menu` VALUES (125, 14);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门',
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `mobile` varchar(20) NOT NULL COMMENT '手机',
  `photo` varchar(100) DEFAULT NULL COMMENT '头像',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `status` tinyint(11) NOT NULL COMMENT '状态1：正常，0：禁用',
  `create_by` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `dept_id` (`dept_id`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_time`) USING BTREE,
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, NULL, 'admin', '$2a$10$9SUnFeHnLZm4evVsyIOLkuftrQOUiuMh52MMLAuAVI9Li29SyuNJi', '888882q31', '13249073372', '/2021/3/11/6e4d51f5b5934e5eaa29e515037e865f.jpg', '21221@qq.com', 1, NULL, '2021-01-19 00:57:42', NULL, '2021-03-11 02:00:56', '');
INSERT INTO `sys_user` VALUES (2, 11, '黄灯南', '$2a$10$j50Q.X3QJGK7GyCBclAJJ.TSbBjlsZgX.Hc5jOCwqgms1aaDPvZuy', '黄登峰', '13249074472', NULL, NULL, 1, NULL, '2021-01-18 23:13:51', NULL, '2021-03-03 00:05:50', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (2, 125);
INSERT INTO `sys_user_role` VALUES (1, 126);
INSERT INTO `sys_user_role` VALUES (2, 126);
INSERT INTO `sys_user_role` VALUES (2, 128);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
