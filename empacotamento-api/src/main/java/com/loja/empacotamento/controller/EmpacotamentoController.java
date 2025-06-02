package com.loja.empacotamento.controller;

import com.loja.empacotamento.dto.EmpacotamentoResponse;
import com.loja.empacotamento.dto.PedidoRequest;
import com.loja.empacotamento.model.Pedido;
import com.loja.empacotamento.model.Produto;
import com.loja.empacotamento.service.EmpacotamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empacotamento")
@Api(value = "API de Empacotamento", description = "Operações relacionadas ao empacotamento de produtos")
public class EmpacotamentoController {

    private final EmpacotamentoService empacotamentoService;

    @Autowired
    public EmpacotamentoController(EmpacotamentoService empacotamentoService) {
        this.empacotamentoService = empacotamentoService;
    }

    @PostMapping
    @ApiOperation(value = "Processa pedidos e retorna a melhor forma de empacotamento", response = EmpacotamentoResponse.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pedidos processados com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity<List<EmpacotamentoResponse>> processarPedidos(@Valid @RequestBody List<PedidoRequest> pedidosRequest) {
        List<Pedido> pedidos = pedidosRequest.stream()
                .map(this::converterParaPedido)
                .collect(Collectors.toList());

        List<EmpacotamentoResponse> resultado = empacotamentoService.processarPedidos(pedidos);
        return ResponseEntity.ok(resultado);
    }

    private Pedido converterParaPedido(PedidoRequest pedidoRequest) {
        List<Produto> produtos = pedidoRequest.getProdutos().stream()
                .map(PedidoRequest.ProdutoDTO::toProduto)
                .collect(Collectors.toList());

        return new Pedido(pedidoRequest.getId(), produtos);
    }
}
