
package com.equaled.customexception;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommonHttpException extends BaseException{
    private Integer responseCode;
    private String responseMessage;
}
