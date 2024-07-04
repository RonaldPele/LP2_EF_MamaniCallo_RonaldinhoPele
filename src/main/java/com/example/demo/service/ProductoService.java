package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.*;

public interface ProductoService {
	
	List<ProductoEntity> obtenerTodosProducto() ;

    ProductoEntity obtenerProductoPorId(Integer id);

    ProductoEntity guardarActualizarProducto(ProductoEntity producto);

    void eliminarEmpleado(Integer id);

}
