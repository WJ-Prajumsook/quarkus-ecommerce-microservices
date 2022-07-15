package org.wj.prajumsook.eshop.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.eshop.entity.AddressEntity;
import org.wj.prajumsook.eshop.model.Address;
import org.wj.prajumsook.eshop.repository.AddressRepository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class AddressService {

  @Inject
  AddressRepository addressRepository;

  public Multi<Address> findAll() {
    return addressRepository.streamAll().onItem().transform(AddressService::mapToDomain);
  }

  public Uni<Address> findById(Long id) {
    return addressRepository.findById(id)
        .onItem().ifNotNull().transform(AddressService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Address not found", 404));
  }

  public Uni<Address> create(Address address) {
    return Panache.withTransaction(() -> addressRepository.persistAndFlush(mapToEntity(address)))
        .onItem().transform(AddressService::mapToDomain);
  }

  public Uni<Boolean> delete(Long id) {
    return Panache.withTransaction(() -> addressRepository.deleteById(id));
  }

  public static AddressEntity mapToEntity(Address address) {
    return new ObjectMapper().convertValue(address, AddressEntity.class);
  }

  public static Address mapToDomain(AddressEntity entity) {
    return new ObjectMapper().convertValue(entity, Address.class);
  }
}
