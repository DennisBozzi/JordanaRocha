package jordanarocha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    //Variáveis para a criação de cache das telas
    private static Stage stage;
    private static Scene loginPage;
    private static Scene principalPage;
    private static Scene cadastroLogin;
    private static Scene cadastroPage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //OBS Sempre depois do start criar o primaryStage
        stage = primaryStage;

        //Em cache a tela de Login
        Parent fxmlLoginPage = FXMLLoader.load(getClass().getResource("FXML/LoginFXML.fxml"));
        loginPage = new Scene(fxmlLoginPage);

        //Em cache a tela principal
        Parent fxmlPrincipal = FXMLLoader.load(getClass().getResource("FXML/PrincipalFXML.fxml"));
        principalPage = new Scene(fxmlPrincipal);

        //Em cache a tela principal
        Parent fxmlCadastro = FXMLLoader.load(getClass().getResource("FXML/CadastroFXML.fxml"));
        cadastroLogin = new Scene(fxmlCadastro);
        
        //Em cache a tela principal
        Parent fxmlNovoUsuario = FXMLLoader.load(getClass().getResource("FXML/NovoUsuarioFXML.fxml"));
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

    public static void trocaTela(String nomeTela) {

        switch (nomeTela) {
            case "login":

                //Chamando a tela que será exibida caso chame "login"
                stage.setScene(loginPage);
                //Centralizando a tela depois de chama-la
                stage.centerOnScreen();
                break;

            case "principal":

                stage.setScene(principalPage);
                stage.centerOnScreen();
                break;

            case "cadastro":

                stage.setScene(cadastroLogin);
                stage.centerOnScreen();
                break;

            case "novoUsuario":

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
