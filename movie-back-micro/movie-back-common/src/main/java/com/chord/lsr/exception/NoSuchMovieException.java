package com.chord.lsr.exception;

/**
 * 账号被锁定异常
 */
public class NoSuchMovieException extends BaseException {

    public NoSuchMovieException() {
    }

    public NoSuchMovieException(String msg) {
        super(msg);
    }

}
