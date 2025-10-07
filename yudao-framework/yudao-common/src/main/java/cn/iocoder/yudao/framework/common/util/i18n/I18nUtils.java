package cn.iocoder.yudao.framework.common.util.i18n;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 国际化工具类
 *
 * @author 芋道源码
 */
public class I18nUtils {

    private static MessageSource messageSource;

    /**
     * 获取国际化消息
     *
     * @param code 消息代码
     * @return 国际化消息
     */
    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * 获取国际化消息
     *
     * @param code 消息代码
     * @param args 参数
     * @return 国际化消息
     */
    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * 获取国际化消息
     *
     * @param code   消息代码
     * @param args   参数
     * @param locale 语言环境
     * @return 国际化消息
     */
    public static String getMessage(String code, Object[] args, Locale locale) {
        if (messageSource == null) {
            messageSource = SpringUtil.getBean(MessageSource.class);
        }
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            // 如果找不到对应的国际化消息，返回原始代码
            return code;
        }
    }

    /**
     * 获取当前语言环境
     *
     * @return 当前语言环境
     */
    public static Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * 设置当前语言环境
     *
     * @param locale 语言环境
     */
    public static void setCurrentLocale(Locale locale) {
        LocaleContextHolder.setLocale(locale);
    }

    /**
     * 判断是否为中文环境
     *
     * @return 是否为中文环境
     */
    public static boolean isChineseLocale() {
        Locale locale = getCurrentLocale();
        return Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TRADITIONAL_CHINESE.equals(locale);
    }

    /**
     * 判断是否为英文环境
     *
     * @return 是否为英文环境
     */
    public static boolean isEnglishLocale() {
        Locale locale = getCurrentLocale();
        return Locale.ENGLISH.equals(locale) || Locale.US.equals(locale);
    }
}