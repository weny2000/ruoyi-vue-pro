-- 多语言化数据表结构

-- 1. 系统语言配置表
CREATE TABLE `system_language` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '语言ID',
  `code` varchar(10) NOT NULL COMMENT '语言代码，如：zh_CN, en_US',
  `name` varchar(50) NOT NULL COMMENT '语言名称',
  `native_name` varchar(50) NOT NULL COMMENT '本地语言名称',
  `flag_icon` varchar(100) DEFAULT NULL COMMENT '国旗图标',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0禁用 1启用）',
  `is_default` tinyint NOT NULL DEFAULT 0 COMMENT '是否默认语言（0否 1是）',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`, `deleted`, `tenant_id`) COMMENT '语言代码唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统语言配置表';

-- 2. 国际化消息表
CREATE TABLE `system_i18n_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `message_key` varchar(200) NOT NULL COMMENT '消息键',
  `language_code` varchar(10) NOT NULL COMMENT '语言代码',
  `message_value` text NOT NULL COMMENT '消息值',
  `module` varchar(50) DEFAULT NULL COMMENT '所属模块',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_key_lang` (`message_key`, `language_code`, `deleted`, `tenant_id`) COMMENT '消息键和语言唯一索引',
  KEY `idx_module` (`module`) COMMENT '模块索引',
  KEY `idx_language_code` (`language_code`) COMMENT '语言代码索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='国际化消息表';

-- 3. 菜单多语言表
CREATE TABLE `system_menu_i18n` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `language_code` varchar(10) NOT NULL COMMENT '语言代码',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_menu_lang` (`menu_id`, `language_code`, `deleted`, `tenant_id`) COMMENT '菜单和语言唯一索引',
  KEY `idx_menu_id` (`menu_id`) COMMENT '菜单ID索引',
  KEY `idx_language_code` (`language_code`) COMMENT '语言代码索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单多语言表';

-- 4. 字典类型多语言表
CREATE TABLE `system_dict_type_i18n` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_type_id` bigint NOT NULL COMMENT '字典类型ID',
  `language_code` varchar(10) NOT NULL COMMENT '语言代码',
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_type_lang` (`dict_type_id`, `language_code`, `deleted`, `tenant_id`) COMMENT '字典类型和语言唯一索引',
  KEY `idx_dict_type_id` (`dict_type_id`) COMMENT '字典类型ID索引',
  KEY `idx_language_code` (`language_code`) COMMENT '语言代码索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型多语言表';

-- 5. 字典数据多语言表
CREATE TABLE `system_dict_data_i18n` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_data_id` bigint NOT NULL COMMENT '字典数据ID',
  `language_code` varchar(10) NOT NULL COMMENT '语言代码',
  `label` varchar(100) NOT NULL COMMENT '字典标签',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_data_lang` (`dict_data_id`, `language_code`, `deleted`, `tenant_id`) COMMENT '字典数据和语言唯一索引',
  KEY `idx_dict_data_id` (`dict_data_id`) COMMENT '字典数据ID索引',
  KEY `idx_language_code` (`language_code`) COMMENT '语言代码索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据多语言表';

-- 插入默认语言配置
INSERT INTO `system_language` (`code`, `name`, `native_name`, `flag_icon`, `sort`, `status`, `is_default`) VALUES
('zh_CN', '简体中文', '简体中文', '🇨🇳', 1, 1, 1),
('zh_TW', '繁体中文', '繁體中文', '🇹🇼', 2, 1, 0),
('en_US', 'English (US)', 'English (US)', '🇺🇸', 3, 1, 0),
('en_GB', 'English (UK)', 'English (UK)', '🇬🇧', 4, 1, 0),
('ja_JP', '日本语', '日本語', '🇯🇵', 5, 1, 0),
('ko_KR', '韩语', '한국어', '🇰🇷', 6, 1, 0),
('fr_FR', '法语', 'Français', '🇫🇷', 7, 1, 0),
('de_DE', '德语', 'Deutsch', '🇩🇪', 8, 1, 0),
('es_ES', '西班牙语', 'Español', '🇪🇸', 9, 1, 0),
('pt_BR', '葡萄牙语', 'Português', '🇧🇷', 10, 1, 0),
('ru_RU', '俄语', 'Русский', '🇷🇺', 11, 1, 0),
('ar_SA', '阿拉伯语', 'العربية', '🇸🇦', 12, 1, 0);

-- 插入基础国际化消息（中文）
INSERT INTO `system_i18n_message` (`message_key`, `language_code`, `message_value`, `module`, `description`) VALUES
-- 系统通用
('system.success', 'zh_CN', '操作成功', 'system', '操作成功提示'),
('system.error', 'zh_CN', '操作失败', 'system', '操作失败提示'),
('system.unknown.error', 'zh_CN', '未知错误', 'system', '未知错误提示'),
('system.param.error', 'zh_CN', '参数错误', 'system', '参数错误提示'),
('system.no.permission', 'zh_CN', '没有权限', 'system', '无权限提示'),
('system.not.login', 'zh_CN', '未登录', 'system', '未登录提示'),
('system.login.expired', 'zh_CN', '登录已过期', 'system', '登录过期提示'),

