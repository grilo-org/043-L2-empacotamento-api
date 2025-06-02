package com.loja.empacotamento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private String id;
    private String nome;
    private double altura;
    private double largura;
    private double comprimento;
    
    public double getVolume() {
        return altura * largura * comprimento;
    }
}
