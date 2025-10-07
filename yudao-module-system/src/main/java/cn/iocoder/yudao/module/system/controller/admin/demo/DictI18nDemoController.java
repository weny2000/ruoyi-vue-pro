package cn.iocoder.yudao.module.system.controller.admin.demo;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.i18n.I18nUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.yudao.module.system.service.dict.DictI18nDataService;
import cn.iocoder.yudao.module.system.service.dict.DictI18nService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 字典多语言演示控制器
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 字典多语言演示")
@RestController
@RequestMapping("/system/dict-i18n-demo")
public class DictI18nDemoController {

    @Resource
    private DictI18nService dictI18nService;
    @Resource
    private DictI18nDataService dictI18nDataService;

    @GetMapping("/dict-types")
    @Operation(summary = "获取字典类型列表（多语言演示）")
    public CommonResult<Map<String, Object>> getDictTypesDemo(HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        
        Map<String, Object> result = new HashMap<>();
        result.put("currentLanguage", languageCode);
        result.put("currentLocale", I18nUtils.getCurrentLocale().toString());
        
        // 获取字典类型列表（带国际化）
        List<DictTypeDO> dictTypes = dictI18nDataService.getDictTypeListWithI18n(languageCode);
        result.put("dictTypes", dictTypes);
        
        return success(result);
    }

    @GetMapping("/dict-data")
    @Operation(summary = "获取字典数据列表（多语言演示）")
    @Parameter(name = "dictType", description = "字典类型", example = "sys_common_status")
    public CommonResult<Map<String, Object>> getDictDataDemo(@RequestParam(defaultValue = "sys_common_status") String dictType,
                                                            HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        
        Map<String, Object> result = new HashMap<>();
        result.put("currentLanguage", languageCode);
        result.put("dictType", dictType);
        
        // 获取字典数据列表（带国际化）
        List<DictDataDO> dictDataList = dictI18nDataService.getDictDataListByDictTypeWithI18n(dictType, languageCode);
        result.put("dictDataList", dictDataList);
        
        // 获取字典类型名称（带国际化）
        String dictTypeName = dictI18nService.getDictTypeName(dictType, languageCode);
        result.put("dictTypeName", dictTypeName);
        
        return success(result);
    }

    @GetMapping("/dict-label")
    @Operation(summary = "获取字典标签（多语言演示）")
    @Parameter(name = "dictType", description = "字典类型", example = "sys_common_status")
    @Parameter(name = "value", description = "字典值", example = "0")
    public CommonResult<Map<String, Object>> getDictLabelDemo(@RequestParam(defaultValue = "sys_common_status") String dictType,
                                                             @RequestParam(defaultValue = "0") String value,
                                                             HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        
        Map<String, Object> result = new HashMap<>();
        result.put("currentLanguage", languageCode);
        result.put("dictType", dictType);
        result.put("value", value);
        
        // 获取字典标签（带国际化）
        String label = dictI18nDataService.getDictDataLabel(dictType, value, languageCode);
        result.put("label", label);
        
        return success(result);
    }

    @GetMapping("/all-dict-data")
    @Operation(summary = "获取所有字典数据（多语言演示）")
    public CommonResult<Map<String, Object>> getAllDictDataDemo(HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        
        Map<String, Object> result = new HashMap<>();
        result.put("currentLanguage", languageCode);
        
        // 获取所有字典数据（带国际化）
        Map<String, List<DictDataDO>> allDictData = dictI18nDataService.getAllDictDataWithI18n(languageCode);
        result.put("allDictData", allDictData);
        result.put("dictTypeCount", allDictData.size());
        
        return success(result);
    }

    @PostMapping("/init-demo-data")
    @Operation(summary = "初始化演示数据")
    public CommonResult<String> initDemoData() {
        try {
            // 这里可以添加一些演示数据的初始化逻辑
            // 例如为常用字典类型添加英文翻译
            
            // 注意：这只是演示，实际使用时应该通过管理界面或数据导入来添加翻译
            
            return success("演示数据初始化完成（注意：实际翻译数据需要通过管理界面添加）");
        } catch (Exception e) {
            return CommonResult.error(500, "初始化失败：" + e.getMessage());
        }
    }

    @GetMapping("/compare-languages")
    @Operation(summary = "对比不同语言的字典数据")
    @Parameter(name = "dictType", description = "字典类型", example = "sys_common_status")
    public CommonResult<Map<String, Object>> compareLanguages(@RequestParam(defaultValue = "sys_common_status") String dictType) {
        Map<String, Object> result = new HashMap<>();
        result.put("dictType", dictType);
        
        // 对比中英文
        List<DictDataDO> zhData = dictI18nDataService.getDictDataListByDictTypeWithI18n(dictType, "zh_CN");
        List<DictDataDO> enData = dictI18nDataService.getDictDataListByDictTypeWithI18n(dictType, "en_US");
        
        result.put("zh_CN", zhData);
        result.put("en_US", enData);
        
        // 字典类型名称对比
        String zhTypeName = dictI18nService.getDictTypeName(dictType, "zh_CN");
        String enTypeName = dictI18nService.getDictTypeName(dictType, "en_US");
        
        Map<String, String> typeNames = new HashMap<>();
        typeNames.put("zh_CN", zhTypeName);
        typeNames.put("en_US", enTypeName);
        result.put("typeNames", typeNames);
        
        return success(result);
    }

    /**
     * 从请求中获取语言代码
     */
    private String getLanguageCode(HttpServletRequest request) {
        // 优先从请求参数获取
        String lang = request.getParameter("lang");
        if (lang != null) {
            return lang;
        }
        
        // 从请求头获取
        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage != null) {
            if (acceptLanguage.toLowerCase().contains("en")) {
                return "en_US";
            }
            if (acceptLanguage.toLowerCase().contains("zh-tw")) {
                return "zh_TW";
            }
        }
        
        // 默认返回中文
        return "zh_CN";
    }

}