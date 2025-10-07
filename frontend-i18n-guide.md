# 前端国际化实施指南

## Vue3 + Element Plus 国际化配置

### 1. 安装依赖

```bash
npm install vue-i18n@9
```

### 2. 创建国际化配置文件

#### 2.1 创建 `src/locales/index.js`

```javascript
import { createI18n } from 'vue-i18n'
import zhCN from './zh-CN'
import enUS from './en-US'

// 获取浏览器语言
function getLanguage() {
  const language = localStorage.getItem('language') || navigator.language || 'zh-CN'
  const locales = ['zh-CN', 'en-US']
  for (const locale of locales) {
    if (language.indexOf(locale) > -1) {
      return locale
    }
  }
  return 'zh-CN'
}

const i18n = createI18n({
  legacy: false, // 使用 Composition API
  locale: getLanguage(),
  fallbackLocale: 'zh-CN',
  messages: {
    'zh-CN': zhCN,
    'en-US': enUS
  }
})

export default i18n
```

#### 2.2 创建中文语言包 `src/locales/zh-CN.js`

```javascript
export default {
  // 通用
  common: {
    confirm: '确认',
    cancel: '取消',
    save: '保存',
    delete: '删除',
    edit: '编辑',
    add: '新增',
    search: '搜索',
    reset: '重置',
    submit: '提交',
    back: '返回',
    close: '关闭',
    loading: '加载中...',
    noData: '暂无数据',
    operation: '操作',
    status: '状态',
    createTime: '创建时间',
    updateTime: '更新时间',
    remark: '备注',
    enable: '启用',
    disable: '禁用',
    yes: '是',
    no: '否'
  },

  // 系统相关
  system: {
    title: '芋道管理系统',
    welcome: '欢迎使用',
    logout: '退出登录',
    profile: '个人中心',
    changePassword: '修改密码',
    language: '语言切换'
  },

  // 登录相关
  login: {
    title: '用户登录',
    username: '用户名',
    password: '密码',
    captcha: '验证码',
    rememberMe: '记住我',
    login: '登录',
    register: '注册',
    forgetPassword: '忘记密码',
    loginSuccess: '登录成功',
    loginFailed: '登录失败',
    usernameRequired: '请输入用户名',
    passwordRequired: '请输入密码',
    captchaRequired: '请输入验证码'
  },

  // 菜单相关
  menu: {
    dashboard: '首页',
    system: '系统管理',
    user: '用户管理',
    role: '角色管理',
    menu: '菜单管理',
    dept: '部门管理',
    post: '岗位管理',
    dict: '字典管理',
    config: '参数设置',
    notice: '通知公告',
    log: '日志管理'
  },

  // 用户管理
  user: {
    username: '用户名',
    nickname: '昵称',
    email: '邮箱',
    mobile: '手机号',
    status: '状态',
    createTime: '创建时间',
    addUser: '新增用户',
    editUser: '编辑用户',
    deleteUser: '删除用户',
    resetPassword: '重置密码',
    userInfo: '用户信息',
    basicInfo: '基本信息',
    roleInfo: '角色信息'
  },

  // 角色管理
  role: {
    roleName: '角色名称',
    roleKey: '权限字符',
    roleSort: '显示顺序',
    status: '状态',
    createTime: '创建时间',
    addRole: '新增角色',
    editRole: '编辑角色',
    deleteRole: '删除角色',
    assignPermission: '分配权限',
    roleInfo: '角色信息',
    permissionInfo: '权限信息'
  },

  // 消息提示
  message: {
    success: '操作成功',
    error: '操作失败',
    warning: '警告',
    info: '提示',
    confirmDelete: '确认删除这条记录吗？',
    deleteSuccess: '删除成功',
    saveSuccess: '保存成功',
    updateSuccess: '更新成功',
    addSuccess: '新增成功',
    operationSuccess: '操作成功',
    operationFailed: '操作失败',
    networkError: '网络错误，请稍后重试',
    systemError: '系统错误',
    parameterError: '参数错误',
    noPermission: '没有权限',
    loginExpired: '登录已过期，请重新登录'
  },

  // 表单验证
  validation: {
    required: '此项为必填项',
    email: '请输入正确的邮箱地址',
    mobile: '请输入正确的手机号码',
    minLength: '长度不能少于{min}个字符',
    maxLength: '长度不能超过{max}个字符',
    numeric: '请输入数字',
    positive: '请输入正数',
    integer: '请输入整数'
  }
}
```

#### 2.3 创建英文语言包 `src/locales/en-US.js`

