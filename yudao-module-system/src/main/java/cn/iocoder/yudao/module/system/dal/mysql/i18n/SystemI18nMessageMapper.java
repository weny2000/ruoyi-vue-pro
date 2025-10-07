package cn.iocoder.yudao.module.system.dal.mysql.i18n;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.i18n.vo.message.SystemI18nMessagePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.i18n.SystemI18nMessageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 国际化消息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SystemI18nMessageMapper extends BaseMapperX<SystemI18nMessageDO> {

    default PageResult<SystemI18nMessageDO> selectPage(SystemI18nMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SystemI18nMessageDO>()
                .likeIfPresent(SystemI18nMessageDO::getMessageKey, reqVO.getMessageKey())
                .eqIfPresent(SystemI18nMessageDO::getLanguageCode, reqVO.getLanguageCode())
                .eqIfPresent(SystemI18nMessageDO::getModule, reqVO.getModule())
                .orderByAsc(SystemI18nMessageDO::getMessageKey)
                .orderByAsc(SystemI18nMessageDO::getLanguageCode));
    }

    default List<SystemI18nMessageDO> selectListByLanguageCode(String languageCode) {
        return selectList(SystemI18nMessageDO::getLanguageCode, languageCode);
    }

    default SystemI18nMessageDO selectByKeyAndLanguage(String messageKey, String languageCode) {
        return selectOne(new LambdaQueryWrapperX<SystemI18nMessageDO>()
                .eq(SystemI18nMessageDO::getMessageKey, messageKey)
                .eq(SystemI18nMessageDO::getLanguageCode, languageCode));
    }

    default List<SystemI18nMessageDO> selectListByModule(String module) {
        return selectList(SystemI18nMessageDO::getModule, module);
    }

    default List<SystemI18nMessageDO> selectListByKeys(List<String> messageKeys, String languageCode) {
        return selectList(new LambdaQueryWrapperX<SystemI18nMessageDO>()
                .in(SystemI18nMessageDO::getMessageKey, messageKeys)
                .eq(SystemI18nMessageDO::getLanguageCode, languageCode));
    }

}