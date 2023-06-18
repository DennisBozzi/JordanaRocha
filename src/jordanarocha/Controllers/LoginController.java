package jordanarocha.Controllers;

import com.jfoenix.controls.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import jordanarocha.App;
import jordanarocha.Models.VendedorDAO;
import jordanarocha.Tabelas.Vendedor;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField loginField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label labelErrado;

    //Instanciando VendedorDAO
    VendedorDAO vendedorDAO = new VendedorDAO();

    //Administrador
    Vendedor administrador = vendedorDAO.getVendedorByID(1);

    String loginAdmin = administrador.getUsuarioVendedor();
    String senhaAdmin = administrador.getSenhaVendedor();

    //Vendedor Logado
    private Vendedor vendedorLogado;

    //Método que atribui o Vendedor Logado
    public void setVendedorLogado(Vendedor vendedor) {
        this.vendedorLogado = vendedor;
        // Agora você pode usar vendedorLogado neste controlador
    }

    //Método de login
    @FXML
    private void Login() {

        vendedorLogado = vendedorDAO.verificaCredenciais(loginField.getText(), passwordField.getText());

        //Conferindo login e senha
        if ((vendedorLogado != null && vendedorLogado.getStatusVendedor() == 1)
                || (loginField.getText().equals(loginAdmin) && (passwordField.getText().equals(senhaAdmin)))) {

            // Os dados estão corretos
            labelErrado.setVisible(false);
            loginField.clear();
            passwordField.clear();

            vendedorLogado = vendedorDAO.getVendedorByID(vendedorLogado.getIdVendedor());

            System.out.println(vendedorLogado.toString());

            App.trocaTela("principal", vendedorLogado);

        } else {

            // Dados incorretos
            labelErrado.setVisible(true);
        }

    }

    @FXML
    private void CadastroVendedor(ActionEvent event) {
        App.trocaTela("cadastro", vendedorLogado);
    }

    public void LogarComEnter(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                Login();
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
