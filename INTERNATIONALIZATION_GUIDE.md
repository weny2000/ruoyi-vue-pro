# 芋道项目多语言化完整实施指南

## 概述

本指南提供了芋道项目完整的多语言化（国际化）解决方案，包括后端Spring Boot应用和前端Vue应用的国际化支持。

## 目录

1. [后端多语言化](#后端多语言化)
2. [前端多语言化](#前端多语言化)
3. [数据库多语言化](#数据库多语言化)
4. [部署和配置](#部署和配置)
5. [最佳实践](#最佳实践)
6. [常见问题](#常见问题)

## 后端多语言化

### 1. 核心组件

#### 1.1 国际化配置类
- `I18nConfiguration.java` - Spring Boot国际化配置
- `I18nLocaleInterceptor.java` - 语言切换拦截器
- `I18nUtils.java` - 国际化工具类

#### 1.2 增强的ErrorCode类
- 支持国际化的错误码对象
- 自动根据当前语言环境返回对应的错误消息
- 支持参数化消息

#### 1.3 资源文件结构
```
src/main/resources/i18n/
├── messages_zh_CN.properties     # 中文消息
├── messages_en_US.properties     # 英文消息
├── error_codes_zh_CN.properties  # 中文错误码
└── error_codes_en_US.properties  # 英文错误码
```

### 2. 使用方式

#### 2.1 在代码中使用国际化
```java
// 获取国际化消息
String message = I18nUtils.getMessage("user.login.success");

// 带参数的国际化消息
String message = I18nUtils.getMessage("user.disabled", new Object[]{"张三"});

// 在异常中使用
throw new ServiceException(USER_NOT_EXISTS);
```

#### 2.2 HTTP请求头支持
客户端可以通过以下方式指定语言：
- 请求头：`Accept-Language: zh-CN`
- 请求参数：`?lang=en_US`

### 3. API接口

#### 3.1 语言管理接口
- `GET /system/i18n/current-locale` - 获取当前语言环境
- `POST /system/i18n/change-locale` - 切换语言环境
- `GET /system/i18n/supported-locales` - 获取支持的语言列表

#### 3.2 演示接口
- `GET /system/i18n-demo/success` - 成功消息演示
- `GET /system/i18n-demo/error` - 错误消息演示
- `GET /system/i18n-demo/exception` - 异常演示

## 前端多语言化

### 1. Vue3 + Element Plus 配置

#### 1.1 安装依赖
```bash
npm install vue-i18n@9
```

#### 1.2 目录结构
```
src/locales/
├── index.js           # 国际化配置入口
├── zh-CN.js          # 中文语言包
└── en-US.js          # 英文语言包
```

#### 1.3 组件使用
```vue
<template>
  <div>
    <h1>{{ $t('system.title') }}</h1>
    <el-button>{{ $t('common.save') }}</el-button>
  </div>
</template>

<script setup>
import { useI18n } from 'vue-i18n'
const { t } = useI18n()

const handleSave = () => {
  ElMessage.success(t('message.saveSuccess'))
}
</script>
```

### 2. 语言切换组件
提供了完整的语言切换组件 `LangSelect.vue`，支持：
- 下拉选择语言
- 本地存储用户选择
- 自动刷新页面应用新语言

### 3. HTTP请求集成
- 自动在请求头中添加 `Accept-Language`
- 响应错误的国际化处理
- Element Plus组件的语言同步

## 数据库多语言化

### 1. 数据表设计

#### 1.1 核心表结构
- `system_language` - 系统语言配置表
- `system_i18n_message` - 国际化消息表
- `system_menu_i18n` - 菜单多语言表
- `system_dict_type_i18n` - 字典类型多语言表
- `system_dict_data_i18n` - 字典数据多语言表

#### 1.2 设计原则
- 主表存储默认语言数据
- 多语言表存储其他语言翻译
- 支持租户隔离
- 支持软删除

### 2. 数据访问层
- `SystemLanguageMapper` - 语言配置数据访问
- `SystemI18nMessageMapper` - 国际化消息数据访问
- 支持按语言代码查询
- 支持模块化管理

## 部署和配置

### 1. 后端配置

#### 1.1 application.yaml 配置
```yaml
spring:
  messages:
    basename: i18n/messages,i18n/error_codes
    encoding: UTF-8
    cache-duration: -1
```

#### 1.2 默认语言设置
系统默认语言为简体中文（zh_CN），可通过配置修改。

### 2. 前端配置

#### 1.3 构建配置
确保构建工具正确处理多语言资源文件。

#### 1.4 CDN部署
如果使用CDN，确保语言包文件正确上传和访问。

### 3. 数据库初始化

#### 3.1 执行SQL脚本
```bash
# 执行多语言表结构创建脚本
mysql -u username -p database_name < sql/mysql/i18n-tables.sql
```

#### 3.2 初始化数据
脚本会自动插入：
- 12种常用语言配置
- 基础国际化消息（中英文）

## 最佳实践

### 1. 消息键命名规范
- 使用层级结构：`module.function.action`
- 示例：`user.login.success`、`role.name.duplicate`
- 保持键名简洁且具有描述性

### 2. 参数化消息
```properties
# 中文
user.disabled=名字为【{0}】的用户已被禁用

# 英文  
user.disabled=User with name [{0}] has been disabled
```

### 3. 错误码国际化
```java
// 定义错误码时指定国际化键
ErrorCode USER_NOT_EXISTS = new ErrorCode(1_002_003_003, "用户不存在", "user.not.exists");

// 使用时自动获取国际化消息
throw new ServiceException(USER_NOT_EXISTS);
```

### 4. 前端最佳实践
- 统一管理所有文本资源
- 使用TypeScript定义国际化键类型
- 组件化语言切换功能
- 缓存用户语言选择

### 5. 性能优化
- 使用缓存减少数据库查询
- 按需加载语言包
- 合理设置缓存过期时间

## 支持的语言

系统默认支持以下语言：

| 语言代码 | 语言名称 | 本地名称 | 状态 |
|---------|---------|---------|------|
| zh_CN | 简体中文 | 简体中文 | ✅ 完整支持 |
| en_US | English (US) | English (US) | ✅ 完整支持 |
| zh_TW | 繁体中文 | 繁體中文 | 🔄 部分支持 |
| ja_JP | 日本语 | 日本語 | 🔄 部分支持 |
| ko_KR | 韩语 | 한국어 | 🔄 部分支持 |
| fr_FR | 法语 | Français | 🔄 部分支持 |
| de_DE | 德语 | Deutsch | 🔄 部分支持 |
| es_ES | 西班牙语 | Español | 🔄 部分支持 |

## 常见问题

### 1. 语言切换不生效
**问题**：切换语言后页面显示没有变化
**解决**：
- 检查请求头是否正确设置
- 确认资源文件路径正确
- 验证国际化配置是否生效

### 2. 部分文本没有翻译
**问题**：某些文本仍显示为中文
**解决**：
- 检查对应的国际化键是否存在
- 确认资源文件中是否有对应翻译
- 使用默认消息作为回退

### 3. 参数化消息格式错误
**问题**：带参数的消息显示异常
**解决**：
- 检查参数占位符格式：`{0}`, `{1}`
- 确认参数数组顺序正确
- 验证参数类型匹配

### 4. 数据库查询性能问题
**问题**：多语言查询导致性能下降
**解决**：
- 添加适当的数据库索引
- 使用缓存减少查询频率
- 考虑使用视图简化查询

### 5. 前端构建问题
**问题**：构建时语言包文件丢失
**解决**：
- 检查构建配置中的资源文件处理
- 确认语言包文件路径正确
- 验证打包后的文件结构

## 扩展新语言

### 1. 后端扩展
1. 在数据库中添加新语言配置
2. 创建对应的资源文件
3. 翻译所有消息键

### 2. 前端扩展
1. 创建新的语言包文件
2. 在国际化配置中注册
3. 更新语言选择组件

### 3. 测试验证
1. 验证所有功能在新语言下正常工作
2. 检查文本显示是否正确
3. 测试语言切换功能

## 维护和更新

### 1. 添加新的国际化消息
1. 在所有语言的资源文件中添加对应键值
2. 更新数据库中的国际化消息表
3. 测试新消息的显示效果

### 2. 修改现有消息
1. 同步更新所有语言版本
2. 考虑向后兼容性
3. 通知相关开发人员

### 3. 定期维护
1. 检查未翻译的消息
2. 更新过时的翻译
3. 优化性能和缓存策略

## 总结

本多语言化方案提供了完整的国际化支持，包括：

- ✅ 后端Spring Boot国际化配置
- ✅ 前端Vue3国际化支持
- ✅ 数据库多语言表设计
- ✅ 错误码国际化
- ✅ HTTP请求语言检测
- ✅ 管理界面语言切换
- ✅ 性能优化和缓存
- ✅ 扩展性设计

通过遵循本指南，可以快速为芋道项目添加完整的多语言支持，提升用户体验和产品的国际化水平。