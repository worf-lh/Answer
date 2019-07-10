package com.potato.answer.exception;

/**
 * Server API 响应结果含error_code且error_code值不为0时，抛出此异常
 *
 * @author lihao
 * @date 2019/7/10 14:33
 * @desc todo
 */
public class ApiException extends RuntimeException {

    private int code;//错误码

    public ApiException(int code, String defaultMessage) {
        this(getErrorMessage(code, defaultMessage));
        this.code = code;
    }

    public ApiException(int code) {
        this(getErrorMessage(code, null));
        this.code = code;
    }

    public ApiException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    private static String getErrorMessage(int code, String defaultMessage) {
        return defaultMessage;
    }

}