```javascript
export default {
  // Common
  common: {
    confirm: 'Confirm',
    cancel: 'Cancel',
    save: 'Save',
    delete: 'Delete',
    edit: 'Edit',
    add: 'Add',
    search: 'Search',
    reset: 'Reset',
    submit: 'Submit',
    back: 'Back',
    close: 'Close',
    loading: 'Loading...',
    noData: 'No Data',
    operation: 'Operation',
    status: 'Status',
    createTime: 'Create Time',
    updateTime: 'Update Time',
    remark: 'Remark',
    enable: 'Enable',
    disable: 'Disable',
    yes: 'Yes',
    no: 'No'
  },

  // System
  system: {
    title: 'Yudao Management System',
    welcome: 'Welcome',
    logout: 'Logout',
    profile: 'Profile',
    changePassword: 'Change Password',
    language: 'Language'
  },

  // Login
  login: {
    title: 'User Login',
    username: 'Username',
    password: 'Password',
    captcha: 'Captcha',
    rememberMe: 'Remember Me',
    login: 'Login',
    register: 'Register',
    forgetPassword: 'Forget Password',
    loginSuccess: 'Login Successful',
    loginFailed: 'Login Failed',
    usernameRequired: 'Please enter username',
    passwordRequired: 'Please enter password',
    captchaRequired: 'Please enter captcha'
  },

  // Menu
  menu: {
    dashboard: 'Dashboard',
    system: 'System Management',
    user: 'User Management',
    role: 'Role Management',
    menu: 'Menu Management',
    dept: 'Department Management',
    post: 'Post Management',
    dict: 'Dictionary Management',
    config: 'Config Management',
    notice: 'Notice Management',
    log: 'Log Management'
  },

  // User Management
  user: {
    username: 'Username',
    nickname: 'Nickname',
    email: 'Email',
    mobile: 'Mobile',
    status: 'Status',
    createTime: 'Create Time',
    addUser: 'Add User',
    editUser: 'Edit User',
    deleteUser: 'Delete User',
    resetPassword: 'Reset Password',
    userInfo: 'User Info',
    basicInfo: 'Basic Info',
    roleInfo: 'Role Info'
  },

  // Role Management
  role: {
    roleName: 'Role Name',
    roleKey: 'Role Key',
    roleSort: 'Sort',
    status: 'Status',
    createTime: 'Create Time',
    addRole: 'Add Role',
    editRole: 'Edit Role',
    deleteRole: 'Delete Role',
    assignPermission: 'Assign Permission',
    roleInfo: 'Role Info',
    permissionInfo: 'Permission Info'
  },

  // Messages
  message: {
    success: 'Operation Successful',
    error: 'Operation Failed',
    warning: 'Warning',
    info: 'Info',
    confirmDelete: 'Are you sure to delete this record?',
    deleteSuccess: 'Delete Successful',
    saveSuccess: 'Save Successful',
    updateSuccess: 'Update Successful',
    addSuccess: 'Add Successful',
    operationSuccess: 'Operation Successful',
    operationFailed: 'Operation Failed',
    networkError: 'Network Error, Please Try Again Later',
    systemError: 'System Error',
    parameterError: 'Parameter Error',
    noPermission: 'No Permission',
    loginExpired: 'Login Expired, Please Login Again'
  },

  // Form Validation
  validation: {
    required: 'This field is required',
    email: 'Please enter a valid email address',
    mobile: 'Please enter a valid mobile number',
    minLength: 'Length cannot be less than {min} characters',
    maxLength: 'Length cannot exceed {max} characters',
    numeric: 'Please enter a number',
    positive: 'Please enter a positive number',
    integer: 'Please enter an integer'
  }
}
```

### 3. 在 main.js 中注册

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import i18n from './locales'

