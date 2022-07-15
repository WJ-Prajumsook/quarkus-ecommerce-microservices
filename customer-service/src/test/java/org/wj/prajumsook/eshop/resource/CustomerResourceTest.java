package org.wj.prajumsook.eshop.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.eshop.entity.CartStatus;
import org.wj.prajumsook.eshop.model.Cart;
import org.wj.prajumsook.eshop.model.Customer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;

@QuarkusTest
public class CustomerResourceTest {

  @Test
  public void testListCustomers() {
    given().when().get("/api/v1/customers")
        .then()
        .statusCode(200)
        .body(
            containsString("Wj"));
  }

  @Test
  public void testGetCustomer() {
    Customer customer = given().when()
        .get("/api/v1/customers/11")
        .then()
        .statusCode(200)
        .extract()
        .as(Customer.class);

    assertEquals("Wj", customer.getFirstName());
    assertEquals("Prajumsook", customer.getLastName());
  }

  @Test
  public void testCreateCustomer() {
    Customer customer = new Customer()
        .setFirstName("test")
        .setLastName("test")
        .setEmail("test@test.com")
        .setTelephone("00-000-000-000");

    given().when()
        .body(customer)
        .contentType(ContentType.JSON)
        .post("/api/v1/customers")
        .then()
        .statusCode(200)
        .body(
            containsString("test"));
  }

  @Test
  public void testUpdateCustomer() {
    Customer customer = given().when()
        .get("/api/v1/customers/11")
        .then()
        .statusCode(200)
        .extract()
        .as(Customer.class);

    customer.setFirstName("Updated");

    Customer response = given().when()
        .body(customer)
        .contentType(ContentType.JSON)
        .put("/api/v1/customers")
        .then()
        .statusCode(200)
        .extract()
        .as(Customer.class);

    assertEquals("Updated", response.getFirstName());
  }

  @Test
  public void testDeleteCustomer() {
    Customer customer = new Customer()
        .setFirstName("firstname")
        .setLastName("lastname")
        .setEmail("email")
        .setTelephone("telephone");

    Customer cus = given().when()
        .body(customer)
        .contentType(ContentType.JSON)
        .post("/api/v1/customers")
        .then()
        .statusCode(200)
        .extract()
        .as(Customer.class);

    given().when()
        .delete("/api/v1/customers/" + cus.getId())
        .then()
        .statusCode(204)
        .body(emptyString());
  }

  @Test
  public void testGetCarts() {
    given().when()
        .get("/api/v1/carts")
        .then()
        .statusCode(200)
        .body(
            containsString("NEW"));
  }

  @Test
  public void testGetCart() {
    Cart cart = given().when()
        .get("/api/v1/carts/11")
        .then()
        .statusCode(200)
        .extract()
        .as(Cart.class);

    assertEquals(11, cart.getCustomer().getId());

  }

  @Test
  public void testCreateCart() {
    Customer customer = new Customer()
        .setFirstName("firstname")
        .setLastName("lastname")
        .setEmail("email")
        .setTelephone("telephone");

    Customer cus = given().when()
        .body(customer)
        .contentType(ContentType.JSON)
        .post("/api/v1/customers")
        .then()
        .statusCode(200)
        .extract()
        .as(Customer.class);

    given().when()
        .contentType(ContentType.JSON)
        .body(cus)
        .post("/api/v1/carts")
        .then()
        .statusCode(201);
  }
}
