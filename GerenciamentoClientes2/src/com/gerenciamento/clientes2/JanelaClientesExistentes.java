package com.gerenciamento.clientes2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JanelaClientesExistentes {
    private List<String> clientes;
    private DefaultListModel<String> listModel;
    private JList<String> listaClientes;

    public JanelaClientesExistentes(List<String> clientes) {
        this.clientes = clientes;

        JFrame frame = new JFrame("Clientes Existentes");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Barra de pesquisa
        JTextField searchField = new JTextField();
        searchField.setToolTipText("Digite o nome do cliente para pesquisar");
        searchField.addCaretListener(e -> filtrarClientes(searchField.getText()));

        // Modelo e lista de clientes
        listModel = new DefaultListModel<>();
        atualizarListaClientes(clientes); // Inicializa a lista com todos os clientes
        listaClientes = new JList<>(listModel);
        listaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Painel de rolagem para a lista de clientes
        JScrollPane scrollPane = new JScrollPane(listaClientes);

        // Botões
        JButton detalhesButton = new JButton("Ver Detalhes");
        detalhesButton.addActionListener(e -> {
            String clienteSelecionado = listaClientes.getSelectedValue();
            if (clienteSelecionado != null) {
                mostrarDetalhesCliente(clienteSelecionado);
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> {
            String clienteSelecionado = listaClientes.getSelectedValue();
            if (clienteSelecionado != null) {
                // Fechar a janela de clientes antes de abrir a janela de edição
                frame.dispose();
                editarCliente(clienteSelecionado);
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> {
            String clienteSelecionado = listaClientes.getSelectedValue();
            if (clienteSelecionado != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Tem certeza de que deseja excluir este cliente?",
                        "Confirmação de Exclusão",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    excluirCliente(clienteSelecionado);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton exportarButton = new JButton("Exportar Dados");
        exportarButton.addActionListener(e -> {
            // Código para exportar os dados
            exportarDados(frame);
        });

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> frame.dispose());

        // Adiciona os botões no painel inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(detalhesButton);
        bottomPanel.add(editarButton);
        bottomPanel.add(excluirButton);
        bottomPanel.add(exportarButton);  // Adicionando o botão de exportação
        bottomPanel.add(voltarButton);

        frame.add(searchField, BorderLayout.NORTH); // Barra de pesquisa no topo
        frame.add(scrollPane, BorderLayout.CENTER); // Lista de clientes no centro
        frame.add(bottomPanel, BorderLayout.SOUTH); // Painel de botões na parte inferior

        frame.setVisible(true);
    }

    // Método para atualizar a lista de clientes exibidos
    private void atualizarListaClientes(List<String> clientes) {
        listModel.clear();
        for (String cliente : clientes) {
            String[] detalhes = cliente.split(",");
            listModel.addElement(detalhes[0]); // Adiciona o nome do cliente
        }
    }

    // Método para filtrar a lista de clientes com base na pesquisa
    private void filtrarClientes(String termoPesquisa) {
        if (termoPesquisa.isEmpty()) {
            atualizarListaClientes(clientes); // Se o campo de pesquisa estiver vazio, exibe todos os clientes
        } else {
            List<String> clientesFiltrados = clientes.stream()
                    .filter(cliente -> cliente.toLowerCase().contains(termoPesquisa.toLowerCase()))
                    .collect(Collectors.toList());
            atualizarListaClientes(clientesFiltrados);
        }
    }

    // Método para excluir o cliente
    private void excluirCliente(String clienteSelecionado) {
        // Remove o cliente selecionado da lista
        clientes.remove(clienteSelecionado);

        // Atualiza a lista de clientes na interface
        atualizarListaClientes(clientes);

        // Salva as alterações no arquivo após exclusão
        Main.salvarClientes();

        // Mostra uma mensagem de sucesso
        JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para mostrar os detalhes do cliente
    private void mostrarDetalhesCliente(String nomeCliente) {
        JFrame detalhesFrame = new JFrame("Detalhes do Cliente");
        detalhesFrame.setSize(800, 400);
        detalhesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detalhesFrame.setLayout(new GridLayout(0, 2));

        // Busca os detalhes completos do cliente na lista
        String clienteCompleto = null;
        for (String cliente : clientes) {
            String[] detalhes = cliente.split(",");
            if (detalhes[0].equals(nomeCliente)) {
                clienteCompleto = cliente;
                break;
            }
        }

        // Exibe os detalhes do cliente
        if (clienteCompleto != null) {
            String[] detalhes = clienteCompleto.split(",");
            detalhesFrame.add(new JLabel("Nome:"));
            detalhesFrame.add(new JLabel(detalhes[0]));
            detalhesFrame.add(new JLabel("Email:"));
            detalhesFrame.add(new JLabel(detalhes[1]));
            detalhesFrame.add(new JLabel("Telefone:"));
            detalhesFrame.add(new JLabel(detalhes[2]));
            detalhesFrame.add(new JLabel("Data de Nascimento:"));
            detalhesFrame.add(new JLabel(detalhes[3]));
            detalhesFrame.add(new JLabel("Última Consulta:"));
            detalhesFrame.add(new JLabel(detalhes[4]));
            detalhesFrame.add(new JLabel("Observações:"));
            detalhesFrame.add(new JLabel(detalhes[5]));
        }

        detalhesFrame.setVisible(true);
    }

    // Método para editar as informações do cliente
    private void editarCliente(String nomeCliente) {
        final String[] clienteOriginal = new String[1]; // Agora é um array para poder modificar dentro do escopo final
        for (String cliente : clientes) {
            String[] detalhes = cliente.split(",");
            if (detalhes[0].equals(nomeCliente)) {
                clienteOriginal[0] = cliente;  // Atribuindo ao array
                break;
            }
        }

        if (clienteOriginal[0] != null) {
            String[] detalhes = clienteOriginal[0].split(",");

            // Janela de edição com o layout do novo cliente
            JFrame frame = new JFrame("Editar Cliente");
            frame.setSize(1200, 800);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridLayout(9, 2));

            frame.add(new JLabel("Nome:"));
            JTextField nomeField = new JTextField(detalhes[0]);
            frame.add(nomeField);

            frame.add(new JLabel("Email:"));
            JTextField emailField = new JTextField(detalhes[1]);
            frame.add(emailField);

            frame.add(new JLabel("Telefone:"));
            JTextField telefoneField = new JTextField(detalhes[2]);
            frame.add(telefoneField);

            frame.add(new JLabel("Data de Nascimento (DD/MM/YYYY):"));
            JFormattedTextField dataNascimentoField = new JFormattedTextField(detalhes[3]);
            dataNascimentoField.setColumns(10);
            frame.add(dataNascimentoField);

            frame.add(new JLabel("Última Consulta (DD/MM/YYYY):"));
            JFormattedTextField ultimaConsultaField = new JFormattedTextField(detalhes[4]);
            ultimaConsultaField.setColumns(10);
            frame.add(ultimaConsultaField);

            frame.add(new JLabel("Observações:"));
            JTextArea observacoesArea = new JTextArea(detalhes[5]);
            observacoesArea.setLineWrap(true);
            observacoesArea.setWrapStyleWord(true);
            observacoesArea.setRows(5);
            observacoesArea.setColumns(20);
            frame.add(new JScrollPane(observacoesArea));

            JButton salvarButton = new JButton("Salvar");
            salvarButton.addActionListener(e -> {
                // Atualiza as informações do cliente
                String nome = nomeField.getText();
                String email = emailField.getText();
                String telefone = telefoneField.getText();
                String dataNascimento = dataNascimentoField.getText();
                String ultimaConsulta = ultimaConsultaField.getText();
                String observacoes = observacoesArea.getText();

                String clienteEditado = String.join(",", nome, email, telefone, dataNascimento, ultimaConsulta, observacoes);
                clientes.set(clientes.indexOf(clienteOriginal[0]), clienteEditado);

                Main.salvarClientes();
                JOptionPane.showMessageDialog(null, "Cliente editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                frame.dispose(); // Fecha a janela de edição
                atualizarListaClientes(clientes); // Atualiza a lista
            });

            frame.add(salvarButton);

            JButton voltarButton = new JButton("Voltar");
            voltarButton.addActionListener(e -> frame.dispose());
            frame.add(voltarButton);

            frame.setVisible(true);
        }
    }

    // Método para exportar os dados
    private void exportarDados(JFrame frame) {
        // Usando JFileChooser para salvar os dados em um arquivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha o local para salvar os dados");

        // Defina um filtro de extensão para o arquivo (opcional)
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Escreve os dados no arquivo CSV
                for (String cliente : clientes) {
                    writer.write(cliente + "\n");
                }
                JOptionPane.showMessageDialog(frame, "Dados exportados com sucesso!", "Exportação", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao exportar os dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
