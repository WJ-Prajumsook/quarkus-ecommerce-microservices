package org.wj.prajumsook.eshop.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.eshop.model.Category;
import org.wj.prajumsook.eshop.model.Product;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;

@QuarkusTest
public class ProductResourceTest {

  @Test
  public void testGetAllProducts() {
    given()
        .when().get("/api/v1/products")
        .then()
        .statusCode(200)
        .body(
            containsString("product description"));
  }

  @Test
  public void testGetProductByid() {
    Product product = given().when()
        .get("/api/v1/products/222")
        .then()
        .statusCode(200)
        .extract()
        .as(Product.class);

    assertEquals("product title", product.getTitle());
    assertEquals(BigDecimal.valueOf(1.98), product.getPrice());
  }

  @Test
  public void testCreateProduct() {
    Category category = given().when()
        .get("/api/v1/categories/111")
        .then()
        .statusCode(200)
        .extract()
        .as(Category.class);
    Product product = new Product()
        .setCategory(category)
        .setTitle("title")
        .setStatus("AVAILABLE")
        .setPrice(BigDecimal.valueOf(22.99))
        .setImageUrl("url")
        .setDescription("description");

    given().when()
        .body(product)
        .contentType(ContentType.JSON)
        .post("/api/v1/products")
        .then()
        .statusCode(201);

  }

  @Test
  public void testDeleteProduct() {
    given().when()
        .delete("/api/v1/products/222")
        .then()
        .statusCode(204);
  }

  @Test
  public void testGetAllCategories() {
    given().when()
        .get("/api/v1/categories")
        .then()
        .statusCode(200)
        .body(containsString("cate-name"));
  }

  @Test
  public void testGetCategoryById() {
    given().when()
        .get("/api/v1/categories/111")
        .then()
        .statusCode(200)
        .body(containsString("cate-name"));
  }

  @Test
  public void testCreateAndDeleteCategory() {
    Category category = new Category()
        .setName("name")
        .setDescription("description");

    Category cate = given().when()
        .body(category)
        .contentType(ContentType.JSON)
        .post("/api/v1/categories")
        .then()
        .statusCode(200)
        .body(containsString("name"))
        .extract()
        .as(Category.class);

    given().when()
        .delete("/api/v1/categories/" + cate.getId())
        .then()
        .statusCode(204)
        .body(emptyString());
  }

}
