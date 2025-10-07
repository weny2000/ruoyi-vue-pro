package cn.iocoder.yudao.framework.web.core.handler;

import cn.hutool.core.util.StrUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化语言拦截器
 * 支持从请求头 Accept-Language 和请求参数 lang 中获取语言信息
 *
 * @author 芋道源码
 */
public class I18nLocaleInterceptor implements HandlerInterceptor {

    private static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    private static final String PARAM_LANG = "lang";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 优先从请求参数获取语言
        String lang = request.getParameter(PARAM_LANG);
        
        // 2. 如果参数中没有，从请求头获取
        if (StrUtil.isEmpty(lang)) {
            lang = request.getHeader(HEADER_ACCEPT_LANGUAGE);
        }

        // 3. 解析并设置语言环境
        if (StrUtil.isNotEmpty(lang)) {
            Locale locale = parseLocale(lang);
            if (locale != null) {
                LocaleContextHolder.setLocale(locale);
            }
        }

        return true;
    }

    /**
     * 解析语言字符串为Locale对象
     *
     * @param lang 语言字符串
     * @return Locale对象
     */
    private Locale parseLocale(String lang) {
        try {
            // 处理常见的语言格式
            lang = lang.toLowerCase().trim();
            
            // 处理带权重的Accept-Language头，取第一个
            if (lang.contains(",")) {
                lang = lang.split(",")[0].trim();
            }
            
            // 处理带权重的语言
            if (lang.contains(";")) {
                lang = lang.split(";")[0].trim();
            }

            switch (lang) {
                case "zh":
                case "zh-cn":
                case "zh_cn":
                    return Locale.SIMPLIFIED_CHINESE;
                case "zh-tw":
                case "zh_tw":
                    return Locale.TRADITIONAL_CHINESE;
                case "en":
                case "en-us":
                case "en_us":
                    return Locale.US;
                case "en-gb":
                case "en_gb":
                    return Locale.UK;
                case "ja":
                case "ja-jp":
                case "ja_jp":
                    return Locale.JAPAN;
                case "ko":
                case "ko-kr":
                case "ko_kr":
                    return Locale.KOREA;
                default:
                    // 尝试解析标准格式 language-country
                    if (lang.contains("-")) {
                        String[] parts = lang.split("-");
                        if (parts.length == 2) {
                            return new Locale(parts[0], parts[1].toUpperCase());
                        }
                    }
                    // 尝试解析标准格式 language_country
                    if (lang.contains("_")) {
                        String[] parts = lang.split("_");
                        if (parts.length == 2) {
                            return new Locale(parts[0], parts[1].toUpperCase());
                        }
                    }
                    // 只有语言代码
                    return new Locale(lang);
            }
        } catch (Exception e) {
            // 解析失败时返回null，使用默认语言
            return null;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理ThreadLocal，避免内存泄漏
        LocaleContextHolder.resetLocaleContext();
    }
}