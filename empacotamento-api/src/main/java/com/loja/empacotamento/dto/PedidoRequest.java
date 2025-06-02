package com.loja.empacotamento.dto;

import com.loja.empacotamento.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    @NotNull(message = "O ID do pedido não pode ser nulo")
    private String id;
    
    @NotEmpty(message = "A lista de produtos não pode estar vazia")
    @Valid
    private List<ProdutoDTO> produtos;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProdutoDTO {
        @NotNull(message = "O ID do produto não pode ser nulo")
        private String id;
        
        private String nome;
        
        @NotNull(message = "A altura do produto não pode ser nula")
        private Double altura;
        
        @NotNull(message = "A largura do produto não pode ser nula")
        private Double largura;
        
        @NotNull(message = "O comprimento do produto não pode ser nulo")
        private Double comprimento;
        
        public Produto toProduto() {
            return new Produto(id, nome, altura, largura, comprimento);
        }
    }
}
