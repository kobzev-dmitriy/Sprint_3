package ru.praktikum_services.qa_scooter.order.create;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.model.Order;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(value = Parameterized.class)
public class OrdersCreateTest {
    private OrderClient orderClient;
    private Order order;
    private String[] colors = new String[2];
    private ValidatableResponse response;
    private int orderTrack;
    private int orderId;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = Order.getRandom();
    }

    @After
    public void tearDown(){
        orderId = orderClient.getOne(orderTrack).extract().path("order.id");
        orderClient.finish(orderId);
    }

    @Parameterized.Parameters(name = "{index}: цвет самоката: {0} и {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"", ""},
                        {"BLACK", ""},
                        {"", "GREY"},
                        {"BLACK", "GREY"}
                }
        );
    }

    public OrdersCreateTest(String color_1, String color_2) {
        colors[0] = color_1;
        colors[1] = color_2;
    }

    @Test
    @Story("Создание заказа")
    @Description("Создание заказа. Позитивный сценарий")
    @DisplayName("Создание заказа. Позитивный сценарий")
    public void checkNewOrderCanBeCreated() {
        //Arrange
        order.setColors(colors);

        //Act
        response = orderClient.create(order);
        orderTrack = response.assertThat().extract().path("track");

        //Assert
        response.assertThat().statusCode(SC_CREATED);
        assertThat(orderTrack, is(not(0)));
    }
}