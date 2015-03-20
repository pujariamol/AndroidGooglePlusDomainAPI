package com.amol.util;

/**
 * Created by Amol on 3/11/2015.
 */
public class Constants {

    public final static String NA = "Not Available";

//    public static enum GENDER{MALE,FEMALE};
    public final static String getGender(int val){
        if(val == 0){
            return "Male";
        }else{
            return "Female";
        }
    }

}
