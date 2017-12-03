package com.inspection.application.mode.api;

/**
 * 没有登陆异常
 * Created by zhangan on 2017-05-16.
 */

public class NotLoggedThrowable extends Throwable {

    public NotLoggedThrowable() {
    }

    public NotLoggedThrowable(String message) {
        super(message);
    }

    public NotLoggedThrowable(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLoggedThrowable(Throwable cause) {
        super(cause);
    }
}
