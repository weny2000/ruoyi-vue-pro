package cn.iocoder.yudao.module.system.api.dict;

import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;

import java.util.List;
import java.util.Map;

/**
 * 字典数据多语言 API 接口
 *
 * @author 芋道源码
 */
public interface DictDataI18nApi {

    /**
     * 获取字典数据的国际化标签
     *
     * @param dictType     字典类型
     * @param value        字典值
     * @param languageCode 语言代码
     * @return 国际化标签
     */
    String getDictDataLabel(String dictType, String value, String languageCode);

    /**
     * 批量获取字典数据的国际化标签
     *
     * @param dictType     字典类型
     * @param values       字典值列表
     * @param languageCode 语言代码
     * @return 字典值 -> 国际化标签的映射
     */
    Map<String, String> getDictDataLabels(String dictType, List<String> values, String languageCode);

    /**
     * 根据字典类型获取字典数据列表（带国际化）
     *
     * @param dictType     字典类型
     * @param languageCode 语言代码
     * @return 字典数据列表
     */
    List<DictDataDO> getDictDataListByDictType(String dictType, String languageCode);

    /**
     * 获取所有字典数据（按字典类型分组，带国际化）
     *
     * @param languageCode 语言代码
     * @return 字典类型 -> 字典数据列表的映射
     */
    Map<String, List<DictDataDO>> getAllDictData(String languageCode);

}