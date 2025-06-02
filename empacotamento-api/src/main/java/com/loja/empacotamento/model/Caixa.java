package com.loja.empacotamento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caixa {
    private String tipo;
    private double altura;
    private double largura;
    private double comprimento;
    private List<Produto> produtos = new ArrayList<>();
    
    public double getVolume() {
        return altura * largura * comprimento;
    }
    
    public double getVolumeOcupado() {
        return produtos.stream().mapToDouble(Produto::getVolume).sum();
    }
    
    public double getEspacoDisponivel() {
        return getVolume() - getVolumeOcupado();
    }
    
    public boolean podeAcomodar(Produto produto) {
        // Verifica se as dimensões do produto cabem na caixa (considerando rotações possíveis)
        return (produto.getAltura() <= altura && produto.getLargura() <= largura && produto.getComprimento() <= comprimento) ||
               (produto.getAltura() <= altura && produto.getLargura() <= comprimento && produto.getComprimento() <= largura) ||
               (produto.getAltura() <= largura && produto.getLargura() <= altura && produto.getComprimento() <= comprimento) ||
               (produto.getAltura() <= largura && produto.getLargura() <= comprimento && produto.getComprimento() <= altura) ||
               (produto.getAltura() <= comprimento && produto.getLargura() <= altura && produto.getComprimento() <= largura) ||
               (produto.getAltura() <= comprimento && produto.getLargura() <= largura && produto.getComprimento() <= altura);
    }
    
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }
}
