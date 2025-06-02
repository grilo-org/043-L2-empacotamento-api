package com.loja.empacotamento.dto;

import com.loja.empacotamento.model.Caixa;
import com.loja.empacotamento.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpacotamentoResponse {
    private String pedidoId;
    private List<CaixaDTO> caixas;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CaixaDTO {
        private String tipo;
        private double altura;
        private double largura;
        private double comprimento;
        private List<ProdutoDTO> produtos;
        
        public static CaixaDTO fromCaixa(Caixa caixa) {
            List<ProdutoDTO> produtosDTO = caixa.getProdutos().stream()
                    .map(ProdutoDTO::fromProduto)
                    .collect(Collectors.toList());
            
            return new CaixaDTO(
                    caixa.getTipo(),
                    caixa.getAltura(),
                    caixa.getLargura(),
                    caixa.getComprimento(),
                    produtosDTO
            );
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProdutoDTO {
        private String id;
        private String nome;
        private double altura;
        private double largura;
        private double comprimento;
        
        public static ProdutoDTO fromProduto(Produto produto) {
            return new ProdutoDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getAltura(),
                    produto.getLargura(),
                    produto.getComprimento()
            );
        }
    }
}
