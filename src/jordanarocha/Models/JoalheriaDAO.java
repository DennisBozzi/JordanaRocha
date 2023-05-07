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

    //Método para ler os clientes - Nele ele coloca os clientes em uma observable list e depois coloco eles em uma tabela
    public ObservableList<Cliente> getClientes() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT nome_cliente, cpf_cliente, email_cliente, endereco_cliente, telefone_cliente, observacao_cliente FROM clientes")) {

            while (rs.next()) {
                String nome = rs.getString("nome_cliente");
                String cpf = rs.getString("cpf_cliente");
                String email = rs.getString("email_cliente");
                String endereco = rs.getString("endereco_cliente");
                String celular = rs.getString("telefone_cliente");
                String observacao = rs.getString("observacao_cliente");
                clientes.add(new Cliente(nome, cpf, email, endereco, celular, observacao));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientes;
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
}
