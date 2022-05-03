package ru.praktikum_services.qa_scooter.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.model.Courier;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(value = Parameterized.class)
public class CreateNewCourierWithoutMandatoryFieldsTest {
    private CourierClient courierClient;
    private Courier body;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Parameterized.Parameters(name = "{index}: в теле запроса отмутствует поле: {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Courier.setRandomLogin()},
                {Courier.setRandomPassword()},
                }
        );
    }

    public CreateNewCourierWithoutMandatoryFieldsTest(Courier body){
        this.body = body;
    }

    @Test
    @Story("Создание курьера")
    @Description("Создание курьера без обязательных полей невозможно")
    @DisplayName("Создание курьера без обязательных полей невозможно")
    public void checkCourierCanNotBeCreatedWithoutMandatoryFields(){
        //Arrange
        Courier courier = body;

        //Act
        response = courierClient.create(courier);

        //Assert
        response.assertThat().statusCode(SC_BAD_REQUEST);
        response.assertThat().extract().path("message").equals("Недостаточно данных для создания учетной записи");
    }
}