/*
 Navicat Premium Data Transfer

 Source Server         : dgdg
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 112.74.76.80:3306
 Source Schema         : cooking

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 19/08/2020 09:09:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment_info
-- ----------------------------
DROP TABLE IF EXISTS `comment_info`;
CREATE TABLE `comment_info` (
  `comment_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dish_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `comment_text` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `comment_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `parent_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `comment_delete` int DEFAULT '0',
  KEY `comment_index` (`user_id`,`comment_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of comment_info
-- ----------------------------
BEGIN;
INSERT INTO `comment_info` VALUES ('8EE59131A00677408749432B22E27AB6160B', 'b4c42c8635894f8c94ffd66ee8da4e45', '80e349cb2b6f4630b0f2308980744656', '做出来真的超好吃~推荐', '2020-08-18 15:46:17', '0', 0);
INSERT INTO `comment_info` VALUES ('8EE59131A00677408749432B22E27AB6160B', 'b4c42c8635894f8c94ffd66ee8da4e45', '469faee89b844a14bf2cc4895642bafc', '我也觉得', '2020-08-18 15:48:08', '80e349cb2b6f4630b0f2308980744656', 0);
INSERT INTO `comment_info` VALUES ('775F96A6BD2E514EFE48483DE2E32D236BA5', 'd177e5e9271a4eccab03434f0ee45b31', '80e349cb2b6f4630b0f2308980744656', '还有人没做过吗！！！', '2020-08-18 15:50:05', '0', 0);
INSERT INTO `comment_info` VALUES ('42E396FC9938CE4B7C398E91967E7B76B9F8', '1c002303495944ccb091d4a6dcb5edff', '81f1706d64a34939941a855cf8cab40c', '不错 不错', '2020-08-18 16:08:01', '0', 0);
INSERT INTO `comment_info` VALUES ('2063DE07560F58414BAAFD42E7462B7B2C27', '1c002303495944ccb091d4a6dcb5edff', '81f1706d64a34939941a855cf8cab40c', '啊啊啊啊 爱了爱了', '2020-08-18 16:08:16', '0', 0);
INSERT INTO `comment_info` VALUES ('42E396FC9938CE4B7C398E91967E7B76B9F8', '1c002303495944ccb091d4a6dcb5edff', '81f1706d64a34939941a855cf8cab40c', '真的呢！！！', '2020-08-18 16:08:32', '81f1706d64a34939941a855cf8cab40c', 0);
INSERT INTO `comment_info` VALUES ('42E396FC9938CE4B7C398E91967E7B76B9F8', '1c002303495944ccb091d4a6dcb5edff', '81f1706d64a34939941a855cf8cab40c', '欧克欧克', '2020-08-18 16:09:44', '81f1706d64a34939941a855cf8cab40c', 0);
INSERT INTO `comment_info` VALUES ('2063DE07560F58414BAAFD42E7462B7B2C27', '1c002303495944ccb091d4a6dcb5edff', '80e349cb2b6f4630b0f2308980744656', '爱了爱了', '2020-08-18 16:09:53', '81f1706d64a34939941a855cf8cab40c', 0);
INSERT INTO `comment_info` VALUES ('2063DE07560F58414BAAFD42E7462B7B2C27', '1c002303495944ccb091d4a6dcb5edff', '81f1706d64a34939941a855cf8cab40c', '呜呜呜 你好棒', '2020-08-18 16:11:35', '80e349cb2b6f4630b0f2308980744656', 0);
INSERT INTO `comment_info` VALUES ('2063DE07560F58414BAAFD42E7462B7B2C27', '1c002303495944ccb091d4a6dcb5edff', '81f1706d64a34939941a855cf8cab40c', '好无聊，没人理我', '2020-08-18 16:13:27', '81f1706d64a34939941a855cf8cab40c', 0);
INSERT INTO `comment_info` VALUES ('2063DE07560F58414BAAFD42E7462B7B2C27', '1c002303495944ccb091d4a6dcb5edff', '81f1706d64a34939941a855cf8cab40c', '啊啊啊啊 人呢！！！', '2020-08-18 16:13:45', '81f1706d64a34939941a855cf8cab40c', 0);
INSERT INTO `comment_info` VALUES ('B6196637188D164A509B647D27F1F57B4CA6', '17f22bd635054bdaaca225109ab4dfce', '80e349cb2b6f4630b0f2308980744656', '真的好好吃啊', '2020-08-18 16:23:59', '0', 0);
INSERT INTO `comment_info` VALUES ('F3FCC0812C80624EC97A29505A9FE81F4528', 'fd30c9ab78674c3dbb370730e96c1c33', '81f1706d64a34939941a855cf8cab40c', '路过的亲，记得点个赞~', '2020-08-18 16:33:39', '0', 0);
INSERT INTO `comment_info` VALUES ('975C255708FA354B8DC94E10BDF552791FD7', '01d95a56e4f243adb3cb677c30a12d44', 'b995549f3f9b4d2e91036091a79f56ba', '111', '2020-08-18 16:40:20', '0', 0);
INSERT INTO `comment_info` VALUES ('975C255708FA354B8DC94E10BDF552791FD7', '01d95a56e4f243adb3cb677c30a12d44', 'b995549f3f9b4d2e91036091a79f56ba', '232323', '2020-08-18 16:40:39', 'b995549f3f9b4d2e91036091a79f56ba', 0);
INSERT INTO `comment_info` VALUES ('975C255708FA354B8DC94E10BDF552791FD7', '01d95a56e4f243adb3cb677c30a12d44', 'b995549f3f9b4d2e91036091a79f56ba', '232322323', '2020-08-18 16:40:43', 'b995549f3f9b4d2e91036091a79f56ba', 0);
INSERT INTO `comment_info` VALUES ('975C255708FA354B8DC94E10BDF552791FD7', '01d95a56e4f243adb3cb677c30a12d44', 'b995549f3f9b4d2e91036091a79f56ba', '232323', '2020-08-18 16:40:48', 'b995549f3f9b4d2e91036091a79f56ba', 0);
COMMIT;

-- ----------------------------
-- Table structure for dish_info
-- ----------------------------
DROP TABLE IF EXISTS `dish_info`;
CREATE TABLE `dish_info` (
  `dish_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tag_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dish_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dish_intro` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dish_text` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `dish_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `dish_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `dish_delete` int DEFAULT '0',
  PRIMARY KEY (`dish_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dish_info
-- ----------------------------
BEGIN;
INSERT INTO `dish_info` VALUES ('01d95a56e4f243adb3cb677c30a12d44', '5bb3804ca25a47b191c8a86f663cbc59', '1', 'W\'w\'w\'w', 'r\'r\'r\'r', '<p>t&#39;t&#39;t&#39;t&#39;t<img src=\"jsp/upload/image/20200818/1597766950354032692.png\" title=\"\" alt=\"\" width=\"512\"/>吃个火锅黄金分割符号皇家歌剧院工行建行湖广会馆放假回家好好v韩国成功v你好好更何况均衡价格v脚后跟会经常聚会救护车</p>', '2020-08-18 16:09:28', '2020-08-18 16:09:28', 0);
INSERT INTO `dish_info` VALUES ('17f22bd635054bdaaca225109ab4dfce', '80e349cb2b6f4630b0f2308980744656', '2', '川菜必点菜谱 既是菜又是酱 下饭神器', '这是一到经典川菜必点 好吃不腻 及其下饭', '<p><img src=\"jsp/upload/image/20200818/1597765915657020342.png\" title=\"\" alt=\"\" width=\"512\"/></p><p>食材：<br/>姜蒜切沫备用<br/>二荆条指天椒切沫备用<br/>鸡脯肉剁碎备用</p><p></p><p>鸡脯肉用黑胡椒 生粉 生抽腌制10分钟</p><p></p><p>油5层热 下腌好的鸡脯肉 翻炒变色下姜蒜爆香</p><p></p><p>暴香后下辣椒沫爆香，加料酒去腥 老抽上色 猛火快炒1分钟</p><p></p><p>加入芽菜 爆炒2分钟，@不要加调味料了 翻炒出香味即可出锅了</p><p></p><p>可做菜又可以做酱菜 及其下饭 好吃不腻</p><p><img src=\"jsp/upload/image/20200818/1597765980626090876.png\" title=\"\" alt=\"\" width=\"512\"/></p>', '2020-08-18 15:53:04', '2020-08-18 15:53:04', 0);
INSERT INTO `dish_info` VALUES ('38aa9c4daaa04fda976d107d9063a3b5', 'b995549f3f9b4d2e91036091a79f56ba', '1', '123', '123', '<p><img src=\"jsp/upload/image/20200818/1597768922534076921.png\" title=\"\" alt=\"\" width=\"512\"/></p><p>1234354543东方华府很多很多水水水水水水水水事实上当时官方打算挂的SD敢达身高多少郭德纲郭德纲</p>', '2020-08-18 16:42:10', '2020-08-18 16:42:10', 0);
INSERT INTO `dish_info` VALUES ('b2bc37fcaece46f48505651c5f3de48d', '80e349cb2b6f4630b0f2308980744656', '1', '注意！这不是黑暗料理！香蕉生菜奶昔', '充满能量！', '<p><img src=\"jsp/upload/image/20200818/1597768440969082433.png\" title=\"\" alt=\"\" width=\"512\"/></p><p>家里经常有香蕉已经软烂的不像话，吃着口感又不好，扔了又浪费，真的是很尴尬的程度了。<br/><br/>突如其来的想法，拿生菜综合一下香蕉的甜腻，不知道会不会是黑暗料理，试了一下结果不错～\r\n &nbsp; &nbsp; &nbsp;</p><p>香蕉1根，生菜2片，切小块</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>倒入100ml优酸乳，选你喜欢的口味就好</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>再倒入100ml椰汁，椰汁恬淡解腻（也可以不放）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>榨汁，OK啦，就这么简单！</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>刚榨完绿油油的超好看哟～拍照抓紧！熊熊等待天光，错过了最美的颜色，不过还是很好看哒</p><p><br/></p>', '2020-08-18 16:34:39', '2020-08-18 16:34:39', 0);
INSERT INTO `dish_info` VALUES ('b4c42c8635894f8c94ffd66ee8da4e45', '80e349cb2b6f4630b0f2308980744656', '2', '川菜经典“蒜泥白肉”', '肉汁饱满肥而不腻', '<p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\"><img src=\"jsp/upload/image/20200818/1597765501029021400.png\" title=\"\" alt=\"\" width=\"384\"/></span></p><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\"><br/></span></p><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">蒜泥白肉是一道菜品，属于川菜菜系，制作原料主要有蒜泥、肉等，口味鲜美，营养丰富，食时用筷拌合，随着热气，一股酱油、辣椒油和大蒜组合的香味直扑鼻端，使人食欲大振。蒜味浓厚，肥而不腻。</span></p><ol class=\" list-paddingleft-2\" style=\"list-style-type: decimal;\"><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">将锅烧热，五花肉皮朝下放入锅中把皮烙成金黄色（这一步是为了去除残留的猪毛和皮腥味），将烙好的五花肉放入温水中洗净表皮备用<img src=\"jsp/upload/image/20200818/1597765444072082806.png\" title=\"\" alt=\"\" width=\"512\"/></span></p></li><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">洗干净的五花肉冷水下锅，加入拍散的生姜、打好结的小葱、少许青花椒，再倒入料酒，大火烧开后，再转入小火煮20分钟（煮至用筷子轻戳五花肉即可戳透，就说明五花肉已经煮熟了）<img src=\"jsp/upload/image/20200818/1597765453180009285.png\" title=\"\" alt=\"\" width=\"512\"/></span><br/></p></li></ol>', '2020-08-18 15:45:04', '2020-08-18 15:45:04', 0);
INSERT INTO `dish_info` VALUES ('d177e5e9271a4eccab03434f0ee45b31', '80e349cb2b6f4630b0f2308980744656', '2', '水煮肉片', '色香味俱全', '<p><img src=\"jsp/upload/image/20200818/1597764663438095907.png\" title=\"\" alt=\"\" width=\"512\"/></p><ol class=\" list-paddingleft-2\" style=\"list-style-type: decimal;\"><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">材料处理</span><br/><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">猪肉洗净切薄片，加入白糖，鸡蛋清一个，酱油，水淀粉，盐适量腌制20分钟</span></p></li><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">豆瓣剁碎，干辣椒切段，姜蒜切片，蒜切末，葱切葱花,油麦菜切段</span></p></li><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">配菜炒制</span><br/><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">锅中放油，下入干辣椒，花椒，姜蒜，炒出香味，将油麦菜倒入锅中，加盐鸡精调味，炒断生放入碗中备用</span></p></li><li><p><img src=\"jsp/upload/image/20200818/1597764709904084309.png\" title=\"\" alt=\"\" width=\"512\"/><img src=\"jsp/upload/image/20200818/1597764715807056518.png\" title=\"\" alt=\"\" width=\"512\"/></p></li><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">成品制作</span><br/><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">起锅烧油，下入豆瓣酱，炒散后下葱姜蒜和花椒，小火炒出香味，下入干辣椒增加颜色和辣味。</span></p></li><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">再加入适量清水，大火烧开，放入盐，鸡精，白糖，小火煮2分钟</span></p></li><li><p><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">将码好的肉片下入锅中，肉片熟后加入水淀粉勾芡，加花椒油香油出锅</span><br/><br/><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">将汤河和肉片倒入铺好的菜的碗中，撒少许花椒面，辣椒面，蒜泥和芝麻</span><br/><br/><span style=\"color: rgb(34, 34, 34); font-size: 18px; background-color: rgb(255, 255, 255);\">将油烧至6成热淋到蒜泥上撒上葱花即可</span><br/></p></li></ol>', '2020-08-18 15:32:49', '2020-08-18 15:32:49', 0);
INSERT INTO `dish_info` VALUES ('f19bbe04a3744bb8bcc2deeaf610cf49', '5931bd77c41d4d838482fe0133569f0d', '7', '清真鲈鱼', '懒人福音', '<p>只需买好鱼,切葱姜蒜 即可</p><p>上锅开蒸</p><p><img src=\"jsp/upload/image/20200818/1597768429065002027.png\" title=\"\" alt=\"\" width=\"512\"/></p><p>....................................................................................<br/></p>', '2020-08-18 16:34:17', '2020-08-18 16:34:17', 0);
INSERT INTO `dish_info` VALUES ('f7bcbd1710ea41848c875b0dff86bc34', '469faee89b844a14bf2cc4895642bafc', '1', '土笋冻', '一种色香味俱佳的特色传统风味小吃', '<p style=\"border: 0px; margin: 0.63em 0px 1.8em; padding: 0px; font-size: 16px; counter-reset: list-1 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0; color: rgb(25, 25, 25); font-family: &quot;PingFang SC&quot;, Arial, 微软雅黑, 宋体, simsun, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;\">土笋冻是起源于福建泉州，一种色香味俱佳的特色传统风味小吃，是一种由特有产品加工而成的冻品，相传发明人是民族英雄郑成功。</p><p style=\"border: 0px; margin: 0.63em 0px 1.8em; padding: 0px; font-size: 16px; counter-reset: list-1 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0; color: rgb(25, 25, 25); font-family: &quot;PingFang SC&quot;, Arial, 微软雅黑, 宋体, simsun, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;\">此冻味美甘鲜，颜色白润晶莹剔透，其肉清，味美甘鲜，清香软嫩，滑溜爽口。配上好酱油、北醋、甜酱、辣酱、芥辣、蒜蓉、海蜇及芫荽、酸白萝卜丝、辣椒丝、番茄片等就成了色香味俱佳的风味小吃了。</p><p style=\"border: 0px; margin: 0.63em 0px 1.8em; padding: 0px; font-size: 16px; counter-reset: list-1 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0; color: rgb(25, 25, 25); font-family: &quot;PingFang SC&quot;, Arial, 微软雅黑, 宋体, simsun, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;\">对了忘了说，这种冻的主料不是什么笋，而是一种蠕虫……</p><p><img src=\"jsp/upload/image/20200818/1597765950364078360.png\" title=\"\" alt=\"\" width=\"512\"/></p>', '2020-08-18 15:52:46', '2020-08-18 15:52:46', 0);
INSERT INTO `dish_info` VALUES ('fd30c9ab78674c3dbb370730e96c1c33', '81f1706d64a34939941a855cf8cab40c', '4', '电饭锅做香喷喷的腊肠煲仔饭', '很好吃呢！！', '<p style=\"text-align:center\"><img src=\"jsp/upload/image/20200818/1597768098226098419.png\" title=\"\" alt=\"\" width=\"251\" height=\"241\"/></p><p>1、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">准备所有食材。</span></p><p>2、<span style=\"background-color: rgb(255, 255, 255); font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px;\">腊肠切片，淘洗干净大米。</span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"></span>3、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">加一指节的水没过大米。</span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">4、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">把切片的腊肠加进去。</span></span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">5、</span><span style=\"background-color: rgb(255, 255, 255); font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px;\">盖上盖子，小熊微电脑智能电饭煲启动煮饭键</span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"></span>6、<span style=\"background-color: rgb(255, 255, 255); font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px;\">上海青洗净焯水。</span></p><p>7、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">胡萝卜我切成了片，然后用的果蔬压模器压了形状，把胡萝卜也焯水煮熟。</span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">8、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">腊肠饭快要好的时候我们来调酱汁，把2勺生抽半勺蚝油和半勺细砂糖放碗里搅拌均匀备用。</span></span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">9、</span></span><span style=\"background-color: rgb(255, 255, 255); font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px;\">30分钟后，腊肠饭就煮好了</span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"></span></span>10、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">把青菜胡萝卜摆上去，</span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">11、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">酱汁淋上去。</span></span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">12、<span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">搅拌均匀就可以吃了，香喷喷的腊肠煲仔饭就做好了，再也不用去外面吃了，自己做的干净卫生又好吃。</span></span></span></p><p><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: &quot;Hiragino Sans GB&quot;, STHeiti, 微软雅黑, &quot;Microsoft YaHei&quot;, Helvetica, Arial, serif; font-size: 18px; background-color: rgb(255, 255, 255);\">13、点个赞！</span></span></span></p>', '2020-08-18 16:31:54', '2020-08-18 16:31:54', 0);
COMMIT;

-- ----------------------------
-- Table structure for menu_dish
-- ----------------------------
DROP TABLE IF EXISTS `menu_dish`;
CREATE TABLE `menu_dish` (
  `menu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dish_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `collection_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `collection_delete` int DEFAULT '0',
  KEY `menu_dish_index` (`menu_id`) USING BTREE,
  KEY `menu_dish_dish_id_index` (`dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu_dish
-- ----------------------------
BEGIN;
INSERT INTO `menu_dish` VALUES ('acc156d81c8942088a6ad81864410380', 'b4c42c8635894f8c94ffd66ee8da4e45', '2020-08-18 15:46:01', 0);
INSERT INTO `menu_dish` VALUES ('df83f7e3c3d64076ae0836f6bd465f09', 'b4c42c8635894f8c94ffd66ee8da4e45', '2020-08-18 15:47:56', 0);
INSERT INTO `menu_dish` VALUES ('acc156d81c8942088a6ad81864410380', '17f22bd635054bdaaca225109ab4dfce', '2020-08-18 16:23:49', 0);
INSERT INTO `menu_dish` VALUES ('acc156d81c8942088a6ad81864410380', 'b2bc37fcaece46f48505651c5f3de48d', '2020-08-18 17:09:13', 0);
COMMIT;

-- ----------------------------
-- Table structure for menu_info
-- ----------------------------
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE `menu_info` (
  `menu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `menu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `menu_delete` int DEFAULT '0',
  KEY `menu_index` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu_info
-- ----------------------------
BEGIN;
INSERT INTO `menu_info` VALUES ('acc156d81c8942088a6ad81864410380', '80e349cb2b6f4630b0f2308980744656', '土土最爱', 0);
INSERT INTO `menu_info` VALUES ('df83f7e3c3d64076ae0836f6bd465f09', '469faee89b844a14bf2cc4895642bafc', '测试菜谱1', 0);
INSERT INTO `menu_info` VALUES ('4d50603903e94032ad019187b505de45', '81f1706d64a34939941a855cf8cab40c', '路光莹爱吃的', 0);
COMMIT;

-- ----------------------------
-- Table structure for menu_popular
-- ----------------------------
DROP TABLE IF EXISTS `menu_popular`;
CREATE TABLE `menu_popular` (
  `dish_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dish_like` int DEFAULT '0',
  `dish_collect` int DEFAULT '0',
  `dish_click` int DEFAULT '0',
  PRIMARY KEY (`dish_id`) USING BTREE,
  KEY `menu_pop` (`dish_id`,`dish_like`,`dish_collect`,`dish_click`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu_popular
-- ----------------------------
BEGIN;
INSERT INTO `menu_popular` VALUES ('01d95a56e4f243adb3cb677c30a12d44', 4, 0, 0);
INSERT INTO `menu_popular` VALUES ('17f22bd635054bdaaca225109ab4dfce', 1, 1, 0);
INSERT INTO `menu_popular` VALUES ('38aa9c4daaa04fda976d107d9063a3b5', 0, 0, 0);
INSERT INTO `menu_popular` VALUES ('b2bc37fcaece46f48505651c5f3de48d', 1, 1, 0);
INSERT INTO `menu_popular` VALUES ('b4c42c8635894f8c94ffd66ee8da4e45', 2, 2, 0);
INSERT INTO `menu_popular` VALUES ('d177e5e9271a4eccab03434f0ee45b31', 2, 1, 0);
INSERT INTO `menu_popular` VALUES ('f19bbe04a3744bb8bcc2deeaf610cf49', 2, 0, 0);
INSERT INTO `menu_popular` VALUES ('f7bcbd1710ea41848c875b0dff86bc34', 1, 1, 0);
INSERT INTO `menu_popular` VALUES ('fd30c9ab78674c3dbb370730e96c1c33', 1, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for picture_info
-- ----------------------------
DROP TABLE IF EXISTS `picture_info`;
CREATE TABLE `picture_info` (
  `dish_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pic_address` longtext CHARACTER SET utf8 COLLATE utf8_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of picture_info
-- ----------------------------
BEGIN;
INSERT INTO `picture_info` VALUES ('d177e5e9271a4eccab03434f0ee45b31', 'jsp/upload/image/20200818/1597764663438095907.png');
INSERT INTO `picture_info` VALUES ('d177e5e9271a4eccab03434f0ee45b31', 'jsp/upload/image/20200818/1597764709904084309.png');
INSERT INTO `picture_info` VALUES ('d177e5e9271a4eccab03434f0ee45b31', 'jsp/upload/image/20200818/1597764715807056518.png');
INSERT INTO `picture_info` VALUES ('b4c42c8635894f8c94ffd66ee8da4e45', 'jsp/upload/image/20200818/1597765501029021400.png');
INSERT INTO `picture_info` VALUES ('b4c42c8635894f8c94ffd66ee8da4e45', 'jsp/upload/image/20200818/1597765444072082806.png');
INSERT INTO `picture_info` VALUES ('b4c42c8635894f8c94ffd66ee8da4e45', 'jsp/upload/image/20200818/1597765453180009285.png');
INSERT INTO `picture_info` VALUES ('f7bcbd1710ea41848c875b0dff86bc34', 'jsp/upload/image/20200818/1597765950364078360.png');
INSERT INTO `picture_info` VALUES ('17f22bd635054bdaaca225109ab4dfce', 'jsp/upload/image/20200818/1597765915657020342.png');
INSERT INTO `picture_info` VALUES ('17f22bd635054bdaaca225109ab4dfce', 'jsp/upload/image/20200818/1597765980626090876.png');
INSERT INTO `picture_info` VALUES ('1c002303495944ccb091d4a6dcb5edff', 'jsp/upload/image/20200818/1597766080661019102.png');
INSERT INTO `picture_info` VALUES ('01d95a56e4f243adb3cb677c30a12d44', 'jsp/upload/image/20200818/1597766950354032692.png');
INSERT INTO `picture_info` VALUES ('e9f8cbb1cb3a4fa1b236e883c0bbe3cd', 'jsp/upload/image/20200818/1597765950364078360.png');
INSERT INTO `picture_info` VALUES ('52661a1754324788887a6761d35feac9', 'jsp/upload/image/20200818/1597764663438095907.png');
INSERT INTO `picture_info` VALUES ('52661a1754324788887a6761d35feac9', 'jsp/upload/image/20200818/1597764709904084309.png');
INSERT INTO `picture_info` VALUES ('52661a1754324788887a6761d35feac9', 'jsp/upload/image/20200818/1597764715807056518.png');
INSERT INTO `picture_info` VALUES ('fd30c9ab78674c3dbb370730e96c1c33', 'jsp/upload/image/20200818/1597768098226098419.png');
INSERT INTO `picture_info` VALUES ('c243b4b0d5a64dad868bd254b95caf73', 'jsp/upload/image/20200818/1597768326810045109.png');
INSERT INTO `picture_info` VALUES ('0e3c80e9d40c4db9acf2328b747d87d6', 'jsp/upload/image/20200818/1597768326810045109.png');
INSERT INTO `picture_info` VALUES ('98868f0b0998478ab72fb5109b08232d', 'jsp/upload/image/20200818/1597768326810045109.png');
INSERT INTO `picture_info` VALUES ('9e7a591d521d46d480022be23fabc723', 'jsp/upload/image/20200818/1597768326810045109.png');
INSERT INTO `picture_info` VALUES ('f19bbe04a3744bb8bcc2deeaf610cf49', 'jsp/upload/image/20200818/1597768429065002027.png');
INSERT INTO `picture_info` VALUES ('b2bc37fcaece46f48505651c5f3de48d', 'jsp/upload/image/20200818/1597768440969082433.png');
INSERT INTO `picture_info` VALUES ('38aa9c4daaa04fda976d107d9063a3b5', 'jsp/upload/image/20200818/1597768922534076921.png');
INSERT INTO `picture_info` VALUES ('f5f9ec75d93b410eb89de02ae8eca736', 'jsp/upload/image/20200818/1597768440969082433.png');
COMMIT;

-- ----------------------------
-- Table structure for tag_info
-- ----------------------------
DROP TABLE IF EXISTS `tag_info`;
CREATE TABLE `tag_info` (
  `tag_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tag_intro` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tag_delete` int DEFAULT '0',
  PRIMARY KEY (`tag_id`) USING BTREE,
  KEY `tag_index` (`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tag_info
-- ----------------------------
BEGIN;
INSERT INTO `tag_info` VALUES ('1', '黑暗料理', '这是人吃的？', 0);
INSERT INTO `tag_info` VALUES ('2', '川菜', '川菜是中国八大菜系之一，起源于四川、重庆，以麻、辣、鲜、香为特色。', 0);
INSERT INTO `tag_info` VALUES ('3', '鲁菜', '鲁菜起源于山东的齐鲁风味，是中国传统四大菜系（也是八大菜系）中唯一的自发型菜系（相对于淮扬、川、粤等影响型菜系而言），是历史最悠久、技法最丰富、难度最高、最见功力的菜系之一。', 0);
INSERT INTO `tag_info` VALUES ('4', '粤菜', '粤菜即广东菜，源自中原，经历了两千多年的发展历程后，到了晚清时期已渐成熟 [7]  。由广府菜（即广州府菜）、潮州菜（也称潮汕菜）、东江菜（也称客家菜）三种地方风味组成，三种风味各具特色', 0);
INSERT INTO `tag_info` VALUES ('5', '苏菜', '即江苏菜系。江苏菜系在烹饪学术上一般称为“苏菜”，由南京、徐海、淮扬和苏南四种风味江组成，是宫廷第二大菜系，今天国宴仍以淮扬菜系为主。', 0);
INSERT INTO `tag_info` VALUES ('6', '闽菜', '闽菜是以闽东、闽南、闽西、闽北、闽中、莆仙地方风味菜为主形成的菜系。以福州菜为代表。', 0);
INSERT INTO `tag_info` VALUES ('7', '浙菜', '浙江地处中国东海之滨，素称鱼米之乡，特产丰富，盛产山珍海味和各种鱼类。', 0);
INSERT INTO `tag_info` VALUES ('8', '徽菜', '指徽州菜，简称徽菜，是中国八大菜系之一。', 0);
INSERT INTO `tag_info` VALUES ('9', '湘菜', '湘菜是中国历史悠久的一个地方风味菜。', 0);
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_nickname` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_password` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_mail` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_picture` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_delete` int DEFAULT '0',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `login_index` (`user_name`,`user_password`,`user_mail`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES ('0', 'adminstor', '管理者', 'JfnnlDI7RTiF', '6666666@qq.com', 'http://pic1.sc.chinaz.com/files/pic/pic9/202007/bpic20724.jpg', '2020-08-11 18:10:25', '2020-08-14 14:29:26', 0);
INSERT INTO `user_info` VALUES ('0e4b84249e0c49bf820e6ce3cb0623fe', '111111111', '快来修改昵称吧~', 'G72IZGCCcBXl', '11111111@11.com', './static/images/logo.png', '2020-08-18 15:34:07', '2020-08-18 15:34:07', 0);
INSERT INTO `user_info` VALUES ('469faee89b844a14bf2cc4895642bafc', 'testUser', 'WonderFour', 'JfnnlDI7RTiF', '1234567@qq.com', './static/images/logo.png', '2020-08-18 15:45:09', '2020-08-18 15:58:07', 0);
INSERT INTO `user_info` VALUES ('5931bd77c41d4d838482fe0133569f0d', 'zhangpeng', '快来修改昵称吧~', 'JfnnlDI7RTiF', '1084668052@qq.com', './static/images/logo.png', '2020-08-18 15:33:56', '2020-08-18 15:33:56', 0);
INSERT INTO `user_info` VALUES ('5bb3804ca25a47b191c8a86f663cbc59', 'huojq111', 're\'t', 'JfnnlDI7RTiF', 'qwer@1.1', './static/images/logo.png', '2020-08-18 16:01:41', '2020-08-18 16:15:31', 0);
INSERT INTO `user_info` VALUES ('73b995b341b94db6bd1fb1900ecd6a23', '123456789', '快来修改昵称吧~', 'JfnnlDI7RTiF', '123456789@qq.com', './static/images/logo.png', '2020-08-18 15:35:37', '2020-08-18 15:35:37', 0);
INSERT INTO `user_info` VALUES ('80e349cb2b6f4630b0f2308980744656', 'wangji233', '一口士土', 'JfnnlDI7RTiF', 'wangji233@qq.com', './static/images/logo.png', '2020-08-18 15:26:36', '2020-08-18 15:33:16', 0);
INSERT INTO `user_info` VALUES ('81f1706d64a34939941a855cf8cab40c', 'luguangying', '小路咩咩叫', 'JfnnlDI7RTiF', 'luguangying@qq.com', './static/images/logo.png', '2020-08-18 15:45:27', '2020-08-18 16:32:16', 0);
INSERT INTO `user_info` VALUES ('9a93d70ef42541a58f1dab5e2dbc2de7', 'cxxcxx123', '快来修改昵称吧~', 'JfnnlDI7RTiF', '3516564940@qq.com', './static/images/logo.png', '2020-08-18 16:42:33', '2020-08-18 16:42:33', 0);
INSERT INTO `user_info` VALUES ('b995549f3f9b4d2e91036091a79f56ba', '1422009417', '快来修改昵称吧~', 'JdVa0oOqQAr0', '1422009417@12.co', './static/images/logo.png', '2020-08-18 16:10:31', '2020-08-18 16:10:31', 0);
INSERT INTO `user_info` VALUES ('bfc98290253840a9b890d14b454b6818', 'zhangsanw', '快来修改昵称吧~', 'rbyRpD6Yijtb', '111@11.com', './static/images/logo.png', '2020-08-18 17:03:24', '2020-08-18 17:03:24', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
