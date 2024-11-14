package com.gerenciamento.clientes2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<String> clientesSalvos = new ArrayList<>(); // Lista para armazenar os clientes

    public static void main(String[] args) {
        carregarClientes(); // Carrega clientes do arquivo ao iniciar
        SwingUtilities.invokeLater(Main::init);
    }

    public static void init() {
        JFrame frame = new JFrame("Gerenciamento de Clientes");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));

        JButton novoClienteButton = new JButton("Novo Cliente");
        novoClienteButton.addActionListener(e -> new JanelaNovoCliente());

        JButton clientesExistentesButton = new JButton("Clientes Já Existentes");
        clientesExistentesButton.addActionListener(e -> new JanelaClientesExistentes(clientesSalvos));

        frame.add(novoClienteButton);
        frame.add(clientesExistentesButton);

        frame.setVisible(true);
    }

    // Método para adicionar cliente à lista
    public static void adicionarCliente(String cliente) {
        clientesSalvos.add(cliente);
        salvarClientes(); // Salva clientes sempre que um novo cliente é adicionado
    }

    // Método para salvar a lista de clientes em um arquivo
    public static void salvarClientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt"))) {
            for (String cliente : clientesSalvos) {
                writer.write(cliente);
                writer.newLine(); // Nova linha para cada cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar clientes do arquivo
    public static void carregarClientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                clientesSalvos.add(linha); // Adiciona cada linha lida à lista de clientes
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
