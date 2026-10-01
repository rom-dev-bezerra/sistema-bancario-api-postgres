package com.bancocentral.api_postgres.controller;

import com.bancocentral.api_postgres.model.ContaBancaria;
import com.bancocentral.api_postgres.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaRepository repository;

    //primeira rota: listando do banco de dados
    @GetMapping
    public List<ContaBancaria> listarTodas() {
        return repository.findAll();
    }

    // rota para cadastrar uma nova conta no banco de dados
    @PostMapping
    public ContaBancaria cadastrarConta(@RequestBody ContaBancaria novaConta) {
        return repository.save(novaConta);
    }

    // Rota para buscar uma conta
    @GetMapping("/{numero}")
    public ContaBancaria buscarContaPorNumero(@PathVariable int numero) {
        return repository.findById(numero)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Conta não encontrada"
                ));
    }

    @PostMapping("/{numero}/depositar")
    public String depositarAPI(@PathVariable int numero, @RequestParam double valor) {

        ContaBancaria conta = repository.findById(numero)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Conta não encontrada"
                ));

        boolean sucesso = conta.depositar(valor);

        if (sucesso) {
            repository.save(conta);
            return "Depósito de: R$ " + valor + " realizado com sucesso! Saldo atualizado: R$ " + conta.getSaldo();
        } else {
            return "Erro: Valor de depósito inválido";
        }
    }

    @PostMapping("/{numero}/sacar")
    public String sacarAPI(@PathVariable int numero, @RequestParam double valor) {

        ContaBancaria conta = repository.findById(numero)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Conta não encontrada"
                ));

        boolean sucesso = conta.sacar(valor);

        if (sucesso) {
            repository.save(conta);
            return "Saque de: R$ " + valor + " realizado com sucesso! Saldo atualizado: R$ " + conta.getSaldo();
        } else {
            return "Erro: Valor de saque inválido";
        }
    }

    @PostMapping("/{numero}/transferir")
    public String transferirAPI(@PathVariable int numero, @RequestParam int contaDestino, @RequestParam double valor) {

        // busca a conta origem no banco
        ContaBancaria origem = repository.findById(numero)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Conta de origem não encontrada"
                ));

        // busca a conta de DESTINO no banco
        ContaBancaria destino = repository.findById(contaDestino)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Conta de destino não encontrada"
                ));

        boolean sucesso = origem.transferir(destino, valor);
        if (sucesso) {
            repository.save(origem);
            repository.save(destino);

            return "Transferência de R$ " + valor + " realizada com sucesso de "
                    + origem.getNomeTitular() + " para " + destino.getNomeTitular() + "!";
        } else {
            return "Erro: Saldo insuficiente ou valor de transferência inválido.";
        }
    }
}