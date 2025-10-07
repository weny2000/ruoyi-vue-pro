package cn.iocoder.yudao.module.system.controller.admin.dict.vo.i18n;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 字典数据多语言保存 Request VO")
@Data
public class DictDataI18nSaveReqVO {

    @Schema(description = "字典数据ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "字典数据ID不能为空")
    private Long dictDataId;

    @Schema(description = "语言代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "en_US")
    @NotEmpty(message = "语言代码不能为空")
    private String languageCode;

    @Schema(description = "字典标签", requiredMode = Schema.RequiredMode.REQUIRED, example = "Enabled")
    @NotEmpty(message = "字典标签不能为空")
    private String label;

}