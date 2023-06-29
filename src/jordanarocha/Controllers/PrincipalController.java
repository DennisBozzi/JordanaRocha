package jordanarocha.Controllers;

import com.jfoenix.controls.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
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
import javafx.scene.shape.Circle;
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
            atualizaClienteNomeField, atualizaClienteCPFField, atualizaClienteCelularField, atualizaClienteEmailField, atualizaClienteEnderecoField, nomeAtributoField, nomeAtualizaProduto, valorAtualizaProduto,
            clienteModalVenda, vendedorModalVenda, comissaoModalVenda, diaModalVenda, pagamentoModalVenda;

    @FXML
    private Pane paneComeco, modalConfirmaAlterarCliente, paneVender, paneCadastrarProduto, paneCadastrarCliente, paneCadastrarAtributo, paneRelatorios, paneSubMenu, markComeco, markVender, markRelatorios,
            markCadastrar, paneFundoDaModal, modalConfirmaApagarProduto, modalEditaProduto, paneListaProdutos, modalFormaDePagamento, modalConfirmaApagarVenda, modalVenda;

    @FXML
    private Label cpfJaExiste, cpfEInvalido, labelProdutos, labelClientes, labelVendas, headerConfirmaExcluir, labelConfirmaAtualizado, labelMensagem, bodyConfirmaExcluir, labelAtributo, labelProduto, labelAtualizaProduto,
            nomeVendedorLogado, cargoUsuario, nenhumProduto, produtoEscolhido, labelValorTotal, labelComissao, camposNulosLabel, camposNulosLabel2, valorModalVenda, labelClienteReferenciado;

    @FXML
    private TableColumn<Cliente, String> colunaNomeCliente, colunaCPFCliente, colunaEnderecoCliente, colunaEmailCliente, colunaCelularCliente, recenteIdCliente, recenteNomeCliente, recenteCpfCliente;

    @FXML
    private TableColumn<Produto, String> colunaIdProdutos, colunaJoiaProdutos, colunaLigaProdutos, colunaAcessorioProdutos, colunaEscolheId, colunaEscolheNome, carrinhoId, carrinhoNome, colunaNomeModal, recenteNomeProduto,
            recenteIdProduto;

    @FXML
    private TableColumn<Venda, String> colunaIdVenda, colunaCompradorVenda, colunaVendedorVenda, recenteIdVenda, recenteCompradorVenda;

    @FXML
    private TableColumn<Venda, Timestamp> colunaDataVenda;

    @FXML
    private TableColumn<Produto, Double> colunaValorProdutos, colunaEscolheValor, carrinhoValor, colunaValorVenda, colunaValorModal, recenteValorVenda;

    @FXML
    private TableColumn<Produto, byte[]> colunaFotoProdutos, colunaEscolheFoto, carrinhoFoto, colunaImagemModal, recenteFotoProduto;

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
    private ImageView imagemTemporaria, imagemAtualizaProduto, imagemVendedorLogado;

    @FXML
    private TableView<Produto> tabelaProdutos, tabelaEscolhaProduto, tabelaCarrinho, tabelaModal, tabelaProdutosRecentes;

    @FXML
    private TableView<Cliente> tabelaClientes, tabelaClientesRecentes;

    @FXML
    private TableView<Venda> tabelaVendas, tabelaVendasRecentes;

    @FXML
    private JFXDialogLayout dialogConfirmaExluir;

    @FXML
    private JFXDialog dialogExcluir;

    @FXML
    private ToggleGroup pagamento;

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Instanciando formatador
    Formatacao formatacao = new Formatacao();

    //Instanciando a classe de trocar tela
    TrocaTela tela;

    //Intanciando a classe de trocar tabelas de consulta
    TrocaTabela tabela;
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    //Instanciando a classe do CRUD
    JoalheriaDAO joalheriaDao = new JoalheriaDAO();
    AtributosDAO atributoDAO = new AtributosDAO();
    CRUDAtributo CRUDatributos = new CRUDAtributo();
    ProdutosDAO produtosDAO = new ProdutosDAO();
    VendasDAO vendasDAO = new VendasDAO();

    //Variável do produto que será atualizado
    private Produto produtoAtualiza;

    //Variáveis do Vendedor que Conectou
    private Vendedor vendedorLogado;
    String primeiroNome;
    Image imagemLogado;
    int idLogado;
    double valorTotal;
    double comissao;

    //Variáveis para a realização da Venda
    List<Produto> carrinho = new ArrayList<>();

    //Variáveis para conferir se foram alteradas
    private String nomeTemp, cpfTemp, enderecoTemp, emailTemp, celularTemp, observacaoTemp, nomeProdutoTemp, valorProdutoTemp, acessorioProdutoTemp, pedraProdutoTemp, ligaProdutoTemp, tamanhoProdutoTemp;
    public Cliente clienteSelecionado;
    public Cliente clienteAtualizado;

    public Produto produtoSelecionado;
    public Produto produtoAtualizado;
    public Venda vendaSelecionada;

    //ObservableLists da comboBox
    ObservableList<String> acessorios;
    ObservableList<String> ligas;
    ObservableList<String> tamanhos;
    ObservableList<String> pedras;

    //Variável para converter a imagem para byte
    private byte[] imagemBytes = null;

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Método que atribui o Vendedor Logado
    public void setVendedorLogado(Vendedor vendedor) {
        this.vendedorLogado = vendedor;
        // Agora você pode usar vendedorLogado neste controlador
        formataVendedorLogado();
    }

    //Método que formata o vendedor que acabou de logar
    public void formataVendedorLogado() {

        if (vendedorLogado != null) {

            //Atribuindo a primeiroNome o nome do vendedor logado
            primeiroNome = vendedorLogado.getNomeVendedor().trim();
            String[] nomeSeparado = primeiroNome.split(" ");
            String primeiroNome = nomeSeparado[0];

            if (vendedorLogado.getFotoVendedor() != null) {

                //Atribuindo a imagemLogado a imagem do vendedor logado
                imagemLogado = formatacao.convertByteToImage(vendedorLogado.getFotoVendedor());

            }

            //Atribuindo a idLogado o id do vendedor logado
            idLogado = vendedorLogado.getIdVendedor();

            //Colocando no Label o nome do Vendedor
            nomeVendedorLogado.setText(primeiroNome);

            //Colocando no ImageView a imagem do Vendedor
            imagemVendedorLogado.setImage(imagemLogado);

            //Atribuindo o cargo, dependendo da pessoa
            if (vendedorLogado.getIdVendedor() == 1) {
                cargoUsuario.setText("administrador");
            } else {
                cargoUsuario.setText("vendedor");
            }

            //Criando um círculo que será usado para recortar a imagem
            Circle clip = new Circle(47, 47, 47); //47 é a metade de 94, então o círculo será centralizado na imagem

            //Aplicando o recorte ao ImageView
            imagemVendedorLogado.setClip(clip);

        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Métodos que irão nos botoes pára alternar os panes
    public void TrocaPaneComeco(ActionEvent event) {
        tela.TrocaPane("comeco");
        atualizaTabelasInicio();
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
        App.trocaTela("login", vendedorLogado);
        limparCarrinho();
        tela.TrocaPane("comeco");
        limparCamposVenda();
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
                BufferedImage originalImage = ImageIO.read(arquivoSelecionado);

                // Redimensionando para 94x94 sem cortar
                java.awt.Image tmp = originalImage.getScaledInstance(94, 94, java.awt.Image.SCALE_SMOOTH);
                BufferedImage resizedImage = new BufferedImage(94, 94, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();

                //Convertendo a imagem redimensionada para um array de bytes
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "png", byteOutput);
                imagemBytes = byteOutput.toByteArray();

                //Convertendo a imagem redimensionada para um objeto Image do JavaFX
                ByteArrayInputStream byteInput = new ByteArrayInputStream(imagemBytes);
                Image imagem = new Image(byteInput);
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
                }
                return false;
            });
        });

        // Define a lista de itens da tabela como a lista de clientes filtrados
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
        produtosDAO.updateProduto(produtoAtualiza);

        fadeLabelConfirmaAtualizado(labelConfirmaAtualizado);
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
                BufferedImage originalImage = ImageIO.read(arquivoSelecionado);

                // Redimensionando para 94x94 sem cortar
                java.awt.Image tmp = originalImage.getScaledInstance(94, 94, java.awt.Image.SCALE_SMOOTH);
                BufferedImage resizedImage = new BufferedImage(94, 94, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();

                //Convertendo a imagem recortada e redimensionada para um array de bytes
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "png", byteOutput);
                byte[] imagemBytes = byteOutput.toByteArray();

                //Convertendo a imagem recortada e redimensionada para um objeto Image do JavaFX
                ByteArrayInputStream byteInput = new ByteArrayInputStream(imagemBytes);
                Image imagem = new Image(byteInput);
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

        //Autocomplete Nome e CPF da tela de Venda
        formatacao.autocompleteCPF(autocompleteCPF, autocompleteNome);
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
    void excluirCliente(ActionEvent event) throws SQLException {
        //Código que identifica a linha selecionada na tabela
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        //Dando um get no CPF da linha selecionada
        String cpf = clienteSelecionado.getCpf();

        //Dando um get no Id da linha selecionada
        int id = clienteSelecionado.getId();

        if (!joalheriaDao.clienteTemVendas(id)) {
            //Excluindo com o parametro do cpf sendo passado
            joalheriaDao.excluirCliente(cpf);

            //Método para atualizar a tabela após excluir
            atualizaClientes();
        } else {
            fadeLabelConfirmaAtualizado(labelClienteReferenciado);
        }

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
        fadeLabelConfirmaAtualizado(labelConfirmaAtualizado);
    }

    //Fade para o label, após 2 segundos ela some - Uma mensagem de confirmação que foi atualizado o cadastro
    public void fadeLabelConfirmaAtualizado(Label label) {
        label.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(4), label);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(event -> label.setVisible(false));
        fade.play();
    } //Fim do Update

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Adicionar Venda (CREATE)
    public void adicionarVenda() {

        int idVendedor = vendedorLogado.getIdVendedor();

        int idCliente = joalheriaDao.getIdByCPF(autocompleteCPF.getText());

        RadioButton botaoSelecionado = (RadioButton) pagamento.getSelectedToggle();

        String formaPagamento = botaoSelecionado.getText();

        List<Integer> idsProdutos = carrinho.stream().map(Produto::getIdProduto).collect(Collectors.toList());

        System.out.println(formaPagamento);

        vendasDAO.inserirDadosVendas(idVendedor, idCliente, idsProdutos, valorTotal, formaPagamento, comissao);

        atualizaVendas();
        limparCamposVenda();
        limparCarrinho();
        fechaOpcoesDePagamento();
    }

    //Método que abre opções de pagamento
    public void abreOpcoesDePagamento() {
        if (!autocompleteCPF.getText().equals("") && !autocompleteNome.getText().equals("")) {

            if (!carrinho.isEmpty()) {

                paneFundoDaModal.setVisible(true);
                paneFundoDaModal.toFront();

                modalFormaDePagamento.setVisible(true);
                modalFormaDePagamento.toFront();

            } else {
                fadeLabelConfirmaAtualizado(camposNulosLabel);
            }

        } else {
            fadeLabelConfirmaAtualizado(camposNulosLabel2);
        }
    }

    //Método que fecha a modal de pagamento
    public void fechaOpcoesDePagamento() {

        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();

        modalFormaDePagamento.setVisible(false);
        modalFormaDePagamento.toBack();

    }

    //Método que abre lista de produtos
    public void abreListaProdutos() {

        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();

        paneListaProdutos.setVisible(true);
        paneListaProdutos.toFront();

        populaListaProdutos();

    }

    //Método que fecha lista de produtos
    public void fechaListaProdutos() {

        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toFront();

        paneListaProdutos.setVisible(false);
        paneListaProdutos.toFront();

    }

    //Popular lista de produtos
    public void populaListaProdutos() {
        ObservableList<Produto> produtos = produtosDAO.getProdutos();

        colunaEscolheId.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colunaEscolheNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaEscolheValor.setCellValueFactory(new PropertyValueFactory<>("valorProduto"));
        colunaEscolheFoto.setCellValueFactory(new PropertyValueFactory<>("fotoProduto"));

        //Formatando a coluna de valor para aparecer R$#.###.###,##
        colunaEscolheValor.setCellFactory(new FormattedCurrencyCellFactory());

        //"Formatando" a coluna de imagem para ela aparecer em uma imageView
        colunaEscolheFoto.setCellFactory(param -> new ImageTableCell());

        tabelaEscolhaProduto.setItems(produtos);
    }

    //Ao clicar no botao confirmar de escolher o produto
    public void escolheProduto() {

        Produto produtoSelecionado = tabelaEscolhaProduto.getSelectionModel().getSelectedItem();

        // Verifica se produtoSelecionado é nulo
        if (produtoSelecionado == null) {
            fadeLabelConfirmaAtualizado(nenhumProduto);
            return;
        }

        int id = produtoSelecionado.getIdProduto();

        Produto produtoCompleto = produtosDAO.getProdutoById(id);

        if (carrinho == null) {
            carrinho = new ArrayList<>(); //Se carrinho for nulo, inicializa.
        }

        if (!carrinho.contains(produtoCompleto)) {
            carrinho.add(produtoCompleto);
        } else { //Caso já exista o produto na lista
            fadeLabelConfirmaAtualizado(produtoEscolhido);
            System.out.println("Já existe o produto");
        }

        listaCarrinho();

    }

    //Adiciona ao carrinho de comprar o produto selecionado
    public void listaCarrinho() {
        //Observable List que recebe o getProdutos, ou seja, recebe todos os produtos do DB
        ObservableList<Produto> produtosEscolhidos = FXCollections.observableArrayList(carrinho);

        carrinhoId.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        carrinhoNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        carrinhoValor.setCellValueFactory(new PropertyValueFactory<>("valorProduto"));

        //Formatando a coluna de valor para aparecer R$#.###.###,##
        carrinhoValor.setCellFactory(new FormattedCurrencyCellFactory());

        // Coluna de imagem
        carrinhoFoto.setCellValueFactory(new PropertyValueFactory<>("fotoProduto"));

        //"Formatando" a coluna de imagem para ela aparecer em uma imageView
        carrinhoFoto.setCellFactory(param -> new ImageTableCell());

        tabelaCarrinho.setItems(produtosEscolhidos);

        valorTotal = 0;
        comissao = 0;

        for (int i = 0; i < produtosEscolhidos.size(); i++) {
            valorTotal += produtosEscolhidos.get(i).getValorProduto();
            comissao = (vendedorLogado.getComissaoVendedor() / 100) * valorTotal;
        }

        String valorTotalString = formatter.format(valorTotal);
        String comissaoString = formatter.format(comissao);

        labelValorTotal.setText(valorTotalString);
        labelComissao.setText(comissaoString);

        fechaListaProdutos();
    }

    //Limpa todos os produtos adicionados no carrinho
    public void limparCarrinho() {
        carrinho.clear();
        listaCarrinho();
    }

    public void limparCamposVenda() {
        autocompleteCPF.setText("");
        autocompleteNome.setText("");
    }

    //Método que popula as tabelas de Vendas (READ)
    public void atualizaVendas() {
        //Observable List que recebe o getVendas, ou seja, recebe todos as Vendas do BD
        ObservableList<Venda> vendas = vendasDAO.getVendas();

        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("idVendas"));
        colunaCompradorVenda.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colunaVendedorVenda.setCellValueFactory(new PropertyValueFactory<>("nomeVendedor"));
        colunaDataVenda.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        colunaValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        //Formatando a coluna de valor para aparecer R$#.###.###,##
        colunaValorVenda.setCellFactory(new FormattedCurrencyCellFactory());

        // Cria uma nova lista observável para armazenar os resultados da pesquisa, só que agora no tipo FilteredList
        FilteredList<Venda> vendasFiltradas = new FilteredList<>(vendas, p -> true);

        colunaDataVenda.setCellFactory(column -> formatacao.formatDateCell());

        // Adiciona um listener ao campo de pesquisa
        campoPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            vendasFiltradas.setPredicate(venda -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Verifica se o Nome do Clinte setá no valor digitado na pesquisa
                String lowerCaseFilter = newValue.toLowerCase();
                if (venda.getNomeCliente().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        // Define a lista de itens da tabela como a lista de clientes filtrados
        tabelaVendas.setItems(vendasFiltradas);
    }

    //Método para apagar as Vendas (DELETE)
    @FXML
    public void apagarVendas() {
        vendaSelecionada = tabelaVendas.getSelectionModel().getSelectedItem();

        int id = vendaSelecionada.getIdVendas();

        vendasDAO.deleteVenda(id);

        atualizaVendas();
        fecharConfirmaExcluirVenda();

    }

    @FXML
    public void abrirConfirmaExcluirVenda() {

        paneFundoDaModal.toFront();
        paneFundoDaModal.setVisible(true);

        modalConfirmaApagarVenda.toFront();
        modalConfirmaApagarVenda.setVisible(true);

    }

    @FXML
    public void fecharConfirmaExcluirVenda() {
        paneFundoDaModal.toBack();
        paneFundoDaModal.setVisible(false);

        modalConfirmaApagarVenda.toBack();
        modalConfirmaApagarVenda.setVisible(false);
    }

    //Método para visualizar as Vendas com 2 CLicks
    @FXML
    private void clicarParaAbrirVenda(MouseEvent event) {
        if (event.getClickCount() == 2) { // Verifica se é um clique duplo

            Venda selected = tabelaVendas.getSelectionModel().getSelectedItem();

            if (selected != null) {
                vendaSelecionada = selected;
                abreModalVenda();
                atribuirDadosModal();
            }
        }
    }

    public void abreModalVenda() {

        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();

        modalVenda.setVisible(true);
        modalVenda.toFront();

    }

    public void fechaModalVenda() {

        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();

        modalVenda.setVisible(false);
        modalVenda.toBack();

    }

    public void atribuirDadosModal() {

        String valorTotalString = formatter.format(vendaSelecionada.getValorTotal());
        String comissaoString = formatter.format(vendaSelecionada.getComissaoVenda());
        String dataString = String.valueOf(vendaSelecionada.getDataVenda());

        clienteModalVenda.setText(vendaSelecionada.getNomeCliente());
        vendedorModalVenda.setText(vendaSelecionada.getNomeVendedor());
        comissaoModalVenda.setText(comissaoString);
        diaModalVenda.setText(dataString);
        pagamentoModalVenda.setText(vendaSelecionada.getFormaPagamento());

        valorModalVenda.setText(valorTotalString);

        ObservableList<Produto> produtos = vendasDAO.getProdutosVendidoss(vendaSelecionada.getIdVendas());

        colunaImagemModal.setCellValueFactory(new PropertyValueFactory<>("fotoProduto"));
        colunaNomeModal.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaValorModal.setCellValueFactory(new PropertyValueFactory<>("valorProduto"));

        //Formatando a coluna de valor para aparecer R$#.###.###,##
        colunaValorModal.setCellFactory(new FormattedCurrencyCellFactory());

        //"Formatando" a coluna de imagem para ela aparecer em uma imageView
        colunaImagemModal.setCellFactory(param -> new ImageTableCell());

        tabelaModal.setItems(produtos);

        System.out.println(produtos.toString());

    }

    //----------------------------------------------------------------------------- Tela de Início ------------------------------------------------------------------------------------------
    public void atualizaTabelasInicio() {

        // -------------------------------- Populando Clientes
        //Observable List que recebe o getProdutos, ou seja, recebe todos os produtos do DB
        ObservableList<Produto> produtos = produtosDAO.getProdutos10();

        recenteIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        recenteNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        recenteFotoProduto.setCellValueFactory(new PropertyValueFactory<>("fotoProduto"));

        //"Formatando" a coluna de imagem para ela aparecer em uma imageView
        recenteFotoProduto.setCellFactory(param -> new ImageTableCell());

        //"Formatando" para aparecer apenas o primeio nome
        formatacao.primeiroNomeNaColuna(recenteNomeProduto);

        // Define a lista de itens da tabela que será listado
        tabelaProdutosRecentes.setItems(produtos);

        // -------------------------------- Populando Clientes
        //Observable List que recebe o getClientes, ou seja recebe todos os clientes do DB
        ObservableList<Cliente> clientes = joalheriaDao.getClientes10();

        recenteIdCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        recenteNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        recenteCpfCliente.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        //"Formatando" para aparecer apenas o primeio nome
        formatacao.primeiroNomeNaColuna(recenteNomeCliente);

        // Define a lista de itens da tabela que será listado
        tabelaClientesRecentes.setItems(clientes);

        // -------------------------------- Populando Vendas
        //Observable List que recebe o getVendas, ou seja, recebe todos as Vendas do BD
        ObservableList<Venda> vendas = vendasDAO.getVendas10();

        recenteIdVenda.setCellValueFactory(new PropertyValueFactory<>("idVendas"));
        recenteCompradorVenda.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        recenteValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        //Formatando a coluna de valor para aparecer R$#.###.###,##
        recenteValorVenda.setCellFactory(new FormattedCurrencyCellFactory());

        //"Formatando" para aparecer apenas o primeio nome
        formatacao.primeiroNomeNaColuna(recenteCompradorVenda);

        // Define a lista de itens da tabela que será listado
        tabelaVendasRecentes.setItems(vendas);

    }

    public void vendasInicio() {
        tela.TrocaPane("relatorios");
        tabela.trocaTabela("vendas");
    }

    public void produtosInicio() {
        tela.TrocaPane("relatorios");
        tabela.trocaTabela("produtos");
    }

    public void clientesInicio() {
        tela.TrocaPane("relatorios");
        tabela.trocaTabela("clientes");
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Iniciando TrocaTela
        tela = new TrocaTela(paneComeco, paneVender, paneCadastrarProduto, paneCadastrarCliente, paneCadastrarAtributo, paneRelatorios, paneSubMenu, markComeco, markVender, markRelatorios, markCadastrar);
        tela.TrocaPane("comeco"); //Sempre inicia na mesma tela

        //Iniciando Tabela
        tabela = new TrocaTabela(tabelaProdutos, tabelaClientes, tabelaVendas, labelClientes, labelProdutos, labelVendas);

        //Autocomplete Nome e CPF da tela de Venda
        formatacao.autocompleteCPF(autocompleteCPF, autocompleteNome);

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

        //Populando as ComboBox
        popularComboBox();

        //Comando que popula a lista de Clientes
        atualizaClientes();
        atualizaProdutos();
        atualizaVendas();
        atualizaTabelasInicio();
        limparCarrinho();
    }

}
