package cn.iocoder.yudao.framework.common.exception;

import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.exception.enums.ServiceErrorCodeRange;
import cn.iocoder.yudao.framework.common.util.i18n.I18nUtils;
import lombok.Data;

/**
 * 错误码对象
 *
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCodeConstants}
 * 业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 *
 * 支持国际化的错误码对象
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示（默认消息）
     */
    private final String msg;
    /**
     * 国际化消息键
     */
    private final String i18nKey;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
        this.i18nKey = "error.code." + code;
    }

    public ErrorCode(Integer code, String message, String i18nKey) {
        this.code = code;
        this.msg = message;
        this.i18nKey = i18nKey;
    }

    /**
     * 获取国际化后的错误消息
     *
     * @return 国际化错误消息
     */
    public String getI18nMsg() {
        return getI18nMsg(null);
    }

    /**
     * 获取国际化后的错误消息
     *
     * @param args 消息参数
     * @return 国际化错误消息
     */
    public String getI18nMsg(Object[] args) {
        try {
            String i18nMsg = I18nUtils.getMessage(i18nKey, args);
            // 如果国际化消息与键相同，说明没有找到对应的国际化消息，返回默认消息
            return i18nKey.equals(i18nMsg) ? msg : i18nMsg;
        } catch (Exception e) {
            // 国际化失败时返回默认消息
            return msg;
        }
    }
}
