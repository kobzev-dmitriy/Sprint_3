package ru.praktikum_services.qa_scooter.model;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class Courier {
    static Faker faker = new Faker(new Locale("ru_RU"));

    public String login;
    public String password;
    public String firstName;

    public Courier(String login, String password, String firstName) {
        this.firstName = firstName;
        this.login = login;
        this.password = password;
    }

    public Courier() {
    }

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = faker.name().firstName();
        return new Courier(login, password, firstName);
    }

    public String getLogin() {
        return login;
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public static Courier setRandomLogin() {
        return new Courier().setLogin(RandomStringUtils.randomAlphabetic(10));
    }

    public static Courier setRandomPassword() {
        return new Courier().setPassword(RandomStringUtils.randomAlphabetic(10));
    }
}
