package com.gerenciamento.clientes2;

import javax.swing.*;
import java.awt.*;

public class JanelaDetalhesCliente {
    public JanelaDetalhesCliente(String cliente) {
        JFrame frame = new JFrame("Detalhes do Cliente");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        // Exibir detalhes do cliente
        // Aqui você deve extrair e mostrar as informações do cliente
        frame.add(new JLabel("Detalhes de: " + cliente));  // Placeholder
        // Adicione mais JLabel aqui com as informações necessárias

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> frame.dispose());  // Fecha a janela de detalhes
        frame.add(voltarButton);

        frame.setVisible(true);
    }
}
