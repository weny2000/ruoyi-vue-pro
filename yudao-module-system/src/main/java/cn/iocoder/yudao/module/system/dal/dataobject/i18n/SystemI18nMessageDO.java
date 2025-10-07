package cn.iocoder.yudao.module.system.dal.dataobject.i18n;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 国际化消息 DO
 *
 * @author 芋道源码
 */
@TableName("system_i18n_message")
@KeySequence("system_i18n_message_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemI18nMessageDO extends BaseDO {

    /**
     * 消息ID
     */
    @TableId
    private Long id;
    /**
     * 消息键
     */
    private String messageKey;
    /**
     * 语言代码
     */
    private String languageCode;
    /**
     * 消息值
     */
    private String messageValue;
    /**
     * 所属模块
     */
    private String module;
    /**
     * 描述
     */
    private String description;

}