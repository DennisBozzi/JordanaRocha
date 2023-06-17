package jordanarocha.Controllers;

import com.jfoenix.controls.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import javax.imageio.ImageIO;
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
    public JFXTextField autocompleteNome, autocompleteCPF, fieldNomeProduto, fieldValorProduto, cadastroClienteNome, cadastroClienteCPF, campoPesquisa, cadastroClienteEndereco, cadastroClienteCelular, cadastroClienteEmail,
            atualizaClienteNomeField, atualizaClienteCPFField, atualizaClienteCelularField, atualizaClienteEmailField, atualizaClienteEnderecoField, nomeAtributoField, nomeAtualizaProduto, valorAtualizaProduto;

    @FXML
    private Pane paneComeco, modalConfirmaAlterarCliente, paneVender, paneCadastrarProduto, paneCadastrarCliente, paneCadastrarAtributo, paneRelatorios, paneSubMenu, markComeco, markVender, markRelatorios,
            markCadastrar, paneFundoDaModal, modalConfirmaApagarProduto, modalEditaProduto;

    @FXML
    private Label cpfJaExiste, cpfEInvalido, labelProdutos, labelClientes, labelVendas, headerConfirmaExcluir, labelConfirmaAtualizado, labelMensagem, bodyConfirmaExcluir, labelAtributo, labelProduto, labelAtualizaProduto;

    @FXML
    private TableColumn<Cliente, String> colunaNomeCliente, colunaCPFCliente, colunaEnderecoCliente, colunaEmailCliente, colunaCelularCliente;

    @FXML
    private TableColumn<Produto, String> colunaIdProdutos, colunaJoiaProdutos, colunaLigaProdutos, colunaAcessorioProdutos;

    @FXML
    private TableColumn<Produto, Double> colunaValorProdutos;

    @FXML
    private TableColumn<Produto, byte[]> colunaFotoProdutos;

    @FXML
    private JFXComboBox<String> comboAcessorio, comboLiga, comboPedra, comboTamanho, comboAtualizaAcessorio, comboAtualizaPedra, comboAtualizaLiga, comboAtualizaTamanho;

    @FXML
    private JFXTextArea cadastroClienteObservacao, atualizaClienteObservacaoField;

    @FXML
    private StackPane stackPaneConfirmaExcluir, stackAtualizaCliente;

    @FXML
    private JFXRadioButton btnAcessorio, btnLiga, btnPedra, btnTamanho;

    @FXML
    private JFXButton botaoExcluirCliente, botaoExcluirProduto, botaoExcluirVenda, botaoAtualizaImagemProduto;

    @FXML
    private Button anexarImagemButton;

    @FXML
    private ImageView imagemTemporaria, imagemAtualizaProduto;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableView<String> tabelaVendas;

    @FXML
    private JFXDialogLayout dialogConfirmaExluir;

    @FXML
    private JFXDialog dialogExcluir;

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Instanciando formatador
    Formatacao formatacao = new Formatacao();

    //Instanciando a classe de trocar tela
    TrocaTela tela;

    //Intanciando a classe de trocar tabelas de consulta
    TrocaTabela tabela;

    //Instanciando a classe do CRUD
    JoalheriaDAO joalheriaDao = new JoalheriaDAO();
    AtributosDAO atributoDAO = new AtributosDAO();
    CRUDAtributo CRUDatributos = new CRUDAtributo();
    ProdutosDAO produtosDAO = new ProdutosDAO();

    private Cliente cliente;
    private Produto produtoAtualiza;

    //Variáveis para conferir se foram alteradas
    private String nomeTemp, cpfTemp, enderecoTemp, emailTemp, celularTemp, observacaoTemp, nomeProdutoTemp, valorProdutoTemp, acessorioProdutoTemp, pedraProdutoTemp, ligaProdutoTemp, tamanhoProdutoTemp;
    public Cliente clienteSelecionado;
    public Cliente clienteAtualizado;

    public Produto produtoSelecionado;
    public Produto produtoAtualizado;

    //ObservableLists da comboBox
    ObservableList<String> acessorios;
    ObservableList<String> ligas;
    ObservableList<String> tamanhos;
    ObservableList<String> pedras;

    //Variável para converter a imagem para byte
    private byte[] imagemBytes = null;

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
        mostraTabelaProdutos();
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
    void mostraTabelaClientes() {
        tabela.trocaTabela("clientes");

        botaoExcluirProduto.setVisible(false);
        botaoExcluirCliente.setVisible(true);
        botaoExcluirVenda.setVisible(false);
        atualizaClientes();
    }

    @FXML
    void mostraTabelaProdutos() {
        tabela.trocaTabela("produtos");

        botaoExcluirProduto.setVisible(true);
        botaoExcluirCliente.setVisible(false);
        botaoExcluirVenda.setVisible(false);
        atualizaProdutos();
    }

    @FXML
    void mostraTabelaRelatorios() {
        tabela.trocaTabela("vendas");

        botaoExcluirProduto.setVisible(false);
        botaoExcluirCliente.setVisible(false);
        botaoExcluirVenda.setVisible(true);

    }

    //Método para sair da tela e voltar para o Login
    public void sairPrincipal() {
        App.trocaTela("login");
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Listas para popular os ComboBox
    public void popularComboBox() {
        acessorios = atributoDAO.getAcesorio();
        ligas = atributoDAO.getLiga();
        tamanhos = atributoDAO.getTamanho();
        pedras = atributoDAO.getPedra();

        FXCollections.sort(ligas);
        FXCollections.sort(tamanhos);
        FXCollections.sort(pedras);
        FXCollections.sort(acessorios);

        comboAcessorio.getItems().clear();
        comboLiga.getItems().clear();
        comboPedra.getItems().clear();
        comboTamanho.getItems().clear();

        //Populando as ComboBox
        comboAcessorio.getItems().addAll(acessorios);
        comboLiga.getItems().addAll(ligas);
        comboPedra.getItems().addAll(pedras);
        comboTamanho.getItems().addAll(tamanhos);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método para adicionar imagem no cadastro de Produto
    @FXML
    void anexarImagem(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione a imagem do produto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));

        File arquivoSelecionado = fileChooser.showOpenDialog(anexarImagemButton.getScene().getWindow());

        if (arquivoSelecionado != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(arquivoSelecionado);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteOutput);
                imagemBytes = byteOutput.toByteArray();

                Image imagem = new Image(arquivoSelecionado.toURI().toString());
                imagemTemporaria.setImage(imagem);
            } catch (IOException e) {
                System.out.println("Erro ao converter imagem: " + e.getMessage());
            }
        }

        //Centralizar botao da imagem após imagem ser escolhida
        anexarImagemButton.layoutXProperty().bind(paneCadastrarProduto.widthProperty().subtract(anexarImagemButton.widthProperty()).divide(2));
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método para adicionarAtributo
    public void adicionarAtributo(ActionEvent event) {
        Atributo newAtributo = CRUDatributos.getAtributoFromInput(nomeAtributoField, btnAcessorio, btnLiga, btnPedra, btnTamanho);
        atributoDAO.addAtributo(newAtributo);
        nomeAtributoField.clear();
        btnAcessorio.setSelected(true);

        popularComboBox();

        fadeLabelAtributo();
    }

    //Fade para a label que confirma que um atributo foi cadastrado
    public void fadeLabelAtributo() {
        labelAtributo.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(4), labelAtributo);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(event -> labelAtributo.setVisible(false));
        fade.play();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método para adicionarProduto(CREATE)
    public void adicionarProduto() {

        String nome = fieldNomeProduto.getText();

        double valor = 0;

        if (!fieldValorProduto.getText().isEmpty()) {
            valor = Double.parseDouble(fieldValorProduto.getText());
        }

        String acessorio = comboAcessorio.getValue();
        String liga = comboLiga.getValue();
        String pedra = comboPedra.getValue();
        String tamanho = comboTamanho.getValue();
        byte[] imagem = imagemBytes;

        Produto produto = new Produto(nome, valor, acessorio, liga, pedra, tamanho, imagem);

        produtosDAO.addProduto(produto);
    }

    //Método para Conferir os Dados do Produto antes de adicioná-lo(CREATE)
    public void confereDadosProduto() {

        String nome = fieldNomeProduto.getText();

        double valor = 0;

        if (!fieldValorProduto.getText().equals("")) {
            valor = Double.parseDouble(fieldValorProduto.getText());
        }

        String acessorio = comboAcessorio.getValue();
        String liga = comboLiga.getValue();
        String pedra = comboPedra.getValue();
        String tamanho = comboTamanho.getValue();

        if (!nome.equals("") || (valor > 0 && !fieldValorProduto.getText().isEmpty())) { //Confere se os campos nome e valor estão preenchidos(CREATE)
            if (acessorio != null || liga != null || pedra != null || tamanho != null) { //Confere se o produto tem no mínimo um atributo vinculado
                adicionarProduto();
                labelProduto.setStyle("text-fill: #095742;");
                labelProduto.setText("Novo produto cadastrado!");

                fieldNomeProduto.clear();
                fieldValorProduto.clear();

                comboLiga.setValue("");
                comboAcessorio.setValue("");
                comboPedra.setValue("");
                comboTamanho.setValue("");

                imagemTemporaria.setImage(new Image("jordanarocha/Imagens/default-image.jpg"));

            } else {
                labelProduto.setStyle("-fx-text-fill: red;");
                labelProduto.setText("Selecione no mínimo um atributo!");
            }
        } else {
            labelProduto.setStyle("-fx-text-fill: red;");
            labelProduto.setText("Existem campos não preenchidos!");
        }

        //Centraliza mensagem
        labelProduto.layout();
        labelProduto.layoutXProperty().bind(paneCadastrarProduto.widthProperty().subtract(labelProduto.widthProperty()).divide(2));
        labelProduto.layoutYProperty().bind(paneCadastrarProduto.heightProperty().subtract(71));
        fadeLabelProduto();
    }

    //Fade para a label que confirma que um atributo foi cadastrado(CREATE)
    public void fadeLabelProduto() {
        labelProduto.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(4), labelProduto);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(event -> labelAtributo.setVisible(false));
        fade.play();
    }

    //Método para ler os produtos e adicionar na lista (READ)
    @FXML
    public void atualizaProdutos() {
        //Observable List que recebe o getProdutos, ou seja, recebe todos os produtos do DB
        ObservableList<Produto> produtos = produtosDAO.getProdutos();

        colunaIdProdutos.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colunaJoiaProdutos.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaLigaProdutos.setCellValueFactory(new PropertyValueFactory<>("ligaProduto"));
        colunaAcessorioProdutos.setCellValueFactory(new PropertyValueFactory<>("acessorioProduto"));
        colunaValorProdutos.setCellValueFactory(new PropertyValueFactory<>("valorProduto"));

        //Formatando a coluna de valor para aparecer R$#.###.###,##
        colunaValorProdutos.setCellFactory(new FormattedCurrencyCellFactory());

        // Coluna de imagem
        colunaFotoProdutos.setCellValueFactory(new PropertyValueFactory<>("fotoProduto"));

        //"Formatando" a coluna de imagem para ela aparecer em uma imageView
        colunaFotoProdutos.setCellFactory(param -> new ImageTableCell());

        // Cria uma nova lista observável para armazenar os resultados da pesquisa, só que agora no tipo FilteredList
        FilteredList<Produto> produtosFiltrados = new FilteredList<>(produtos, p -> true);

        // Adiciona um listener ao campo de pesquisa
        campoPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            produtosFiltrados.setPredicate(produto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Verifica se o nome ou CPF do cliente contém o valor digitado na pesquisa
                String lowerCaseFilter = newValue.toLowerCase();
                if (produto.getNomeProduto().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (produto.getTamanhoProduto().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        // Define a lista de itens da tabela como a lista de clientes filtrados
        tabelaProdutos.setItems(produtosFiltrados);

        // Define a lista de itens da tabela como a lista de produtos
        tabelaProdutos.setItems(produtosFiltrados);
    }

    //(DELETE)
    @FXML
    public void abrirModalApagarProduto() {
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();

        modalConfirmaApagarProduto.setVisible(true);
        modalConfirmaApagarProduto.toFront();
    }

    //(DELETE)
    @FXML
    public void fecharModalApagarProduto() {
        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();

        modalConfirmaApagarProduto.setVisible(false);
        modalConfirmaApagarProduto.toBack();
    }

    //Método para apagar os produtos (DELETE)
    @FXML
    public void apagarProduto() {
        produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        int id = produtoSelecionado.getIdProduto();

        produtosDAO.excluirProduto(id);

        atualizaProdutos();
        fecharModalApagarProduto();
    }

    //Método para editar os produtos(UPDATE)
    @FXML
    private void clicarParaAbrirEditorDeProduto(MouseEvent event) {
        if (event.getClickCount() == 2) { // Verifica se é um clique duplo
            Produto selected = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (selected != null) {
                produtoSelecionado = selected;
                abrirModalEditarProduto(produtoSelecionado);
            }
        }
    }

    //Método que abre a modal - Como parametro passamos o produto selecionado! (UPDATE)
    private void abrirModalEditarProduto(Produto produto) {

        // Busque o cliente atualizado do banco de dados
        Produto updateProduto = produtosDAO.getProdutoById(produto.getIdProduto());

        // Se o cliente atualizado for encontrado, atualize os campos de texto
        if (updateProduto != null) {
            produto = updateProduto;
        }

        // Convertendo a imagem do banco de Byte para Image para poder colocar no imageView
        byte[] fotoVendedor = produto.getFotoProduto();
        Image imagemProduto = null;
        if (fotoVendedor != null) {
            imagemProduto = new Image(new ByteArrayInputStream(fotoVendedor));
        }

        //Abrindo a modal e colocando pane cinza no fundo
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();
        modalEditaProduto.setVisible(true);
        modalEditaProduto.toFront();

        //Variáveis do produto selecionado
        String atualizaNome = produto.getNomeProduto();
        String atualizaValor = Double.toString(produto.getValorProduto());
        String atualizaAcessorio = produto.getAcessorioProduto();
        String atualizaPedra = produto.getPedraProduto();
        String atualizaLiga = produto.getLigaProduto();
        String atualizaTamanho = produto.getTamanhoProduto();

        comboAtualizaProduto();

        //Populando TEXTFIELD e IMAGEM com os valores do PRODUTO selecionado
        nomeAtualizaProduto.setText(atualizaNome);
        valorAtualizaProduto.setText(atualizaValor);
        comboAtualizaAcessorio.setValue(atualizaAcessorio);
        comboAtualizaPedra.setValue(atualizaPedra);
        comboAtualizaLiga.setValue(atualizaLiga);
        comboAtualizaTamanho.setValue(atualizaTamanho);
        imagemAtualizaProduto.setImage(imagemProduto);

        //Passando atributos temporários para comparações futuras
        nomeProdutoTemp = produto.getNomeProduto();
        valorProdutoTemp = Double.toString(produto.getValorProduto());
        acessorioProdutoTemp = produto.getAcessorioProduto();
        tamanhoProdutoTemp = produto.getTamanhoProduto();
        ligaProdutoTemp = produto.getLigaProduto();
        pedraProdutoTemp = produto.getPedraProduto();

        // Centraliza a imagem do cliente vinda do Banco de Dados.
        botaoAtualizaImagemProduto.layoutXProperty().bind(modalEditaProduto.widthProperty().subtract(botaoAtualizaImagemProduto.widthProperty()).divide(2));
    }

    //(UPDATE)
    public void fecharModalEditarProduto() {
        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();

        modalEditaProduto.setVisible(false);
        modalEditaProduto.toBack();
    }

    //(UPDATE)
    public void abrirConfirmacaoEditarProduto() {

        int id = produtoSelecionado.getIdProduto();

        String nome = nomeAtualizaProduto.getText();

        Double valor = 0.0;

        if (!valorAtualizaProduto.getText().isEmpty()) {
            valor = Double.parseDouble(valorAtualizaProduto.getText());
        }

        String acessorio = comboAtualizaAcessorio.getValue();
        String pedra = comboAtualizaPedra.getValue();
        String liga = comboAtualizaLiga.getValue();
        String tamanho = comboAtualizaTamanho.getValue();

        if (!nome.isEmpty() && !valorAtualizaProduto.getText().isEmpty()) {
            if (!acessorio.isEmpty() || !pedra.isEmpty() || !liga.isEmpty() || !tamanho.isEmpty()) {

                produtoAtualiza = new Produto(id, nome, valor, acessorio, liga, pedra, tamanho);

                System.out.println(produtoAtualiza.toString());

                abreConfirmacaoEditarProduto();
                atualizaProdutos();
                fecharModalEditarProduto();
                labelAtualizaProduto.setStyle("-fx-text-fill: #095742;");

            } else {

                labelAtualizaProduto.setText("Preencha no mínimo um seletor.");
                labelAtualizaProduto.setStyle("-fx-text-fill: red;");

            }
        } else {

            labelAtualizaProduto.setText("O campo nome ou valor está nulo.");
            labelAtualizaProduto.setStyle("-fx-text-fill: red;");

            nomeAtualizaProduto.setUnFocusColor(Color.RED);
            valorAtualizaProduto.setUnFocusColor(Color.RED);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
                nomeAtualizaProduto.setUnFocusColor(Color.WHITE);
                valorAtualizaProduto.setUnFocusColor(Color.WHITE);
            }));
            timeline.play();
        }

        //Depois de receber a mensagem, ela é pintada de vermelho - Junto com o textField de CPF - Durante 2.5 segundos
        labelAtualizaProduto.setVisible(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
            labelAtualizaProduto.setVisible(false);
        }));

        timeline.play();

        //Centraliza a mensagem na tela
        labelAtualizaProduto.layout();
        labelAtualizaProduto.layoutXProperty().bind(modalEditaProduto.widthProperty().subtract(labelAtualizaProduto.widthProperty()).divide(2));
    }

    public void abreConfirmacaoEditarProduto() {
        System.out.println("Tudo certo!");
        produtosDAO.updateProduto(produtoAtualiza);
    }

    //Atualiza ComboBox do Painel de Atualiza Produto (UPDATE)
    public void comboAtualizaProduto() {
        acessorios = atributoDAO.getAcesorio();
        ligas = atributoDAO.getLiga();
        tamanhos = atributoDAO.getTamanho();
        pedras = atributoDAO.getPedra();

        FXCollections.sort(ligas);
        FXCollections.sort(tamanhos);
        FXCollections.sort(pedras);
        FXCollections.sort(acessorios);

        comboAtualizaAcessorio.getItems().clear();
        comboAtualizaLiga.getItems().clear();
        comboAtualizaPedra.getItems().clear();
        comboAtualizaTamanho.getItems().clear();

        //Populando as ComboBox
        comboAtualizaAcessorio.getItems().addAll(acessorios);
        comboAtualizaLiga.getItems().addAll(ligas);
        comboAtualizaPedra.getItems().addAll(pedras);
        comboAtualizaTamanho.getItems().addAll(tamanhos);
    }

    //Anexar IMAGEM na modal de atualizar PRODUTO
    @FXML
    void anexarImagemProdutoAtualizado(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione a imagem do produto");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));

        File arquivoSelecionado = fileChooser.showOpenDialog(anexarImagemButton.getScene().getWindow());

        if (arquivoSelecionado != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(arquivoSelecionado);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteOutput);
                imagemBytes = byteOutput.toByteArray();

                Image imagem = new Image(arquivoSelecionado.toURI().toString());
                imagemAtualizaProduto.setImage(imagem);

                Produto atualizaFoto = new Produto(produtoSelecionado.getIdProduto(), imagemBytes);

                produtosDAO.updateImagemProduto(atualizaFoto);

                //Centralizar botao da imagem após imagem ser escolhida
                botaoAtualizaImagemProduto.layoutXProperty().bind(modalEditaProduto.widthProperty().subtract(botaoAtualizaImagemProduto.widthProperty()).divide(2));

                atualizaProdutos();

            } catch (IOException e) {
                System.out.println("Erro ao converter imagem: " + e.getMessage());
            }
        } else {
            // O usuário fechou a janela de diálogo sem selecionar um arquivo.
            System.out.println("Nenhum arquivo selecionado!");
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
    //Esse seria o READ do cRud (READ)
    public void atualizaClientes() {

        //Observable List que recebe o getClientes, ou seja recebe todos os clientes do DB
        ObservableList<Cliente> clientes = joalheriaDao.getClientes();

        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCPFCliente.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaEnderecoCliente.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colunaEmailCliente.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaCelularCliente.setCellValueFactory(new PropertyValueFactory<>("celular"));

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
    //Abre uma modal (pop-up) com SIM e NAO, para o cliente escolher se deseja realmente excluir
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

    //Método que fecha a modal ao clicar em NAO
    @FXML
    void fecharConfirmExcluirCliente() {
        paneFundoDaModal.toBack();
        paneFundoDaModal.setVisible(false);
        stackPaneConfirmaExcluir.toBack();
        dialogExcluir.close();
    }

    //Esse método é o delte do cruD
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

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Esse é o update do crUd Cliente
    //Método que abre a modal com 2 Clicks
    @FXML
    private void clicarParaAbrirEditorDeCliente(MouseEvent event) {
        if (event.getClickCount() == 2) { // Verifica se é um clique duplo
            Cliente selectedClient = tabelaClientes.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                clienteSelecionado = selectedClient;
                abrirPainelDeEditarCliente(clienteSelecionado);
            }
        }
    }

    //Método para fechar a modal ao clicar no X
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

    private void abrirPainelDeEditarCliente(Cliente cliente) {
        // Busque o cliente atualizado do banco de dados
        Cliente updatedClient = joalheriaDao.getClienteByCPF(cliente.getCpf());

        // Se o cliente atualizado for encontrado, atualize os campos de texto
        if (updatedClient != null) {
            cliente = updatedClient;
        }

        //Abrindo a modal e colocando pane cinza no fundo
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();
        stackAtualizaCliente.setVisible(true);
        stackAtualizaCliente.toFront();

        //Centralizando Modal
        double posX = (paneFundoDaModal.getWidth() - stackAtualizaCliente.getWidth()) / 2;
        double posY = (paneFundoDaModal.getHeight() - stackAtualizaCliente.getHeight()) / 2;
        stackAtualizaCliente.translateXProperty().set(posX);
        stackAtualizaCliente.translateYProperty().set(posY);

        //Criando ObservableList, necessária para eu conseguir consultar os clientes no banco de dados
        ObservableList<Cliente> clientes = joalheriaDao.getClientes();

        //Populando textFields com os valores do cliente selecionado
        atualizaClienteNomeField.setText(cliente.getNome());
        atualizaClienteCPFField.setText(cliente.getCpf());
        atualizaClienteCelularField.setText(cliente.getCelular());
        atualizaClienteEmailField.setText(cliente.getEmail());
        atualizaClienteEnderecoField.setText(cliente.getEndereco());
        atualizaClienteObservacaoField.setText(cliente.getObservacao());

        //Passando atributos temporários para comparações futuras
        nomeTemp = atualizaClienteNomeField.getText();
        cpfTemp = atualizaClienteCPFField.getText();
        celularTemp = atualizaClienteCelularField.getText();
        enderecoTemp = atualizaClienteEnderecoField.getText();
        emailTemp = atualizaClienteEmailField.getText();
        observacaoTemp = atualizaClienteObservacaoField.getText();

    }

    //Faz comparacoes antes de chamar a confirmacao de SIM e NAO
    public void salvarAlteracaoCliente() {

        //Confirmações
        Boolean confirma1 = true;
        Boolean confirma2 = true;
        Boolean confirma3 = false;

        //Capturando os valores dos campos de texto - Para futuras comparações
        String nome = atualizaClienteNomeField.getText();
        String cpf = atualizaClienteCPFField.getText();
        String celular = atualizaClienteCelularField.getText();
        String endereco = atualizaClienteEnderecoField.getText();
        String email = atualizaClienteEmailField.getText();
        String observacao = atualizaClienteObservacaoField.getText();

        //Verifica se o CPF foi alterado, se sim - Confere se é valido - Confere se já é cadastrado
        if (!cpf.equals(cpfTemp)) {
            if (!Validador.isCPF(cpf)) {
                confirma1 = false;
            }
            if (Validador.isCpfCadastrado(cpf)) {
                confirma2 = false;
            }
        }

        //Confere se houve alguma mudança nos campos preenchidos
        if (!nomeTemp.equals(nome) || !cpfTemp.equals(cpf) || !celularTemp.equals(celular) || !enderecoTemp.equals(endereco) || !emailTemp.equals(email) || !observacaoTemp.equals(observacao)) {
            confirma3 = true;
        }

        //Caso as confirmacoes passem pelos testes ele atualiza o cliente normalmente - Caso não passe em algum dos testes ele informa uma mensagem
        if (confirma1 && confirma2 && confirma3) {
            abrirConfirmaAlteracaoCliente();
            clienteAtualizado = new Cliente(nome, cpf, email, celular, endereco, observacao);
        } else {
            //Exibe uma mensagem de erro adequada e muda a cor do unfocus para vermelho
            if (!confirma3) {
                labelMensagem.setText("Nenhum campo foi alterado");
            } else if (!confirma2) {
                labelMensagem.setText("O CPF já está cadastrado");
            } else if (!confirma1) {
                labelMensagem.setText("O CPF é inválido");
            }
            //Depois de receber a mensagem, ela é pintada de vermelho - Junto com o textField de CPF - Durante 2.5 segundos
            labelMensagem.setVisible(true);
            atualizaClienteCPFField.setUnFocusColor(Color.RED);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
                labelMensagem.setVisible(false);
                atualizaClienteCPFField.setUnFocusColor(Color.WHITE);
            }));
            timeline.play();
        }
        //Centraliza a mensagem na tela
        labelMensagem.layout();
        labelMensagem.layoutXProperty().bind(stackAtualizaCliente.widthProperty().subtract(labelMensagem.widthProperty()).divide(2));
        labelMensagem.layoutYProperty().bind(stackAtualizaCliente.heightProperty().subtract(120));
    }

    //A caixa de dialogo com SIM e NAO para confirmar se deseja alterar os dados do cliente
    public void abrirConfirmaAlteracaoCliente() {
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();
        modalConfirmaAlterarCliente.setLayoutX((paneFundoDaModal.getWidth() - stackPaneConfirmaExcluir.getWidth()) / 2);
        modalConfirmaAlterarCliente.setLayoutY((paneFundoDaModal.getHeight() - stackPaneConfirmaExcluir.getHeight()) / 2);
        modalConfirmaAlterarCliente.setVisible(true);
        modalConfirmaAlterarCliente.toFront();
    }

    //Caso escolha NAO na janela de dialogo fecha e nao altera os valores
    public void fechaConfirmaAlteracaoCliente() {
        modalConfirmaAlterarCliente.setVisible(false);
        modalConfirmaAlterarCliente.toBack();
        abrirPainelDeEditarCliente(clienteSelecionado);
    }

    //Caso escolha SIM ele faz o update na tabela no banco de dados, atualiza a tabela no front-end, fecha a modal e o painel cinza
    public void confirmaAlteracaoCliente() {
        fechaConfirmaAlteracaoCliente();
        fecharTelaEditorDeCliente();
        joalheriaDao.updateCliente(clienteAtualizado);
        atualizaClientes();
        fadeLabelConfirmaAtualizado();
    }

    //Fade para o label, após 2 segundos ela some - Uma mensagem de confirmação que foi atualizado o cadastro
    public void fadeLabelConfirmaAtualizado() {
        labelConfirmaAtualizado.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(4), labelConfirmaAtualizado);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(event -> labelConfirmaAtualizado.setVisible(false));
        fade.play();
    } //Fim do Update

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Initialize
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

        //Formata CPF e Telefone dos Field da tela de Cadastro de Cliente
        formatacao.formataCPFEnquantoDigita(cadastroClienteCPF);
        formatacao.formataCelularEnquantoDigita(cadastroClienteCelular);

        //Formata CPF e Telefone da Modal de Atualização de Cliente
        formatacao.formataCPFEnquantoDigita(atualizaClienteCPFField);
        formatacao.formataCelularEnquantoDigita(atualizaClienteCelularField);

        //Formata Valor produto para aceitar só números
        formatacao.soNumerosTextField(fieldValorProduto);

        popularComboBox();

        //Comando que popula a lista de Clientes
        atualizaClientes();
        atualizaProdutos();

        System.out.println(comboAcessorio.getValue());
    }

}
