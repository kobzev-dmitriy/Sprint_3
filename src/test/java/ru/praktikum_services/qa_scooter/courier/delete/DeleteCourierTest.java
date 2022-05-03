package ru.praktikum_services.qa_scooter.courier.delete;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.model.Courier;
import ru.praktikum_services.qa_scooter.model.CourierCredentials;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class DeleteCourierTest {
    private CourierClient courierClient;
    private int courierId;
    private ValidatableResponse response;
    private ValidatableResponse responseLogin;
    Courier courier;


    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @Test
    @Story("Удаление курьера")
    @Description("Удаление курьера")
    @DisplayName("Удаление курьера {courier}")
    public void checkCourierCanBeDeleted(){
        //Arrange
        courierId = courierClient.createCourierAndGetCourierId(courier);

        //Act
        response = courierClient.delete(courierId);
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        //Assert
        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("ok").equals(true);
        responseLogin.assertThat().statusCode(SC_NOT_FOUND);
        responseLogin.assertThat().extract().path("message").equals("Учетная запись не найдена");
    }
}