package com.wemakeitwork.allenvooreen.web.error;

public class MemberAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 5861310537366287163L;

    public MemberAlreadyExistsException() {
        super();
    }

    public MemberAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MemberAlreadyExistsException(final String message) {
        super(message);
    }

    public MemberAlreadyExistsException(final Throwable cause) {
        super(cause);
    }
}
