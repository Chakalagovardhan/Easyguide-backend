package com.gova.EasyGuide.exceptions;

public class AllExceptions {

    public static  class userAllReadyExist extends RuntimeException{

        public userAllReadyExist(String str)
        {
            super(str);
        }
    }

    public static class courseAllReadyExist extends RuntimeException{
        public courseAllReadyExist(String str) {
            super(str);
        }

    }
    public static class userNotFoundExist extends RuntimeException{
        public userNotFoundExist(String str)
        {
            super(str);
        }
    }
}
