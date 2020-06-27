package com.sistemaspedidos;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.repositories.CategoriaRepository;

@SpringBootApplication
public class SistemaDePedidosApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SistemaDePedidosApplication.class, args);
	}

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
	}

}
