package cn.iocoder.yudao.module.system.dal.mysql.i18n;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.i18n.vo.language.SystemLanguagePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.i18n.SystemLanguageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统语言配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SystemLanguageMapper extends BaseMapperX<SystemLanguageDO> {

    default PageResult<SystemLanguageDO> selectPage(SystemLanguagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SystemLanguageDO>()
                .likeIfPresent(SystemLanguageDO::getName, reqVO.getName())
                .eqIfPresent(SystemLanguageDO::getCode, reqVO.getCode())
                .eqIfPresent(SystemLanguageDO::getStatus, reqVO.getStatus())
                .orderByAsc(SystemLanguageDO::getSort));
    }

    default List<SystemLanguageDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<SystemLanguageDO>()
                .eq(SystemLanguageDO::getStatus, status)
                .orderByAsc(SystemLanguageDO::getSort));
    }

    default SystemLanguageDO selectByCode(String code) {
        return selectOne(SystemLanguageDO::getCode, code);
    }

    default SystemLanguageDO selectDefaultLanguage() {
        return selectOne(SystemLanguageDO::getIsDefault, true);
    }

}