package cn.iocoder.yudao.module.system.dal.dataobject.dict;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 字典数据多语言 DO
 *
 * @author 芋道源码
 */
@TableName("system_dict_data_i18n")
@KeySequence("system_dict_data_i18n_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictDataI18nDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 字典数据ID
     */
    private Long dictDataId;
    /**
     * 语言代码
     */
    private String languageCode;
    /**
     * 字典标签
     */
    private String label;

}