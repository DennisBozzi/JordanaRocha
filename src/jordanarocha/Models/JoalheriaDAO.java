package jordanarocha.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jordanarocha.Tabelas.Cliente;

public class JoalheriaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/jordanarocha";
    private static final String USER = "root";
    private static final String PASSWORD = "Dennozzo124!";

    //Método de conexão
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver JDBC do MySQL não encontrado");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //CRUD CLIENTE---------------------------------------CRUD CLIENTE---------------------------------------CRUD CLIENTE---------------------------------------CRUD CLIENTE---------------------------------------
    //Método para adicionar cliente
    public void addCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome_cliente, cpf_cliente, email_cliente, telefone_cliente, endereco_cliente, observacao_cliente) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getCpf());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getCelular());
            preparedStatement.setString(5, cliente.getEndereco());
            preparedStatement.setString(6, cliente.getObservacao());

            preparedStatement.executeUpdate();

            System.out.println("O cliente " + cliente.getNome() + " foi cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método que confere se o cpf já está cadastrado no banco de dados
    public boolean cpfExists(String cpf) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection(); // Método para obter a conexão com o banco de dados
            String sql = "SELECT COUNT(*) FROM clientes WHERE cpf_cliente = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar recursos em ordem reversa de abertura
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Método para ler os clientes (READ)
    public ObservableList<Cliente> getClientes() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_cliente, nome_cliente, cpf_cliente, email_cliente, telefone_cliente, endereco_cliente, observacao_cliente FROM clientes")) {

            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String nome = rs.getString("nome_cliente");
                String cpf = rs.getString("cpf_cliente");
                String email = rs.getString("email_cliente");
                String celular = rs.getString("telefone_cliente");
                String endereco = rs.getString("endereco_cliente");
                String observacao = rs.getString("observacao_cliente");
                clientes.add(new Cliente(id, nome, cpf, email, celular, endereco, observacao));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientes;
    }

    //Método que pega o cliente pelo CPF
    public Cliente getClienteByCPF(String cpf) {
        Cliente cliente = null;
        String query = "SELECT * FROM clientes WHERE cpf_cliente = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nome_cliente");
                String endereco = resultSet.getString("endereco_cliente");
                String celular = resultSet.getString("telefone_cliente");
                String email = resultSet.getString("email_cliente");
                String observacao = resultSet.getString("observacao_cliente");

                cliente = new Cliente(nome, cpf, email, celular, endereco, observacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    //Método para Excluir Cliente, passando o CPF como parametro.
    public void excluirCliente(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf_cliente = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    // Método para atualizar cliente
    public void updateCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nome_cliente = ?, email_cliente = ?, telefone_cliente = ?, endereco_cliente = ?, observacao_cliente = ? WHERE cpf_cliente = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getEmail());
            preparedStatement.setString(3, cliente.getCelular());
            preparedStatement.setString(4, cliente.getEndereco());
            preparedStatement.setString(5, cliente.getObservacao());
            preparedStatement.setString(6, cliente.getCpf());

            preparedStatement.executeUpdate();

            System.out.println("O cliente " + cliente.getNome() + " foi atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método para obter lista de CPFs
    public ObservableList<String> getCPFs() {
        ObservableList<String> cpfs = FXCollections.observableArrayList();
        String sql = "SELECT cpf_cliente FROM clientes";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cpfs.add(resultSet.getString("cpf_cliente"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cpfs;
    }

    //Método para obter lista de nomes
    public ObservableList<String> getNomes() {
        ObservableList<String> nomes = FXCollections.observableArrayList();
        String sql = "SELECT nome_cliente FROM clientes";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                nomes.add(resultSet.getString("nome_cliente"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomes;
    }

    // Método para obter o nome associado a um CPF
    public String getNomeByCPF(String cpf) {
        String nome = null;
        String sql = "SELECT nome_cliente FROM clientes WHERE cpf_cliente = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cpf);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nome = resultSet.getString("nome_cliente");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nome;
    }

    // Método para obter o CPF associado a um Nome
    public String getCPFByNome(String nome) {
        String cpf = null;
        String sql = "SELECT cpf_cliente FROM clientes WHERE nome_cliente = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cpf = resultSet.getString("cpf_cliente");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cpf;
    }

    // Método para obter o ID associado a um CPF
    public int getIdByCPF(String cpf) {
        int id = -1;
        String sql = "SELECT id_cliente FROM clientes WHERE cpf_cliente = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cpf);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id_cliente");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    //Método que confere se o cliente está associado a alguma venda
    public boolean clienteTemVendas(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vendas WHERE id_cliente = ?";

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                } else {
                    return false;
                }
            }
        }
    }

    //Método que retorna os ultimos 10 clientes
    public ObservableList<Cliente> getClientes10() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        String sql = "SELECT id_cliente, nome_cliente, cpf_cliente, email_cliente, telefone_cliente, endereco_cliente, observacao_cliente FROM clientes ORDER BY id_cliente DESC LIMIT 10";

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String nome = rs.getString("nome_cliente");
                String cpf = rs.getString("cpf_cliente");
                String email = rs.getString("email_cliente");
                String celular = rs.getString("telefone_cliente");
                String endereco = rs.getString("endereco_cliente");
                String observacao = rs.getString("observacao_cliente");
                clientes.add(new Cliente(id, nome, cpf, email, celular, endereco, observacao));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientes;
    }

}
