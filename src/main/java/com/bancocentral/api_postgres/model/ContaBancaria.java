package com.bancocentral.api_postgres.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ContaBancaria {

    private String nomeTitular;
    private double saldo;

    @Id
    private int numeroConta;
    private String senha;

    // Construtor vazio obrigatório para o Spring Data JPA
    public ContaBancaria() {
    }

    // Construtor completo
    public ContaBancaria(int numeroConta, String senha, String nomeTitular, double saldoInicial) {
        this.nomeTitular = nomeTitular;
        this.numeroConta = numeroConta;
        this.senha = senha;
        if (saldoInicial >= 0) {
            this.saldo = saldoInicial;
        }
    }

    // Getters e Postters
    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // MÉTODOS DE REGRA DE NEGÓCIO
    public boolean depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        } else {
            return false;
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
            return true;
        } else {
            return false;
        }
    }

    public boolean transferir(ContaBancaria contaBancaria, double valor) {
        if (valor <= 0) {
            return false;
        }
        if (this.saldo >= valor) {
            this.saldo -= valor;
            contaBancaria.depositarDeTransferencia(valor);
            return true;
        } else {
            return false;
        }
    }

    public void depositarDeTransferencia(double valor) {
        this.saldo += valor;
    }

    public void apresentar() {
        System.out.println("--- Dados da Conta ---");
        System.out.println("Titular: " + this.nomeTitular);
        System.out.println("Saldo atual: R$" + this.saldo);
    }
}
