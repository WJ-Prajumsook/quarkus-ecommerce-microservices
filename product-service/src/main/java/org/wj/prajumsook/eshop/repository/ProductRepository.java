package org.wj.prajumsook.eshop.repository;

import javax.enterprise.context.ApplicationScoped;

import org.wj.prajumsook.eshop.entity.ProductEntity;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity> {
}
