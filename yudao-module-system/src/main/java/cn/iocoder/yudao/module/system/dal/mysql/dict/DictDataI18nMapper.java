package cn.iocoder.yudao.module.system.dal.mysql.dict;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataI18nDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典数据多语言 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DictDataI18nMapper extends BaseMapperX<DictDataI18nDO> {

    default List<DictDataI18nDO> selectListByDictDataId(Long dictDataId) {
        return selectList(DictDataI18nDO::getDictDataId, dictDataId);
    }

    default DictDataI18nDO selectByDictDataIdAndLanguage(Long dictDataId, String languageCode) {
        return selectOne(new LambdaQueryWrapperX<DictDataI18nDO>()
                .eq(DictDataI18nDO::getDictDataId, dictDataId)
                .eq(DictDataI18nDO::getLanguageCode, languageCode));
    }

    default List<DictDataI18nDO> selectListByLanguageCode(String languageCode) {
        return selectList(DictDataI18nDO::getLanguageCode, languageCode);
    }

    default void deleteByDictDataId(Long dictDataId) {
        delete(DictDataI18nDO::getDictDataId, dictDataId);
    }

    default List<DictDataI18nDO> selectListByDictDataIds(List<Long> dictDataIds, String languageCode) {
        return selectList(new LambdaQueryWrapperX<DictDataI18nDO>()
                .in(DictDataI18nDO::getDictDataId, dictDataIds)
                .eq(DictDataI18nDO::getLanguageCode, languageCode));
    }

    default List<DictDataI18nDO> selectListByDictDataIdsAndLanguages(List<Long> dictDataIds, List<String> languageCodes) {
        return selectList(new LambdaQueryWrapperX<DictDataI18nDO>()
                .in(DictDataI18nDO::getDictDataId, dictDataIds)
                .in(DictDataI18nDO::getLanguageCode, languageCodes));
    }

}