-- 用户相关
('user.not.exists', 'zh_CN', '用户不存在', 'user', '用户不存在提示'),
('user.disabled', 'zh_CN', '用户已被禁用', 'user', '用户被禁用提示'),
('user.login.success', 'zh_CN', '登录成功', 'user', '登录成功提示'),
('user.login.failed', 'zh_CN', '登录失败', 'user', '登录失败提示'),
('user.logout.success', 'zh_CN', '退出成功', 'user', '退出成功提示'),
('user.password.error', 'zh_CN', '密码错误', 'user', '密码错误提示'),

-- 角色相关
('role.not.exists', 'zh_CN', '角色不存在', 'role', '角色不存在提示'),
('role.name.duplicate', 'zh_CN', '角色名称已存在', 'role', '角色名称重复提示'),
('role.disabled', 'zh_CN', '角色已被禁用', 'role', '角色被禁用提示'),

-- 菜单相关
('menu.not.exists', 'zh_CN', '菜单不存在', 'menu', '菜单不存在提示'),
('menu.name.duplicate', 'zh_CN', '菜单名称已存在', 'menu', '菜单名称重复提示'),
('menu.has.children', 'zh_CN', '存在子菜单，无法删除', 'menu', '菜单有子项提示');

-- 插入基础国际化消息（英文）
INSERT INTO `system_i18n_message` (`message_key`, `language_code`, `message_value`, `module`, `description`) VALUES
-- System common
('system.success', 'en_US', 'Operation successful', 'system', 'Operation success message'),
('system.error', 'en_US', 'Operation failed', 'system', 'Operation failed message'),
('system.unknown.error', 'en_US', 'Unknown error', 'system', 'Unknown error message'),
('system.param.error', 'en_US', 'Parameter error', 'system', 'Parameter error message'),
('system.no.permission', 'en_US', 'No permission', 'system', 'No permission message'),
('system.not.login', 'en_US', 'Not logged in', 'system', 'Not logged in message'),
('system.login.expired', 'en_US', 'Login expired', 'system', 'Login expired message'),

-- User related
('user.not.exists', 'en_US', 'User does not exist', 'user', 'User not exists message'),
('user.disabled', 'en_US', 'User has been disabled', 'user', 'User disabled message'),
('user.login.success', 'en_US', 'Login successful', 'user', 'Login success message'),
('user.login.failed', 'en_US', 'Login failed', 'user', 'Login failed message'),
('user.logout.success', 'en_US', 'Logout successful', 'user', 'Logout success message'),
('user.password.error', 'en_US', 'Password error', 'user', 'Password error message'),

-- Role related
('role.not.exists', 'en_US', 'Role does not exist', 'role', 'Role not exists message'),
('role.name.duplicate', 'en_US', 'Role name already exists', 'role', 'Role name duplicate message'),
('role.disabled', 'en_US', 'Role has been disabled', 'role', 'Role disabled message'),

-- Menu related
('menu.not.exists', 'en_US', 'Menu does not exist', 'menu', 'Menu not exists message'),
('menu.name.duplicate', 'en_US', 'Menu name already exists', 'menu', 'Menu name duplicate message'),
('menu.has.children', 'en_US', 'Has sub-menus, cannot delete', 'menu', 'Menu has children message');

-- 插入字典多语言示例数据
-- 注意：以下数据需要根据实际的字典类型和数据ID进行调整

-- 假设系统状态字典类型ID为1，插入英文翻译
INSERT INTO `system_dict_type_i18n` (`dict_type_id`, `language_code`, `name`) VALUES
(1, 'en_US', 'System Status'),
(1, 'ja_JP', 'システム状態'),
(1, 'ko_KR', '시스템 상태');

-- 假设系统状态字典数据的ID，插入英文翻译
-- 这些数据需要根据实际的字典数据ID进行调整
INSERT INTO `system_dict_data_i18n` (`dict_data_id`, `language_code`, `label`) VALUES
-- 启用/禁用状态
(1, 'en_US', 'Enabled'),
(1, 'ja_JP', '有効'),
(1, 'ko_KR', '활성화'),
(2, 'en_US', 'Disabled'),
(2, 'ja_JP', '無効'),
(2, 'ko_KR', '비활성화');

-- 性别字典多语言
-- 假设性别字典类型ID为2
INSERT INTO `system_dict_type_i18n` (`dict_type_id`, `language_code`, `name`) VALUES
(2, 'en_US', 'Gender'),
(2, 'ja_JP', '性別'),
(2, 'ko_KR', '성별');

-- 假设性别字典数据ID
INSERT INTO `system_dict_data_i18n` (`dict_data_id`, `language_code`, `label`) VALUES
-- 男/女/未知
(3, 'en_US', 'Male'),
(3, 'ja_JP', '男性'),
(3, 'ko_KR', '남성'),
(4, 'en_US', 'Female'),
(4, 'ja_JP', '女性'),
(4, 'ko_KR', '여성'),
(5, 'en_US', 'Unknown'),
(5, 'ja_JP', '不明'),
(5, 'ko_KR', '알 수 없음');