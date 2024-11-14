package com.gerenciamento.clientes2;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class JanelaNovoCliente {
    public JanelaNovoCliente() {
        JFrame frame = new JFrame("Adicionar Novo Cliente");
        frame.setSize(1200, 800); // Ajuste na altura para acomodar todos os campos
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Muda para DISPOSE_ON_CLOSE
        frame.setLayout(new GridLayout(9, 2)); // Aumenta o número de linhas para acomodar todos os campos

        // Adicionando campos de entrada
        frame.add(new JLabel("Nome:"));
        JTextField nomeField = new JTextField();
        frame.add(nomeField);

        frame.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        frame.add(emailField);

        frame.add(new JLabel("Telefone:"));
        JTextField telefoneField = new JTextField();
        frame.add(telefoneField);

        frame.add(new JLabel("Data de Nascimento (DD/MM/YYYY):"));
        JFormattedTextField dataNascimentoField = new JFormattedTextField(createFormatter("##/##/####"));
        dataNascimentoField.setColumns(10); // Define o tamanho do campo
        frame.add(dataNascimentoField);

        frame.add(new JLabel("Última Consulta (DD/MM/YYYY):"));
        JFormattedTextField ultimaConsultaField = new JFormattedTextField(createFormatter("##/##/####"));
        ultimaConsultaField.setColumns(10); // Define o tamanho do campo
        frame.add(ultimaConsultaField);

        frame.add(new JLabel("Observações:"));
        JTextArea observacoesArea = new JTextArea();
        observacoesArea.setLineWrap(true); // Habilita a quebra de linha automática
        observacoesArea.setWrapStyleWord(true); // Garante que a quebra de linha ocorre de forma correta nas palavras
        observacoesArea.setRows(5); // Define um número de linhas para a área de texto
        observacoesArea.setColumns(20); // Define o número de colunas visíveis
        frame.add(new JScrollPane(observacoesArea)); // Coloca a JTextArea dentro de um JScrollPane

        // Botão de salvar
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> {
            // Verifica se o nome está vazio
            String nome = nomeField.getText();
            if (nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "O campo 'Nome' não pode ficar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Sai da função se o nome estiver vazio
            }

            // Coleta os dados dos outros campos
            String email = emailField.getText();
            String telefone = telefoneField.getText();
            String dataNascimento = dataNascimentoField.getText();
            String ultimaConsulta = ultimaConsultaField.getText();
            String observacoes = observacoesArea.getText();

            // Cria uma string com todos os detalhes do cliente
            String detalhesCliente = String.join(",", nome, email, telefone, dataNascimento, ultimaConsulta, observacoes);
            Main.adicionarCliente(detalhesCliente); // Adiciona a string à lista de clientes

            // Limpa os campos após salvar
            nomeField.setText("");
            emailField.setText("");
            telefoneField.setText("");
            dataNascimentoField.setText("");
            ultimaConsultaField.setText("");
            observacoesArea.setText("");

            // Fecha a janela de novo cliente
            frame.dispose();
        });
        frame.add(salvarButton);

        // Botão de voltar
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            frame.dispose(); // Fecha a janela de novo cliente
        });
        frame.add(voltarButton);

        frame.setVisible(true);
    }

    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholderCharacter('_'); // Caracter que substitui a barra
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }
}