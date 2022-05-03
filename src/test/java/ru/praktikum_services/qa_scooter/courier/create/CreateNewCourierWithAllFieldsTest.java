package ru.praktikum_services.qa_scooter.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.model.Courier;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CreateNewCourierWithAllFieldsTest {

    private CourierClient courierClient;
    private int courierId;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @Story("Создание курьера")
    @Description ("Создание уникального курьера со всеми полями")
    @DisplayName("Создание уникального курьера {courier} со всеми полями")
    public void checkCourierCanBeCreatedWithAllFields(){
        //Arrange
        Courier courier = Courier.getRandom();

        //Act and Assert
        actAndAssert(courier);
    }

    @Test
    @Story("Создание курьера")
    @Description ("Создание уникального курьера без обязательного поля FirstName")
    public void checkCourierCanBeCreatedWithoutFirstName(){
        //Arrange
        Courier courier = Courier.getRandom();
        courier.setFirstName(null);

        //Act and Assert
        actAndAssert(courier);
    }

    private void actAndAssert(Courier courier){
        //Act
        response = courierClient.create(courier);
        courierId = courierClient.getCourierId(courier);

        //Assert
        response.assertThat().statusCode(SC_CREATED);
        response.assertThat().extract().path("ok").equals(true);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}