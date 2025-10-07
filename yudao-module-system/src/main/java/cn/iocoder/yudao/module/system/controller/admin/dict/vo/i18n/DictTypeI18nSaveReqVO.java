package cn.iocoder.yudao.module.system.controller.admin.dict.vo.i18n;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 字典类型多语言保存 Request VO")
@Data
public class DictTypeI18nSaveReqVO {

    @Schema(description = "字典类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    @Schema(description = "语言代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "en_US")
    @NotEmpty(message = "语言代码不能为空")
    private String languageCode;

    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "System Status")
    @NotEmpty(message = "字典名称不能为空")
    private String name;

}