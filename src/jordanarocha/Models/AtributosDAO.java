package jordanarocha.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jordanarocha.Tabelas.Atributo;
import jordanarocha.Tabelas.Cliente;

public class AtributosDAO {

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
    public void addAtributo(Atributo atributo) {
        String sql = "INSERT INTO atributos (nome_atributo, valor_atributo) VALUES (?, ?)";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, atributo.getNome());
            preparedStatement.setString(2, atributo.getValor());

            preparedStatement.executeUpdate();

            System.out.println("O atributo " + atributo.getNome() + " foi cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Lendo os atributos cadastrados (READ)
    public ObservableList<Atributo> getAtributos() {
        ObservableList<Atributo> atributos = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_atributos, nome_atributo, valor_atributo FROM atributos")) {

            while (rs.next()) {
                int id = rs.getInt("id_atributos");
                String nome = rs.getString("nome_atributo");
                String valor = rs.getString("valor_atributo");
                atributos.add(new Atributo(id, nome, valor));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return atributos;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Lendo os acessórios
    public ObservableList<String> getAcesorio() {

        ObservableList<String> acessorios = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_atributos, nome_atributo, valor_atributo FROM atributos WHERE valor_atributo = 'Acessorio'")) {

            while (rs.next()) {

                int id = rs.getInt("id_atributos");
                String nome = rs.getString("nome_atributo");
                String valor = rs.getString("valor_atributo");
                acessorios.add(nome);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return acessorios;
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Lendo os Ligas
    public ObservableList<String> getLiga() {

        ObservableList<String> acessorios = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_atributos, nome_atributo, valor_atributo FROM atributos WHERE valor_atributo = 'Liga'")) {

            while (rs.next()) {

                int id = rs.getInt("id_atributos");
                String nome = rs.getString("nome_atributo");
                String valor = rs.getString("valor_atributo");
                acessorios.add(nome);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return acessorios;
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Lendo os Pedras
    public ObservableList<String> getPedra() {

        ObservableList<String> acessorios = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_atributos, nome_atributo, valor_atributo FROM atributos WHERE valor_atributo = 'Pedra'")) {

            while (rs.next()) {

                int id = rs.getInt("id_atributos");
                String nome = rs.getString("nome_atributo");
                String valor = rs.getString("valor_atributo");
                acessorios.add(nome);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return acessorios;
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Lendo os tamanhos
    public ObservableList<String> getTamanho() {

        ObservableList<String> acessorios = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_atributos, nome_atributo, valor_atributo FROM atributos WHERE valor_atributo = 'Tamanho'")) {

            while (rs.next()) {

                int id = rs.getInt("id_atributos");
                String nome = rs.getString("nome_atributo");
                String valor = rs.getString("valor_atributo");
                acessorios.add(nome);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return acessorios;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Excluindo os atribudos cadastrados (DELETE)
    public void excluirAtributo(int id) {
        String sql = "DELETE FROM atributos WHERE id_atributos = ?";
        try {
            PreparedStatement stmt = getConnection().prepareCall(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao excluir atributo: " + e.getMessage());
        }
    }
}
