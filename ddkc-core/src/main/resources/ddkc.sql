/*
 Navicat Premium Data Transfer

 Source Server         : 本地库
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : goodboys

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 17/12/2018 17:23:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE "flyway_schema_history" (
  "installed_rank" int(11) NOT NULL,
  "version" varchar(50) DEFAULT NULL,
  "description" varchar(200) NOT NULL,
  "type" varchar(20) NOT NULL,
  "script" varchar(1000) NOT NULL,
  "checksum" int(11) DEFAULT NULL,
  "installed_by" varchar(100) NOT NULL,
  "installed_on" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "execution_time" int(11) NOT NULL,
  "success" tinyint(1) NOT NULL,
  PRIMARY KEY ("installed_rank"),
  KEY "flyway_schema_history_s_idx" ("success")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of flyway_schema_history
-- ----------------------------
BEGIN;
INSERT INTO `flyway_schema_history` VALUES (1, '1', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', NULL, 'root', '2018-12-17 16:55:44', 0, 1);
INSERT INTO `flyway_schema_history` VALUES (2, '1.0.1', 'addColumnBySystem', 'SQL', 'V1_0_1__addColumnBySystem.sql', 424685091, 'root', '2018-12-17 17:05:13', 135, 1);
COMMIT;

-- ----------------------------
-- Table structure for r_knowledge_question
-- ----------------------------
DROP TABLE IF EXISTS `r_knowledge_question`;
CREATE TABLE "r_knowledge_question" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '试题与知识点的关联关系ID',
  "knowledge_id" int(11) DEFAULT NULL COMMENT '知识点ID',
  "question_id" int(11) DEFAULT NULL COMMENT '试题ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_knowledge_question_t_knowledge" ("knowledge_id"),
  KEY "fk_r_knowledge_question_t_question_bank" ("question_id"),
  CONSTRAINT "fk_r_knowledge_question_t_knowledge" FOREIGN KEY ("knowledge_id") REFERENCES "t_knowledge" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_knowledge_question_t_question_bank" FOREIGN KEY ("question_id") REFERENCES "t_question_bank" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点与试题的关联关系';

-- ----------------------------
-- Table structure for r_knowledge_subject
-- ----------------------------
DROP TABLE IF EXISTS `r_knowledge_subject`;
CREATE TABLE "r_knowledge_subject" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '学科与知识点的关联关系表',
  "knowledge_id" int(11) DEFAULT NULL COMMENT '知识点ID',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_knowledge_subject_t_knowledge" ("knowledge_id"),
  KEY "fk_r_knowledge_subject_t_subject" ("subject_id"),
  CONSTRAINT "fk_r_knowledge_subject_t_knowledge" FOREIGN KEY ("knowledge_id") REFERENCES "t_knowledge" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_knowledge_subject_t_subject" FOREIGN KEY ("subject_id") REFERENCES "t_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点与学科的关联关系表';

-- ----------------------------
-- Table structure for r_limit_practice_classes
-- ----------------------------
DROP TABLE IF EXISTS `r_limit_practice_classes`;
CREATE TABLE "r_limit_practice_classes" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '关联关系ID',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  "limit_id" int(11) DEFAULT NULL COMMENT '限时练习ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_limit_practice_classes_t_classes" ("class_id"),
  KEY "fk_r_limit_practice_classes_t_limit_time_practice" ("limit_id"),
  CONSTRAINT "fk_r_limit_practice_classes_t_classes" FOREIGN KEY ("class_id") REFERENCES "t_classes" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_limit_practice_classes_t_limit_time_practice" FOREIGN KEY ("limit_id") REFERENCES "t_limit_time_practice" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限时练习与班级关联关系';

-- ----------------------------
-- Table structure for r_limit_practice_question
-- ----------------------------
DROP TABLE IF EXISTS `r_limit_practice_question`;
CREATE TABLE "r_limit_practice_question" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '关联关系ID',
  "limit_id" int(11) DEFAULT NULL COMMENT '限时练习ID',
  "question_id" int(11) DEFAULT NULL COMMENT '题库ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_limit_practice_question_t_limit_time_practice" ("limit_id"),
  KEY "fk_r_limit_practice_question_t_question_bank" ("question_id"),
  CONSTRAINT "fk_r_limit_practice_question_t_limit_time_practice" FOREIGN KEY ("limit_id") REFERENCES "t_limit_time_practice" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_limit_practice_question_t_question_bank" FOREIGN KEY ("question_id") REFERENCES "t_question_bank" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限时练习与题库关联关系表';

-- ----------------------------
-- Table structure for r_special_practice_classes
-- ----------------------------
DROP TABLE IF EXISTS `r_special_practice_classes`;
CREATE TABLE "r_special_practice_classes" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '关联关系ID',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  "special_id" int(11) DEFAULT NULL COMMENT '专项练习ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_special_practice_classes_t_classes" ("class_id"),
  KEY "fk_r_special_practice_classes_t_special_practice" ("special_id"),
  CONSTRAINT "fk_r_special_practice_classes_t_classes" FOREIGN KEY ("class_id") REFERENCES "t_classes" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_special_practice_classes_t_special_practice" FOREIGN KEY ("special_id") REFERENCES "t_special_practice" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专项练习与班级关联关系表';

-- ----------------------------
-- Table structure for r_special_practice_question
-- ----------------------------
DROP TABLE IF EXISTS `r_special_practice_question`;
CREATE TABLE "r_special_practice_question" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '关联关系ID',
  "special_id" int(11) DEFAULT NULL COMMENT '专项练习ID',
  "question_id" int(11) DEFAULT NULL COMMENT '题库ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_special_practice_question_t_question_bank" ("question_id"),
  KEY "fk_r_special_practice_question_t_special_practice" ("special_id"),
  CONSTRAINT "fk_r_special_practice_question_t_question_bank" FOREIGN KEY ("question_id") REFERENCES "t_question_bank" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_special_practice_question_t_special_practice" FOREIGN KEY ("special_id") REFERENCES "t_special_practice" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专项练习与练习题关联关系表';

-- ----------------------------
-- Table structure for r_stage_question
-- ----------------------------
DROP TABLE IF EXISTS `r_stage_question`;
CREATE TABLE "r_stage_question" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '教案阶段与练习题关联关系ID',
  "teaching_plan_id" int(11) DEFAULT NULL COMMENT '教案ID',
  "stage_id" int(11) DEFAULT NULL COMMENT '阶段ID',
  "question_id" int(11) DEFAULT NULL COMMENT '试题ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_stage_question_t_question_bank" ("question_id"),
  KEY "fk_r_stage_question_t_stage" ("stage_id"),
  KEY "fk_r_stage_question_t_teaching_plan" ("teaching_plan_id"),
  CONSTRAINT "fk_r_stage_question_t_question_bank" FOREIGN KEY ("question_id") REFERENCES "t_question_bank" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_stage_question_t_stage" FOREIGN KEY ("stage_id") REFERENCES "t_stage" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_stage_question_t_teaching_plan" FOREIGN KEY ("teaching_plan_id") REFERENCES "t_teaching_plan" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教案阶段与练习题的关联关系';

-- ----------------------------
-- Table structure for r_student_classes
-- ----------------------------
DROP TABLE IF EXISTS `r_student_classes`;
CREATE TABLE "r_student_classes" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '学生与班级的关联关系表ID',
  "stu_id" int(11) DEFAULT NULL COMMENT '学生ID',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_student_classes_t_classes" ("class_id"),
  KEY "fk_r_student_classes_t_student" ("stu_id"),
  CONSTRAINT "fk_r_student_classes_t_classes" FOREIGN KEY ("class_id") REFERENCES "t_classes" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_student_classes_t_student" FOREIGN KEY ("stu_id") REFERENCES "t_student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生与班级的关联关系表';

-- ----------------------------
-- Table structure for r_sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `r_sys_role_resource`;
CREATE TABLE "r_sys_role_resource" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "role_id" int(11) NOT NULL DEFAULT '0' COMMENT '角色组ID',
  "resource_id" int(11) NOT NULL COMMENT '资源功能ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_sys_role_resource_t_sys_resource" ("resource_id"),
  KEY "fk_r_sys_role_resource_t_sys_role" ("role_id"),
  CONSTRAINT "fk_r_sys_role_resource_t_sys_resource" FOREIGN KEY ("resource_id") REFERENCES "t_sys_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_sys_role_resource_t_sys_role" FOREIGN KEY ("role_id") REFERENCES "t_sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15550 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限对应请求';

-- ----------------------------
-- Records of r_sys_role_resource
-- ----------------------------
BEGIN;
INSERT INTO `r_sys_role_resource` VALUES (15513, 13, 6);
INSERT INTO `r_sys_role_resource` VALUES (15514, 13, 786);
INSERT INTO `r_sys_role_resource` VALUES (15515, 13, 787);
INSERT INTO `r_sys_role_resource` VALUES (15516, 13, 788);
INSERT INTO `r_sys_role_resource` VALUES (15517, 13, 789);
INSERT INTO `r_sys_role_resource` VALUES (15518, 13, 790);
INSERT INTO `r_sys_role_resource` VALUES (15519, 13, 791);
INSERT INTO `r_sys_role_resource` VALUES (15520, 13, 792);
INSERT INTO `r_sys_role_resource` VALUES (15521, 13, 793);
INSERT INTO `r_sys_role_resource` VALUES (15522, 13, 794);
INSERT INTO `r_sys_role_resource` VALUES (15523, 13, 795);
INSERT INTO `r_sys_role_resource` VALUES (15524, 13, 796);
INSERT INTO `r_sys_role_resource` VALUES (15525, 13, 797);
INSERT INTO `r_sys_role_resource` VALUES (15526, 13, 798);
INSERT INTO `r_sys_role_resource` VALUES (15527, 13, 799);
INSERT INTO `r_sys_role_resource` VALUES (15528, 13, 800);
INSERT INTO `r_sys_role_resource` VALUES (15529, 13, 801);
INSERT INTO `r_sys_role_resource` VALUES (15530, 13, 802);
INSERT INTO `r_sys_role_resource` VALUES (15531, 14, 6);
INSERT INTO `r_sys_role_resource` VALUES (15532, 14, 786);
INSERT INTO `r_sys_role_resource` VALUES (15533, 14, 787);
INSERT INTO `r_sys_role_resource` VALUES (15534, 14, 788);
INSERT INTO `r_sys_role_resource` VALUES (15535, 14, 789);
INSERT INTO `r_sys_role_resource` VALUES (15536, 14, 790);
INSERT INTO `r_sys_role_resource` VALUES (15537, 14, 791);
INSERT INTO `r_sys_role_resource` VALUES (15538, 14, 792);
INSERT INTO `r_sys_role_resource` VALUES (15539, 15, 6);
INSERT INTO `r_sys_role_resource` VALUES (15540, 15, 793);
INSERT INTO `r_sys_role_resource` VALUES (15541, 15, 794);
INSERT INTO `r_sys_role_resource` VALUES (15542, 15, 795);
INSERT INTO `r_sys_role_resource` VALUES (15543, 15, 796);
INSERT INTO `r_sys_role_resource` VALUES (15544, 15, 797);
INSERT INTO `r_sys_role_resource` VALUES (15545, 15, 798);
INSERT INTO `r_sys_role_resource` VALUES (15546, 15, 799);
INSERT INTO `r_sys_role_resource` VALUES (15547, 15, 800);
INSERT INTO `r_sys_role_resource` VALUES (15548, 15, 801);
INSERT INTO `r_sys_role_resource` VALUES (15549, 15, 802);
COMMIT;

-- ----------------------------
-- Table structure for r_sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `r_sys_role_user`;
CREATE TABLE "r_sys_role_user" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "uid" int(11) NOT NULL,
  "role_id" int(11) NOT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of r_sys_role_user
-- ----------------------------
BEGIN;
INSERT INTO `r_sys_role_user` VALUES (91, 1, 4);
INSERT INTO `r_sys_role_user` VALUES (97, 58, 13);
INSERT INTO `r_sys_role_user` VALUES (101, 62, 14);
COMMIT;

-- ----------------------------
-- Table structure for r_tag_subject
-- ----------------------------
DROP TABLE IF EXISTS `r_tag_subject`;
CREATE TABLE "r_tag_subject" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '关联关系ID',
  "tag_id" int(11) DEFAULT NULL COMMENT '标签ID',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  PRIMARY KEY ("id"),
  KEY "fk_r_tag_subject_t_subject" ("subject_id"),
  KEY "fk_r_tag_subject_t_tag" ("tag_id"),
  CONSTRAINT "fk_r_tag_subject_t_subject" FOREIGN KEY ("subject_id") REFERENCES "t_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_r_tag_subject_t_tag" FOREIGN KEY ("tag_id") REFERENCES "t_tag" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签与学科关联关系表';

-- ----------------------------
-- Table structure for t_account_log
-- ----------------------------
DROP TABLE IF EXISTS `t_account_log`;
CREATE TABLE "t_account_log" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '操作日志ID',
  "area" varchar(512) DEFAULT NULL COMMENT '区域',
  "methods" varchar(512) DEFAULT NULL COMMENT '方法名',
  "paths" varchar(512) DEFAULT NULL COMMENT '请求全路径',
  "params" varchar(512) DEFAULT NULL COMMENT '请求参数',
  "descr" varchar(512) DEFAULT NULL COMMENT '请求描述',
  "addr" varchar(512) DEFAULT NULL COMMENT '省市县',
  "ip" varchar(20) DEFAULT NULL COMMENT 'IP地址',
  "account_id" int(11) DEFAULT NULL COMMENT '操作人ID',
  "account_time" datetime DEFAULT NULL COMMENT '操作时间',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  PRIMARY KEY ("id"),
  KEY "fk_t_account_log_t_sys_user" ("account_id"),
  CONSTRAINT "fk_t_account_log_t_sys_user" FOREIGN KEY ("account_id") REFERENCES "t_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- ----------------------------
-- Table structure for t_banner
-- ----------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE "t_banner" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT 'bannerID',
  "school_id" int(11) DEFAULT NULL COMMENT '加盟校ID',
  "name" varchar(512) DEFAULT NULL COMMENT 'banner名称',
  "url" varchar(512) DEFAULT NULL COMMENT '链接地址',
  "cover" varchar(512) DEFAULT NULL COMMENT '封面',
  "app_type" int(11) DEFAULT NULL COMMENT 'app 端口。0表示家长端；1表示教师端',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示已删除',
  "craetor_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "weight" int(11) DEFAULT NULL COMMENT '权重顺序',
  PRIMARY KEY ("id"),
  KEY "fk_t_banner_t_join_school" ("school_id"),
  CONSTRAINT "fk_t_banner_t_join_school" FOREIGN KEY ("school_id") REFERENCES "t_join_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='banner表';

-- ----------------------------
-- Table structure for t_classes
-- ----------------------------
DROP TABLE IF EXISTS `t_classes`;
CREATE TABLE "t_classes" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  "class_name" varchar(512) DEFAULT NULL COMMENT '班级名称',
  "class_code" varchar(512) DEFAULT NULL COMMENT '班级代码',
  "begin_year" int(11) DEFAULT NULL COMMENT '开班年份',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  "grade_id" int(11) DEFAULT NULL COMMENT '年级ID',
  "class_teacher" int(11) DEFAULT NULL COMMENT '班主任ID',
  "class_teacher_name" varchar(512) DEFAULT NULL COMMENT '班主任名称',
  "instructor" int(11) DEFAULT NULL COMMENT '任课老师ID',
  "instructor_name" varchar(512) DEFAULT NULL COMMENT '任课老师名称',
  "class_form" int(11) DEFAULT NULL COMMENT '班级形式。0表示标准班；1表示非标准班',
  "class_type" int(11) DEFAULT NULL COMMENT '班级类型。0表示普通班；1表示金牌班',
  "begin_time" datetime DEFAULT NULL COMMENT '开班时间',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示停班；2表示已删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "student_counts" int(11) DEFAULT NULL COMMENT '学生数量（冗余字段）',
  PRIMARY KEY ("id"),
  KEY "fk_t_classes_t_grade" ("grade_id"),
  KEY "fk_t_classes_t_join_school" ("school_id"),
  KEY "fk_t_classes_t_subject" ("subject_id"),
  CONSTRAINT "fk_t_classes_t_grade" FOREIGN KEY ("grade_id") REFERENCES "t_grade" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_classes_t_join_school" FOREIGN KEY ("school_id") REFERENCES "t_join_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_classes_t_subject" FOREIGN KEY ("subject_id") REFERENCES "t_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级管理表';

-- ----------------------------
-- Table structure for t_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE "t_dictionary" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '系统字典ID',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "name" varchar(512) DEFAULT NULL COMMENT '字典名称',
  "val" varchar(512) DEFAULT NULL COMMENT '字典值',
  "parent_id" int(11) DEFAULT NULL COMMENT '上一级ID',
  "parent_name" varchar(512) DEFAULT NULL COMMENT '上一级名称',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示已删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建人',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

-- ----------------------------
-- Table structure for t_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE "t_feedback" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  "types" int(11) DEFAULT NULL COMMENT '反馈类型。0表示Android；1表示iOS',
  "app_type" int(11) DEFAULT NULL COMMENT 'app类型。0表示家长端；1表示教师端',
  "title" varchar(512) DEFAULT NULL COMMENT '反馈标题',
  "content" text COMMENT '反馈内容',
  "user_id" int(11) DEFAULT NULL COMMENT '反馈用户ID。（可能是老师，也可能是学生，也可能是家长）',
  "school_id" int(11) DEFAULT NULL COMMENT '所属学校ID',
  "school_name" varchar(512) DEFAULT NULL COMMENT '所属学校',
  "annex" varchar(512) DEFAULT NULL COMMENT '附件图片下载地址，多个用`#`号隔开',
  "status" int(11) DEFAULT NULL COMMENT '处理状态。0表示待处理；1表示挂起；2表示已处理',
  "action_content" text COMMENT '处理内容',
  "creator_id" int(11) DEFAULT NULL COMMENT '操作人ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '操作人',
  "create_time" datetime DEFAULT NULL COMMENT '反馈时间',
  "update_time" datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='信息反馈表';

-- ----------------------------
-- Table structure for t_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE "t_grade" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '年级表',
  "name" varchar(512) DEFAULT NULL COMMENT '年级名称',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  "remark" text COMMENT '年级说明',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示已删除',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建人ID',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY ("id"),
  KEY "fk_t_grade_t_subject" ("subject_id"),
  CONSTRAINT "fk_t_grade_t_subject" FOREIGN KEY ("subject_id") REFERENCES "t_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='年级管理表';

-- ----------------------------
-- Table structure for t_join_school
-- ----------------------------
DROP TABLE IF EXISTS `t_join_school`;
CREATE TABLE "t_join_school" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '加盟校ID',
  "school_name" varchar(512) DEFAULT NULL COMMENT '学校名称',
  "contact" varchar(512) DEFAULT NULL COMMENT '联系人',
  "phone" varchar(11) DEFAULT NULL COMMENT '联系电话',
  "province" varchar(512) DEFAULT NULL COMMENT '所在省份',
  "city" varchar(512) DEFAULT NULL COMMENT '所在城市',
  "area" varchar(512) DEFAULT NULL COMMENT '所在区县',
  "address" text COMMENT '详细地址',
  "join_date" datetime DEFAULT NULL COMMENT '加盟时间',
  "user_id" int(11) DEFAULT NULL COMMENT '登录账号ID',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示已删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建用户ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "subjects" text COMMENT '加盟学科信息',
  "expire_date" datetime DEFAULT NULL COMMENT '加盟到期时间',
  PRIMARY KEY ("id"),
  KEY "fk_t_join_school_t_sys_user" ("user_id"),
  CONSTRAINT "fk_t_join_school_t_sys_user" FOREIGN KEY ("user_id") REFERENCES "t_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='加盟校管理';

-- ----------------------------
-- Table structure for t_join_school_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_join_school_subject`;
CREATE TABLE "t_join_school_subject" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '学校加盟学科ID',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  "join_date" datetime DEFAULT NULL COMMENT '加盟开始时间',
  "expire_date" datetime DEFAULT NULL COMMENT '加盟到期时间',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建人ID',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示已删除',
  PRIMARY KEY ("id"),
  KEY "fk_t_join_school_subject_t_join_school" ("school_id"),
  KEY "fk_t_join_school_subject_t_subject" ("subject_id"),
  CONSTRAINT "fk_t_join_school_subject_t_join_school" FOREIGN KEY ("school_id") REFERENCES "t_join_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_join_school_subject_t_subject" FOREIGN KEY ("subject_id") REFERENCES "t_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学校加盟学科表';

-- ----------------------------
-- Table structure for t_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `t_knowledge`;
CREATE TABLE "t_knowledge" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '知识点ID',
  "name" varchar(512) DEFAULT NULL COMMENT '知识点名称',
  "subjects" varchar(512) DEFAULT NULL COMMENT '绑定学科信息',
  "grade_id" int(11) DEFAULT NULL COMMENT '年级ID',
  "remark" text COMMENT '备注',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "level" int(11) DEFAULT NULL COMMENT '知识点层级。0表示顶级知识点，数字越大层级越深',
  "parent_id" int(11) DEFAULT NULL COMMENT '上一级知识点ID',
  "level_path" varchar(512) DEFAULT NULL COMMENT '层级路径',
  "parent_path" varchar(512) DEFAULT NULL COMMENT '上一级知识点层级路径',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点表';

-- ----------------------------
-- Table structure for t_limit_practice_classes_log_question_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_limit_practice_classes_log_question_detail`;
CREATE TABLE "t_limit_practice_classes_log_question_detail" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  "limit_log_id" int(11) DEFAULT NULL COMMENT '限时答题记录ID',
  "limit_log_detail_id" int(11) DEFAULT NULL COMMENT '限时答题记录详情ID',
  "stu_id" int(11) DEFAULT NULL COMMENT '学生ID',
  "question_id" int(11) DEFAULT NULL COMMENT '题目ID',
  "answer" varchar(512) DEFAULT NULL COMMENT '答案',
  "results" int(11) DEFAULT NULL COMMENT '是否正确。0表示正确；1表示错误',
  "create_time" datetime DEFAULT NULL COMMENT '答题时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限时练习记录答题详情';

-- ----------------------------
-- Table structure for t_limit_time_practice
-- ----------------------------
DROP TABLE IF EXISTS `t_limit_time_practice`;
CREATE TABLE "t_limit_time_practice" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '限时练习ID',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "name" varchar(512) DEFAULT NULL COMMENT '限时练习名称',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  "grade_id" int(11) DEFAULT NULL COMMENT '年级ID',
  "limit_time" int(11) DEFAULT NULL COMMENT '限时时间',
  "release_time" datetime DEFAULT NULL COMMENT '限时开始时间',
  "end_time" datetime DEFAULT NULL COMMENT '截止时间',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示草稿；1表示发布；2表示开始；3表示结束',
  "task_time" datetime DEFAULT NULL COMMENT '定时发布时间',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建人',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "is_task" int(11) DEFAULT NULL COMMENT '是否定时发布。0表示否；1表示是',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限时练习表';

-- ----------------------------
-- Table structure for t_limit_time_practice_class_log_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_limit_time_practice_class_log_detail`;
CREATE TABLE "t_limit_time_practice_class_log_detail" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '限时练习记录详情表',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  "limit_id" int(11) DEFAULT NULL COMMENT '限时练习ID',
  "limit_time_practice_classes_log_id" int(11) DEFAULT NULL COMMENT '练习记录ID',
  "stu_id" int(11) DEFAULT NULL COMMENT '学生ID',
  "question_counts" int(11) DEFAULT NULL COMMENT '练习题数量',
  "correct_counts" int(11) DEFAULT NULL COMMENT '答题正确数',
  "currect_rate" double DEFAULT NULL COMMENT '答题正确率',
  "create_time" datetime DEFAULT NULL COMMENT '答题时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限时练习记录详情';

-- ----------------------------
-- Table structure for t_limit_time_practice_classes_log
-- ----------------------------
DROP TABLE IF EXISTS `t_limit_time_practice_classes_log`;
CREATE TABLE "t_limit_time_practice_classes_log" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '限时练习记录ID',
  "numbers" varchar(20) DEFAULT NULL COMMENT '限时练习记录编号',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  "class_name" varchar(512) DEFAULT NULL COMMENT '班级名称',
  "counts" int(11) DEFAULT NULL COMMENT '班级人数',
  "practice_time" datetime DEFAULT NULL COMMENT '练习时间',
  "correct_rate" double DEFAULT NULL COMMENT '正确率',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "limit_id" int(11) DEFAULT NULL COMMENT '限时练习ID',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级限时练习记录表';

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE "t_message" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '系统消息表',
  "types" int(11) DEFAULT NULL COMMENT '类型。0表示新闻；1表示通知；',
  "title" varchar(512) DEFAULT NULL COMMENT '消息标题',
  "send_time" datetime DEFAULT NULL COMMENT '发布时间',
  "expire_begin_time" datetime DEFAULT NULL COMMENT '有效期开始时间',
  "expire_end_time" datetime DEFAULT NULL COMMENT '有效期截止时间',
  "content" text COMMENT '内容',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示草稿；1表示已发布；2表示已作废；3表示已删除',
  "sender" varchar(512) DEFAULT NULL COMMENT '发布人',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "school_id" int(11) DEFAULT NULL COMMENT '加盟校ID',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统消息表';

-- ----------------------------
-- Table structure for t_question_bank
-- ----------------------------
DROP TABLE IF EXISTS `t_question_bank`;
CREATE TABLE "t_question_bank" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '题库ID',
  "dry" text COMMENT '题干',
  "abbr" varchar(512) DEFAULT NULL COMMENT '简称',
  "star" int(11) DEFAULT NULL COMMENT '难易程度。1-5颗星',
  "tags" varchar(512) DEFAULT NULL COMMENT '标签信息集合',
  "knowledges" varchar(512) DEFAULT NULL COMMENT '知识点集合',
  "scope" int(11) DEFAULT NULL COMMENT '是否公开。0表示公开；1表示不公开',
  "content" text COMMENT '题内容',
  "ans_type" int(11) DEFAULT NULL COMMENT '答案类型。0表示选择题；1表示判断题；2表示填空题。',
  "choose_a" text COMMENT '选项A',
  "choose_b" text COMMENT '选项B',
  "choose_c" text COMMENT '选项c',
  "choose_d" text COMMENT '选项d',
  "choose_result" varchar(10) DEFAULT NULL COMMENT '选项答案',
  "flag_true" text COMMENT '判断题。正确答案内容',
  "flag_false" text COMMENT '判断题。错误答案内容',
  "flag_result" int(11) DEFAULT NULL COMMENT '判断题正确答案。0表示应选择正确答案；1表示应选择错误答案',
  "fill_type" int(11) DEFAULT NULL COMMENT '填空题。答案类型。0表示文字或数字；1表示分数；2表示带分数',
  "fill_value" varchar(512) DEFAULT NULL COMMENT '填空题标准答案。\n当fillType==2时作为分子；当fillType==3时作为带分数的数字',
  "fill_value2" varchar(50) DEFAULT NULL COMMENT '当fillType==2时，作为分母；当fillType==3时作为带分数的分子',
  "fill_value3" varchar(50) DEFAULT NULL COMMENT '当fillType==3时有值，作为带分数的分母',
  "resolution_process" text COMMENT '解析过程',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示停用；2表示已删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题库表';

-- ----------------------------
-- Table structure for t_renewal
-- ----------------------------
DROP TABLE IF EXISTS `t_renewal`;
CREATE TABLE "t_renewal" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '续费ID',
  "order_id" varchar(50) DEFAULT NULL COMMENT '续费订单号（13位年月日时分秒毫秒拼接）',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  "before_expire_date" datetime DEFAULT NULL COMMENT '续费前截止日期',
  "renewal_date" datetime DEFAULT NULL COMMENT '续费日期',
  "price" decimal(10,2) DEFAULT NULL COMMENT '续费金额',
  "duration" int(11) DEFAULT NULL COMMENT '续费时长(最小时间单位为天)',
  "after_expire_date" datetime DEFAULT NULL COMMENT '续费后截止时间',
  "remark" text COMMENT '备注',
  "creator_id" int(11) DEFAULT NULL COMMENT '操作人ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '操作人',
  "create_time" datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY ("id"),
  KEY "fk_t_renewal_t_join_school" ("school_id"),
  KEY "fk_t_renewal_t_subject" ("subject_id"),
  CONSTRAINT "fk_t_renewal_t_join_school" FOREIGN KEY ("school_id") REFERENCES "t_join_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_renewal_t_subject" FOREIGN KEY ("subject_id") REFERENCES "t_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='续费管理';

-- ----------------------------
-- Table structure for t_self_help_practice
-- ----------------------------
DROP TABLE IF EXISTS `t_self_help_practice`;
CREATE TABLE "t_self_help_practice" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '自助练习ID',
  "stu_id" int(11) DEFAULT NULL COMMENT '学生ID',
  "question_id" int(11) DEFAULT NULL COMMENT '题目ID',
  "answer" varchar(512) DEFAULT NULL COMMENT '提交答案',
  "results" int(11) DEFAULT NULL COMMENT '答题结果。0表示正确；1表示错误',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "create_time" datetime DEFAULT NULL COMMENT '答题时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助练习表';

-- ----------------------------
-- Table structure for t_service_agreement
-- ----------------------------
DROP TABLE IF EXISTS `t_service_agreement`;
CREATE TABLE "t_service_agreement" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '服务协议ID',
  "type" int(11) DEFAULT NULL COMMENT '协议类型。根据系统字典表进行获取',
  "type_name" varchar(512) DEFAULT NULL COMMENT '类型名称',
  "content" text COMMENT '服务内容',
  "school_id" int(11) DEFAULT NULL COMMENT '加盟校ID',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建用户ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示生效；1表示失效',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='加盟校服务协议表';

-- ----------------------------
-- Table structure for t_special_practice
-- ----------------------------
DROP TABLE IF EXISTS `t_special_practice`;
CREATE TABLE "t_special_practice" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '专项练习ID',
  "name" varchar(512) DEFAULT NULL COMMENT '专项练习名称',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  "grade_id" int(11) DEFAULT NULL COMMENT '年级ID',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示草稿；1表示发布；2表示开始；3表示结束',
  "release_time" datetime DEFAULT NULL COMMENT '发布时间',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建人',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "end_time" datetime DEFAULT NULL COMMENT '截止时间',
  "is_task" int(11) DEFAULT NULL COMMENT '是否定时发布。0表示否；1表示是',
  "task_time" datetime DEFAULT NULL COMMENT '定时发布时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专项练习管理表';

-- ----------------------------
-- Table structure for t_special_practice_class_log_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_special_practice_class_log_detail`;
CREATE TABLE "t_special_practice_class_log_detail" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '班级学生专项练习记录',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  "special_id" int(11) DEFAULT NULL COMMENT '专项练习ID',
  "stu_id" int(11) DEFAULT NULL COMMENT '学生ID',
  "question_counts" int(11) DEFAULT NULL COMMENT '练习题数量',
  "special_practice_classes_log_id" int(11) DEFAULT NULL COMMENT '班级练习记录ID',
  "numbers" varchar(20) DEFAULT NULL COMMENT '班级练习编号',
  "correct_counts" int(11) DEFAULT NULL COMMENT '回答正确总数',
  "currect_rate" double DEFAULT NULL COMMENT '正确率',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY ("id"),
  KEY "fk_t_special_practice_class_log_detail_t_student" ("stu_id"),
  KEY "fk_t_spial_p_c_log_dil_t_special_p_cls_log" ("special_practice_classes_log_id"),
  CONSTRAINT "fk_t_special_practice_class_log_detail_t_student" FOREIGN KEY ("stu_id") REFERENCES "t_student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_spial_p_c_log_dil_t_special_p_cls_log" FOREIGN KEY ("special_practice_classes_log_id") REFERENCES "t_special_practice_classes_log" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级专项练习日志详情';

-- ----------------------------
-- Table structure for t_special_practice_classes_log
-- ----------------------------
DROP TABLE IF EXISTS `t_special_practice_classes_log`;
CREATE TABLE "t_special_practice_classes_log" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '班级专项练习记录ID',
  "numbers" varchar(20) DEFAULT NULL COMMENT '班级专项练习编号',
  "class_id" int(11) DEFAULT NULL COMMENT '班级ID',
  "class_name" varchar(512) DEFAULT NULL COMMENT '班级名称',
  "counts" int(11) DEFAULT NULL COMMENT '人数',
  "practice_time" datetime DEFAULT NULL COMMENT '练习时间',
  "correct_rate" double DEFAULT NULL COMMENT '正确率（百分比）',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "special_id" int(11) DEFAULT NULL COMMENT '专项ID',
  PRIMARY KEY ("id"),
  KEY "fk_t_special_practice_classes_log_t_classes" ("class_id"),
  KEY "fk_t_special_practice_classes_log_t_join_school" ("school_id"),
  KEY "fk_t_special_practice_classes_log_t_special_practice" ("special_id"),
  CONSTRAINT "fk_t_special_practice_classes_log_t_classes" FOREIGN KEY ("class_id") REFERENCES "t_classes" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_special_practice_classes_log_t_join_school" FOREIGN KEY ("school_id") REFERENCES "t_join_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_special_practice_classes_log_t_special_practice" FOREIGN KEY ("special_id") REFERENCES "t_special_practice" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级专项练习记录';

-- ----------------------------
-- Table structure for t_special_practice_classes_log_question_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_special_practice_classes_log_question_detail`;
CREATE TABLE "t_special_practice_classes_log_question_detail" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '专项练习答题记录ID',
  "special_practice_classes_log_id" int(11) DEFAULT NULL COMMENT '班级专项练习记录ID',
  "stu_id" int(11) DEFAULT NULL COMMENT '学生ID',
  "question_id" int(11) DEFAULT NULL COMMENT '题库ID',
  "answer" varchar(512) DEFAULT NULL COMMENT '回答答案',
  "results" int(11) DEFAULT NULL COMMENT '是否正确。0表示正确；1表示错误',
  "create_time" datetime DEFAULT NULL COMMENT '答题时间',
  "special_practice_classes_log_detail_id" int(11) DEFAULT NULL COMMENT '班级专项练习记录明细ID',
  PRIMARY KEY ("id"),
  KEY "fk_t_special_p_c_log_q_d_t_special_p_class_l_d" ("special_practice_classes_log_detail_id"),
  KEY "fk_t_special_practice_c_l_q_d_t_special__c_l" ("special_practice_classes_log_id"),
  KEY "fk_t_special_practice_c_log_q_de_t_q_b" ("question_id"),
  CONSTRAINT "fk_t_special_p_c_log_q_d_t_special_p_class_l_d" FOREIGN KEY ("special_practice_classes_log_detail_id") REFERENCES "t_special_practice_class_log_detail" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_special_practice_c_l_q_d_t_special__c_l" FOREIGN KEY ("special_practice_classes_log_id") REFERENCES "t_special_practice_classes_log" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_special_practice_c_log_q_de_t_q_b" FOREIGN KEY ("question_id") REFERENCES "t_question_bank" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生专项练习答题信息记录表';

-- ----------------------------
-- Table structure for t_stage
-- ----------------------------
DROP TABLE IF EXISTS `t_stage`;
CREATE TABLE "t_stage" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '教案阶段ID',
  "name" varchar(512) DEFAULT NULL COMMENT '阶段名称',
  "stage_index" int(11) DEFAULT NULL COMMENT '阶段数',
  "limit_time" int(11) DEFAULT NULL COMMENT '限制分钟数',
  "status" int(11) DEFAULT NULL COMMENT '阶段状态。0表示正常；1表示已删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建人ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "teaching_plan_id" int(11) DEFAULT NULL COMMENT '教案ID',
  PRIMARY KEY ("id"),
  KEY "fk_t_stage_t_teaching_plan" ("teaching_plan_id"),
  CONSTRAINT "fk_t_stage_t_teaching_plan" FOREIGN KEY ("teaching_plan_id") REFERENCES "t_teaching_plan" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教案阶段';

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE "t_student" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  "school_id" int(11) DEFAULT NULL COMMENT '学校ID',
  "name" varchar(512) DEFAULT NULL COMMENT '学生名称',
  "stu_no" varchar(50) DEFAULT NULL COMMENT '学生学号',
  "cover" varchar(128) DEFAULT NULL COMMENT '头像',
  "id_card" varchar(18) DEFAULT NULL COMMENT '身份证号码',
  "sex" varchar(2) DEFAULT NULL COMMENT '性别。',
  "father_name" varchar(512) DEFAULT NULL COMMENT '父亲名称',
  "father_phone" varchar(11) DEFAULT NULL COMMENT '父亲联系电话',
  "mother_name" varchar(512) DEFAULT NULL COMMENT '母亲名称',
  "mother_phone" varchar(11) DEFAULT NULL COMMENT '母亲联系电话',
  "email" varchar(128) DEFAULT NULL COMMENT '电子邮箱',
  "addr_province" varchar(256) DEFAULT NULL COMMENT '家庭住址省',
  "addr_city" varchar(256) DEFAULT NULL COMMENT '市',
  "addr_area" varchar(256) DEFAULT NULL COMMENT '区',
  "address" varchar(512) DEFAULT NULL COMMENT '详细地址',
  "join_time" datetime DEFAULT NULL COMMENT '入校时间',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示在校；1表示离校',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY ("id"),
  KEY "fk_t_student_t_join_school" ("school_id"),
  CONSTRAINT "fk_t_student_t_join_school" FOREIGN KEY ("school_id") REFERENCES "t_join_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- ----------------------------
-- Table structure for t_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_subject`;
CREATE TABLE "t_subject" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '学科ID',
  "remark" text COMMENT '学科说明',
  "status" int(11) DEFAULT NULL COMMENT '学科状态。0表示正常；1表示停用；2表示已删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建用户ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建用户',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学科表';

-- ----------------------------
-- Table structure for t_sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_login_log`;
CREATE TABLE "t_sys_login_log" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "user_id" int(11) DEFAULT NULL COMMENT '用户ID',
  "user_name" varchar(128) DEFAULT NULL COMMENT '登录用户昵称',
  "realm_name" varchar(128) DEFAULT NULL COMMENT '真实名称',
  "status" int(11) DEFAULT NULL COMMENT '状态',
  "remark" varchar(512) DEFAULT NULL COMMENT '描述',
  "login_ip" varchar(128) DEFAULT NULL COMMENT '登录IP',
  "login_province" varchar(512) DEFAULT NULL COMMENT '登录省份',
  "login_address" varchar(512) DEFAULT NULL COMMENT '登录地址',
  "login_time" datetime DEFAULT NULL COMMENT '登录时间',
  "school_id" int(20) DEFAULT NULL COMMENT '学校ID',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=3939 DEFAULT CHARSET=utf8mb4 COMMENT='用户登录日志表';

-- ----------------------------
-- Records of t_sys_login_log
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_login_log` VALUES (1, 1, 'test', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2017-02-20 21:58:59', NULL);
INSERT INTO `t_sys_login_log` VALUES (3825, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-03 14:57:06', NULL);
INSERT INTO `t_sys_login_log` VALUES (3826, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-29 23:26:57', NULL);
INSERT INTO `t_sys_login_log` VALUES (3827, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-29 23:31:26', NULL);
INSERT INTO `t_sys_login_log` VALUES (3828, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-29 23:35:06', NULL);
INSERT INTO `t_sys_login_log` VALUES (3829, 1, 'biao.chen', '超级管理员', 0, NULL, '182.150.27.185', '四川省', '四川省成都市', '2018-08-23 15:28:46', 1);
INSERT INTO `t_sys_login_log` VALUES (3830, 63, 'test', '111111', 0, NULL, '182.150.27.185', '四川省', '四川省成都市', '2018-08-23 15:29:26', 1);
INSERT INTO `t_sys_login_log` VALUES (3831, 62, 'admin', '超级管理员', 0, NULL, '182.150.27.185', '四川省', '四川省成都市', '2018-08-23 15:29:37', NULL);
INSERT INTO `t_sys_login_log` VALUES (3832, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-23 15:37:44', 1);
INSERT INTO `t_sys_login_log` VALUES (3833, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-23 17:45:52', NULL);
INSERT INTO `t_sys_login_log` VALUES (3834, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-23 18:49:42', NULL);
INSERT INTO `t_sys_login_log` VALUES (3835, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-23 19:05:00', 1);
INSERT INTO `t_sys_login_log` VALUES (3836, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-23 19:07:15', NULL);
INSERT INTO `t_sys_login_log` VALUES (3837, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-23 19:09:37', 1);
INSERT INTO `t_sys_login_log` VALUES (3838, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-23 19:21:20', 1);
INSERT INTO `t_sys_login_log` VALUES (3839, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 09:43:27', 1);
INSERT INTO `t_sys_login_log` VALUES (3840, 64, 'test2', '张三', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 14:58:22', 2);
INSERT INTO `t_sys_login_log` VALUES (3841, 64, 'test2', '张三', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 15:25:14', 2);
INSERT INTO `t_sys_login_log` VALUES (3842, 64, 'test2', '张三', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 15:50:50', 2);
INSERT INTO `t_sys_login_log` VALUES (3843, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 15:53:52', 1);
INSERT INTO `t_sys_login_log` VALUES (3844, 64, 'test2', '张三', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 15:59:23', 2);
INSERT INTO `t_sys_login_log` VALUES (3845, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 16:04:36', 1);
INSERT INTO `t_sys_login_log` VALUES (3846, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-24 18:17:13', 1);
INSERT INTO `t_sys_login_log` VALUES (3847, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-28 20:21:59', 1);
INSERT INTO `t_sys_login_log` VALUES (3848, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-28 20:22:31', 1);
INSERT INTO `t_sys_login_log` VALUES (3849, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-29 19:00:26', 1);
INSERT INTO `t_sys_login_log` VALUES (3850, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-29 19:05:45', 1);
INSERT INTO `t_sys_login_log` VALUES (3851, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-30 14:13:44', 1);
INSERT INTO `t_sys_login_log` VALUES (3852, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-30 16:15:41', 1);
INSERT INTO `t_sys_login_log` VALUES (3853, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-31 14:17:46', 1);
INSERT INTO `t_sys_login_log` VALUES (3854, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-31 14:18:47', 2);
INSERT INTO `t_sys_login_log` VALUES (3855, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-08-31 14:19:18', 1);
INSERT INTO `t_sys_login_log` VALUES (3856, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 09:26:43', 2);
INSERT INTO `t_sys_login_log` VALUES (3857, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 09:54:20', NULL);
INSERT INTO `t_sys_login_log` VALUES (3858, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:00:54', 1);
INSERT INTO `t_sys_login_log` VALUES (3859, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:04:04', NULL);
INSERT INTO `t_sys_login_log` VALUES (3860, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:08:37', 2);
INSERT INTO `t_sys_login_log` VALUES (3861, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:08:50', NULL);
INSERT INTO `t_sys_login_log` VALUES (3862, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:27:10', 2);
INSERT INTO `t_sys_login_log` VALUES (3863, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:36:36', NULL);
INSERT INTO `t_sys_login_log` VALUES (3864, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:42:55', 2);
INSERT INTO `t_sys_login_log` VALUES (3865, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:44:09', 2);
INSERT INTO `t_sys_login_log` VALUES (3866, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:46:20', 1);
INSERT INTO `t_sys_login_log` VALUES (3867, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 10:59:24', 1);
INSERT INTO `t_sys_login_log` VALUES (3868, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 11:00:34', 1);
INSERT INTO `t_sys_login_log` VALUES (3869, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 11:01:04', 1);
INSERT INTO `t_sys_login_log` VALUES (3870, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 11:10:21', NULL);
INSERT INTO `t_sys_login_log` VALUES (3871, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 11:14:20', 1);
INSERT INTO `t_sys_login_log` VALUES (3872, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 11:41:21', 1);
INSERT INTO `t_sys_login_log` VALUES (3873, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 12:32:17', 1);
INSERT INTO `t_sys_login_log` VALUES (3874, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 12:32:56', 1);
INSERT INTO `t_sys_login_log` VALUES (3875, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 13:07:14', 1);
INSERT INTO `t_sys_login_log` VALUES (3876, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 13:48:36', 1);
INSERT INTO `t_sys_login_log` VALUES (3877, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 14:26:30', 1);
INSERT INTO `t_sys_login_log` VALUES (3878, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 14:40:56', 1);
INSERT INTO `t_sys_login_log` VALUES (3879, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 14:57:19', 1);
INSERT INTO `t_sys_login_log` VALUES (3880, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 15:16:40', 1);
INSERT INTO `t_sys_login_log` VALUES (3881, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 15:18:26', 1);
INSERT INTO `t_sys_login_log` VALUES (3882, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 16:06:12', 1);
INSERT INTO `t_sys_login_log` VALUES (3883, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 16:06:22', 1);
INSERT INTO `t_sys_login_log` VALUES (3884, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 16:17:07', 1);
INSERT INTO `t_sys_login_log` VALUES (3885, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-03 16:17:37', 1);
INSERT INTO `t_sys_login_log` VALUES (3886, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-05 10:30:34', 1);
INSERT INTO `t_sys_login_log` VALUES (3887, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-05 10:57:52', 1);
INSERT INTO `t_sys_login_log` VALUES (3888, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-05 11:09:46', 1);
INSERT INTO `t_sys_login_log` VALUES (3889, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-05 14:31:26', 1);
INSERT INTO `t_sys_login_log` VALUES (3890, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-05 14:31:36', 1);
INSERT INTO `t_sys_login_log` VALUES (3891, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-05 14:32:30', 1);
INSERT INTO `t_sys_login_log` VALUES (3892, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-07 14:49:09', 1);
INSERT INTO `t_sys_login_log` VALUES (3893, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-07 14:50:59', 1);
INSERT INTO `t_sys_login_log` VALUES (3894, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-07 14:51:34', 1);
INSERT INTO `t_sys_login_log` VALUES (3895, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-08 14:49:43', 1);
INSERT INTO `t_sys_login_log` VALUES (3896, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-08 14:52:42', 1);
INSERT INTO `t_sys_login_log` VALUES (3897, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-08 14:53:20', 1);
INSERT INTO `t_sys_login_log` VALUES (3898, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-08 15:53:51', 1);
INSERT INTO `t_sys_login_log` VALUES (3899, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-08 15:54:43', 1);
INSERT INTO `t_sys_login_log` VALUES (3900, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-12 10:50:51', 1);
INSERT INTO `t_sys_login_log` VALUES (3901, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-12 12:09:11', 1);
INSERT INTO `t_sys_login_log` VALUES (3902, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 10:37:21', 1);
INSERT INTO `t_sys_login_log` VALUES (3903, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 10:38:33', 1);
INSERT INTO `t_sys_login_log` VALUES (3904, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 12:46:37', 1);
INSERT INTO `t_sys_login_log` VALUES (3905, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 12:47:02', 1);
INSERT INTO `t_sys_login_log` VALUES (3906, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 12:49:35', 1);
INSERT INTO `t_sys_login_log` VALUES (3907, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 12:50:47', 1);
INSERT INTO `t_sys_login_log` VALUES (3908, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 13:49:49', 1);
INSERT INTO `t_sys_login_log` VALUES (3909, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 15:40:04', 1);
INSERT INTO `t_sys_login_log` VALUES (3910, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 18:58:09', 1);
INSERT INTO `t_sys_login_log` VALUES (3911, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 18:59:30', 1);
INSERT INTO `t_sys_login_log` VALUES (3912, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-13 18:59:51', 1);
INSERT INTO `t_sys_login_log` VALUES (3913, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-14 14:05:35', 1);
INSERT INTO `t_sys_login_log` VALUES (3914, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-14 14:07:21', 1);
INSERT INTO `t_sys_login_log` VALUES (3915, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-14 16:26:07', 1);
INSERT INTO `t_sys_login_log` VALUES (3916, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-14 16:26:39', 1);
INSERT INTO `t_sys_login_log` VALUES (3917, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 13:18:30', NULL);
INSERT INTO `t_sys_login_log` VALUES (3918, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 13:21:47', 1);
INSERT INTO `t_sys_login_log` VALUES (3919, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 20:03:46', 1);
INSERT INTO `t_sys_login_log` VALUES (3920, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 20:15:39', 1);
INSERT INTO `t_sys_login_log` VALUES (3921, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 20:16:57', 1);
INSERT INTO `t_sys_login_log` VALUES (3922, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 20:19:47', 1);
INSERT INTO `t_sys_login_log` VALUES (3923, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 20:21:30', 1);
INSERT INTO `t_sys_login_log` VALUES (3924, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-17 21:08:04', 1);
INSERT INTO `t_sys_login_log` VALUES (3925, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-18 09:06:02', 1);
INSERT INTO `t_sys_login_log` VALUES (3926, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-18 13:45:30', 1);
INSERT INTO `t_sys_login_log` VALUES (3927, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-18 20:07:08', 1);
INSERT INTO `t_sys_login_log` VALUES (3928, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-18 20:09:47', 1);
INSERT INTO `t_sys_login_log` VALUES (3929, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 20:17:45', 1);
INSERT INTO `t_sys_login_log` VALUES (3930, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 20:23:03', 1);
INSERT INTO `t_sys_login_log` VALUES (3931, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 20:36:30', 1);
INSERT INTO `t_sys_login_log` VALUES (3932, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 20:51:46', NULL);
INSERT INTO `t_sys_login_log` VALUES (3933, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 20:52:14', 1);
INSERT INTO `t_sys_login_log` VALUES (3934, 62, 'admin', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 20:53:12', NULL);
INSERT INTO `t_sys_login_log` VALUES (3935, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 20:53:35', 1);
INSERT INTO `t_sys_login_log` VALUES (3936, 63, 'test', '111111', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 21:03:07', 1);
INSERT INTO `t_sys_login_log` VALUES (3937, 1, 'biao.chen', '超级管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-09-19 21:04:21', 1);
INSERT INTO `t_sys_login_log` VALUES (3938, 1, 'biao.chen', '管理员', 0, NULL, '47.106.186.186', '广东省', '广东省', '2018-12-17 17:05:22', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_resource`;
CREATE TABLE "t_sys_resource" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '功能ID，功能在数据库的唯一标示。',
  "resource_name" varchar(40) NOT NULL COMMENT '功能名称',
  "resource_url" varchar(255) NOT NULL COMMENT '功能请求路径',
  "is_basic" int(1) NOT NULL DEFAULT '0' COMMENT '是否是基础功能,0表示不是基础功能，1表示是基础功能。',
  "parent_id" int(4) NOT NULL COMMENT '父节点ID',
  "parent_path" varchar(500) DEFAULT NULL COMMENT '所有父级id拼接',
  "level" int(4) DEFAULT NULL COMMENT '当前节点的级别，例如例如根节点，第一级节点，，，，',
  "remark" varchar(20) DEFAULT NULL COMMENT '功能描述',
  "status" int(1) DEFAULT '0' COMMENT '状态0表示正常，1表示不正常',
  "resource_kind" int(1) unsigned zerofill DEFAULT NULL COMMENT '1菜单资源属于商城，2菜单资源属于平台，3菜单资源属于直播 5菜单属于erp 6菜单属于系统',
  "resource_type" int(4) DEFAULT NULL COMMENT '资源类型 0 菜单 1按钮',
  "seq" int(1) DEFAULT NULL COMMENT '排序',
  "open_mode" int(1) DEFAULT NULL COMMENT '打开方式 ajax,iframe',
  "opened" int(1) DEFAULT NULL COMMENT '打开状态',
  "icon" varchar(32) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=803 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='请求资源';

-- ----------------------------
-- Records of t_sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_resource` VALUES (6, '系统', '#', 1, -1, '/', 0, NULL, 0, 6, 0, NULL, NULL, NULL, '&#xe614;');
INSERT INTO `t_sys_resource` VALUES (84, '系统管理', '#', 1, 6, '/6/', 1, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (85, '系统用户管理', '/system/user', 1, 84, '/6/84/', 2, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (86, '系统角色管理', '/system/role', 1, 84, '/6/84/', 2, '', 0, 6, 0, 2, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (87, '系统资源管理', '/system/resource/add', 1, 84, '/6/84/', 2, '', 0, 6, 0, 3, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (245, '添加', '/system/user/add', 1, 85, '/6/84/85/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (246, '删除', '/system/user/delete', 1, 85, '/6/84/85/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (247, '编辑', '/system/user/edit', 1, 85, '/6/84/85/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (248, '添加', '/system/role/add', 1, 86, '/6/84/86/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (249, '编辑', '/system/role/edit', 1, 86, '/6/84/86/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (250, '删除', '/system/role/delete', 1, 86, '/6/84/86/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (251, '添加', '/system/resource/add', 1, 87, '/6/84/87/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (252, '编辑', '/system/resource/edit', 1, 87, '/6/84/87/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (253, '删除', '/system/resource/delete', 1, 87, '/6/84/87/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (786, '配置管理', '#', 1, 6, '/6/', 1, '', 0, 6, 0, 2, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (787, '基本信息设置', '/setting/basic', 1, 786, '/6/786/', 2, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (788, '后台账户管理', '/setting/account', 1, 786, '/6/786/', 2, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (789, '添加', '/setting/account/add', 1, 788, '/6/786/788/', 3, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (790, '编辑', '/setting/account/edit', 1, 788, '/6/786/788/', 3, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (791, '删除', '/setting/account/delete', 1, 788, '/6/786/788/', 3, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (792, '搜索链接', '/search/links', 1, 786, '/6/786/', 2, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (793, '内容管理', '#', 1, 6, '/6/', 1, '', 0, 6, 0, 3, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (794, '客户项目', '/contentManager/customerProject', 1, 793, '/6/793/', 2, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (795, '添加', '/contentManager/customerProject/add', 1, 794, '/6/793/794/', 3, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (796, '编辑', '/contentManager/customerProject/edit', 1, 794, '/6/793/794/', 3, '', 0, 6, 1, 2, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (797, '删除', '/contentManager/customerProject/delete', 1, 794, '/6/793/794/', 3, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (798, '项目列表', '/contentManager/customerProject/list', 1, 794, '/6/793/794/', 3, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (799, '添加', '/contentManager/customerProject/list/add', 1, 798, '/6/793/794/798/', 4, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (800, '编辑', '/contentManager/customerProject/list/edit', 1, 798, '/6/793/794/798/', 4, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (801, '删除', '/contentManager/customerProject/list/delete', 1, 798, '/6/793/794/798/', 4, '', 0, 6, 1, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (802, '查看全部链接', '/contentManager/customerProject/list/links', 1, 798, '/6/793/794/798/', 4, '', 0, 6, 1, 1, NULL, NULL, '');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE "t_sys_role" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '角色组的唯一标示',
  "role_name" varchar(30) NOT NULL COMMENT '角色组名称',
  "description" varchar(80) DEFAULT NULL COMMENT '角色组描述',
  "status" int(1) NOT NULL DEFAULT '0' COMMENT '角色状态,0默认为有效，1表示已被禁用不能使用。',
  "creator" varchar(32) NOT NULL COMMENT '角色组创建人',
  "createtime" bigint(20) NOT NULL COMMENT '创建时间',
  "last_update_creator" varchar(32) DEFAULT NULL COMMENT '此角色组上一次修改人',
  "last_update_createtime" bigint(20) DEFAULT NULL COMMENT '此角色上一次修改时间',
  "parent_id" int(11) DEFAULT NULL,
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "school_id" int(20) DEFAULT NULL COMMENT '加盟校ID',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role` VALUES (4, 'administrator', '超级管理员', 0, 'root', 1487596593093, 'root', 1530938189814, NULL, NULL, NULL);
INSERT INTO `t_sys_role` VALUES (13, '管理员', '', 0, 'biao.chen', 1533273896228, 'biao.chen', 1533273896228, 13, NULL, NULL);
INSERT INTO `t_sys_role` VALUES (14, '设置管理', '', 0, 'biao.chen', 1533273916449, 'biao.chen', 1533273916449, 13, NULL, NULL);
INSERT INTO `t_sys_role` VALUES (15, '内容管理', '', 0, 'biao.chen', 1533273933288, 'biao.chen', 1533273933288, 13, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_sub_manage
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_sub_manage`;
CREATE TABLE "t_sys_sub_manage" (
  "id" int(11) unsigned NOT NULL AUTO_INCREMENT,
  "name" varchar(255) NOT NULL COMMENT '系统唯一key',
  "value" varchar(255) NOT NULL COMMENT '系统名称',
  "url" varchar(255) DEFAULT NULL COMMENT '系统访问路径',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='子系统管理表';

-- ----------------------------
-- Records of t_sys_sub_manage
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_sub_manage` VALUES (6, 'system', '系统', 'http://localhost:9002/console');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE "t_sys_user" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '登录用户ID',
  "name" varchar(32) NOT NULL COMMENT '用户昵称',
  "email" varchar(128) DEFAULT NULL COMMENT '用户email号。用于保证用户唯一，且用于通过email进行帐号激活。',
  "password" varchar(32) NOT NULL COMMENT '密码',
  "status" int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  "creator" varchar(32) NOT NULL COMMENT '创建者',
  "createtime" bigint(20) NOT NULL COMMENT '创建时间',
  "login_Times" int(8) DEFAULT NULL COMMENT '登录次数',
  "last_Login_Time" bigint(20) DEFAULT NULL COMMENT '上次登录时间',
  "last_Login_Ip" varchar(20) DEFAULT NULL COMMENT '上次登录IP',
  "real_name" varchar(32) DEFAULT NULL COMMENT '用户真实姓名',
  "phone" varchar(32) DEFAULT NULL COMMENT '手机号',
  "id_card" varchar(18) DEFAULT NULL COMMENT '身份证号码',
  "contact_phone" varchar(11) DEFAULT NULL COMMENT '紧急联系电话',
  "remark" text COMMENT '备注',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "role_names" varchar(512) DEFAULT NULL COMMENT '角色名称，多个情况下以、号隔开',
  "school_id" int(20) DEFAULT NULL COMMENT '加盟校ID',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user` VALUES (1, 'biao.chen', 'promise_adison@163.com', '96e79218965eb72c92a549dd5a330112', 0, 'root', 1487417957302, 508, 1530286505548, '0:0:0:0:0:0:0:1', '管理员', '18317014696', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_user` VALUES (58, 'admin', 'trip@trip.com', '96e79218965eb72c92a549dd5a330112', 0, 'biao.chen', 1531069002899, 0, NULL, NULL, '管理员', '13800000000', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_user` VALUES (62, 'test', 'aa@42trip.com', '96e79218965eb72c92a549dd5a330112', 0, 'biao.chen', 1533283558728, 0, NULL, NULL, 'abc', '18788888888', NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE "t_tag" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  "name" varchar(512) DEFAULT NULL COMMENT '标签名称',
  "remark" text COMMENT '标签描述',
  "status" int(11) DEFAULT NULL COMMENT '标签状态。0表示正常；1表示禁用；2表示已删除',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建用户ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建用户',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- ----------------------------
-- Table structure for t_teaching_plan
-- ----------------------------
DROP TABLE IF EXISTS `t_teaching_plan`;
CREATE TABLE "t_teaching_plan" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '教案ID',
  "name" varchar(512) DEFAULT NULL COMMENT '教案名称',
  "expire_begin_date" datetime DEFAULT NULL COMMENT '有效期，开始时间',
  "expire_end_date" datetime DEFAULT NULL COMMENT '有效期截止时间',
  "grade_id" int(11) DEFAULT NULL COMMENT '年级ID',
  "knowledge_id" int(11) DEFAULT NULL COMMENT '知识点ID',
  "subject_id" int(11) DEFAULT NULL COMMENT '学科ID',
  "limit_time" int(11) DEFAULT NULL COMMENT '时间限制',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建人ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建人',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "update_time" datetime DEFAULT NULL COMMENT '更新时间',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示已删除',
  "question_counts" int(11) DEFAULT NULL COMMENT '考题数量',
  PRIMARY KEY ("id"),
  KEY "fk_t_teaching_plan_t_grade" ("grade_id"),
  KEY "fk_t_teaching_plan_t_knowledge" ("knowledge_id"),
  KEY "fk_t_teaching_plan_t_subject" ("subject_id"),
  CONSTRAINT "fk_t_teaching_plan_t_grade" FOREIGN KEY ("grade_id") REFERENCES "t_grade" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_teaching_plan_t_knowledge" FOREIGN KEY ("knowledge_id") REFERENCES "t_knowledge" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_t_teaching_plan_t_subject" FOREIGN KEY ("subject_id") REFERENCES "t_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教案表';

-- ----------------------------
-- Table structure for t_version
-- ----------------------------
DROP TABLE IF EXISTS `t_version`;
CREATE TABLE "t_version" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "types" int(11) DEFAULT NULL COMMENT '版本类型。0表示Android；1表示iOS',
  "version_code" varchar(50) DEFAULT NULL COMMENT '版本号',
  "url" varchar(512) DEFAULT NULL COMMENT '下载地址',
  "remark" text COMMENT '备注',
  "status" int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示删除',
  "upgrade" int(11) DEFAULT NULL COMMENT '是否强制更新。0表示强制更新；1表示不强制更新',
  "app_type" int(11) DEFAULT NULL COMMENT 'app类型。0表示家长端；1表示教师端',
  "creator_id" int(11) DEFAULT NULL COMMENT '创建者ID',
  "creator" varchar(512) DEFAULT NULL COMMENT '创建者',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='版本管理表';

SET FOREIGN_KEY_CHECKS = 1;
