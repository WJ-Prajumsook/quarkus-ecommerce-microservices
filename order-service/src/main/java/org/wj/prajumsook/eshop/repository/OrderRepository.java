package org.wj.prajumsook.eshop.repository;

import javax.enterprise.context.ApplicationScoped;

import org.wj.prajumsook.eshop.entity.OrderEntity;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<OrderEntity> {
}
