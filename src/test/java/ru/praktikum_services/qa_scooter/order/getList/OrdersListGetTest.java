package ru.praktikum_services.qa_scooter.order.getList;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum_services.qa_scooter.client.OrderClient;

import java.util.HashMap;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;


public class OrdersListGetTest {
    private OrderClient orderClient;
    private ValidatableResponse response;
    private int limit;
    List<HashMap<String, Object>> orders;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Story("Получить заказ по его номеру.")
    @Description("Получить заказ по его номеру. Позитивный сценарий")
    @DisplayName("Получить заказ по его номеру. Позитивный сценарий")
    public void checkListOfOrdersCanBeGet() {
        //Act
        limit = 10;
        response = orderClient.getList(limit);
        orders = response.extract().jsonPath().getList("orders");

        //Assert
        response.assertThat().statusCode(SC_OK);
        response.assertThat().body("data.orders", not(emptyArray()));
        assertThat(orders.size(),equalTo(limit));
        orders.forEach(order -> assertThat(order.get("id"), is(not(0))));
    }
}