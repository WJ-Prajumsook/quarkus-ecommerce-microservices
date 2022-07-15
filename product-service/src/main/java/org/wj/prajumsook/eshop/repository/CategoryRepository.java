package org.wj.prajumsook.eshop.repository;

import javax.enterprise.context.ApplicationScoped;

import org.wj.prajumsook.eshop.entity.CategoryEntity;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<CategoryEntity> {
}
