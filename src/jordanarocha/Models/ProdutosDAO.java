package jordanarocha.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jordanarocha.Tabelas.Produto;

public class ProdutosDAO {

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

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Adicionando Atributo ao Banco de Dados (CREATE)
    public void addProduto(Produto produto) {
        String sql = "INSERT INTO produto (nomeProduto, valorProduto, acessorioProduto, ligaProduto, pedraProduto, tamanhoProduto, fotoProduto) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, produto.getNomeProduto());
            preparedStatement.setDouble(2, produto.getValorProduto());
            preparedStatement.setString(3, produto.getAcessorioProduto());
            preparedStatement.setString(4, produto.getLigaProduto());
            preparedStatement.setString(5, produto.getPedraProduto());
            preparedStatement.setString(6, produto.getTamanhoProduto());

            if (produto.getFotoProduto() != null) {
                preparedStatement.setBytes(7, produto.getFotoProduto());
            } else {
                preparedStatement.setNull(7, java.sql.Types.BLOB);
            }

            preparedStatement.executeUpdate();

            System.out.println("O produto " + produto.getNomeProduto() + " foi cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Lendo os Produtos do Banco de Dados (READ)
    public ObservableList<Produto> getProdutos() {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT idProduto, nomeProduto, valorProduto, acessorioProduto, ligaProduto, pedraProduto, tamanhoProduto, fotoProduto FROM produto WHERE statusProduto = 1")) {

            while (rs.next()) {
                int id = rs.getInt("idProduto");
                String nome = rs.getString("nomeProduto");
                Double valor = rs.getDouble("valorProduto");
                String acessorio = rs.getString("acessorioProduto");
                String liga = rs.getString("ligaProduto");
                String pedra = rs.getString("pedraProduto");
                String tamanho = rs.getString("tamanhoProduto");
                byte[] foto = rs.getBytes("fotoProduto");

                produtos.add(new Produto(id, nome, valor, acessorio, liga, pedra, tamanho, foto));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return produtos;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Excluindo os produtos cadastrados (DELETE)
    public void excluirProduto(int id) {
        String sql = "DELETE FROM produto WHERE idProduto = ?";
        try {
            PreparedStatement stmt = getConnection().prepareCall(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Pegando os DADOS do PRODUTO pelo ID passado
    public Produto getProdutoById(int id) {
        Produto produto = null;
        String query = "SELECT * FROM produto WHERE idProduto = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nomeProduto");
                String valor = resultSet.getString("valorProduto");
                String acessorio = resultSet.getString("acessorioProduto");
                String liga = resultSet.getString("ligaProduto");
                String pedra = resultSet.getString("pedraProduto");
                String tamanho = resultSet.getString("tamanhoProduto");
                byte[] foto = resultSet.getBytes("fotoProduto");

                Double valorDouble = Double.parseDouble(valor);

                produto = new Produto(id, nome, valorDouble, acessorio, liga, pedra, tamanho, foto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    // Método para atualizar Produto
    public void updateProduto(Produto produto) {
        String sql = "UPDATE produto SET nomeProduto = ?, valorProduto = ?, acessorioProduto = ?, ligaProduto = ?, pedraProduto = ?, tamanhoProduto = ? WHERE idProduto = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, produto.getNomeProduto());
            preparedStatement.setDouble(2, produto.getValorProduto());
            preparedStatement.setString(3, produto.getAcessorioProduto());
            preparedStatement.setString(4, produto.getLigaProduto());
            preparedStatement.setString(5, produto.getPedraProduto());
            preparedStatement.setString(6, produto.getTamanhoProduto());

            preparedStatement.setInt(7, produto.getIdProduto());

            preparedStatement.executeUpdate();

            System.out.println("O produto " + produto.getNomeProduto() + " foi atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método para atualizar foto Produto
    public void updateImagemProduto(Produto produto) {
        String sql = "UPDATE produto SET fotoProduto = ? WHERE idProduto = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBytes(1, produto.getFotoProduto());
            preparedStatement.setInt(2, produto.getIdProduto());

            // Executar a atualização
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
