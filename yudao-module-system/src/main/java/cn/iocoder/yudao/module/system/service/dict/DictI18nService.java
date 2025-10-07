package cn.iocoder.yudao.module.system.service.dict;

import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataI18nDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeI18nDO;

import java.util.List;
import java.util.Map;

/**
 * 字典多语言 Service 接口
 *
 * @author 芋道源码
 */
public interface DictI18nService {

    // ========== 字典类型多语言 ==========

    /**
     * 创建字典类型多语言
     *
     * @param dictTypeId   字典类型ID
     * @param languageCode 语言代码
     * @param name         字典名称
     * @return 多语言ID
     */
    Long createDictTypeI18n(Long dictTypeId, String languageCode, String name);

    /**
     * 更新字典类型多语言
     *
     * @param dictTypeId   字典类型ID
     * @param languageCode 语言代码
     * @param name         字典名称
     */
    void updateDictTypeI18n(Long dictTypeId, String languageCode, String name);

    /**
     * 删除字典类型的所有多语言
     *
     * @param dictTypeId 字典类型ID
     */
    void deleteDictTypeI18nByDictTypeId(Long dictTypeId);

    /**
     * 获取字典类型的多语言列表
     *
     * @param dictTypeId 字典类型ID
     * @return 多语言列表
     */
    List<DictTypeI18nDO> getDictTypeI18nList(Long dictTypeId);

    /**
     * 获取字典类型的国际化名称
     *
     * @param dictType     字典类型
     * @param languageCode 语言代码
     * @return 国际化名称，如果没有则返回原名称
     */
    String getDictTypeName(String dictType, String languageCode);

    /**
     * 批量获取字典类型的国际化名称
     *
     * @param dictTypes    字典类型列表
     * @param languageCode 语言代码
     * @return 字典类型 -> 国际化名称的映射
     */
    Map<String, String> getDictTypeNames(List<String> dictTypes, String languageCode);

    // ========== 字典数据多语言 ==========

    /**
     * 创建字典数据多语言
     *
     * @param dictDataId   字典数据ID
     * @param languageCode 语言代码
     * @param label        字典标签
     * @return 多语言ID
     */
    Long createDictDataI18n(Long dictDataId, String languageCode, String label);

    /**
     * 更新字典数据多语言
     *
     * @param dictDataId   字典数据ID
     * @param languageCode 语言代码
     * @param label        字典标签
     */
    void updateDictDataI18n(Long dictDataId, String languageCode, String label);

    /**
     * 删除字典数据的所有多语言
     *
     * @param dictDataId 字典数据ID
     */
    void deleteDictDataI18nByDictDataId(Long dictDataId);

    /**
     * 获取字典数据的多语言列表
     *
     * @param dictDataId 字典数据ID
     * @return 多语言列表
     */
    List<DictDataI18nDO> getDictDataI18nList(Long dictDataId);

    /**
     * 获取字典数据的国际化标签
     *
     * @param dictType     字典类型
     * @param value        字典值
     * @param languageCode 语言代码
     * @return 国际化标签，如果没有则返回原标签
     */
    String getDictDataLabel(String dictType, String value, String languageCode);

    /**
     * 获取字典数据列表的国际化标签
     *
     * @param dictDataList 字典数据列表
     * @param languageCode 语言代码
     * @return 带国际化标签的字典数据列表
     */
    List<DictDataDO> getDictDataListWithI18n(List<DictDataDO> dictDataList, String languageCode);

    /**
     * 获取字典类型列表的国际化名称
     *
     * @param dictTypeList 字典类型列表
     * @param languageCode 语言代码
     * @return 带国际化名称的字典类型列表
     */
    List<DictTypeDO> getDictTypeListWithI18n(List<DictTypeDO> dictTypeList, String languageCode);

    /**
     * 批量保存字典类型多语言
     *
     * @param dictTypeId      字典类型ID
     * @param i18nTranslations 多语言翻译 Map<语言代码, 名称>
     */
    void saveDictTypeI18nBatch(Long dictTypeId, Map<String, String> i18nTranslations);

    /**
     * 批量保存字典数据多语言
     *
     * @param dictDataId      字典数据ID
     * @param i18nTranslations 多语言翻译 Map<语言代码, 标签>
     */
    void saveDictDataI18nBatch(Long dictDataId, Map<String, String> i18nTranslations);

}