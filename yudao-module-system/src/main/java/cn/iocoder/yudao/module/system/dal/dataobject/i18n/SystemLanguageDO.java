package cn.iocoder.yudao.module.system.dal.dataobject.i18n;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 系统语言配置 DO
 *
 * @author 芋道源码
 */
@TableName("system_language")
@KeySequence("system_language_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemLanguageDO extends BaseDO {

    /**
     * 语言ID
     */
    @TableId
    private Long id;
    /**
     * 语言代码，如：zh_CN, en_US
     */
    private String code;
    /**
     * 语言名称
     */
    private String name;
    /**
     * 本地语言名称
     */
    private String nativeName;
    /**
     * 国旗图标
     */
    private String flagIcon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态（0禁用 1启用）
     */
    private Integer status;
    /**
     * 是否默认语言（0否 1是）
     */
    private Boolean isDefault;

}