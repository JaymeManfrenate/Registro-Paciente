package com.gerenciamento.clientes2;

import java.time.LocalDate;

public class Cliente {
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private LocalDate ultimaConsulta;
    private String observacoes;

    public Cliente(String nome, String email, LocalDate dataNascimento, LocalDate ultimaConsulta, String observacoes) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.ultimaConsulta = ultimaConsulta;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public LocalDate getUltimaConsulta() {
        return ultimaConsulta;
    }

    public String getObservacoes() {
        return observacoes;
    }
}
