package ru.praktikum_services.qa_scooter.courier.login;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.model.CourierCredentials;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(value = Parameterized.class)
public class LoginWithoutMandatoryFieldsTest {
    private CourierClient courierClient;
    private CourierCredentials body;
    private ValidatableResponse response;

   @Before
   public void setUp() {
      courierClient = new CourierClient();
    }

    @Parameterized.Parameters(name = "{index}: в теле запроса отсутствует поле: {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {CourierCredentials.onlyLogin() },
                        {CourierCredentials.onlyPassword()},
                }
        );
    }

    public LoginWithoutMandatoryFieldsTest(CourierCredentials body) {
        this.body = body;
    }

    @Test
    @Story("Логин курьера в системе")
    @Description("Логин курьера в системе без обязательных полей")
    @DisplayName("Логин курьера в системе без обязательных полей")
    public void checkNewCourierCanNotLoginWithoutMandatoryFields() {
        //Act
        response = courierClient.login(body);

        //Assert
        response.assertThat().statusCode(SC_BAD_REQUEST);
        response.assertThat().extract().path("message").equals("Недостаточно данных для входа");
    }
}