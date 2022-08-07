package com.bside.idle.notice.exception;

public class DuplicatedNoticeException extends RuntimeException {

	public DuplicatedNoticeException() {
	}

	public DuplicatedNoticeException(String message) {
		super(message);
	}

	public DuplicatedNoticeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedNoticeException(Throwable cause) {
		super(cause);
	}

	public DuplicatedNoticeException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
