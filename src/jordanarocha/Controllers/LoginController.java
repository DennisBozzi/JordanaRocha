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

    String loginAdmin = "admin";
    String senhaAdmin = "admin";

    //Instanciando VendedorDAO
    VendedorDAO vendedorDAO = new VendedorDAO();

    Vendedor vendedor;

    //Método de login
    @FXML
    private void Login() {

        vendedor = vendedorDAO.verificaCredenciais(loginField.getText(), passwordField.getText());
        
        //Conferindo login e senha
        if ((vendedor != null && vendedor.getStatusVendedor() == 1) || (loginField.getText().equals(loginAdmin) && (passwordField.getText().equals(senhaAdmin)))) {

            // Os dados estão corretos
            App.trocaTela("principal");
            labelErrado.setVisible(false);
            loginField.clear();
            passwordField.clear();
        } else {

            // Dados incorretos
            labelErrado.setVisible(true);
        }

    }

    @FXML
    private void CadastroVendedor(ActionEvent event) {
        App.trocaTela("cadastro");
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
