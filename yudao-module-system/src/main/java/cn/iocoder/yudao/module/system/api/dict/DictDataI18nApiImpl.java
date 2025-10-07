package cn.iocoder.yudao.module.system.api.dict;

import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.service.dict.DictI18nDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 字典数据多语言 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictDataI18nApiImpl implements DictDataI18nApi {

    @Resource
    private DictI18nDataService dictI18nDataService;

    @Override
    public String getDictDataLabel(String dictType, String value, String languageCode) {
        return dictI18nDataService.getDictDataLabel(dictType, value, languageCode);
    }

    @Override
    public Map<String, String> getDictDataLabels(String dictType, List<String> values, String languageCode) {
        return dictI18nDataService.getDictDataLabels(dictType, values, languageCode);
    }

    @Override
    public List<DictDataDO> getDictDataListByDictType(String dictType, String languageCode) {
        return dictI18nDataService.getDictDataListByDictTypeWithI18n(dictType, languageCode);
    }

    @Override
    public Map<String, List<DictDataDO>> getAllDictData(String languageCode) {
        return dictI18nDataService.getAllDictDataWithI18n(languageCode);
    }

}