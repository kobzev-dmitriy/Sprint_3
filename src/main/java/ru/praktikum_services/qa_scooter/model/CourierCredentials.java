package ru.praktikum_services.qa_scooter.model;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {

    public String login;
    public String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials onlyLogin(){
        return new CourierCredentials(RandomStringUtils.randomAlphabetic(10),null);

    }

    public static CourierCredentials onlyPassword(){
        return new CourierCredentials(null, RandomStringUtils.randomAlphabetic(10));
    }
}