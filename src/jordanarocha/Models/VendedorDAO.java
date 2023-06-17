package jordanarocha.Models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jordanarocha.Tabelas.*;

public class VendedorDAO {

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

// Adicionando Vendedor (CREATE)
    public void addVendedor(Vendedor vendedor) {
        String sql = "INSERT INTO vendedor (comissao_vendedor, nome_vendedor, cpf_vendedor, rg_vendedor, endereco_vendedor, celular_vendedor, foto_vendedor, email_vendedor, usuario_vendedor, senha_vendedor, observacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, vendedor.getComissaoVendedor());
            preparedStatement.setString(2, vendedor.getNomeVendedor());
            preparedStatement.setString(3, vendedor.getCpfVendedor());
            preparedStatement.setString(4, vendedor.getRgVendedor());
            preparedStatement.setString(5, vendedor.getEnderecoVendedor());
            preparedStatement.setString(6, vendedor.getCelularVendedor());

            if (vendedor.getFotoVendedor() != null) {
                preparedStatement.setBytes(7, vendedor.getFotoVendedor());
            } else {
                preparedStatement.setNull(7, java.sql.Types.BLOB);
            }

            preparedStatement.setString(8, vendedor.getEmailVendedor());
            preparedStatement.setString(9, vendedor.getUsuarioVendedor());
            preparedStatement.setString(10, vendedor.getSenhaVendedor());
            preparedStatement.setString(11, vendedor.getobservacaoVendedor());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("O vendedor " + vendedor.getNomeVendedor() + " foi cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar o vendedor " + vendedor.getNomeVendedor() + ".");
            }

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
            String sql = "SELECT COUNT(*) FROM vendedor WHERE cpf_vendedor = ?";
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

