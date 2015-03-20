package com.amol.plusmini;


import com.google.api.services.plusDomains.model.Circle;
import com.google.api.services.plusDomains.model.Person;

import java.util.List;

/**
 * Created by Amol on 3/17/2015.
 */
public class ShareObject {

    private static Person selfProfile;
    private static List<Circle> circleList;
    private static List<Person> circlePeople;
    private static String token;

    public static Person getSelfProfile() {
        return selfProfile;
    }

    public static void setSelfProfile(Person selfProfile) {
        ShareObject.selfProfile = selfProfile;
    }

    public static List<Circle> getCircleList() {
        return circleList;
    }

    public static void setCircleList(List<Circle> circleList) {
        ShareObject.circleList = circleList;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ShareObject.token = token;
    }

    public static List<Person> getCirclePeople() {
        return circlePeople;
    }

    public static void setCirclePeople(List<Person> circlePeople) {
        ShareObject.circlePeople = circlePeople;
    }
}
