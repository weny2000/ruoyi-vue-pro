package cn.iocoder.yudao.module.system.controller.admin.dict;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.i18n.I18nUtils;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.i18n.DictDataI18nSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.i18n.DictTypeI18nSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataI18nDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeI18nDO;
import cn.iocoder.yudao.module.system.service.dict.DictI18nDataService;
import cn.iocoder.yudao.module.system.service.dict.DictI18nService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 字典多语言管理控制器
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 字典多语言")
@RestController
@RequestMapping("/system/dict-i18n")
@Validated
public class DictI18nController {

    @Resource
    private DictI18nService dictI18nService;
    @Resource
    private DictI18nDataService dictI18nDataService;

    // ========== 字典类型多语言 ==========

    @PostMapping("/dict-type")
    @Operation(summary = "创建/更新字典类型多语言")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Long> saveDictTypeI18n(@Valid @RequestBody DictTypeI18nSaveReqVO saveReqVO) {
        Long id = dictI18nService.createDictTypeI18n(saveReqVO.getDictTypeId(), 
                saveReqVO.getLanguageCode(), saveReqVO.getName());
        return success(id);
    }

    @PostMapping("/dict-type/batch")
    @Operation(summary = "批量保存字典类型多语言")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> saveDictTypeI18nBatch(@RequestParam Long dictTypeId,
                                                      @RequestBody Map<String, String> i18nTranslations) {
        dictI18nService.saveDictTypeI18nBatch(dictTypeId, i18nTranslations);
        return success(true);
    }

    @GetMapping("/dict-type/{dictTypeId}")
    @Operation(summary = "获取字典类型的多语言列表")
    @Parameter(name = "dictTypeId", description = "字典类型ID", required = true)
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<List<DictTypeI18nDO>> getDictTypeI18nList(@PathVariable Long dictTypeId) {
        List<DictTypeI18nDO> list = dictI18nService.getDictTypeI18nList(dictTypeId);
        return success(list);
    }

    @DeleteMapping("/dict-type/{dictTypeId}")
    @Operation(summary = "删除字典类型的所有多语言")
    @Parameter(name = "dictTypeId", description = "字典类型ID", required = true)
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteDictTypeI18n(@PathVariable Long dictTypeId) {
        dictI18nService.deleteDictTypeI18nByDictTypeId(dictTypeId);
        return success(true);
    }

    // ========== 字典数据多语言 ==========

    @PostMapping("/dict-data")
    @Operation(summary = "创建/更新字典数据多语言")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Long> saveDictDataI18n(@Valid @RequestBody DictDataI18nSaveReqVO saveReqVO) {
        Long id = dictI18nService.createDictDataI18n(saveReqVO.getDictDataId(), 
                saveReqVO.getLanguageCode(), saveReqVO.getLabel());
        return success(id);
    }

    @PostMapping("/dict-data/batch")
    @Operation(summary = "批量保存字典数据多语言")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> saveDictDataI18nBatch(@RequestParam Long dictDataId,
                                                      @RequestBody Map<String, String> i18nTranslations) {
        dictI18nService.saveDictDataI18nBatch(dictDataId, i18nTranslations);
        return success(true);
    }

    @GetMapping("/dict-data/{dictDataId}")
    @Operation(summary = "获取字典数据的多语言列表")
    @Parameter(name = "dictDataId", description = "字典数据ID", required = true)
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<List<DictDataI18nDO>> getDictDataI18nList(@PathVariable Long dictDataId) {
        List<DictDataI18nDO> list = dictI18nService.getDictDataI18nList(dictDataId);
        return success(list);
    }

    @DeleteMapping("/dict-data/{dictDataId}")
    @Operation(summary = "删除字典数据的所有多语言")
    @Parameter(name = "dictDataId", description = "字典数据ID", required = true)
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteDictDataI18n(@PathVariable Long dictDataId) {
        dictI18nService.deleteDictDataI18nByDictDataId(dictDataId);
        return success(true);
    }

    // ========== 字典数据查询（带国际化） ==========

    @GetMapping("/dict-data/list")
    @Operation(summary = "获得字典数据列表（带国际化）")
    @Parameter(name = "dictType", description = "字典类型", required = true, example = "sys_common_status")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<List<DictDataDO>> getDictDataListWithI18n(@RequestParam String dictType,
                                                                  HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        List<DictDataDO> list = dictI18nDataService.getDictDataListByDictTypeWithI18n(dictType, languageCode);
        return success(list);
    }

    @GetMapping("/dict-data/page")
    @Operation(summary = "获得字典数据分页列表（带国际化）")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<PageResult<DictDataDO>> getDictDataPageWithI18n(@Valid DictDataPageReqVO pageReqVO,
                                                                        HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        PageResult<DictDataDO> pageResult = dictI18nDataService.getDictDataPageWithI18n(pageReqVO, languageCode);
        return success(pageResult);
    }

    @GetMapping("/dict-type/list")
    @Operation(summary = "获得字典类型列表（带国际化）")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<List<DictTypeDO>> getDictTypeListWithI18n(HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        List<DictTypeDO> list = dictI18nDataService.getDictTypeListWithI18n(languageCode);
        return success(list);
    }

    @GetMapping("/dict-data/all")
    @Operation(summary = "获得所有字典数据（带国际化）")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<Map<String, List<DictDataDO>>> getAllDictDataWithI18n(HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        Map<String, List<DictDataDO>> result = dictI18nDataService.getAllDictDataWithI18n(languageCode);
        return success(result);
    }

    @GetMapping("/dict-data/label")
    @Operation(summary = "获取字典数据标签（带国际化）")
    @Parameter(name = "dictType", description = "字典类型", required = true)
    @Parameter(name = "value", description = "字典值", required = true)
    public CommonResult<String> getDictDataLabel(@RequestParam String dictType,
                                                @RequestParam String value,
                                                HttpServletRequest request) {
        String languageCode = getLanguageCode(request);
        String label = dictI18nDataService.getDictDataLabel(dictType, value, languageCode);
        return success(label);
    }

    // ========== 工具方法 ==========

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
            // 简单解析Accept-Language头
            if (acceptLanguage.toLowerCase().contains("en")) {
                return "en_US";
            }
            if (acceptLanguage.toLowerCase().contains("zh-tw")) {
                return "zh_TW";
            }
        }
        
        // 默认返回当前语言环境
        return I18nUtils.getCurrentLocale().toString();
    }

}