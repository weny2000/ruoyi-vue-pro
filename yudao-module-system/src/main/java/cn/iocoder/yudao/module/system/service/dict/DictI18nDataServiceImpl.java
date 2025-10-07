package cn.iocoder.yudao.module.system.service.dict;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.i18n.I18nUtils;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典数据多语言增强服务实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class DictI18nDataServiceImpl implements DictI18nDataService {

    @Resource
    private DictDataService dictDataService;
    @Resource
    private DictTypeService dictTypeService;
    @Resource
    private DictI18nService dictI18nService;

    @Override
    public List<DictDataDO> getDictDataListWithI18n(Integer status, String dictType, String languageCode) {
        // 获取原始数据
        List<DictDataDO> dictDataList = dictDataService.getDictDataList(status, dictType);
        
        // 如果没有指定语言或者是默认语言，直接返回
        if (languageCode == null || I18nUtils.isChineseLocale()) {
            return dictDataList;
        }
        
        // 应用国际化
        return dictI18nService.getDictDataListWithI18n(dictDataList, languageCode);
    }

    @Override
    public PageResult<DictDataDO> getDictDataPageWithI18n(DictDataPageReqVO pageReqVO, String languageCode) {
        // 获取原始分页数据
        PageResult<DictDataDO> pageResult = dictDataService.getDictDataPage(pageReqVO);
        
        // 如果没有指定语言或者是默认语言，直接返回
        if (languageCode == null || I18nUtils.isChineseLocale()) {
            return pageResult;
        }
        
        // 应用国际化
        List<DictDataDO> i18nList = dictI18nService.getDictDataListWithI18n(pageResult.getList(), languageCode);
        return new PageResult<>(i18nList, pageResult.getTotal());
    }

    @Override
    public List<DictTypeDO> getDictTypeListWithI18n(String languageCode) {
        // 获取原始数据
        List<DictTypeDO> dictTypeList = dictTypeService.getDictTypeList();
        
        // 如果没有指定语言或者是默认语言，直接返回
        if (languageCode == null || I18nUtils.isChineseLocale()) {
            return dictTypeList;
        }
        
        // 应用国际化
        return dictI18nService.getDictTypeListWithI18n(dictTypeList, languageCode);
    }

    @Override
    public DictDataDO getDictDataWithI18n(Long id, String languageCode) {
        // 获取原始数据
        DictDataDO dictData = dictDataService.getDictData(id);
        if (dictData == null) {
            return null;
        }
        
        // 如果没有指定语言或者是默认语言，直接返回
        if (languageCode == null || I18nUtils.isChineseLocale()) {
            return dictData;
        }
        
        // 应用国际化
        String i18nLabel = dictI18nService.getDictDataLabel(dictData.getDictType(), dictData.getValue(), languageCode);
        dictData.setLabel(i18nLabel);
        
        return dictData;
    }

    @Override
    @Cacheable(value = "dict_data_list_i18n", key = "#dictType + '_' + #languageCode")
    public List<DictDataDO> getDictDataListByDictTypeWithI18n(String dictType, String languageCode) {
        // 获取原始数据
        List<DictDataDO> dictDataList = dictDataService.getDictDataListByDictType(dictType);
        
        // 如果没有指定语言或者是默认语言，直接返回
        if (languageCode == null || I18nUtils.isChineseLocale()) {
            return dictDataList;
        }
        
        // 应用国际化
        return dictI18nService.getDictDataListWithI18n(dictDataList, languageCode);
    }

    @Override
    @Cacheable(value = "dict_data_label_i18n", key = "#dictType + '_' + #value + '_' + #languageCode")
    public String getDictDataLabel(String dictType, String value, String languageCode) {
        return dictI18nService.getDictDataLabel(dictType, value, languageCode);
    }

    @Override
    public Map<String, String> getDictDataLabels(String dictType, List<String> values, String languageCode) {
        Map<String, String> result = new HashMap<>();
        
        if (CollUtil.isEmpty(values)) {
            return result;
        }
        
        for (String value : values) {
            String label = getDictDataLabel(dictType, value, languageCode);
            result.put(value, label);
        }
        
        return result;
    }

    @Override
    @Cacheable(value = "all_dict_data_i18n", key = "#languageCode")
    public Map<String, List<DictDataDO>> getAllDictDataWithI18n(String languageCode) {
        // 获取所有字典类型
        List<DictTypeDO> dictTypeList = dictTypeService.getDictTypeList();
        
        Map<String, List<DictDataDO>> result = new HashMap<>();
        
        for (DictTypeDO dictType : dictTypeList) {
            List<DictDataDO> dictDataList = getDictDataListByDictTypeWithI18n(dictType.getType(), languageCode);
            result.put(dictType.getType(), dictDataList);
        }
        
        return result;
    }

}