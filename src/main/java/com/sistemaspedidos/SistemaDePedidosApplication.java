package com.sistemaspedidos;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sistemaspedidos.domain.Categoria;
import com.sistemaspedidos.domain.Cidade;
import com.sistemaspedidos.domain.Cliente;
import com.sistemaspedidos.domain.Endereco;
import com.sistemaspedidos.domain.Estado;
import com.sistemaspedidos.domain.ItemPedido;
import com.sistemaspedidos.domain.Pagamento;
import com.sistemaspedidos.domain.PagamentoComBoleto;
import com.sistemaspedidos.domain.PagamentoComCartao;
import com.sistemaspedidos.domain.Pedido;
import com.sistemaspedidos.domain.Produto;
import com.sistemaspedidos.enuns.EstadoPagamento;
import com.sistemaspedidos.enuns.TipoCliente;
import com.sistemaspedidos.repositories.CategoriaRepository;
import com.sistemaspedidos.repositories.CidadeRepository;
import com.sistemaspedidos.repositories.ClienteRepository;
import com.sistemaspedidos.repositories.EnderecoRepository;
import com.sistemaspedidos.repositories.EstadoRepository;
import com.sistemaspedidos.repositories.ItemPedidoRepository;
import com.sistemaspedidos.repositories.PagamentoRepository;
import com.sistemaspedidos.repositories.PedidoRepository;
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
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		//CATEGORIAS
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		
		//PRODUTOS
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().add(prod3);
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		//Estado
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "Uberlândia");
		
		//CIDADE
		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		//CLIENTE
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		//TELEFONE
		cliente1.getTelefones().addAll(Arrays.asList("(67) 9911-5577", "(67) 8851-888"));
		
		//ENDERECO
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardin", "38220834", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);
        
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
	
	    clienteRepository.saveAll(Arrays.asList(cliente1));
	    enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
	    
	    //PEDIDO
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    
	    Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
	    Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
	    
	    Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
	    ped1.setPagamento(pagto1);
	    
	    Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PEDENTE, ped2, sdf.parse("20/10/2017 00:00"),null);
	    ped2.setPagamento(pagto2);
	    
	    cliente1.getPedidos().addAll(Arrays.asList(ped1, ped2));
	    
	    pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
	    pagamentoRepository.saveAll(Arrays.asList(pagto1 , pagto2));
	    
	    //ITEMP EDIDO
	    
	    ItemPedido ip1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.00);
	    ItemPedido ip2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.00);
	    ItemPedido ip3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.00);
	    
	    ped1.getItens().addAll(Arrays.asList(ip1, ip2));
	    ped1.getItens().addAll(Arrays.asList(ip3));
	    
	    prod1.getItens().addAll(Arrays.asList(ip1));
	    prod2.getItens().addAll(Arrays.asList(ip3));
	    prod3.getItens().addAll(Arrays.asList(ip2));
	    
	    itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
