package ru.praktikum_services.qa_scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.model.Courier;
import ru.praktikum_services.qa_scooter.model.CourierCredentials;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.qa_scooter.apidata.EndPoints.COURIER_PATH;

public class CourierClient extends RestAssuredClient {

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .log().body()
                .when()
                .post(COURIER_PATH)
                .then()
                .log().body();
    }

    @Step("Логирование в систему под курьером")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .log().body()
                .when()
                .post(COURIER_PATH + "/login")
                .then()
                .log().body();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .log().uri()
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then()
                .log().body();
    }

    @Step("Удаление курьера. Запрос без Id")
    public ValidatableResponse delete() {
        return given()
                .spec(getBaseSpec())
                .log().uri()
                .when()
                .delete(COURIER_PATH + "/")
                .then()
                .log().body();
    }

    @Step("Получить id курьера")
    public int getCourierId(Courier courier){
        return login(CourierCredentials.from(courier)).extract().path("id");
    }

    @Step("Создать курьера и получить id курьера ")
    public int createCourierAndGetCourierId(Courier courier){
        create(courier);
        return getCourierId(courier);
    }
}