package ru.praktikum_services.qa_scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.model.Order;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.qa_scooter.apidata.EndPoints.ORDERS_PATH;

public class OrderClient extends RestAssuredClient{

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .log().body()
                .when()
                .post(ORDERS_PATH)
                .then()
                .log().body();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getList(Integer limit ){
        return given()
                .spec(getBaseSpec())
                .log().uri()
                .when()
                .get(ORDERS_PATH+"?limit="+limit+"&page=0")
                .then();
    }

    @Step("Получение заказа по трекинговому номеру")
    public ValidatableResponse getOne(Integer track){
        return given()
                .spec(getBaseSpec())
                .log().uri()
                .when()
                .get(ORDERS_PATH+"/track?t="+track)
                .then()
                .log().body();
    }

    @Step("Принять заказ")
    public ValidatableResponse acceptOrder(int orderId, int courierId){
        return given()
                .spec(getBaseSpec())
                .log().uri()
                .when()
                .put(ORDERS_PATH + "/accept/"+orderId+"?courierId="+courierId)
                .then()
                .log().body();
    }

    @Step("Принять заказ")
    public ValidatableResponse acceptOrder( int courierId){
        return given()
                .spec(getBaseSpec())
                .log().uri()
                .when()
                .put(ORDERS_PATH + "/accept/?courierId="+courierId)
                .then()
                .log().body();
    }

    @Step("Завершить заказ")
    public ValidatableResponse finish(int orderId){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(String.format("{\"id\":%s}",orderId))
                .log().body()
                .when()
                .put(ORDERS_PATH + "/finish/"+orderId)
                .then()
                .log().body();
    }
}