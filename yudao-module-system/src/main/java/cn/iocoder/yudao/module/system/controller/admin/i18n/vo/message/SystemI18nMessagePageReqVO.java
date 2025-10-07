package cn.iocoder.yudao.module.system.controller.admin.i18n.vo.message;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 国际化消息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SystemI18nMessagePageReqVO extends PageParam {

    @Schema(description = "消息键", example = "user.login.success")
    private String messageKey;

    @Schema(description = "语言代码", example = "zh_CN")
    private String languageCode;

    @Schema(description = "所属模块", example = "user")
    private String module;

}