const app = createApp(App)
app.use(i18n)
app.mount('#app')
```

### 4. 创建语言切换组件

#### 4.1 创建 `src/components/LangSelect.vue`

```vue
<template>
  <el-dropdown @command="handleCommand" class="lang-select">
    <span class="el-dropdown-link">
      <el-icon><Globe /></el-icon>
      {{ currentLangLabel }}
      <el-icon class="el-icon--right"><arrow-down /></el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item 
          v-for="lang in languages" 
          :key="lang.value"
          :command="lang.value"
          :disabled="lang.value === currentLang"
        >
          {{ lang.label }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { Globe, ArrowDown } from '@element-plus/icons-vue'

const { locale } = useI18n()

const languages = ref([
  { label: '简体中文', value: 'zh-CN' },
  { label: 'English', value: 'en-US' }
])

const currentLang = computed(() => locale.value)

const currentLangLabel = computed(() => {
  const lang = languages.value.find(item => item.value === currentLang.value)
  return lang ? lang.label : '简体中文'
})

const handleCommand = (command) => {
  if (command !== currentLang.value) {
    locale.value = command
    localStorage.setItem('language', command)
    // 可以在这里添加页面刷新或其他逻辑
    window.location.reload()
  }
}
</script>

<style scoped>
.lang-select {
  cursor: pointer;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  color: var(--el-text-color-primary);
}
</style>
```

### 5. 在组件中使用国际化

#### 5.1 在模板中使用

```vue
<template>
  <div>
    <h1>{{ $t('system.title') }}</h1>
    <el-button>{{ $t('common.save') }}</el-button>
    <el-button>{{ $t('common.cancel') }}</el-button>
  </div>
</template>
```

#### 5.2 在脚本中使用

```vue
<script setup>
import { useI18n } from 'vue-i18n'
import { ElMessage } from 'element-plus'

const { t } = useI18n()

const handleSave = () => {
  // 使用国际化消息
  ElMessage.success(t('message.saveSuccess'))
}

const handleError = () => {
  ElMessage.error(t('message.operationFailed'))
}
</script>
```

### 6. HTTP 请求拦截器配置

#### 6.1 在 axios 拦截器中添加语言头

```javascript
import axios from 'axios'
import { useI18n } from 'vue-i18n'

// 请求拦截器
axios.interceptors.request.use(
  config => {
    // 添加语言头
    const { locale } = useI18n()
    config.headers['Accept-Language'] = locale.value
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    const { t } = useI18n()
    
    if (error.response) {
      switch (error.response.status) {
        case 401:
          ElMessage.error(t('message.loginExpired'))
          break
        case 403:
          ElMessage.error(t('message.noPermission'))
          break
        case 500:
          ElMessage.error(t('message.systemError'))
          break
        default:
          ElMessage.error(t('message.networkError'))
      }
    } else {
      ElMessage.error(t('message.networkError'))
    }
    
    return Promise.reject(error)
  }
)
```

### 7. Element Plus 国际化配置

#### 7.1 配置 Element Plus 语言

```javascript
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'
import i18n from './locales'

const app = createApp(App)

// 根据当前语言设置 Element Plus 语言
const getElementLocale = () => {
  const locale = localStorage.getItem('language') || 'zh-CN'
  return locale === 'en-US' ? en : zhCn
}

app.use(ElementPlus, {
  locale: getElementLocale()
})

app.use(i18n)
app.mount('#app')
```

### 8. 动态切换 Element Plus 语言

```javascript
// 在语言切换时同时切换 Element Plus 语言
import { ElConfigProvider } from 'element-plus'
import { provide, ref } from 'vue'

const elementLocale = ref(getElementLocale())

const changeLanguage = (lang) => {
  locale.value = lang
  localStorage.setItem('language', lang)
  elementLocale.value = lang === 'en-US' ? en : zhCn
}

// 在根组件中提供
provide('elementLocale', elementLocale)
```

### 9. 使用示例

#### 9.1 用户管理页面示例

```vue
<template>
  <div class="user-management">
    <div class="header">
      <h2>{{ $t('menu.user') }}</h2>
      <el-button type="primary" @click="handleAdd">
        {{ $t('user.addUser') }}
      </el-button>
    </div>
    
    <el-table :data="userList" v-loading="loading">
      <el-table-column prop="username" :label="$t('user.username')" />
      <el-table-column prop="nickname" :label="$t('user.nickname')" />
      <el-table-column prop="email" :label="$t('user.email')" />
      <el-table-column prop="mobile" :label="$t('user.mobile')" />
      <el-table-column prop="status" :label="$t('user.status')">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? $t('common.enable') : $t('common.disable') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">
            {{ $t('common.edit') }}
          </el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">
            {{ $t('common.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { ElMessage, ElMessageBox } from 'element-plus'

const { t } = useI18n()
const userList = ref([])
const loading = ref(false)

const handleAdd = () => {
  // 新增用户逻辑
}

const handleEdit = (row) => {
  // 编辑用户逻辑
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      t('message.confirmDelete'),
      t('message.warning'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
      }
    )
    
    // 执行删除操作
    // await deleteUser(row.id)
    
    ElMessage.success(t('message.deleteSuccess'))
  } catch (error) {
    // 用户取消删除
  }
}

onMounted(() => {
  // 加载用户列表
})
</script>
```

### 10. 最佳实践

1. **统一管理**: 将所有国际化文本集中在语言包中管理
2. **命名规范**: 使用层级结构的键名，如 `user.addUser`
3. **参数化**: 对于需要动态内容的文本，使用参数化
4. **默认语言**: 设置合适的默认语言和回退语言
5. **缓存策略**: 将用户选择的语言保存到 localStorage
6. **组件封装**: 将语言切换功能封装成可复用组件
7. **类型安全**: 在 TypeScript 项目中定义国际化键的类型

这样就完成了前端的完整国际化配置。