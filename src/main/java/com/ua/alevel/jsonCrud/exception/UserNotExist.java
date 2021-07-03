package com.ua.alevel.jsonCrud.exception;

public class UserNotExist extends Exception{
    String massage;
    int code;
    private int number;
    public int getNumber(){return number;}
    public UserNotExist(String message, int num){
        super(message);
        number=num;
    }
}
