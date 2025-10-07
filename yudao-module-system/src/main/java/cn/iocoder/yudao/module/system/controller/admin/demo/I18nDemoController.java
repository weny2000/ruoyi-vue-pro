package cn.iocoder.yudao.module.system.controller.admin.demo;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.i18n.I18nUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;

/**
 * 国际化演示控制器
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 国际化演示")
@RestController
@RequestMapping("/system/i18n-demo")
public class I18nDemoController {

    @GetMapping("/success")
    @Operation(summary = "成功消息演示")
    public CommonResult<Map<String, String>> successDemo() {
        Map<String, String> result = new HashMap<>();
        result.put("message", I18nUtils.getMessage("system.success"));
        result.put("userLogin", I18nUtils.getMessage("user.login.success"));
        result.put("fileUpload", I18nUtils.getMessage("file.upload.success"));
        return success(result);
    }

    @GetMapping("/error")
    @Operation(summary = "错误消息演示")
    public CommonResult<Map<String, String>> errorDemo() {
        Map<String, String> result = new HashMap<>();
        result.put("paramError", I18nUtils.getMessage("system.param.error"));
        result.put("noPermission", I18nUtils.getMessage("system.no.permission"));
        result.put("userNotExists", I18nUtils.getMessage("user.not.exists"));
        return success(result);
    }

    @GetMapping("/exception")
    @Operation(summary = "异常演示")
    public CommonResult<String> exceptionDemo(@RequestParam(defaultValue = "false") Boolean throwException) {
        if (throwException) {
            // 抛出业务异常，测试国际化错误码
            throw new ServiceException(USER_NOT_EXISTS);
        }
        return success(I18nUtils.getMessage("system.success"));
    }

    @GetMapping("/parameterized")
    @Operation(summary = "参数化消息演示")
    public CommonResult<Map<String, String>> parameterizedDemo() {
        Map<String, String> result = new HashMap<>();
        
        // 演示带参数的国际化消息
        ErrorCode roleNameDuplicate = new ErrorCode(1_002_002_001, "已经存在名为【{0}】的角色");
        result.put("roleError", roleNameDuplicate.getI18nMsg(new Object[]{"管理员"}));
        
        ErrorCode userDisabled = new ErrorCode(1_002_003_006, "名字为【{0}】的用户已被禁用");
        result.put("userError", userDisabled.getI18nMsg(new Object[]{"张三"}));
        
        return success(result);
    }

    @GetMapping("/locale-info")
    @Operation(summary = "当前语言环境信息")
    public CommonResult<Map<String, Object>> localeInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("currentLocale", I18nUtils.getCurrentLocale().toString());
        result.put("isChineseLocale", I18nUtils.isChineseLocale());
        result.put("isEnglishLocale", I18nUtils.isEnglishLocale());
        result.put("displayName", I18nUtils.getCurrentLocale().getDisplayName());
        
        // 演示不同语言环境下的消息
        result.put("welcomeMessage", I18nUtils.getMessage("system.success"));
        result.put("errorMessage", I18nUtils.getMessage("system.error"));
        
        return success(result);
    }
}