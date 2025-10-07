package cn.iocoder.yudao.framework.common.util.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * I18nUtils 测试类
 *
 * @author 芋道源码
 */
@SpringBootTest
class I18nUtilsTest {

    @Test
    void testGetCurrentLocale() {
        // 设置当前语言环境
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        
        // 测试获取当前语言环境
        Locale currentLocale = I18nUtils.getCurrentLocale();
        assertEquals(Locale.SIMPLIFIED_CHINESE, currentLocale);
    }

    @Test
    void testSetCurrentLocale() {
        // 测试设置语言环境
        I18nUtils.setCurrentLocale(Locale.US);
        assertEquals(Locale.US, I18nUtils.getCurrentLocale());
        
        // 恢复默认语言环境
        I18nUtils.setCurrentLocale(Locale.SIMPLIFIED_CHINESE);
    }

    @Test
    void testIsChineseLocale() {
        // 测试中文环境判断
        I18nUtils.setCurrentLocale(Locale.SIMPLIFIED_CHINESE);
        assertTrue(I18nUtils.isChineseLocale());
        
        I18nUtils.setCurrentLocale(Locale.TRADITIONAL_CHINESE);
        assertTrue(I18nUtils.isChineseLocale());
        
        I18nUtils.setCurrentLocale(Locale.US);
        assertFalse(I18nUtils.isChineseLocale());
    }

    @Test
    void testIsEnglishLocale() {
        // 测试英文环境判断
        I18nUtils.setCurrentLocale(Locale.US);
        assertTrue(I18nUtils.isEnglishLocale());
        
        I18nUtils.setCurrentLocale(Locale.ENGLISH);
        assertTrue(I18nUtils.isEnglishLocale());
        
        I18nUtils.setCurrentLocale(Locale.SIMPLIFIED_CHINESE);
        assertFalse(I18nUtils.isEnglishLocale());
    }

    @Test
    void testGetMessage() {
        // 注意：这个测试需要在有MessageSource的Spring上下文中运行
        // 这里只是演示测试结构，实际测试需要配置MessageSource
        
        // 测试获取消息（如果没有找到对应消息，应该返回键本身）
        String message = I18nUtils.getMessage("test.key");
        assertNotNull(message);
        
        // 测试带参数的消息
        String messageWithArgs = I18nUtils.getMessage("test.key.with.args", new Object[]{"参数1", "参数2"});
        assertNotNull(messageWithArgs);
    }
}