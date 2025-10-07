#!/bin/bash

# 芋道项目多语言化部署脚本
# 作者：芋道源码

set -e

echo "=========================================="
echo "芋道项目多语言化部署脚本"
echo "=========================================="

# 配置变量
DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-3306}
DB_NAME=${DB_NAME:-ruoyi-vue-pro}
DB_USER=${DB_USER:-root}
DB_PASSWORD=${DB_PASSWORD}

# 检查必要参数
if [ -z "$DB_PASSWORD" ]; then
    echo "错误: 请设置数据库密码环境变量 DB_PASSWORD"
    exit 1
fi

echo "数据库配置:"
echo "  主机: $DB_HOST"
echo "  端口: $DB_PORT"
echo "  数据库: $DB_NAME"
echo "  用户: $DB_USER"
echo ""

# 1. 检查数据库连接
echo "1. 检查数据库连接..."
mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD -e "SELECT 1;" > /dev/null 2>&1
if [ $? -eq 0 ]; then
    echo "✅ 数据库连接成功"
else
    echo "❌ 数据库连接失败，请检查配置"
    exit 1
fi

# 2. 备份现有数据（如果存在）
echo ""
echo "2. 备份现有数据..."
BACKUP_FILE="backup_$(date +%Y%m%d_%H%M%S).sql"
mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME -e "
SELECT COUNT(*) as count FROM information_schema.tables 
WHERE table_schema = '$DB_NAME' AND table_name IN ('system_language', 'system_i18n_message');
" | grep -q "2" && {
    echo "发现现有多语言表，正在备份..."
    mysqldump -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME \
        system_language system_i18n_message system_menu_i18n \
        system_dict_type_i18n system_dict_data_i18n > $BACKUP_FILE 2>/dev/null || true
    echo "✅ 备份完成: $BACKUP_FILE"
} || {
    echo "✅ 未发现现有多语言表，跳过备份"
}

# 3. 执行数据库脚本
echo ""
echo "3. 执行多语言化数据库脚本..."
if [ -f "sql/mysql/i18n-tables.sql" ]; then
    mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME < sql/mysql/i18n-tables.sql
    echo "✅ 数据库脚本执行成功"
else
    echo "❌ 未找到数据库脚本文件: sql/mysql/i18n-tables.sql"
    exit 1
fi

# 4. 验证表结构
echo ""
echo "4. 验证表结构..."
TABLES=("system_language" "system_i18n_message" "system_menu_i18n" "system_dict_type_i18n" "system_dict_data_i18n")
for table in "${TABLES[@]}"; do
    mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME -e "DESCRIBE $table;" > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo "✅ 表 $table 创建成功"
    else
        echo "❌ 表 $table 创建失败"
        exit 1
    fi
done

# 5. 验证初始数据
echo ""
echo "5. 验证初始数据..."
LANG_COUNT=$(mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME -e "SELECT COUNT(*) FROM system_language;" | tail -n 1)
MSG_COUNT=$(mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME -e "SELECT COUNT(*) FROM system_i18n_message;" | tail -n 1)
DICT_TYPE_I18N_COUNT=$(mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME -e "SELECT COUNT(*) FROM system_dict_type_i18n;" | tail -n 1)
DICT_DATA_I18N_COUNT=$(mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME -e "SELECT COUNT(*) FROM system_dict_data_i18n;" | tail -n 1)

echo "  语言配置数量: $LANG_COUNT"
echo "  国际化消息数量: $MSG_COUNT"
echo "  字典类型多语言数量: $DICT_TYPE_I18N_COUNT"
echo "  字典数据多语言数量: $DICT_DATA_I18N_COUNT"

if [ "$LANG_COUNT" -gt 0 ] && [ "$MSG_COUNT" -gt 0 ]; then
    echo "✅ 初始数据验证成功"
else
    echo "❌ 初始数据验证失败"
    exit 1
fi

# 6. 检查后端代码文件
echo ""
echo "6. 检查后端代码文件..."
BACKEND_FILES=(
    "yudao-framework/yudao-spring-boot-starter-web/src/main/java/cn/iocoder/yudao/framework/web/config/I18nConfiguration.java"
    "yudao-framework/yudao-common/src/main/java/cn/iocoder/yudao/framework/common/util/i18n/I18nUtils.java"
    "yudao-server/src/main/resources/i18n/messages_zh_CN.properties"
    "yudao-server/src/main/resources/i18n/messages_en_US.properties"
)

for file in "${BACKEND_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file"
    else
        echo "❌ $file (缺失)"
    fi
done

# 7. 生成配置报告
echo ""
echo "7. 生成配置报告..."
REPORT_FILE="i18n-deployment-report-$(date +%Y%m%d_%H%M%S).txt"
cat > $REPORT_FILE << EOF
芋道项目多语言化部署报告
生成时间: $(date)

数据库配置:
- 主机: $DB_HOST:$DB_PORT
- 数据库: $DB_NAME
- 用户: $DB_USER

部署结果:
- 语言配置数量: $LANG_COUNT
- 国际化消息数量: $MSG_COUNT
- 字典类型多语言数量: $DICT_TYPE_I18N_COUNT
- 字典数据多语言数量: $DICT_DATA_I18N_COUNT
- 备份文件: $BACKUP_FILE

支持的语言:
$(mysql -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASSWORD $DB_NAME -e "SELECT code, name, native_name, status FROM system_language ORDER BY sort;" 2>/dev/null || echo "无法获取语言列表")

后续步骤:
1. 重启应用服务器
2. 验证前端国际化配置
3. 测试语言切换功能
4. 添加更多语言翻译

注意事项:
- 如需回滚，请使用备份文件: $BACKUP_FILE
- 确保应用配置中启用了国际化支持
- 前端需要单独配置Vue i18n
EOF

echo "✅ 配置报告已生成: $REPORT_FILE"

# 8. 显示后续步骤
echo ""
echo "=========================================="
echo "🎉 多语言化部署完成！"
echo "=========================================="
echo ""
echo "后续步骤:"
echo "1. 重启应用服务器以加载新的国际化配置"
echo "2. 访问 /admin-api/system/i18n/supported-locales 验证API"
echo "3. 配置前端Vue i18n（参考 frontend-i18n-guide.md）"
echo "4. 测试语言切换功能"
echo ""
echo "测试API示例:"
echo "# 基础国际化测试"
echo "curl -H 'Accept-Language: en-US' http://localhost:8080/admin-api/system/i18n-demo/success"
echo "curl -H 'Accept-Language: zh-CN' http://localhost:8080/admin-api/system/i18n-demo/success"
echo ""
echo "# 字典多语言测试"
echo "curl -H 'Accept-Language: en-US' http://localhost:8080/admin-api/system/dict-i18n-demo/dict-types"
echo "curl -H 'Accept-Language: zh-CN' http://localhost:8080/admin-api/system/dict-i18n-demo/dict-data?dictType=sys_common_status"
echo "curl 'http://localhost:8080/admin-api/system/dict-i18n-demo/dict-label?dictType=sys_common_status&value=0&lang=en_US'"
echo ""
echo "如有问题，请查看详细文档: INTERNATIONALIZATION_GUIDE.md"
echo "=========================================="