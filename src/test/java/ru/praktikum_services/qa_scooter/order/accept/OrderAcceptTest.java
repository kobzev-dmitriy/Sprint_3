package ru.praktikum_services.qa_scooter.order.accept;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.model.Courier;
import ru.praktikum_services.qa_scooter.model.Order;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAcceptTest {
    private OrderClient orderClient;
    private CourierClient courierClient;
    private Order order;
    private Courier courier;
    private int orderId;
    private int courierId;
    private int orderTrack;
    private ValidatableResponse responseOrderGetOne;
    private ValidatableResponse responseOrderAcceptGetOne;
    private ValidatableResponse responseAccept;


    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
        order = Order.getRandom();
        courier = Courier.getRandom();
    }

    @After
    public void tearDown() {
        orderClient.finish(orderId);
    }

    @Test
    @Story("Принять заказ")
    @Description("Принять заказ. Позитивный сценарий")
    @DisplayName("Принять заказ. Позитивный сценарий")
    public void checkOrderCanBeAcceptedByCourier() {
        //Arrange
        orderTrack = orderClient.create(order).extract().path("track");
        responseOrderGetOne = orderClient.getOne(orderTrack);
        orderId = responseOrderGetOne.extract().path("order.id");
        courierId = courierClient.createCourierAndGetCourierId(courier);

        //Act
        assertThat(responseOrderGetOne.extract().path("order.status"), equalTo(0));
        responseAccept = orderClient.acceptOrder(orderId, courierId);
        responseOrderAcceptGetOne = orderClient.getOne(orderTrack);

        //Assert
        responseAccept.assertThat().statusCode(SC_OK);
        assertThat(responseOrderAcceptGetOne.extract().path("order.courierFirstName"), equalTo(courier.getFirstName()));
        assertThat(responseOrderAcceptGetOne.extract().path("order.status"), equalTo(1));
    }
}