package com.olx.execption;

public class AdNotFoundException extends RuntimeException {

   public AdNotFoundException(String message){
          super(message);

    }

    public AdNotFoundException(String message , Throwable cause){
       super(message,cause);
    }
}
