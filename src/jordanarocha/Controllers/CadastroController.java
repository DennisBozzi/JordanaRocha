package jordanarocha.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import jordanarocha.App;
import jordanarocha.Tabelas.Vendedor;

public class CadastroController implements Initializable {

    @FXML
    private JFXTextField loginField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label labelErrado;

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

        //Conferindo login e senha
        if (loginField.getText().equals("admin") && passwordField.getText().equals("admin")) {
            App.trocaTela("novoUsuario", vendedorLogado);
            labelErrado.setVisible(false);
            loginField.clear();
            passwordField.clear();
        } //Se estiver errado mostra mensagem de erro
        else {
            labelErrado.setVisible(true);
        }

    }

    public void LogarComEnter(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                Login();
                break;
        }
    }

    public void voltaLogin(ActionEvent event) {
        App.trocaTela("login", vendedorLogado);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
