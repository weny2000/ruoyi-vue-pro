package cn.iocoder.yudao.module.system.dal.mysql.dict;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeI18nDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典类型多语言 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DictTypeI18nMapper extends BaseMapperX<DictTypeI18nDO> {

    default List<DictTypeI18nDO> selectListByDictTypeId(Long dictTypeId) {
        return selectList(DictTypeI18nDO::getDictTypeId, dictTypeId);
    }

    default DictTypeI18nDO selectByDictTypeIdAndLanguage(Long dictTypeId, String languageCode) {
        return selectOne(new LambdaQueryWrapperX<DictTypeI18nDO>()
                .eq(DictTypeI18nDO::getDictTypeId, dictTypeId)
                .eq(DictTypeI18nDO::getLanguageCode, languageCode));
    }

    default List<DictTypeI18nDO> selectListByLanguageCode(String languageCode) {
        return selectList(DictTypeI18nDO::getLanguageCode, languageCode);
    }

    default void deleteByDictTypeId(Long dictTypeId) {
        delete(DictTypeI18nDO::getDictTypeId, dictTypeId);
    }

    default List<DictTypeI18nDO> selectListByDictTypeIds(List<Long> dictTypeIds, String languageCode) {
        return selectList(new LambdaQueryWrapperX<DictTypeI18nDO>()
                .in(DictTypeI18nDO::getDictTypeId, dictTypeIds)
                .eq(DictTypeI18nDO::getLanguageCode, languageCode));
    }

}