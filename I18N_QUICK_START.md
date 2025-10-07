# 芋道项目多语言化快速开始指南

## 🚀 快速部署（5分钟搞定）

### 1. 数据库部署
```bash
# 设置数据库密码
export DB_PASSWORD=your_password

# 执行部署脚本
./script/i18n-deploy.sh
```

### 2. 重启应用
```bash
# 重启Spring Boot应用
./restart.sh  # 或者你的重启脚本
```

### 3. 验证部署
```bash
# 测试中文API
curl -H "Accept-Language: zh-CN" http://localhost:8080/admin-api/system/i18n-demo/success

# 测试英文API  
curl -H "Accept-Language: en-US" http://localhost:8080/admin-api/system/i18n-demo/success
```

## 📋 核心功能

### ✅ 已实现功能

#### 后端功能
- [x] Spring Boot国际化配置
- [x] 自动语言检测（请求头/参数）
- [x] 错误码国际化
- [x] 国际化工具类
- [x] 多语言数据库表
- [x] RESTful API支持
- [x] 异常处理国际化

#### 前端功能
- [x] Vue3 + i18n配置指南
- [x] Element Plus语言同步
- [x] 语言切换组件
- [x] HTTP请求头自动设置
- [x] 本地存储语言偏好

#### 数据库功能
- [x] 多语言表结构设计
- [x] 12种语言预配置
- [x] 租户隔离支持
- [x] 软删除支持
- [x] 字典多语言支持
- [x] 缓存优化

### 🔄 支持的语言

| 语言 | 代码 | 状态 | 完成度 | 字典支持 |
|------|------|------|--------|----------|
| 简体中文 | zh_CN | ✅ | 100% | ✅ |
| English | en_US | ✅ | 100% | ✅ |
| 繁體中文 | zh_TW | 🔄 | 20% | 🔄 |
| 日本語 | ja_JP | 🔄 | 10% | ✅ |
| 한국어 | ko_KR | 🔄 | 10% | ✅ |
| Français | fr_FR | 🔄 | 10% | 🔄 |
| Deutsch | de_DE | 🔄 | 10% | 🔄 |
| Español | es_ES | 🔄 | 10% | 🔄 |

## 🛠️ 使用示例

### 后端使用

#### 1. 在Controller中使用
```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    public CommonResult<List<User>> getUsers() {
        // 自动根据请求头返回对应语言的消息
        return success(userList, I18nUtils.getMessage("user.list.success"));
    }
    
    @PostMapping("/users")
    public CommonResult<User> createUser(@RequestBody User user) {
        // 抛出国际化异常
        if (userExists(user.getUsername())) {
            throw new ServiceException(USER_USERNAME_EXISTS);
        }
        return success(createdUser, I18nUtils.getMessage("user.create.success"));
    }
}
```

#### 2. 在Service中使用
```java
@Service
public class UserService {
    
    public void validateUser(User user) {
        if (user.getAge() < 18) {
            // 带参数的国际化消息
            throw new ServiceException(new ErrorCode(400, 
                I18nUtils.getMessage("user.age.invalid", new Object[]{user.getAge()})));
        }
    }
}
```

### 前端使用

#### 1. 在Vue组件中使用
```vue
<template>
  <div>
    <h1>{{ $t('system.title') }}</h1>
    <el-button @click="handleSave">{{ $t('common.save') }}</el-button>
    <el-button @click="handleCancel">{{ $t('common.cancel') }}</el-button>
  </div>
</template>

<script setup>
import { useI18n } from 'vue-i18n'
import { ElMessage } from 'element-plus'

const { t } = useI18n()

const handleSave = async () => {
  try {
    await saveData()
    ElMessage.success(t('message.saveSuccess'))
  } catch (error) {
    ElMessage.error(t('message.saveFailed'))
  }
}
</script>
```

#### 2. 语言切换组件
```vue
<template>
  <LangSelect />
</template>

<script setup>
import LangSelect from '@/components/LangSelect.vue'
</script>
```

## 🔧 配置说明

### 后端配置

#### application.yaml
```yaml
spring:
  messages:
    basename: i18n/messages,i18n/error_codes
    encoding: UTF-8
    cache-duration: -1
```

#### 自定义配置
```java
@Configuration
public class I18nConfiguration {
    // 已自动配置，无需修改
}
```

### 前端配置

#### main.js
```javascript
import { createApp } from 'vue'
import i18n from './locales'

const app = createApp(App)
app.use(i18n)
```

#### axios拦截器
```javascript
axios.interceptors.request.use(config => {
  config.headers['Accept-Language'] = localStorage.getItem('language') || 'zh-CN'
  return config
})
```

## 📊 API接口

### 国际化管理接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/system/i18n/current-locale` | GET | 获取当前语言 |
| `/system/i18n/change-locale` | POST | 切换语言 |
| `/system/i18n/supported-locales` | GET | 获取支持的语言 |
| `/system/i18n/message` | GET | 获取国际化消息 |

### 演示接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/system/i18n-demo/success` | GET | 成功消息演示 |
| `/system/i18n-demo/error` | GET | 错误消息演示 |
| `/system/i18n-demo/exception` | GET | 异常演示 |
| `/system/i18n-demo/parameterized` | GET | 参数化消息演示 |

