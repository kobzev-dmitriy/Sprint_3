package ru.praktikum_services.qa_scooter.order.accept;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.model.Courier;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAcceptWithWrongOrderIdTest {
    private OrderClient orderClient;
    private CourierClient courierClient;
    private Courier courier;
    private int orderId;
    private int courierId;
    private ValidatableResponse responseAccept;


    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        orderId = Integer.parseInt(RandomStringUtils.random(8, "123456789"));
    }

    @Test
    @Story("Принять заказ")
    @Description("Попытка принять заказ. В запросе указан не существующий orderId")
    @DisplayName("Попытка принять заказ. В запросе указан не существующий orderId")
    public void checkOrderCanNotBeAcceptedWithWrongOrderId() {
        //Arrange
        courierId = courierClient.createCourierAndGetCourierId(courier);

        //Act
        responseAccept = orderClient.acceptOrder(orderId, courierId);

        //Assert
        responseAccept.assertThat().statusCode(SC_NOT_FOUND);
        assertThat(responseAccept.extract().path("message"), equalTo("Заказа с таким id не существует"));
    }
}