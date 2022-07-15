package org.wj.prajumsook.eshop.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.eshop.model.Address;
import org.wj.prajumsook.eshop.model.Order;
import org.wj.prajumsook.eshop.model.OrderItem;
import org.wj.prajumsook.eshop.model.Payment;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashSet;
import java.util.Set;

@QuarkusTest
public class OrderResourceTest {

  @Test
  public void testGetAddres() {
    given()
        .when().get("/api/v1/addresses")
        .then()
        .statusCode(200)
        .body(containsString("city-name"));
  }

  @Test
  public void testGetPayment() {
    given().when()
        .get("/api/v1/payments")
        .then()
        .statusCode(200)
        .body(containsString("card-method"));
  }

  @Test
  public void testGetOrderItem() {
    given().when()
        .get("/api/v1/order_items")
        .then()
        .statusCode(200)
        .body("$.size()", is(3));
  }

  @Test
  public void testCreateOrder() {
    Set<OrderItem> orderItems = new HashSet<>();
    orderItems.add(new OrderItem().setId(221L));
    orderItems.add(new OrderItem().setId(222L));
    orderItems.add(new OrderItem().setId(223L));
    Order order = new Order()
        .setShipmentAddress(new Address().setId(111L))
        .setPayment(new Payment().setId(333L))
        .setOrderItems(orderItems);

    given().when()
        .contentType(ContentType.JSON)
        .body(order)
        .then()
        .statusCode(201);

  }

}
