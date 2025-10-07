package cn.iocoder.yudao.module.system.controller.admin.i18n.vo.language;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 系统语言配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SystemLanguagePageReqVO extends PageParam {

    @Schema(description = "语言名称", example = "简体中文")
    private String name;

    @Schema(description = "语言代码", example = "zh_CN")
    private String code;

    @Schema(description = "状态", example = "1")
    private Integer status;

}