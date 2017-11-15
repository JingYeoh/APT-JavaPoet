package com.jkb.apt.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Log信息输出工具类.
 *
 * @author JingYeoh.
 *         Date 17-11-15.
 *         Github https://github.com/justkiddingbaby
 *         Blog http://blog.justkiddingbaby.com
 */

public class Logger {

    private Messager msg;
    private static final String PREFIX_COMPILE = "compile::";

    public Logger(Messager msg) {
        this.msg = msg;
    }

    public void i(CharSequence info) {
        if (StringUtils.hasEmpty(info)) return;
        msg.printMessage(Diagnostic.Kind.NOTE, PREFIX_COMPILE + info);
    }

    public void e(CharSequence info) {
        if (StringUtils.hasEmpty(info)) return;
        msg.printMessage(Diagnostic.Kind.ERROR, PREFIX_COMPILE + info);
    }

    public void e(Throwable error) {
        if (null != error) {
            msg.printMessage(Diagnostic.Kind.ERROR, PREFIX_COMPILE + "An exception is encountered, [" +
                    error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    public void w(CharSequence info) {
        if (StringUtils.hasEmpty(info)) return;
        msg.printMessage(Diagnostic.Kind.WARNING, PREFIX_COMPILE + info);
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
