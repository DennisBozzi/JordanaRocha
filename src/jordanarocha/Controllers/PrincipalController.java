package jordanarocha.Controllers;

import com.jfoenix.controls.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import jordanarocha.*;
import jordanarocha.Models.*;
import jordanarocha.Tabelas.*;
import org.apache.commons.lang3.text.WordUtils;

public class PrincipalController implements Initializable {

    //Mostrar versão do JAVAFX -- Só chamar no Initialize
    public void showJavaFXVersion() {
        String javaFXVersion = System.getProperty("javafx.version");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("JavaFX Version");
        alert.setHeaderText(null);
        alert.setContentText("JavaFX version: " + javaFXVersion);
        alert.showAndWait();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private ListView<Cliente> listaVendas;

    @FXML
    private TableColumn<Cliente, String> colunaNomeCliente;

    @FXML
    private TableColumn<Cliente, String> colunaCPFCliente;

    @FXML
    private TableColumn<Cliente, String> colunaEnderecoCliente;

    @FXML
    private TableColumn<Cliente, String> colunaEmailCliente;

    @FXML
    private TableColumn<Cliente, String> colunaCelularCliente;

    @FXML
    private Button anexarImagemButton;

    @FXML
    private Pane paneComeco;

    @FXML
    private Pane paneVender;

    @FXML
    private Pane paneCadastrarProduto;

    @FXML
    private Pane paneCadastrarCliente;

    @FXML
    private Pane paneCadastrarAtributo;

    @FXML
    private Pane paneRelatorios;

    @FXML
    private Pane paneSubMenu;

    @FXML
    private ImageView imagemTemporaria;

    @FXML
    private Pane markComeco;

    @FXML
    private Pane markVender;

    @FXML
    private Pane markRelatorios;

    @FXML
    private Pane markCadastrar;

    @FXML
    private Label cpfJaExiste;

    @FXML
    private Label cpfEInvalido;

    @FXML
    public JFXTextField autocompleteNome;

    @FXML
    public JFXTextField autocompleteCPF;

    @FXML
    private TableView<String> tabelaProdutos;

    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableView<String> tabelaVendas;

    @FXML
    private Label labelProdutos;

    @FXML
    private Label labelClientes;

    @FXML
    private Label labelVendas;

    @FXML
    private JFXTextField fieldNovoNomeProduto;

    @FXML
    private JFXTextField fieldNovoValor;

    @FXML
    private JFXComboBox<String> comboAcessorio;

    @FXML
    private JFXComboBox<String> comboLiga;

    @FXML
    private JFXComboBox<String> comboPedra;

    @FXML
    private JFXComboBox<String> comboTamanho;

    @FXML
    private JFXTextField cadastroClienteNome;

    @FXML
    private JFXTextField cadastroClienteCPF;

    @FXML
    private JFXTextField campoPesquisa;

    @FXML
    private JFXTextField cadastroClienteEndereco;

    @FXML
    private JFXTextField cadastroClienteCelular;

    @FXML
    private JFXTextField cadastroClienteEmail;

    @FXML
    private JFXTextArea cadastroClienteObservacao;

    @FXML
    private JFXDialogLayout dialogConfirmaExluir;

    @FXML
    private Pane paneFundoDaModal;

    @FXML
    private JFXDialog dialogExcluir;

    @FXML
    private StackPane stackPaneConfirmaExcluir;

    @FXML
    private StackPane stackAtualizaCliente;

    @FXML
    private Label headerConfirmaExcluir;

    @FXML
    private Label bodyConfirmaExcluir;

    @FXML
    private JFXTextField atualizaClienteNomeField;

    @FXML
    private JFXTextField atualizaClienteCPFField;

    @FXML
    private JFXTextField atualizaClienteCelularField;

    @FXML
    private JFXTextField atualizaClienteEmailField;

    @FXML
    private JFXTextField atualizaClienteEnderecoField;

    @FXML
    private JFXTextArea atualizaClienteObservacaoField;

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Instanciando formatador
    Formatacao formatacao = new Formatacao();

    //Instanciando a classe de trocar tela
    TrocaTela tela;

    //Intanciando a classe de trocar tabelas de consulta
    TrocaTabela tabela;

    //Instanciando a classe do CRUD
    JoalheriaDAO joalheriaDao = new JoalheriaDAO();

    //Instanciado o validador de CPF
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Métodos que irão nos botoes pára alternar os panes
    public void TrocaPaneComeco(ActionEvent event) {
        tela.TrocaPane("comeco");
    }

    public void TrocaPaneVender(ActionEvent event) {
        tela.TrocaPane("vender");
    }

    public void TrocaPaneAdicionar(ActionEvent event) {
        tela.TrocaPane("submenu");
    }

    public void TrocaPaneRelatorios(ActionEvent event) {
        tela.TrocaPane("relatorios");
    }

    public void TrocaPaneAdicionarCliente(ActionEvent event) {
        tela.TrocaPane("adicionarCliente");
    }

    public void TrocaPaneAdicionarProduto(ActionEvent event) {
        tela.TrocaPane("adicionarProduto");
    }

    public void TrocaPaneAdicionarAtributo(ActionEvent event) {
        tela.TrocaPane("adicionarAtributo");
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Métodos para alternar entre as tabelas de Consulta
    @FXML
    void mostraTabelaClientes(ActionEvent event) {
        tabela.trocaTabela("clientes");
        atualizaClientes();
    }

    @FXML
    void mostraTabelaProdutos(ActionEvent event) {
        tabela.trocaTabela("produtos");
    }

    @FXML
    void mostraTabelaRelatorios(ActionEvent event) {
        tabela.trocaTabela("vendas");
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método para sair da tela e voltar para o Login
    public void sairPrincipal() {
        App.trocaTela("login");
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Listas para popular os ComboBox
    List<String> acessorios = Arrays.asList("Colar", "Anel", "Pulseira");
    List<String> ligas = Arrays.asList("Ouro", "Prata", "Latão");
    List<String> pedras = Arrays.asList("Ametista", "Rubi", "Topázio", "Quartzo Rosa", "Citrino", "Esmeralda", "Pérola");
    List<String> tamanhos = Arrays.asList("17", "18", "19");

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método para adicionar imagem no cadastro de Produto
    @FXML
    void anexarImagem(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione a imagem do produto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(anexarImagemButton.getScene().getWindow());

        if (selectedFile != null) {
            Image imagem = new Image(selectedFile.toURI().toString());
            imagemTemporaria.setImage(imagem);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método para adicionarCliente
    //Nele possui duas conferencias(IF) - O primeiro confere se o CPF é válido
    //No segundo confere se o CPF já está cadastrado no banco de dados
    @FXML
    void adicionarCliente() {

        String nome = cadastroClienteNome.getText();
        String nomeFormatado = WordUtils.capitalizeFully(nome); //Formatando nome para a primeira letra da palavra ser maiuscula

        String cpf = cadastroClienteCPF.getText();

        if (Validador.isCPF(cpf)) { //Conferindo se é válido

            if (!joalheriaDao.cpfExists(cpf)) { //Conferindo se está cadastrado

                Cliente novoCliente = new Cliente(nomeFormatado, cadastroClienteCPF.getText(), cadastroClienteEmail.getText(), cadastroClienteCelular.getText(), cadastroClienteEndereco.getText(), cadastroClienteObservacao.getText());

                joalheriaDao.addCliente(novoCliente); //Método Create que adiciona um novo cliente ao BD

                cadastroClienteNome.clear();
                cadastroClienteCPF.clear();
                cadastroClienteEmail.clear();
                cadastroClienteCelular.clear();
                cadastroClienteEndereco.clear();
                cadastroClienteObservacao.clear();
                cadastroClienteCPF.unFocusColorProperty().setValue(Color.rgb(77, 77, 77));

                cpfJaExiste.setVisible(false);
                cpfEInvalido.setVisible(false);

                atualizaClientes();
            } else {
                cadastroClienteCPF.unFocusColorProperty().setValue(Color.rgb(255, 0, 0));

                cpfJaExiste.setVisible(true);
                cpfEInvalido.setVisible(false);
            }
        } else {
            cadastroClienteCPF.unFocusColorProperty().setValue(Color.rgb(255, 0, 0));

            cpfEInvalido.setVisible(true);
            cpfJaExiste.setVisible(false);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Esse método popula e Filtra os clientes da lista. 
    //Esse seria o READ do cRud
    public void atualizaClientes() {

        //Observable List que recebe o getClientes, ou seja recebe todos os clientes do DB
        ObservableList<Cliente> clientes = joalheriaDao.getClientes();

        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCPFCliente.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaEnderecoCliente.setCellValueFactory(new PropertyValueFactory<>("celular"));
        colunaEmailCliente.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaCelularCliente.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        // Cria uma nova lista observável para armazenar os resultados da pesquisa, só que agora no tipo FilteredList
        FilteredList<Cliente> clientesFiltrados = new FilteredList<>(clientes, p -> true);

        // Adiciona um listener ao campo de pesquisa
        campoPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            clientesFiltrados.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Verifica se o nome ou CPF do cliente contém o valor digitado na pesquisa
                String lowerCaseFilter = newValue.toLowerCase();
                if (cliente.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cliente.getCpf().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        // Define a lista de itens da tabela como a lista de clientes filtrados
        tabelaClientes.setItems(clientesFiltrados);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método que abre o painel de confirmação (modal)
    //Marcando "Sim" chama o método excluir, chamando "Nao" chama o método que esconde o pane
    @FXML
    void abrirConfirmExcluirCliente(ActionEvent event) {
        dialogConfirmaExluir.setHeading(headerConfirmaExcluir);
        dialogConfirmaExluir.setBody(bodyConfirmaExcluir);
        dialogConfirmaExluir.setAlignment(Pos.CENTER);

        dialogExcluir.setOverlayClose(false);
        dialogExcluir.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialogExcluir.setDialogContainer(stackPaneConfirmaExcluir);
        paneFundoDaModal.toFront();
        paneFundoDaModal.setVisible(true);
        stackPaneConfirmaExcluir.setLayoutX((paneFundoDaModal.getWidth() - stackPaneConfirmaExcluir.getWidth()) / 2);
        stackPaneConfirmaExcluir.setLayoutY((paneFundoDaModal.getHeight() - stackPaneConfirmaExcluir.getHeight()) / 2);
        stackPaneConfirmaExcluir.toFront();
        dialogExcluir.show();
    }

    //Método que fecha a modal, movendo painel pra tras e tirando sua visibilidade
    @FXML
    void fecharConfirmExcluirCliente() {
        paneFundoDaModal.toBack();
        paneFundoDaModal.setVisible(false);
        stackPaneConfirmaExcluir.toBack();
        dialogExcluir.close();
    }

    //Esse método deleta o cliente selecionado, passando o CPF como parametro. 
    //Esse seria o DELETE do cruD
    @FXML
    void excluirCliente(ActionEvent event) {
        //Código que identifica a linha selecionada na tabela
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        //Dando um get no CPF da linha selecionada
        String cpf = clienteSelecionado.getCpf();

        //Excluindo com o parametro do cpf sendo passado
        joalheriaDao.excluirCliente(cpf);

        //Método para atualizar a tabela após excluir
        atualizaClientes();

        //Método para fechar o dialog (modal)
        fecharConfirmExcluirCliente();
    }

    private void abrirPainelDeEditarCliente(Cliente cliente) {
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();
        stackAtualizaCliente.setVisible(true);
        stackAtualizaCliente.toFront();

        double posX = (paneFundoDaModal.getWidth() - stackAtualizaCliente.getWidth()) / 2;
        double posY = (paneFundoDaModal.getHeight() - stackAtualizaCliente.getHeight()) / 2;

        stackAtualizaCliente.translateXProperty().set(posX);
        stackAtualizaCliente.translateYProperty().set(posY);
    }

    @FXML
    private void clicarParaAbrirEditorDeCliente(MouseEvent event) {
        if (event.getClickCount() == 2) { // Verifica se é um clique duplo
            Cliente selectedClient = tabelaClientes.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                abrirPainelDeEditarCliente(selectedClient);
            }
        }
    }

    @FXML

    private void fecharTelaEditorDeCliente() {
        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();
        stackAtualizaCliente.setVisible(false);
        stackAtualizaCliente.toBack();

        double posX = (paneFundoDaModal.getWidth() - stackAtualizaCliente.getWidth()) / 2;
        double posY = (paneFundoDaModal.getHeight() - stackAtualizaCliente.getHeight()) / 2;

        stackAtualizaCliente.translateXProperty().set(posX);
        stackAtualizaCliente.translateYProperty().set(posY);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Initialize
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Iniciando TrocaTela
        tela = new TrocaTela(paneComeco, paneVender, paneCadastrarProduto, paneCadastrarCliente, paneCadastrarAtributo, paneRelatorios, paneSubMenu, markComeco, markVender, markRelatorios, markCadastrar);
        tela.TrocaPane("comeco"); //Sempre inicia na mesma tela

        //Iniciando Tabela
        tabela = new TrocaTabela(tabelaProdutos, tabelaClientes, tabelaVendas, labelClientes, labelProdutos, labelVendas);

        //Autocomplete Nome e CPF da tela de Venda
        formatacao.autocompleteCliente(autocompleteNome);
        formatacao.autocompleteCPF(autocompleteCPF);

        //Formata CPF da tela de Venda
        formatacao.formataCPFEnquantoDigita(autocompleteCPF);

        //Formata CPF e Telefone dos Field da tela de Cadastro
        formatacao.formataCPFEnquantoDigita(cadastroClienteCPF);
        formatacao.formataCelularEnquantoDigita(cadastroClienteCelular);

        //Populando as ComboBox
        comboAcessorio.getItems().addAll(acessorios);
        comboLiga.getItems().addAll(ligas);
        comboPedra.getItems().addAll(pedras);
        comboTamanho.getItems().addAll(tamanhos);

        //Comando que popula a lista de Clientes
        atualizaClientes();

    }

}
