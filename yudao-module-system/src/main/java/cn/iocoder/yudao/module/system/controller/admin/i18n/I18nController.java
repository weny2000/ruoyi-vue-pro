package cn.iocoder.yudao.module.system.controller.admin.i18n;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.i18n.I18nUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 国际化控制器
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 国际化")
@RestController
@RequestMapping("/system/i18n")
public class I18nController {

    @GetMapping("/current-locale")
    @Operation(summary = "获取当前语言环境")
    public CommonResult<Map<String, Object>> getCurrentLocale() {
        Locale currentLocale = I18nUtils.getCurrentLocale();
        Map<String, Object> result = new HashMap<>();
        result.put("language", currentLocale.getLanguage());
        result.put("country", currentLocale.getCountry());
        result.put("displayName", currentLocale.getDisplayName());
        result.put("locale", currentLocale.toString());
        return success(result);
    }

    @PostMapping("/change-locale")
    @Operation(summary = "切换语言环境")
    @Parameter(name = "locale", description = "语言环境", required = true, example = "zh_CN")
    public CommonResult<String> changeLocale(@RequestParam String locale) {
        try {
            Locale newLocale = parseLocale(locale);
            I18nUtils.setCurrentLocale(newLocale);
            return success(I18nUtils.getMessage("system.success"));
        } catch (Exception e) {
            return CommonResult.error(400, "Invalid locale format: " + locale);
        }
    }

    @GetMapping("/message")
    @Operation(summary = "获取国际化消息")
    @Parameter(name = "code", description = "消息代码", required = true, example = "system.success")
    public CommonResult<String> getMessage(@RequestParam String code,
                                         @RequestParam(required = false) String[] args) {
        String message = I18nUtils.getMessage(code, args);
        return success(message);
    }

    @GetMapping("/supported-locales")
    @Operation(summary = "获取支持的语言列表")
    public CommonResult<Map<String, String>> getSupportedLocales() {
        Map<String, String> locales = new HashMap<>();
        locales.put("zh_CN", "简体中文");
        locales.put("zh_TW", "繁體中文");
        locales.put("en_US", "English (US)");
        locales.put("en_GB", "English (UK)");
        locales.put("ja_JP", "日本語");
        locales.put("ko_KR", "한국어");
        return success(locales);
    }

    @GetMapping("/detect-locale")
    @Operation(summary = "检测客户端语言环境")
    public CommonResult<Map<String, Object>> detectLocale(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // 从请求头获取Accept-Language
        String acceptLanguage = request.getHeader("Accept-Language");
        result.put("acceptLanguage", acceptLanguage);
        
        // 当前设置的语言环境
        Locale currentLocale = LocaleContextHolder.getLocale();
        result.put("currentLocale", currentLocale.toString());
        result.put("currentDisplayName", currentLocale.getDisplayName());
        
        // 系统默认语言环境
        Locale defaultLocale = Locale.getDefault();
        result.put("systemDefaultLocale", defaultLocale.toString());
        
        return success(result);
    }

    /**
     * 解析语言字符串为Locale对象
     */
    private Locale parseLocale(String locale) {
        if (locale == null || locale.trim().isEmpty()) {
            return Locale.getDefault();
        }
        
        locale = locale.trim();
        
        // 处理常见格式
        if (locale.contains("_")) {
            String[] parts = locale.split("_");
            if (parts.length == 2) {
                return new Locale(parts[0], parts[1]);
            }
        } else if (locale.contains("-")) {
            String[] parts = locale.split("-");
            if (parts.length == 2) {
                return new Locale(parts[0], parts[1]);
            }
        }
        
        // 只有语言代码
        return new Locale(locale);
    }
}