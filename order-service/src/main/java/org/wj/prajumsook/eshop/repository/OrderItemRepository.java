package org.wj.prajumsook.eshop.repository;

import javax.enterprise.context.ApplicationScoped;

import org.wj.prajumsook.eshop.entity.OrderItemEntity;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class OrderItemRepository implements PanacheRepository<OrderItemEntity> {
}
