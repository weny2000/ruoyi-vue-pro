package cn.iocoder.yudao.module.system.service.dict;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeDO;

import java.util.List;
import java.util.Map;

/**
 * 字典数据多语言增强服务接口
 *
 * @author 芋道源码
 */
public interface DictI18nDataService {

    /**
     * 获得字典数据列表（带国际化）
     *
     * @param status       状态
     * @param dictType     字典类型
     * @param languageCode 语言代码
     * @return 字典数据全列表
     */
    List<DictDataDO> getDictDataListWithI18n(Integer status, String dictType, String languageCode);

    /**
     * 获得字典数据分页列表（带国际化）
     *
     * @param pageReqVO    分页请求
     * @param languageCode 语言代码
     * @return 字典数据分页列表
     */
    PageResult<DictDataDO> getDictDataPageWithI18n(DictDataPageReqVO pageReqVO, String languageCode);

    /**
     * 获得字典类型列表（带国际化）
     *
     * @param languageCode 语言代码
     * @return 字典类型列表
     */
    List<DictTypeDO> getDictTypeListWithI18n(String languageCode);

    /**
     * 获得字典数据详情（带国际化）
     *
     * @param id           字典数据编号
     * @param languageCode 语言代码
     * @return 字典数据
     */
    DictDataDO getDictDataWithI18n(Long id, String languageCode);

    /**
     * 根据字典类型获取字典数据列表（带国际化）
     *
     * @param dictType     字典类型
     * @param languageCode 语言代码
     * @return 字典数据列表
     */
    List<DictDataDO> getDictDataListByDictTypeWithI18n(String dictType, String languageCode);

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
     * 获取所有字典数据（按字典类型分组，带国际化）
     *
     * @param languageCode 语言代码
     * @return 字典类型 -> 字典数据列表的映射
     */
    Map<String, List<DictDataDO>> getAllDictDataWithI18n(String languageCode);

}