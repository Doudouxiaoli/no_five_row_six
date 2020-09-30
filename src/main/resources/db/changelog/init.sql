SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wechat_user
-- ----------------------------
DROP TABLE IF EXISTS `wechat_user`;
CREATE TABLE `wechat_user`  (
  `wu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wu_openid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'openid',
  `wu_nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `wu_sex` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `wu_city` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `wu_country` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '国家',
  `wu_province` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份',
  `wu_language` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '语言',
  `wu_headimgurl` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `wu_unionid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'unionid',
  `wu_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `wu_headimg` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像本地路径',
  `wu_groupid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户分组id',
  `wu_tagid_list` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签ID列表',
  `wu_subscribe` int(11) NULL DEFAULT NULL COMMENT '是否订阅该公众号标识（0：未关注；1：关注）',
  `wu_subscribe_time` bigint(20) NULL DEFAULT NULL COMMENT '关注时间',
  `wu_create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `wu_update_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`wu_id`) USING BTREE,
  INDEX `open_id`(`wu_openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '微信用户表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `su_id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `su_name` varchar(64) default NULL COMMENT '姓名',
  `su_login_name` varchar(64) default NULL COMMENT '登录名',
  `su_password` varchar(64) default NULL COMMENT '密码',
  `su_create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `su_update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY  (`su_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of bte_sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '管理员', 'admin', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b',null ,null );
-- ----------------------------
-- Table structure for frs_zyx_news
-- ----------------------------
DROP TABLE IF EXISTS `frs_zyx_news`;
CREATE TABLE `frs_zyx_news` (
  `zn_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `zn_nc_id`  bigint(20) DEFAULT NULL COMMENT '模板id',
  `zn_from_id` bigint(20) DEFAULT NULL COMMENT '来源id',
  `zn_from` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '来源名称',
  `zn_pace` bigint(20) DEFAULT NULL COMMENT '当前期数',
  `zn_title` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `zn_address` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地点',
  `zn_title_one` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备用名称1',
  `zn_title_two` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备用名称2',
  `zn_thumbnail_path` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '缩略图路径',
  `zn_carousel_path` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '轮播图路径',
  `zn_intro` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `zn_content` longtext COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `zn_video_path` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '视频路径',
  `zn_banner_pic_path` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头部路径',
  `zn_length` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时长',
  `zn_date` bigint(20) DEFAULT NULL COMMENT '显示用发布日期',
  `zn_start_time` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `zn_end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `zn_hits` int(11) DEFAULT NULL COMMENT '点击量',
  `zn_like_num` int(11) DEFAULT NULL COMMENT '点赞量',
  `zn_book_num` int(11) DEFAULT NULL COMMENT '收藏量',
  `zn_publish_user_id` bigint(20) DEFAULT NULL COMMENT '发布人id',
  `zn_publish_user_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布人姓名',
  `zn_create_time` bigint(20) DEFAULT NULL COMMENT '发布时间',
  `zn_update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `zn_update_user_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人姓名',
  `zn_update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `zn_is_valid` int(11) DEFAULT NULL COMMENT '是否有效(0：无效；1：有效)',
  `zn_is_carousel` int(11) DEFAULT NULL COMMENT '是否显示轮播图,(0,不显示,1:显示)',
  `zn_end_date` bigint(20) DEFAULT NULL COMMENT '直播结束时间',
  `zn_nc_ids` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类id（可选多个）',
  `zn_tag_ids` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容标签（以逗号分隔）',
  PRIMARY KEY (`zn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通用表';
-- ----------------------------
-- Table structure for frs_zyx_visit_log
-- ----------------------------
DROP TABLE IF EXISTS `frs_zyx_visit_log`;
CREATE TABLE `frs_zyx_visit_log` (
  `zvl_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `zvl_user_id` bigint(20) DEFAULT NULL COMMENT '用户主键',
  `zvl_start_time` bigint(20) DEFAULT NULL COMMENT '访问页面开始时间',
  `zvl_end_time` bigint(20) DEFAULT NULL COMMENT '访问页面结束时间',
  `zvl_duration` bigint(20) DEFAULT NULL COMMENT '访问页面时长(秒)',
  `zvl_visit_module` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '观看模块',
  `zvl_news_module` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资讯所属模块',
  `zvl_news_id` bigint(20) DEFAULT NULL COMMENT '资讯主键',
  `zvl_include_video` int(11) DEFAULT NULL COMMENT '是否包含视频(0：不包含，1：包含)',
  `zvl_ip` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ip地址',
  `zvl_like` int(11) DEFAULT NULL COMMENT '是否点赞(0：未点赞；1: 点赞；2：取消点赞)',
  `zvl_like_time` bigint(20) DEFAULT NULL COMMENT '点赞时间/取消点赞时间',
  `zvl_favorite` int(11) DEFAULT NULL COMMENT '是否收藏(0：未收藏；1: 收藏；2：取消收藏)',
  `zvl_favorite_time` bigint(20) DEFAULT NULL COMMENT '收藏时间/取消收藏时间',
  `zvl_vr_id` bigint(20) DEFAULT NULL COMMENT '视频观看记录主键',
  PRIMARY KEY (`zvl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='访问记录日志表';
-- ----------------------------
-- Table structure for frs_zyx_like_record
-- ----------------------------
DROP TABLE IF EXISTS `frs_zyx_like_record`;
CREATE TABLE `frs_zyx_like_record` (
  `zlr_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `zlr_user_id` bigint(20) DEFAULT NULL COMMENT '用户主键',
  `zlr_news_id` bigint(20) DEFAULT NULL COMMENT '资讯主键',
  `zlr_time` bigint(20) DEFAULT NULL COMMENT '点赞时间',
  `zlr_comment_id` bigint(20) DEFAULT NULL COMMENT '评论记录id',
  `zlr_type` int(11) DEFAULT NULL COMMENT '类型（0：资讯点赞，1：评论点赞）',
  PRIMARY KEY (`zlr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点赞表';
