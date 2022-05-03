package ru.praktikum_services.qa_scooter.courier.delete;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class DeleteCourierWithWrongIdTest {
    private CourierClient courierClient;
    private int courierId;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courierId = Integer.parseInt(RandomStringUtils.random(8,"123456789"));
    }

    @Test
    @Story("Удаление курьера")
    @Description("Удаление курьера c несуществующим Id")
    @DisplayName("Удаление курьера c несуществующим Id")
    public void checkCourierCanBeDeleted(){
        //Act
        response = courierClient.delete(courierId);

        //Assert
        response.assertThat().statusCode(SC_NOT_FOUND);
        response.assertThat().extract().path("message").equals("Курьера с таким id нет");
    }
}