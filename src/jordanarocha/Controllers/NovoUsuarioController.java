package jordanarocha.Controllers;

import com.jfoenix.controls.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import jordanarocha.*;

public class NovoUsuarioController implements Initializable {

    @FXML
    private JFXTextField loginField;

    @FXML
    private JFXPasswordField senhaField;

    @FXML
    private JFXPasswordField confirmaSenha;

    @FXML
    private JFXTextField nomeField;

    @FXML
    private JFXTextField cpfField;

    @FXML
    private JFXTextField rgField;

    @FXML
    private JFXTextField enderecoField;

    @FXML
    private JFXTextField telField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField comissaoField;

    @FXML
    private ImageView imagemTemporaria;

    @FXML
    private JFXButton anexarImagemButton;

    @FXML
    private JFXButton botaoExcluirCliente;

    @FXML
    private Pane paneListaVendedores;

    @FXML
    private Pane paneCadastroVendedores;

    @FXML
    private JFXButton botaoNovoUsuario;

    @FXML
    private JFXButton botaoMostraVendedores;

    //Instanciando Formatador
    Formatacao formatacao = new Formatacao();

    //Método para Alternar entre a Tabela de Vendedores Cadastrados e a Tela para Cadastro de Vendedores
    void mudaTela(String tela) {

        switch (tela) {

            case "cadastro":
                paneCadastroVendedores.setVisible(true);
                paneListaVendedores.setVisible(false);

                botaoMostraVendedores.setLayoutY(45);
                botaoNovoUsuario.setLayoutY(36);
                botaoNovoUsuario.setAlignment(Pos.CENTER);
                botaoMostraVendedores.setAlignment(Pos.TOP_CENTER);
                break;

            case "lista":
                paneCadastroVendedores.setVisible(false);
                paneListaVendedores.setVisible(true);

                botaoMostraVendedores.setLayoutY(36);
                botaoNovoUsuario.setLayoutY(45);
                botaoNovoUsuario.setAlignment(Pos.TOP_CENTER);
                botaoMostraVendedores.setAlignment(Pos.CENTER);
                break;

        }
    }

    //Mostra a Tela para Cadastrar
    public void mostraTelaCadastro() {
        mudaTela("cadastro");
    }

    //Mostra a Tela de Vendedores
    public void mostraTelaVendedores() {
        mudaTela("lista");
    }

    //Voltar para a tela de login para Cadastro
    public void voltaCadastro(ActionEvent event) {
        App.trocaTela("cadastro");
    }

    //Método para anexar imagem para Cadastro de Vendedor
    @FXML
    void anexarImagem(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione a imagem do produto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));

        File arquivoSelecionado = fileChooser.showOpenDialog(anexarImagemButton.getScene().getWindow());

        if (arquivoSelecionado != null) {
            Image imagem = new Image(arquivoSelecionado.toURI().toString());
            imagemTemporaria.setImage(imagem);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Chamando a Tela Cadastro logo no começo
        mudaTela("cadastro");

        //Métodos para Formatar, CPF, Celular e RG
        formatacao.formataCPFEnquantoDigita(cpfField);
        formatacao.formataCelularEnquantoDigita(telField);
        formatacao.formataRGEnquantoDigita(rgField);
        
    }
}
