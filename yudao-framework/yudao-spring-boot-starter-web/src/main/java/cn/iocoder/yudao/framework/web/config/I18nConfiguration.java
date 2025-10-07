package cn.iocoder.yudao.framework.web.config;

import cn.iocoder.yudao.framework.web.core.handler.I18nLocaleInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 国际化配置类
 *
 * @author 芋道源码
 */
@Configuration
public class I18nConfiguration implements WebMvcConfigurer {

    /**
     * 国际化资源文件配置
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 设置国际化资源文件路径
        messageSource.setBasenames("i18n/messages", "i18n/error_codes");
        // 设置默认编码
        messageSource.setDefaultEncoding("UTF-8");
        // 设置缓存时间，-1表示永久缓存
        messageSource.setCacheSeconds(-1);
        // 设置当找不到资源时使用默认消息
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /**
     * 语言环境解析器
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        // 设置默认语言为中文
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return resolver;
    }

    /**
     * 语言切换拦截器
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // 设置请求参数名，默认为locale
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * 自定义国际化拦截器
     */
    @Bean
    public I18nLocaleInterceptor i18nLocaleInterceptor() {
        return new I18nLocaleInterceptor();
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义国际化拦截器，优先级最高
        registry.addInterceptor(i18nLocaleInterceptor()).order(0);
        // 添加标准语言切换拦截器
        registry.addInterceptor(localeChangeInterceptor()).order(1);
    }
}