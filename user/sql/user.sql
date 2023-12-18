/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : user_db

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 14/12/2023 15:50:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'username',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `headimgurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '微信openId',
  `permissions` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：默认1启用、0禁用',
  `is_del` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除：默认0未删除、1已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'W15T8FS3s534XGrszv2a63799rs7iVqq$GDnvceOERD0cmDmLQxnpXb3xwlc3G1X/r4r5rogGgYUnB1Qb1sArvtDSLlcP4hUvOOcRsEgwITvyLIz7VtlpMg==', NULL, NULL, '', '', 'super_admin', 1, 0, '2023-10-27 16:35:48', '2023-12-05 17:11:41');
INSERT INTO `user` VALUES (2, 'user', '7N110GTYi5P6sl9z7udFOCP9t15E4y7w$mdXLuXYsVHyrPgIpEL0R2rZESHHDC/G1C1jqeISPr4DZhXMBMAuAVX34hbI4h/DI/9AWfOplNsZWFzpADdFQLw==', NULL, NULL, NULL, NULL, NULL, 1, 0, '2023-11-17 09:55:46', '2023-11-17 09:55:46');

SET FOREIGN_KEY_CHECKS = 1;
