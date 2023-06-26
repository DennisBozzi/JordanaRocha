package jordanarocha.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jordanarocha.Tabelas.*;

public class VendasDAO {

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

    // Método para inserir dados nas tabelas de vendas
    public void inserirDadosVendas(int idVendedor, int idCliente, List<Integer> idProdutos, double valorTotal, String formaPagamento, double comissaoVenda) {
        String sqlVendas = "INSERT INTO vendas (id_vendedor, id_cliente, valorTotal, formaPagamento, comissaoVenda) "
                + "VALUES (?, ?, ?, ?, ?)";

        String sqlVendasProduto = "INSERT INTO vendas_produto (idVendas, idProduto) "
                + "VALUES (?, ?)";

        String sqlAtualizaProduto = "UPDATE produto SET statusProduto = 0 WHERE idProduto = ?";

        try (Connection connection = getConnection(); PreparedStatement statementVendas = connection.prepareStatement(sqlVendas, PreparedStatement.RETURN_GENERATED_KEYS); PreparedStatement statementVendasProduto = connection.prepareStatement(sqlVendasProduto); PreparedStatement statementAtualizaProduto = connection.prepareStatement(sqlAtualizaProduto)) {

            statementVendas.setInt(1, idVendedor);
            statementVendas.setInt(2, idCliente);
            statementVendas.setDouble(3, valorTotal);
            statementVendas.setString(4, formaPagamento);
            statementVendas.setDouble(5, comissaoVenda);

            statementVendas.executeUpdate();

            // Obtém o ID da venda gerado automaticamente
            int idVenda;
            try (ResultSet generatedKeys = statementVendas.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idVenda = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID da venda gerado automaticamente.");
                }
            }

            // Insere os produtos na tabela de associação e atualiza o status dos produtos
            for (int idProduto : idProdutos) {
                statementVendasProduto.setInt(1, idVenda);
                statementVendasProduto.setInt(2, idProduto);
                statementVendasProduto.executeUpdate();

                statementAtualizaProduto.setInt(1, idProduto);
                statementAtualizaProduto.executeUpdate();
            }

            System.out.println("Dados inseridos na tabela de vendas e vendas_produto e statusProduto atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método que retorna uma Venda completa - Com nome de Vendedor e Cliente
    public ObservableList<Venda> getVendas() {
        ObservableList<Venda> vendas = FXCollections.observableArrayList();
        String sql = "SELECT v.*, ve.nome_vendedor as nomeVendedor, c.nome_cliente as nomeCliente FROM vendas v "
                + "JOIN vendedor ve ON v.id_vendedor = ve.id_vendedor "
                + "JOIN clientes c ON v.id_cliente = c.id_cliente";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Venda venda = new Venda(
                        resultSet.getInt("idVendas"),
                        resultSet.getInt("id_vendedor"),
                        resultSet.getInt("id_cliente"),
                        resultSet.getDouble("valorTotal"),
                        resultSet.getString("formaPagamento"),
                        resultSet.getTimestamp("dataVenda"),
                        resultSet.getDouble("comissaoVenda"),
                        resultSet.getString("nomeVendedor"),
                        resultSet.getString("nomeCliente")
                );

                vendas.add(venda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendas;
    }

    // Método para ler e armazenar dados dos produtos vendidos
    public List<Produto> getProdutosVendidos(int idVenda) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT produto.* FROM vendas_produto JOIN produto ON vendas_produto.idProduto = produto.idProduto WHERE vendas_produto.idVendas = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idVenda);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(resultSet.getInt("idProduto"));
                produto.setNomeProduto(resultSet.getString("nomeProduto"));
                produto.setValorProduto(resultSet.getDouble("valorProduto"));
                produto.setAcessorioProduto(resultSet.getString("acessorioProduto"));
                produto.setLigaProduto(resultSet.getString("ligaProduto"));
                produto.setPedraProduto(resultSet.getString("pedraProduto"));
                produto.setTamanhoProduto(resultSet.getString("tamanhoProduto"));
                produto.setFotoProduto(resultSet.getBytes("fotoProduto"));

                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    // Método para deletar uma venda
    public void deleteVenda(int idVendas) {
        String sqlVendasProduto = "DELETE FROM vendas_produto WHERE idVendas = ?";
        String sqlVendas = "DELETE FROM vendas WHERE idVendas = ?";

        Connection connection = null;

        try {
            connection = getConnection();

            // Inicia uma transação
            connection.setAutoCommit(false);

            // Deleta os registros relacionados na tabela vendas_produto
            PreparedStatement preparedStatementVendasProduto = connection.prepareStatement(sqlVendasProduto);
            preparedStatementVendasProduto.setInt(1, idVendas);
            preparedStatementVendasProduto.executeUpdate();

            // Deleta a venda na tabela vendas
            PreparedStatement preparedStatementVendas = connection.prepareStatement(sqlVendas);
            preparedStatementVendas.setInt(1, idVendas);
            preparedStatementVendas.executeUpdate();

            // Commit da transação
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.println("A transação está sendo revertida");
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
    }

}
