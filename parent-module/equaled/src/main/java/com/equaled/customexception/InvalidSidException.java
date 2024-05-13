package com.equaled.customexception;

public class InvalidSidException extends BaseException{

    public  InvalidSidException(Throwable e) {
        super(e);
    }

    public  InvalidSidException(ErrorCodes errorCoEnum) {
        this.errorCode = errorCoEnum;
    }

    public  InvalidSidException(ErrorCodes errorCoEnum, String devMsg) {
        this.errorCode = errorCoEnum;
        this.devMessage = devMsg;
    }
    public InvalidSidException(Throwable e,ErrorCodes error) {
        this.e=e;
        this.errorCode=error;
    }
    public  InvalidSidException(String devMsg) {
        this.devMessage = devMsg;
    }
}
