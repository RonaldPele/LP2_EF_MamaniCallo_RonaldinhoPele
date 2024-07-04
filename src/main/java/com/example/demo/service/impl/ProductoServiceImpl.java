package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.service.ProductoService;
import com.example.demo.repository.*;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<ProductoEntity> obtenerTodosProducto() {
		// TODO Auto-generated method stub
		return productoRepository.findAll();
	}

	@Override
	public ProductoEntity obtenerProductoPorId(Integer id) {
		// TODO Auto-generated method stub
		return productoRepository.findById(id).get();
	}

	@Override
	public ProductoEntity guardarActualizarProducto(ProductoEntity producto) {
		// TODO Auto-generated method stub
		return productoRepository.save(producto);
	}

	@Override
	public void eliminarEmpleado(Integer id) {
		// TODO Auto-generated method stub
		productoRepository.deleteById(id);
		
	}

	
		
		
		
	

	

}



