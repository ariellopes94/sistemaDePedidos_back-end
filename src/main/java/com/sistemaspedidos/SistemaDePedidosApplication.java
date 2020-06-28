package com.sistemaspedidos;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.domain.Produto;
import com.sistemaspedidos.repositories.CategoriaRepository;
import com.sistemaspedidos.repositories.ProdutoRepository;

@SpringBootApplication
public class SistemaDePedidosApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SistemaDePedidosApplication.class, args);
	}

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		//CATEGORIAS
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		//PRODUTOS
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().add(prod3);
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().add(cat1);
		
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
	}

}
