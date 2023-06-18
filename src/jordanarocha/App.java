package jordanarocha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jordanarocha.Controllers.*;
import jordanarocha.Tabelas.Vendedor;

public class App extends Application {

    //Vendedor Logado
    private Vendedor vendedorLogado;

    //Método que atribui o Vendedor Logado
    public void setVendedorLogado(Vendedor vendedor) {
        this.vendedorLogado = vendedor;
        // Agora você pode usar vendedorLogado neste controlador
    }

    //Variáveis para a criação de cache das telas
    private static Stage stage;
    private static Scene loginPage;
    private static Scene principalPage;
    private static Scene cadastroLogin;
    private static Scene cadastroPage;

    //Instanciando Controllers
    private static LoginController loginController;
    private static PrincipalController principalController;
    private static CadastroController cadastroController;
    private static NovoUsuarioController novoUsuarioController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //OBS Sempre depois do start criar o primaryStage
        stage = primaryStage;

        FXMLLoader loader;

        loader = new FXMLLoader(getClass().getResource("FXML/LoginFXML.fxml"));
        Parent fxmlLoginPage = loader.load();
        loginController = loader.getController();
        loginPage = new Scene(fxmlLoginPage);

        loader = new FXMLLoader(getClass().getResource("FXML/PrincipalFXML.fxml"));
        Parent fxmlPrincipal = loader.load();
        principalController = loader.getController();
        principalPage = new Scene(fxmlPrincipal);

        loader = new FXMLLoader(getClass().getResource("FXML/CadastroFXML.fxml"));
        Parent fxmlCadastro = loader.load();
        cadastroController = loader.getController();
        cadastroLogin = new Scene(fxmlCadastro);

        loader = new FXMLLoader(getClass().getResource("FXML/NovoUsuarioFXML.fxml"));
        Parent fxmlNovoUsuario = loader.load();
        novoUsuarioController = loader.getController();
        cadastroPage = new Scene(fxmlNovoUsuario);

        //Variável do ícone
        Image icone = new Image("jordanarocha/Imagens/letraJ.png");

        //Setando atributos para stage
        stage.setScene(loginPage);
        stage.setResizable(false);
        stage.setTitle("Jordana Rocha");
        stage.getIcons().add(icone);
        stage.centerOnScreen();
        stage.show();
    }

    public static void trocaTela(String nomeTela, Vendedor vendedorLogado) {
        switch (nomeTela) {
            case "login":
                loginController.setVendedorLogado(vendedorLogado);
                stage.setScene(loginPage);
                stage.centerOnScreen();
                break;

            case "principal":
                principalController.setVendedorLogado(vendedorLogado);
                stage.setScene(principalPage);
                stage.centerOnScreen();
                break;

            case "cadastro":
                cadastroController.setVendedorLogado(vendedorLogado);
                stage.setScene(cadastroLogin);
                stage.centerOnScreen();
                break;

            case "novoUsuario":
                novoUsuarioController.setVendedorLogado(vendedorLogado);
                stage.setScene(cadastroPage);
                stage.centerOnScreen();
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);

        System.out.println("JavaFX version: " + System.getProperty("javafx.version")); //Printa a versão do meu JavaFX

    }

}
