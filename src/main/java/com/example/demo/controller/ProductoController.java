package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import java.util.Map;

import java.util.HashMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.*;
import com.example.demo.service.impl.PdfService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
    private PdfService pdfService;
	
	
		@GetMapping("/menu")
		public String showMenu(HttpSession session, Model model) {
			if(session.getAttribute("usuario") == null) {
				return "redirect:/";
			}
			
			String correo = session.getAttribute("usuario").toString();
			UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
			model.addAttribute("foto", usuarioEntity.getUrlimagen());
			model.addAttribute("usuarioEntity", usuarioEntity);
			
			
	        List<ProductoEntity> productos = productoService.obtenerTodosProducto();
	        model.addAttribute("productos", productos);
	        return "menu";
	    }
		
		@GetMapping("/registrar_producto")
		public String showFormularioRegistrar(HttpSession session, Model model) {
			
			if(session.getAttribute("usuario") == null) {
				return "redirect:/";
			}
			
		    ProductoEntity producto = new ProductoEntity();
		    List<CategoriaEntity> categorias = categoriaService.obtenerTodasCategorias();
		    model.addAttribute("producto", producto);
		    model.addAttribute("categorias", categorias);
		    return "registrar_producto"; 
		}

		
		@PostMapping("/guardar_producto")
		public String guardarProducto(@ModelAttribute("producto") ProductoEntity producto) {
		    productoService.guardarActualizarProducto(producto);
		   return "redirect:/menu"; 
		}
		
		@GetMapping("/editar/{id}")
	    public String showFormularioEditar(@PathVariable("id") Integer id, Model model) {
			ProductoEntity producto = productoService.obtenerProductoPorId(id);
	        List<CategoriaEntity> categorias = categoriaService.obtenerTodasCategorias();
	        model.addAttribute("producto", producto);
	        model.addAttribute("categorias", categorias);
	        return "editar_producto"; 
	    }
		
		@GetMapping("/detalle/{id}")
	    public String showFormularioDetalle(@PathVariable("id") Integer id, Model model) {
			ProductoEntity producto = productoService.obtenerProductoPorId(id);
	        List<CategoriaEntity> categorias = categoriaService.obtenerTodasCategorias();
	        model.addAttribute("producto", producto);
	        model.addAttribute("categorias", categorias);      
	        return "detalle_producto"; 
	    }
		
		@GetMapping("/eliminar/{id}")
	    public String eliminarEmpleado(@PathVariable("id") Integer id) {
	        productoService.eliminarEmpleado(id);
	        return "redirect:/menu"; 
	    }
		
		
		@GetMapping("/generar_pdf")
		public ResponseEntity<InputStreamResource> generarPdfProductos(HttpSession session) throws IOException {
			
			
			
			String Usuario = (String) session.getAttribute("usuario");
			
		    List<ProductoEntity> productos = productoService.obtenerTodosProducto();
		    Map<String, Object> datosPdf = new HashMap<>();
		    datosPdf.put("productos", productos);
		    datosPdf.put("usuario", Usuario);
		    
		    datosPdf.put("usuario", Usuario);
		    		    
		    ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("temple_pdf", datosPdf);

		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Disposition", "inline; filename=productos.pdf");


		    return ResponseEntity.ok()
		            .headers(headers)
		            .contentType(MediaType.APPLICATION_PDF)
		            .body(new InputStreamResource(pdfBytes));
		}
		

}
