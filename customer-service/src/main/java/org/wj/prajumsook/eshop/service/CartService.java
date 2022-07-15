package org.wj.prajumsook.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.eshop.entity.CartEntity;
import org.wj.prajumsook.eshop.entity.CartStatus;
import org.wj.prajumsook.eshop.entity.CustomerEntity;
import org.wj.prajumsook.eshop.model.Cart;
import org.wj.prajumsook.eshop.repository.CartRepository;
import org.wj.prajumsook.eshop.repository.CustomerRepository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class CartService {

  @Inject
  CartRepository cartRepository;

  @Inject
  CustomerRepository customerRepository;

  public Multi<Cart> findAll() {
    return cartRepository.streamAll()
        .onItem().transform(CartService::mapToDomain);
  }

  public Uni<Cart> findById(Long id) {
    return cartRepository.findById(id)
        .onItem().ifNotNull().transform(CartService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Failed to find cart", 404));
  }

  public Uni<Cart> findByCustomerId(Long customerId) {
    return cartRepository.find("customer_id", customerId).firstResult()
        .onItem().ifNotNull().transform(CartService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Cart not found", 404));
  }

  public void create(Long customerId) {
    CustomerEntity customerEntity = customerRepository.findById(customerId).await().indefinitely();
    CartEntity entity = new CartEntity()
        .setCustomer(customerEntity)
        .setStatus(CartStatus.NEW);

    Panache.withTransaction(() -> cartRepository.persistAndFlush(entity)).await().indefinitely();
  }

  public Uni<Boolean> delete(Long id) {
    return Panache.withTransaction(() -> cartRepository.deleteById(id));
  }

  public static CartEntity mapToEntity(Cart cart) {
    return new ObjectMapper().convertValue(cart, CartEntity.class);
  }

  public static Cart mapToDomain(CartEntity entity) {
    return new ObjectMapper().convertValue(entity, Cart.class);
  }
}
