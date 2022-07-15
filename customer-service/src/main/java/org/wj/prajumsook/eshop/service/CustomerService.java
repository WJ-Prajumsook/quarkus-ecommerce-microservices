package org.wj.prajumsook.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.eshop.entity.CustomerEntity;
import org.wj.prajumsook.eshop.model.Customer;
import org.wj.prajumsook.eshop.repository.CustomerRepository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class CustomerService {

  @Inject
  CustomerRepository customerRepository;

  public Multi<Customer> findAll() {
    return customerRepository.streamAll()
        .onItem().transform(CustomerService::mapToDomain);
  }

  public Uni<Customer> findById(Long id) {
    return customerRepository.findById(id)
        .onItem().ifNotNull().transform(CustomerService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Failed to find customer", 404));
  }

  public Uni<Customer> create(Customer customer) {
    return Panache.withTransaction(() -> customerRepository.persistAndFlush(mapToEntity(customer))
        .onItem()
        .transform(CustomerService::mapToDomain));
  }

  public Uni<Boolean> delete(Long id) {
    return Panache.withTransaction(() -> customerRepository.deleteById(id));
  }

  public Uni<Customer> update(Customer customer) {
    return Panache
        .withTransaction(() -> findById(customer.getId())
            .onItem()
            .transform(entity -> {
              entity.setFirstName(customer.getFirstName());
              entity.setLastName(customer.getLastName());
              entity.setEmail(customer.getEmail());
              entity.setTelephone(customer.getTelephone());
              return entity;
            }));
  }

  public static CustomerEntity mapToEntity(Customer customer) {
    return new ObjectMapper().convertValue(customer, CustomerEntity.class);
  }

  public static Customer mapToDomain(CustomerEntity entity) {
    return new ObjectMapper().convertValue(entity, Customer.class);
  }
}