### 字典多语言接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/system/dict-i18n/dict-data/list` | GET | 获取字典数据列表（带国际化） |
| `/system/dict-i18n/dict-type/list` | GET | 获取字典类型列表（带国际化） |
| `/system/dict-i18n/dict-data/all` | GET | 获取所有字典数据（带国际化） |
| `/system/dict-i18n/dict-data/label` | GET | 获取字典标签（带国际化） |
| `/system/dict-i18n/dict-type` | POST | 创建/更新字典类型多语言 |
| `/system/dict-i18n/dict-data` | POST | 创建/更新字典数据多语言 |

### 字典多语言演示接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/system/dict-i18n-demo/dict-types` | GET | 字典类型多语言演示 |
| `/system/dict-i18n-demo/dict-data` | GET | 字典数据多语言演示 |
| `/system/dict-i18n-demo/dict-label` | GET | 字典标签多语言演示 |
| `/system/dict-i18n-demo/all-dict-data` | GET | 所有字典数据演示 |
| `/system/dict-i18n-demo/compare-languages` | GET | 多语言对比演示 |

## 🧪 测试用例

### 1. 语言切换测试
```bash
# 测试中文
curl -H "Accept-Language: zh-CN" \
  http://localhost:8080/admin-api/system/i18n-demo/success

# 测试英文
curl -H "Accept-Language: en-US" \
  http://localhost:8080/admin-api/system/i18n-demo/success

# 测试参数切换
curl "http://localhost:8080/admin-api/system/i18n-demo/success?lang=en_US"
```

### 2. 异常处理测试
```bash
# 测试国际化异常
curl -H "Accept-Language: en-US" \
  "http://localhost:8080/admin-api/system/i18n-demo/exception?throwException=true"
```

### 3. 参数化消息测试
```bash
# 测试带参数的消息
curl -H "Accept-Language: en-US" \
  http://localhost:8080/admin-api/system/i18n-demo/parameterized
```

### 4. 字典多语言测试
```bash
# 测试字典类型多语言
curl -H "Accept-Language: en-US" \
  http://localhost:8080/admin-api/system/dict-i18n-demo/dict-types

# 测试字典数据多语言
curl -H "Accept-Language: en-US" \
  "http://localhost:8080/admin-api/system/dict-i18n-demo/dict-data?dictType=sys_common_status"

# 测试字典标签多语言
curl "http://localhost:8080/admin-api/system/dict-i18n-demo/dict-label?dictType=sys_common_status&value=0&lang=en_US"

# 对比不同语言的字典数据
curl http://localhost:8080/admin-api/system/dict-i18n-demo/compare-languages?dictType=sys_common_status
```

## 🎯 最佳实践

### 1. 消息键命名
```
✅ 好的命名
user.login.success
role.name.duplicate
menu.delete.confirm

❌ 不好的命名
msg1
error_code_123
userLoginSuccessMessage
```

### 2. 参数化消息
```properties
# 中文
user.welcome=欢迎 {0}，您有 {1} 条新消息

# 英文
user.welcome=Welcome {0}, you have {1} new messages
```

### 3. 错误处理
```java
// 统一的错误码定义
public interface ErrorCodeConstants {
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1001, "用户不存在", "user.not.exists");
}

// 使用时自动国际化
throw new ServiceException(USER_NOT_EXISTS);
```

## 🚨 常见问题

### Q1: 语言切换后不生效？
**A**: 检查以下几点：
1. 请求头是否正确设置
2. 资源文件是否存在
3. 应用是否重启

### Q2: 部分文本没有翻译？
**A**: 
1. 检查资源文件中是否有对应的键
2. 确认键名拼写正确
3. 使用默认消息作为回退

### Q3: 前端语言切换组件不工作？
**A**:
1. 确认Vue i18n正确配置
2. 检查语言包文件路径
3. 验证localStorage存储

### Q4: 数据库查询性能问题？
**A**:
1. 添加适当的索引
2. 使用缓存机制
3. 考虑使用视图

## 📈 性能优化

### 1. 缓存策略
```java
@Cacheable(value = "i18n", key = "#messageKey + '_' + #languageCode")
public String getMessage(String messageKey, String languageCode) {
    // 缓存国际化消息
}
```

### 2. 数据库优化
```sql
-- 添加索引
CREATE INDEX idx_message_key_lang ON system_i18n_message(message_key, language_code);
CREATE INDEX idx_language_code ON system_i18n_message(language_code);
```

### 3. 前端优化
```javascript
// 按需加载语言包
const loadLocaleMessages = async (locale) => {
  const messages = await import(`./locales/${locale}.js`)
  i18n.global.setLocaleMessage(locale, messages.default)
}
```

## 🔮 扩展计划

### 短期计划（1-2周）
- [ ] 完善繁体中文翻译
- [ ] 添加日语基础翻译
- [ ] 优化前端语言切换体验
- [ ] 添加更多测试用例

### 中期计划（1个月）
- [ ] 支持更多欧洲语言
- [ ] 添加管理界面
- [ ] 实现翻译导入导出
- [ ] 性能监控和优化

### 长期计划（3个月）
- [ ] 自动翻译集成
- [ ] 多租户语言隔离
- [ ] 实时语言切换
- [ ] 移动端适配

## 📞 技术支持

如果在使用过程中遇到问题：

1. 查看详细文档：`INTERNATIONALIZATION_GUIDE.md`
2. 检查部署日志：`i18n-deployment-report-*.txt`
3. 运行测试用例验证功能
4. 提交Issue或联系技术支持

---

**🎉 恭喜！你已经成功为芋道项目添加了完整的多语言支持！**