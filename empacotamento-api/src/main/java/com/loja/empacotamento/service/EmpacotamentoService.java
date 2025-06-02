package com.loja.empacotamento.service;

import com.loja.empacotamento.dto.EmpacotamentoResponse;
import com.loja.empacotamento.model.Caixa;
import com.loja.empacotamento.model.Pedido;
import com.loja.empacotamento.model.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpacotamentoService {

    // Definição das caixas disponíveis conforme especificação
    private static final List<Caixa> CAIXAS_DISPONIVEIS = Arrays.asList(
            new Caixa("Caixa 1", 30, 40, 80, new ArrayList<>()),
            new Caixa("Caixa 2", 80, 50, 40, new ArrayList<>()),
            new Caixa("Caixa 3", 50, 80, 60, new ArrayList<>())
    );

    public List<EmpacotamentoResponse> processarPedidos(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::processarPedido)
                .collect(Collectors.toList());
    }

    private EmpacotamentoResponse processarPedido(Pedido pedido) {
        List<Caixa> caixasUtilizadas = empacotar(pedido.getProdutos());
        
        List<EmpacotamentoResponse.CaixaDTO> caixasDTO = caixasUtilizadas.stream()
                .map(EmpacotamentoResponse.CaixaDTO::fromCaixa)
                .collect(Collectors.toList());
        
        return new EmpacotamentoResponse(pedido.getId(), caixasDTO);
    }

    private List<Caixa> empacotar(List<Produto> produtos) {
        // Ordenar produtos por volume (do maior para o menor)
        List<Produto> produtosOrdenados = new ArrayList<>(produtos);
        produtosOrdenados.sort(Comparator.comparing(Produto::getVolume).reversed());
        
        List<Caixa> caixasUtilizadas = new ArrayList<>();
        
        for (Produto produto : produtosOrdenados) {
            boolean alocado = false;
            
            // Tentar alocar em caixas já utilizadas
            for (Caixa caixa : caixasUtilizadas) {
                if (caixa.podeAcomodar(produto)) {
                    caixa.adicionarProduto(produto);
                    alocado = true;
                    break;
                }
            }
            
            // Se não foi possível alocar em caixas existentes, criar uma nova caixa
            if (!alocado) {
                Caixa novaCaixa = encontrarMelhorCaixa(produto);
                if (novaCaixa != null) {
                    novaCaixa.adicionarProduto(produto);
                    caixasUtilizadas.add(novaCaixa);
                } else {
                    // Caso extremo: produto não cabe em nenhuma caixa disponível
                    throw new RuntimeException("Produto não cabe em nenhuma caixa disponível: " + produto.getId());
                }
            }
        }
        
        return caixasUtilizadas;
    }

    private Caixa encontrarMelhorCaixa(Produto produto) {
        return CAIXAS_DISPONIVEIS.stream()
                .filter(caixa -> caixa.podeAcomodar(produto))
                .min(Comparator.comparing(Caixa::getVolume))
                .map(caixa -> new Caixa(caixa.getTipo(), caixa.getAltura(), caixa.getLargura(), caixa.getComprimento(), new ArrayList<>()))
                .orElse(null);
    }
}
