package ru.praktikum_services.qa_scooter.order.accept;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.model.Order;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAcceptWithWrongCourierIdTest {
    private OrderClient orderClient;
    private Order order;
    private int orderId;
    private int courierId;
    private int orderTrack;
    private ValidatableResponse responseOrderGetOne;
    private ValidatableResponse responseOrderAcceptGetOne;
    private ValidatableResponse responseAccept;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = Order.getRandom();
        courierId = Integer.parseInt(RandomStringUtils.random(8, "123456789"));
    }

    @After
    public void tearDown() {
        orderClient.finish(orderId);
    }

    @Test
    @Story("Принять заказ")
    @Description("Попытка принять заказ. В запросе указан не существующий courierId")
    @DisplayName("Попытка принять заказ. В запросе указан не существующий courierId")
    public void checkOrderCanNotBeAcceptedWithWrongCourier() {
        //Arrange
        orderTrack = orderClient.create(order).extract().path("track");
        responseOrderGetOne = orderClient.getOne(orderTrack);
        orderId = responseOrderGetOne.extract().path("order.id");

        //Act
        assertThat(responseOrderGetOne.extract().path("order.status"), equalTo(0));
        responseAccept = orderClient.acceptOrder(orderId, courierId);
        responseOrderAcceptGetOne = orderClient.getOne(orderTrack);

        //Assert
        responseAccept.assertThat().statusCode(SC_NOT_FOUND);
        assertThat(responseAccept.extract().path("message"), equalTo("Курьера с таким id не существует"));
        assertThat(responseOrderAcceptGetOne.extract().path("order.status"), equalTo(0));
    }
}