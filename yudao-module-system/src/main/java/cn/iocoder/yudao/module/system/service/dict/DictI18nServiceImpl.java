package cn.iocoder.yudao.module.system.service.dict;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.i18n.I18nUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataI18nDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeI18nDO;
import cn.iocoder.yudao.module.system.dal.mysql.dict.DictDataI18nMapper;
import cn.iocoder.yudao.module.system.dal.mysql.dict.DictDataMapper;
import cn.iocoder.yudao.module.system.dal.mysql.dict.DictTypeI18nMapper;
import cn.iocoder.yudao.module.system.dal.mysql.dict.DictTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典多语言 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class DictI18nServiceImpl implements DictI18nService {

    @Resource
    private DictTypeI18nMapper dictTypeI18nMapper;
    @Resource
    private DictDataI18nMapper dictDataI18nMapper;
    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private DictDataMapper dictDataMapper;

    // ========== 字典类型多语言 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictTypeI18n(Long dictTypeId, String languageCode, String name) {
        // 检查是否已存在
        DictTypeI18nDO existing = dictTypeI18nMapper.selectByDictTypeIdAndLanguage(dictTypeId, languageCode);
        if (existing != null) {
            // 如果已存在，则更新
            existing.setName(name);
            dictTypeI18nMapper.updateById(existing);
            return existing.getId();
        }

        // 创建新的多语言记录
        DictTypeI18nDO dictTypeI18n = DictTypeI18nDO.builder()
                .dictTypeId(dictTypeId)
                .languageCode(languageCode)
                .name(name)
                .build();
        dictTypeI18nMapper.insert(dictTypeI18n);
        
        // 清除缓存
        evictDictTypeCache(dictTypeId, languageCode);
        
        return dictTypeI18n.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictTypeI18n(Long dictTypeId, String languageCode, String name) {
        DictTypeI18nDO dictTypeI18n = dictTypeI18nMapper.selectByDictTypeIdAndLanguage(dictTypeId, languageCode);
        if (dictTypeI18n != null) {
            dictTypeI18n.setName(name);
            dictTypeI18nMapper.updateById(dictTypeI18n);
            
            // 清除缓存
            evictDictTypeCache(dictTypeId, languageCode);
        } else {
            // 如果不存在，则创建
            createDictTypeI18n(dictTypeId, languageCode, name);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictTypeI18nByDictTypeId(Long dictTypeId) {
        dictTypeI18nMapper.deleteByDictTypeId(dictTypeId);
        
        // 清除所有相关缓存
        evictAllDictTypeCache(dictTypeId);
    }

    @Override
    public List<DictTypeI18nDO> getDictTypeI18nList(Long dictTypeId) {
        return dictTypeI18nMapper.selectListByDictTypeId(dictTypeId);
    }

    @Override
    @Cacheable(value = "dict_type_i18n", key = "#dictType + '_' + #languageCode")
    public String getDictTypeName(String dictType, String languageCode) {
        // 如果是默认语言，直接返回原名称
        if (I18nUtils.isChineseLocale() && "zh_CN".equals(languageCode)) {
            DictTypeDO dictTypeDO = dictTypeMapper.selectByType(dictType);
            return dictTypeDO != null ? dictTypeDO.getName() : dictType;
        }

        // 查找字典类型
        DictTypeDO dictTypeDO = dictTypeMapper.selectByType(dictType);
        if (dictTypeDO == null) {
            return dictType;
        }

        // 查找多语言翻译
        DictTypeI18nDO i18nDO = dictTypeI18nMapper.selectByDictTypeIdAndLanguage(dictTypeDO.getId(), languageCode);
        if (i18nDO != null && StrUtil.isNotEmpty(i18nDO.getName())) {
            return i18nDO.getName();
        }

        // 如果没有找到翻译，返回原名称
        return dictTypeDO.getName();
    }

    @Override
    public Map<String, String> getDictTypeNames(List<String> dictTypes, String languageCode) {
        Map<String, String> result = new HashMap<>();
        
        if (CollUtil.isEmpty(dictTypes)) {
            return result;
        }

        for (String dictType : dictTypes) {
            result.put(dictType, getDictTypeName(dictType, languageCode));
        }
        
        return result;
    }

    // ========== 字典数据多语言 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictDataI18n(Long dictDataId, String languageCode, String label) {
        // 检查是否已存在
        DictDataI18nDO existing = dictDataI18nMapper.selectByDictDataIdAndLanguage(dictDataId, languageCode);
        if (existing != null) {
            // 如果已存在，则更新
            existing.setLabel(label);
            dictDataI18nMapper.updateById(existing);
            return existing.getId();
        }

        // 创建新的多语言记录
        DictDataI18nDO dictDataI18n = DictDataI18nDO.builder()
                .dictDataId(dictDataId)
                .languageCode(languageCode)
                .label(label)
                .build();
        dictDataI18nMapper.insert(dictDataI18n);
        
        // 清除缓存
        evictDictDataCache(dictDataId, languageCode);
        
        return dictDataI18n.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictDataI18n(Long dictDataId, String languageCode, String label) {
        DictDataI18nDO dictDataI18n = dictDataI18nMapper.selectByDictDataIdAndLanguage(dictDataId, languageCode);
        if (dictDataI18n != null) {
            dictDataI18n.setLabel(label);
            dictDataI18nMapper.updateById(dictDataI18n);
            
            // 清除缓存
            evictDictDataCache(dictDataId, languageCode);
        } else {
            // 如果不存在，则创建
            createDictDataI18n(dictDataId, languageCode, label);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictDataI18nByDictDataId(Long dictDataId) {
        dictDataI18nMapper.deleteByDictDataId(dictDataId);
        
        // 清除所有相关缓存
        evictAllDictDataCache(dictDataId);
    }

    @Override
    public List<DictDataI18nDO> getDictDataI18nList(Long dictDataId) {
        return dictDataI18nMapper.selectListByDictDataId(dictDataId);
    }

    @Override
    @Cacheable(value = "dict_data_i18n", key = "#dictType + '_' + #value + '_' + #languageCode")
    public String getDictDataLabel(String dictType, String value, String languageCode) {
        // 如果是默认语言，直接返回原标签
        if (I18nUtils.isChineseLocale() && "zh_CN".equals(languageCode)) {
            DictDataDO dictDataDO = dictDataMapper.selectByDictTypeAndValue(dictType, value);
            return dictDataDO != null ? dictDataDO.getLabel() : value;
        }

        // 查找字典数据
        DictDataDO dictDataDO = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictDataDO == null) {
            return value;
        }

        // 查找多语言翻译
        DictDataI18nDO i18nDO = dictDataI18nMapper.selectByDictDataIdAndLanguage(dictDataDO.getId(), languageCode);
        if (i18nDO != null && StrUtil.isNotEmpty(i18nDO.getLabel())) {
            return i18nDO.getLabel();
        }

        // 如果没有找到翻译，返回原标签
        return dictDataDO.getLabel();
    }

    @Override
    public List<DictDataDO> getDictDataListWithI18n(List<DictDataDO> dictDataList, String languageCode) {
        if (CollUtil.isEmpty(dictDataList)) {
            return dictDataList;
        }

        // 如果是默认语言，直接返回
        if (I18nUtils.isChineseLocale() && "zh_CN".equals(languageCode)) {
            return dictDataList;
        }

        // 批量查询多语言翻译
        List<Long> dictDataIds = dictDataList.stream()
                .map(DictDataDO::getId)
                .collect(Collectors.toList());
        
        List<DictDataI18nDO> i18nList = dictDataI18nMapper.selectListByDictDataIds(dictDataIds, languageCode);
        Map<Long, String> i18nMap = i18nList.stream()
                .collect(Collectors.toMap(DictDataI18nDO::getDictDataId, DictDataI18nDO::getLabel));

        // 设置国际化标签
        for (DictDataDO dictData : dictDataList) {
            String i18nLabel = i18nMap.get(dictData.getId());
            if (StrUtil.isNotEmpty(i18nLabel)) {
                dictData.setLabel(i18nLabel);
            }
        }

        return dictDataList;
    }

    @Override
    public List<DictTypeDO> getDictTypeListWithI18n(List<DictTypeDO> dictTypeList, String languageCode) {
        if (CollUtil.isEmpty(dictTypeList)) {
            return dictTypeList;
        }

        // 如果是默认语言，直接返回
        if (I18nUtils.isChineseLocale() && "zh_CN".equals(languageCode)) {
            return dictTypeList;
        }

        // 批量查询多语言翻译
        List<Long> dictTypeIds = dictTypeList.stream()
                .map(DictTypeDO::getId)
                .collect(Collectors.toList());
        
        List<DictTypeI18nDO> i18nList = dictTypeI18nMapper.selectListByDictTypeIds(dictTypeIds, languageCode);
        Map<Long, String> i18nMap = i18nList.stream()
                .collect(Collectors.toMap(DictTypeI18nDO::getDictTypeId, DictTypeI18nDO::getName));

        // 设置国际化名称
        for (DictTypeDO dictType : dictTypeList) {
            String i18nName = i18nMap.get(dictType.getId());
            if (StrUtil.isNotEmpty(i18nName)) {
                dictType.setName(i18nName);
            }
        }

        return dictTypeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDictTypeI18nBatch(Long dictTypeId, Map<String, String> i18nTranslations) {
        if (CollUtil.isEmpty(i18nTranslations)) {
            return;
        }

        for (Map.Entry<String, String> entry : i18nTranslations.entrySet()) {
            String languageCode = entry.getKey();
            String name = entry.getValue();
            
            if (StrUtil.isNotEmpty(name)) {
                updateDictTypeI18n(dictTypeId, languageCode, name);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDictDataI18nBatch(Long dictDataId, Map<String, String> i18nTranslations) {
        if (CollUtil.isEmpty(i18nTranslations)) {
            return;
        }

        for (Map.Entry<String, String> entry : i18nTranslations.entrySet()) {
            String languageCode = entry.getKey();
            String label = entry.getValue();
            
            if (StrUtil.isNotEmpty(label)) {
                updateDictDataI18n(dictDataId, languageCode, label);
            }
        }
    }

    // ========== 缓存清理 ==========

    @CacheEvict(value = "dict_type_i18n", key = "#dictTypeId + '_' + #languageCode")
    private void evictDictTypeCache(Long dictTypeId, String languageCode) {
        // 缓存清理
    }

    @CacheEvict(value = "dict_type_i18n", allEntries = true)
    private void evictAllDictTypeCache(Long dictTypeId) {
        // 清理所有字典类型缓存
    }

    @CacheEvict(value = "dict_data_i18n", key = "#dictDataId + '_' + #languageCode")
    private void evictDictDataCache(Long dictDataId, String languageCode) {
        // 缓存清理
    }

    @CacheEvict(value = "dict_data_i18n", allEntries = true)
    private void evictAllDictDataCache(Long dictDataId) {
        // 清理所有字典数据缓存
    }

}