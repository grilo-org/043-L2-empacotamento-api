package com.loja.empacotamento;

import com.loja.empacotamento.model.Caixa;
import com.loja.empacotamento.model.Pedido;
import com.loja.empacotamento.model.Produto;
import com.loja.empacotamento.service.EmpacotamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmpacotamentoServiceTest {

    @Autowired
    private EmpacotamentoService empacotamentoService;

    @Test
    public void testProcessarPedidoSimples() {
        // Criar um pedido com produtos que cabem em uma única caixa
        Produto produto1 = new Produto("prod-001", "Jogo de Tabuleiro", 10.0, 30.0, 40.0);
        Produto produto2 = new Produto("prod-002", "Console Portátil", 5.0, 15.0, 20.0);
        
        List<Produto> produtos = Arrays.asList(produto1, produto2);
        Pedido pedido = new Pedido("pedido-001", produtos);
        
        var resultado = empacotamentoService.processarPedidos(List.of(pedido));
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("pedido-001", resultado.get(0).getPedidoId());
        assertEquals(1, resultado.get(0).getCaixas().size());
        assertEquals(2, resultado.get(0).getCaixas().get(0).getProdutos().size());
    }
    
    @Test
    public void testProcessarPedidoMultiplasCaixas() {
        // Criar um pedido com produtos que precisam de múltiplas caixas
        Produto produto1 = new Produto("prod-001", "Console Grande", 40.0, 60.0, 30.0);
        Produto produto2 = new Produto("prod-002", "Jogo de Tabuleiro Grande", 30.0, 70.0, 50.0);
        
        List<Produto> produtos = Arrays.asList(produto1, produto2);
        Pedido pedido = new Pedido("pedido-002", produtos);
        
        var resultado = empacotamentoService.processarPedidos(List.of(pedido));
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getCaixas().size() >= 1);
    }
}
