package com.sistemaspedidos.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaspedidos.domain.ItemPedido;
import com.sistemaspedidos.domain.PagamentoComBoleto;
import com.sistemaspedidos.domain.Pedido;
import com.sistemaspedidos.enuns.EstadoPagamento;
import com.sistemaspedidos.repositories.ItemPedidoRepository;
import com.sistemaspedidos.repositories.PagamentoRepository;
import com.sistemaspedidos.repositories.PedidoRepository;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido buscarPorId(Integer id) {
		 Optional<Pedido> obj = pedidoRepository.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado!"));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PEDENTE);
		obj.getPagamento().setPedido(obj);

		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.findById(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
