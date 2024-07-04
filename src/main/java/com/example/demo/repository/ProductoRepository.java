package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer>{

}
