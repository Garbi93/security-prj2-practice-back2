package org.zerock.securityprj2practiceback2.util;

public class CustomJWTException extends RuntimeException {
    public CustomJWTException(String msg){
        super(msg);
    }
}