    //Método para ler os Vendedores (READ)
    public ObservableList<Vendedor> getVendedores() {
        ObservableList<Vendedor> vendedores = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_vendedor, comissao_vendedor, nome_vendedor, cpf_vendedor, rg_vendedor, endereco_vendedor, celular_vendedor, foto_vendedor, email_vendedor, usuario_vendedor, data_demissao, data_admissao, senha_vendedor, observacao, status FROM vendedor")) {

            while (rs.next()) {
                int id = rs.getInt("id_vendedor");
                double comissao = rs.getDouble("comissao_vendedor");
                String nome = rs.getString("nome_vendedor");
                String cpf = rs.getString("cpf_vendedor");
                String rg = rs.getString("rg_vendedor");
                String endereco = rs.getString("endereco_vendedor");
                String celular = rs.getString("celular_vendedor");
                byte[] foto = rs.getBytes("foto_vendedor");
                String email = rs.getString("email_vendedor");
                String usuario = rs.getString("usuario_vendedor");
                Date demissao = rs.getDate("data_demissao");
                Timestamp admissao = rs.getTimestamp("data_admissao");
                String senha = rs.getString("senha_vendedor");
                String observacao = rs.getString("observacao");
                int status = rs.getInt("status");

                vendedores.add(new Vendedor(id, comissao, nome, cpf, rg, endereco, celular, foto, email, usuario, demissao, admissao, senha, observacao, status));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return vendedores;
    }

    //Excluindo os atribudos cadastrados (DELETE)
    public void excluirVendedor(int id) {
        String sql = "DELETE FROM vendedor WHERE id_vendedor = ?";
        try {
            PreparedStatement stmt = getConnection().prepareCall(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao excluir atributo: " + e.getMessage());
        }
    }

    //Método que pega o vendedor pelo ID
    public Vendedor getVendedorByID(int id) {
        Vendedor vendedor = null;
        String query = "SELECT * FROM vendedor WHERE id_vendedor = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double comissao = resultSet.getDouble("comissao_vendedor");
                String nome = resultSet.getString("nome_vendedor");
                String cpf = resultSet.getString("cpf_vendedor");
                String rg = resultSet.getString("rg_vendedor");
                String endereco = resultSet.getString("endereco_vendedor");
                String celular = resultSet.getString("celular_vendedor");
                byte[] foto = resultSet.getBytes("foto_vendedor");
                String email = resultSet.getString("email_vendedor");
                String usuario = resultSet.getString("usuario_vendedor");
                Date demissao = resultSet.getDate("data_demissao");
                Timestamp admissao = resultSet.getTimestamp("data_admissao");
                String senha = resultSet.getString("senha_vendedor");
                String observacao = resultSet.getString("observacao");
                int status = resultSet.getInt("status");

                vendedor = new Vendedor(id, comissao, nome, cpf, rg, endereco, celular, foto, email, usuario, demissao, admissao, senha, observacao, status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendedor;
    }

    // Método para atualizar Vendedor
    public void updateVendedor(Vendedor vendedor) {
        String sql = "UPDATE vendedor SET nome_vendedor = ?, cpf_vendedor = ?, rg_vendedor = ?, celular_vendedor = ?, email_vendedor = ?, endereco_vendedor = ?, observacao = ?, usuario_vendedor = ?, senha_vendedor = ?, comissao_vendedor = ? WHERE id_vendedor = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vendedor.getNomeVendedor());
            preparedStatement.setString(2, vendedor.getCpfVendedor());
            preparedStatement.setString(3, vendedor.getRgVendedor());
            preparedStatement.setString(4, vendedor.getCelularVendedor());
            preparedStatement.setString(5, vendedor.getEmailVendedor());
            preparedStatement.setString(6, vendedor.getEnderecoVendedor());
            preparedStatement.setString(7, vendedor.getobservacaoVendedor());
            preparedStatement.setString(8, vendedor.getUsuarioVendedor());
            preparedStatement.setString(9, vendedor.getSenhaVendedor());
            preparedStatement.setDouble(10, vendedor.getComissaoVendedor());
            preparedStatement.setInt(11, vendedor.getIdVendedor());

            preparedStatement.executeUpdate();

            System.out.println("O vendedor " + vendedor.getNomeVendedor() + " foi atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método para atualizar foto Vendedor
    public void updateImagemVendedor(Vendedor vendedor) {
        String sql = "UPDATE vendedor SET foto_vendedor = ? WHERE id_vendedor = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBytes(1, vendedor.getFotoVendedor());
            preparedStatement.setInt(2, vendedor.getIdVendedor());

            // Executar a atualização
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método para atualizar Status Vendedor
    public void updateStatusVendedor(Vendedor vendedor) {
        String sql = "UPDATE vendedor SET status = ?, data_demissao = ? WHERE id_vendedor = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, vendedor.getStatusVendedor());

            // Pegar a data e hora atual como Timestamp e inserir no preparedStatement
            Timestamp dataDemissao = new Timestamp(new java.util.Date().getTime());
            preparedStatement.setTimestamp(2, dataDemissao);

            preparedStatement.setInt(3, vendedor.getIdVendedor());

            // Executar a atualização
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Verificando Login e Senha
    public Vendedor verificaCredenciais(String usuario, String senha) {

        // Preparando a consulta SQL
        String sql = "SELECT * FROM vendedor WHERE usuario_vendedor = ? AND senha_vendedor = ? AND status = 1";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = getConnection().prepareStatement(sql);

            // Substituindo os parâmetros '?' pelos valores informados
            statement.setString(1, usuario);
            statement.setString(2, senha);

            // Executando a consulta
            rs = statement.executeQuery();

            // Verificando se a consulta retornou algum resultado
            if (rs.next()) {
                // Os dados estão corretos
                Vendedor vendedor = new Vendedor(rs.getInt("id_vendedor"), rs.getString("nome_vendedor"), rs.getString("cpf_vendedor"), rs.getString("usuario_vendedor"), rs.getString("senha_vendedor"), rs.getBytes("foto_vendedor"), rs.getInt("status"));
                return vendedor;
            }

        } catch (SQLException e) {
            // Trate a exceção aqui
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // Trate a exceção aqui
            }
        }
        return null;
    }
}
