package brincandocombanco1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class AgendaSwing extends JFrame {
    private ContatoDAO contatoDAO;
    private JTable tabelaContatos;
    private DefaultTableModel model;
    private JTextField txtNome, txtEmail, txtTelefone, txtPesquisar;

    public AgendaSwing() {
        contatoDAO = new ContatoDAO();
        setTitle("Agenda de Contatos");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Configura para iniciar em tela cheia
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        setLayout(new BorderLayout(10, 10));

        // Estilo e cores
        Color backgroundColor = new Color(245, 245, 245);
        Color buttonColor = new Color(62, 103, 148);
        Color headerColor = new Color(0, 123, 255);
        Color menuColor = new Color(30, 40, 50);
        Color menuButtonColor = new Color(50, 80, 100);

        // Menu superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(menuColor);

        JMenu menu = new JMenu("Menu");
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Arial", Font.BOLD, 14));

        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.setBackground(menuButtonColor);
        itemSair.setForeground(Color.WHITE);
        itemSair.addActionListener(e -> System.exit(0));
        menu.add(itemSair);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Criando o JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Aba de adicionar
        JPanel panelAdicionar = new JPanel();
        panelAdicionar.setLayout(new GridBagLayout()); // Usando GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        panelAdicionar.setBackground(backgroundColor);
        panelAdicionar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Adicionar Novo Contato", 0, 0, new Font("Arial", Font.BOLD, 14), Color.GRAY));

        // Definir as restrições para o layout GridBag
        gbc.insets = new Insets(5, 5, 5, 5); // Adicionando espaçamento entre os componentes

        // Nome
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panelAdicionar.add(new JLabel("Nome:"), gbc);  // Label "Nome"
        txtNome = new JTextField(20);
        panelAdicionar.add(txtNome, gbc);  // Campo de texto abaixo da label

        // Email
        panelAdicionar.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        panelAdicionar.add(txtEmail, gbc);

        // Telefone
        panelAdicionar.add(new JLabel("Telefone:"), gbc);
        txtTelefone = new JTextField(20);
        panelAdicionar.add(txtTelefone, gbc);

        // Botão para adicionar contato
        JButton btnAdicionar = new JButton("Adicionar Contato");
        btnAdicionar.setBackground(buttonColor);
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdicionar.setPreferredSize(new Dimension(150, 40)); // Ajustando o tamanho do botão
        gbc.gridwidth = GridBagConstraints.REMAINDER; // O botão ocupa toda a linha
        btnAdicionar.addActionListener(e -> adicionarContato());
        panelAdicionar.add(btnAdicionar, gbc);

        // Aba de listagem e pesquisa
        JPanel panelListar = new JPanel();
        panelListar.setLayout(new BorderLayout());
        panelListar.setBackground(backgroundColor);

        JPanel panelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPesquisa.setBackground(backgroundColor);
        txtPesquisar = new JTextField(20);

        // Adiciona o DocumentListener para a pesquisa em tempo real
        txtPesquisar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                pesquisarContato();
            }
            public void removeUpdate(DocumentEvent e) {
                pesquisarContato();
            }
            public void changedUpdate(DocumentEvent e) {
                pesquisarContato();
            }
        });

        panelPesquisa.add(new JLabel("Pesquisar por Nome:"));
        panelPesquisa.add(txtPesquisar);

        panelListar.add(panelPesquisa, BorderLayout.NORTH);

        // Tabela de contatos
        model = new DefaultTableModel(new String[]{"ID", "Nome", "Email", "Telefone"}, 0);
        tabelaContatos = new JTable(model);
        tabelaContatos.setFillsViewportHeight(true);
        tabelaContatos.setRowHeight(25);
        tabelaContatos.getTableHeader().setBackground(headerColor);
        tabelaContatos.getTableHeader().setForeground(Color.WHITE);
        tabelaContatos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(tabelaContatos);
        panelListar.add(scrollPane, BorderLayout.CENTER);

        // Botão de Deletar
        JButton btnDeletar = new JButton("Deletar Contato");
        btnDeletar.setBackground(buttonColor);
        btnDeletar.setForeground(Color.WHITE);
        btnDeletar.setFont(new Font("Arial", Font.BOLD, 12));
        btnDeletar.addActionListener(e -> deletarContato());
        panelListar.add(btnDeletar, BorderLayout.SOUTH);

        // Adicionando as abas no tabbedPane
        tabbedPane.addTab("Adicionar", panelAdicionar);
        tabbedPane.addTab("Listar/Deletar", panelListar);

        // Adicionando a tabbedPane ao frame
        add(tabbedPane, BorderLayout.CENTER);

        // Listar contatos ao iniciar
        listarContatos();
    }

    private void adicionarContato() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nome.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Erro: O nome só pode conter letras e espaços.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!telefone.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Erro: O telefone deve conter apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setEmail(email);
        contato.setTelefone(telefone);

        contatoDAO.adicionarContato(contato);
        JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!");
        limparCampos();
        listarContatos(); // Atualiza a lista após adicionar
    }

    private void listarContatos() {
        List<Contato> contatos = contatoDAO.listarContatos();
        model.setRowCount(0); // Limpa a tabela

        for (Contato contato : contatos) {
            model.addRow(new Object[]{contato.getId(), contato.getNome(), contato.getEmail(), contato.getTelefone()});
        }
    }

    private void pesquisarContato() {
        String termoPesquisa = txtPesquisar.getText().trim().toLowerCase();

        if (termoPesquisa.isEmpty()) {
            listarContatos(); // Se o campo de pesquisa está vazio, lista todos
            return;
        }

        List<Contato> contatos = contatoDAO.listarContatos();
        List<Contato> resultados = contatos.stream()
                .filter(c -> c.getNome().toLowerCase().contains(termoPesquisa))
                .collect(Collectors.toList());

        model.setRowCount(0); // Limpa a tabela
        for (Contato contato : resultados) {
            model.addRow(new Object[]{contato.getId(), contato.getNome(), contato.getEmail(), contato.getTelefone()});
        }
    }

    private void deletarContato() {
        int row = tabelaContatos.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um contato para deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        contatoDAO.deletarContato(id);
        JOptionPane.showMessageDialog(this, "Contato deletado com sucesso!");
        listarContatos();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtPesquisar.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AgendaSwing frame = new AgendaSwing();
            frame.setVisible(true);
        });
    }
}
