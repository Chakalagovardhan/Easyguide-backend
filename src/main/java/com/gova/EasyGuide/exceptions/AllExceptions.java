package com.gova.EasyGuide.exceptions;

public class AllExceptions {

    public static  class userAllReadyExist extends RuntimeException{

        public userAllReadyExist(String str)
        {
            super(str);
        }
    }
}
