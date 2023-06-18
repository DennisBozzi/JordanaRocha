package jordanarocha.Controllers;

import com.jfoenix.controls.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import jordanarocha.*;
import jordanarocha.Tabelas.Atributo;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import jordanarocha.Models.AtributosDAO;
import jordanarocha.Models.VendedorDAO;
import jordanarocha.Tabelas.Cliente;
import jordanarocha.Tabelas.Vendedor;
import org.apache.commons.lang3.text.WordUtils;

public class NovoUsuarioController implements Initializable {

    @FXML
    private JFXTextField loginField, nomeField, cpfField, rgField, enderecoField, telField, emailField, comissaoField, atualizaAtivacaoVendedor, atualizaCPFVendedor, atualizaCelularVendedor, atualizaConfirmaSenhaVendedor,
            atualizaDesativacaoVendedor, atualizaEmailVendedor, atualizaEnderecoVendedor, atualizaNomeVendedor, atualizaSenhaVendedor, atualizaUsuarioVendedor, atualizaRGVendedor, atualizaComissaoVendedor;

    @FXML
    private TableColumn<Atributo, Integer> colunaIDAtributo;

    @FXML
    private TableColumn<Atributo, String> colunaNomeAtributo, colunaValorAtributo;

    @FXML
    private TableColumn<Vendedor, Integer> colunaIDVendedor, colunaStatusVendedor;

    @FXML
    private TableColumn<Vendedor, String> colunaNomeVendedor, colunaCPFVendedor, colunaCelularVendedor;

    @FXML
    private JFXPasswordField senhaField, confirmaSenha;

    @FXML
    private Label cpfInvalido, cadastroConcluido, senhaNaoConfere, comissaoInvalida, labelMensagem, camposNulos;

    @FXML
    private TableView<Atributo> tabelaAtributos;

    @FXML
    private TableView<Vendedor> tabelaVendedores;

    @FXML
    private JFXTextArea observacaoField, atualizaObservacaoVendedor;

    @FXML
    private ImageView imagemTemporaria, atualizaImagemVendedor;

    @FXML
    private JFXButton anexarImagemButton, botaoNovoUsuario, botaoMostraVendedores, botaoMostraAtributos, atualizaImagemBotaoVendedor;

    @FXML
    private Pane paneListaVendedores, paneCadastroVendedores, paneListaAtributos, paneFundoDaModal, paneConfirmaExcluirAtributo, paneConfirmaExcluirVendedor, paneAtualizaVendedor, modalConfirmaAlterarVendedor, paneConfirmDemitirVendedor;

    @FXML
    private AnchorPane anchorZim;

    //Instanciando
    Formatacao formatacao = new Formatacao();
    AtributosDAO CRUDatributos = new AtributosDAO();
    VendedorDAO CRUDvendedor = new VendedorDAO();
    PauseTransition tempo = new PauseTransition(Duration.seconds(2.5));

    //Variáveis
    private byte[] imagemBytes = null;
    public Vendedor vendedorSelecionado;
    Vendedor vendedorAtualizado;
    Vendedor vendedorDesabilitado;

    //Variáveis temporárias
    String nomeTemp, cpfTemp, celularTemp, emailTemp, enderecoTemp, observacaoTemp, ativacaoTemp, comissaoTemp, usuarioTemp, senhaTemp, confirmaSenhaTemp, rgTemp;

    //Vendedor Logado
    private Vendedor vendedorLogado;

    //Método que atribui o Vendedor Logado
    public void setVendedorLogado(Vendedor vendedor) {
        this.vendedorLogado = vendedor;
        // Agora você pode usar vendedorLogado neste controlador
    }

    //Método para Alternar entre a Tabela de Vendedores Cadastrados e a Tela para Cadastro de Vendedores
    void mudaTela(String tela) {
        switch (tela) {
            case "cadastro":
                paneCadastroVendedores.setVisible(true);
                paneListaVendedores.setVisible(false);
                paneListaAtributos.setVisible(false);

                botaoMostraVendedores.setLayoutY(45);
                botaoNovoUsuario.setLayoutY(36);
                botaoMostraAtributos.setLayoutY(45);
                botaoNovoUsuario.setAlignment(Pos.CENTER);
                botaoMostraVendedores.setAlignment(Pos.TOP_CENTER);
                botaoMostraAtributos.setAlignment(Pos.TOP_CENTER);

                break;

            case "lista":
                paneCadastroVendedores.setVisible(false);
                paneListaVendedores.setVisible(true);
                paneListaAtributos.setVisible(false);

                botaoMostraVendedores.setLayoutY(36);
                botaoNovoUsuario.setLayoutY(45);
                botaoMostraAtributos.setLayoutY(45);
                botaoNovoUsuario.setAlignment(Pos.TOP_CENTER);
                botaoMostraVendedores.setAlignment(Pos.CENTER);
                botaoMostraAtributos.setAlignment(Pos.TOP_CENTER);
                atualizaVendedores();

                break;

            case "atributos":
                paneCadastroVendedores.setVisible(false);
                paneListaVendedores.setVisible(false);
                paneListaAtributos.setVisible(true);

                botaoMostraVendedores.setLayoutY(45);
                botaoNovoUsuario.setLayoutY(45);
                botaoMostraAtributos.setLayoutY(36);
                botaoNovoUsuario.setAlignment(Pos.TOP_CENTER);
                botaoMostraVendedores.setAlignment(Pos.TOP_CENTER);
                botaoMostraAtributos.setAlignment(Pos.CENTER);
                atualizaAtributos();

                break;
        }
    }

    //Atualiza a lista de exposição dos atributos (READ)
    public void atualizaAtributos() {

        // Observable List que recebe o getClientes, ou seja recebe todos os clientes do DB
        ObservableList<Atributo> atributos = CRUDatributos.getAtributos();

        colunaIDAtributo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNomeAtributo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaValorAtributo.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabelaAtributos.setItems(atributos);
    }

    //Exclui dados da lista atributos (DELETE)
    public void excluirAtributo() {
        Atributo atributoSelecionado = tabelaAtributos.getSelectionModel().getSelectedItem();
        int id = atributoSelecionado.getId();

        CRUDatributos.excluirAtributo(id);

        fecharConfirmExcluirAtributo();
        atualizaAtributos();
        fecharConfirmExcluirAtributo();
    }

    public void abrirConfirmExcluirAtributo() {
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();

        paneConfirmaExcluirAtributo.setLayoutX((paneFundoDaModal.getWidth() - paneConfirmaExcluirAtributo.getWidth()) / 2);
        paneConfirmaExcluirAtributo.setLayoutY((paneFundoDaModal.getHeight() - paneConfirmaExcluirAtributo.getHeight()) / 2);

        paneConfirmaExcluirAtributo.setVisible(true);
        paneConfirmaExcluirAtributo.toFront();

    }

    public void fecharConfirmExcluirAtributo() {
        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();

        paneConfirmaExcluirAtributo.setVisible(false);
        paneConfirmaExcluirAtributo.toBack();
    }

    //Mostra a Tela para Cadastrar
    public void mostraTelaCadastro() {
        mudaTela("cadastro");
    }

    //Mostra a Tela de Vendedores
    public void mostraTelaVendedores() {
        mudaTela("lista");
    }

    // Mostra a Tela de Atributos
    public void mostraTelaAtributos() {
        mudaTela("atributos");
        atualizaAtributos();
    }

    //Voltar para a tela de login para Cadastro
    public void voltaCadastro(ActionEvent event) {
        App.trocaTela("cadastro", vendedorLogado);
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Método para adicionar Vendedor (CREATE)
    public void adicionarVendedor() {
        String nome = nomeField.getText();
        String nomeFormatado = WordUtils.capitalizeFully(nome);

        String cpf = cpfField.getText();

        String senha = senhaField.getText();
        String senhaConfirmacao = confirmaSenha.getText();

        if (!comissaoField.getText().equals("") || !loginField.getText().equals("") || !senhaField.getText().equals("") || !confirmaSenha.getText().equals("") || !nomeField.getText().equals("") || !cpfField.getText().equals("")
                || !rgField.getText().equals("") || !telField.getText().equals("") || !enderecoField.getText().equals("") || !emailField.getText().equals("") || !observacaoField.getText().equals("")) {
            if (!comissaoField.getText().equals("")) {

                if (!senha.isEmpty() && senha.equals(senhaConfirmacao)) {

                    if (Validador.isCPF(cpf)) { //Conferindo se é válido

                        if (!CRUDvendedor.cpfExists(cpf)) { //Conferindo se está cadastrado

                            Vendedor novoVendedor;
                            novoVendedor = new Vendedor(Double.parseDouble(comissaoField.getText()), nomeFormatado, cpf, rgField.getText(), enderecoField.getText(), telField.getText(), imagemBytes, emailField.getText(), loginField.getText(), senhaField.getText(), observacaoField.getText());

                            CRUDvendedor.addVendedor(novoVendedor); //Método Create que adiciona um novo vendedor ao BD

                            comissaoField.clear();
                            nomeField.clear();
                            rgField.clear();
                            enderecoField.clear();
                            telField.clear();
                            confirmaSenha.clear();
                            emailField.clear();
                            loginField.clear();
                            senhaField.clear();
                            observacaoField.clear();
                            cpfField.clear();

                            imagemTemporaria.setImage(new Image("jordanarocha/Imagens/default-image.jpg"));

                            cpfField.setStyle("-fx-unfocus-color: #FFFFFF;");

                            cpfInvalido.setVisible(false);
                            cpfField.unFocusColorProperty().setValue(Color.rgb(255, 255, 255));
                            senhaNaoConfere.setVisible(false);
                            confirmaSenha.unFocusColorProperty().setValue(Color.rgb(255, 255, 255));
                            comissaoInvalida.setVisible(false);
                            comissaoField.unFocusColorProperty().setValue(Color.rgb(255, 255, 255));

                            cadastroConcluido.setVisible(true);
                            tempo.setOnFinished(event -> cadastroConcluido.setVisible(false));
                            tempo.play();

                            atualizaVendedores();
                        } else {
                            cpfField.unFocusColorProperty().setValue(Color.rgb(255, 0, 0));

                            cpfInvalido.setVisible(true);
                            cpfInvalido.setText("CPF Já Cadastrado");

                            tempo.setOnFinished(event -> {
                                cpfInvalido.setVisible(false);
                                cpfField.unFocusColorProperty().setValue(Color.rgb(255, 255, 255));
                            });

                            tempo.play();
                        }
                    } else {
                        cpfField.unFocusColorProperty().setValue(Color.rgb(255, 0, 0));

                        cpfInvalido.setVisible(true);
                        cpfInvalido.setText("CPF Inválido");

                        tempo.setOnFinished(event -> {
                            cpfInvalido.setVisible(false);
                            cpfField.unFocusColorProperty().setValue(Color.rgb(255, 255, 255));
                        });

                        tempo.play();
                    }
                } else {
                    confirmaSenha.unFocusColorProperty().setValue(Color.rgb(255, 0, 0));

                    senhaNaoConfere.setVisible(true);

                    tempo.setOnFinished(event -> {
                        senhaNaoConfere.setVisible(false);
                        confirmaSenha.unFocusColorProperty().setValue(Color.rgb(255, 255, 255));
                    });

                    tempo.play();
                }

            } else {
                comissaoField.unFocusColorProperty().setValue(Color.rgb(255, 0, 0));

                comissaoInvalida.setVisible(true);

                tempo.setOnFinished(event -> {
                    comissaoInvalida.setVisible(false);
                    comissaoField.unFocusColorProperty().setValue(Color.rgb(255, 255, 255));
                });

                tempo.play();

            }
        } else {
            camposNulos.setVisible(true);

            tempo.setOnFinished(event -> {
                camposNulos.setVisible(false);
            });

            tempo.play();
        }

    }

    //Método para anexar imagem para Cadastro de Vendedor
    @FXML
    public void anexarImagem(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione a imagem do Vendedor");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));

        File arquivoSelecionado = fileChooser.showOpenDialog(anexarImagemButton.getScene().getWindow());

        if (arquivoSelecionado != null) {
            try {
                BufferedImage originalImage = ImageIO.read(arquivoSelecionado);

                //Calculando as dimensões do recorte
                int croppedSize = Math.min(Math.min(originalImage.getWidth(), originalImage.getHeight()), 500);

                //Calculando as coordenadas x e y para o recorte no centro da imagem
                int x = (originalImage.getWidth() - croppedSize) / 2;
                int y = (originalImage.getHeight() - croppedSize) / 2;

                //Recortando a imagem
                BufferedImage croppedImage = originalImage.getSubimage(x, y, croppedSize, croppedSize);

                // Redimensionando para 94x94
                java.awt.Image tmp = croppedImage.getScaledInstance(94, 94, java.awt.Image.SCALE_SMOOTH);
                BufferedImage resizedImage = new BufferedImage(94, 94, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();

                //Convertendo a imagem recortada e redimensionada para um array de bytes
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "png", byteOutput);
                imagemBytes = byteOutput.toByteArray();

                //Convertendo a imagem recortada e redimensionada para um objeto Image do JavaFX
                ByteArrayInputStream byteInput = new ByteArrayInputStream(imagemBytes);
                Image imagem = new Image(byteInput);
                imagemTemporaria.setImage(imagem);

            } catch (IOException e) {
                System.out.println("Erro ao converter imagem: " + e.getMessage());
            }
        }

        // Vincula a propriedade layoutX do botão à metade da largura do painel.
        anexarImagemButton.layoutXProperty().bind(paneFundoDaModal.widthProperty().subtract(anexarImagemButton.widthProperty()).divide(2));

    }

    //Método para listar os Vendedores (READ)
    public void atualizaVendedores() {

        //Observable List que recebe o getClientes, ou seja recebe todos os clientes do DB
        ObservableList<Vendedor> vendedores = CRUDvendedor.getVendedores();

        colunaIDVendedor.setCellValueFactory(new PropertyValueFactory<>("idVendedor"));
        colunaNomeVendedor.setCellValueFactory(new PropertyValueFactory<>("nomeVendedor"));
        colunaCPFVendedor.setCellValueFactory(new PropertyValueFactory<>("cpfVendedor"));
        colunaCelularVendedor.setCellValueFactory(new PropertyValueFactory<>("celularVendedor"));
        colunaStatusVendedor.setCellValueFactory(new PropertyValueFactory<>("statusVendedor"));
        colunaStatusVendedor.setCellFactory(column -> new TableCell<Vendedor, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    // Confere o status
                    if (item == 1) {
                        setText("Ativo");
                    } else {
                        setText("Inativo");
                    }
                }
            }
        });

        tabelaVendedores.setItems(vendedores);
    }

    //Exclui dados da lista vedendores (DELETE)
    public void excluirVendedor() {
        Vendedor vendedorSelecionado = tabelaVendedores.getSelectionModel().getSelectedItem();
        int id = vendedorSelecionado.getIdVendedor();

        if (id != 1) {
            CRUDvendedor.excluirVendedor(id);
        }

        atualizaVendedores();
        fecharConfirmExcluirVendedor();
    }

    public void abrirConfirmExcluirVendedor() {
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();

        paneConfirmaExcluirVendedor.setLayoutX((paneFundoDaModal.getWidth() - paneConfirmaExcluirVendedor.getWidth()) / 2);
        paneConfirmaExcluirVendedor.setLayoutY((paneFundoDaModal.getHeight() - paneConfirmaExcluirVendedor.getHeight()) / 2);

        paneConfirmaExcluirVendedor.setVisible(true);
        paneConfirmaExcluirVendedor.toFront();

    }

    public void fecharConfirmExcluirVendedor() {
        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();

        paneConfirmaExcluirVendedor.setVisible(false);
        paneConfirmaExcluirVendedor.toBack();
    }

    //Altera dados da lista vendedores (UPDATE)
    @FXML
    private void clicarParaAbrirEditorDeVendedor(MouseEvent event) {
        if (event.getClickCount() == 2) { // Verifica se é um clique duplo
            Vendedor selectedClient = tabelaVendedores.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                vendedorSelecionado = selectedClient;
                abrirPainelDeEditarVendedor(vendedorSelecionado);
            }
        }
    }

    //Método para fechar a modal ao clicar no X
    @FXML
    private void fecharTelaEditorDeVendedor() {
        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();
        paneAtualizaVendedor.setVisible(false);
        paneAtualizaVendedor.toBack();
    }

    private void abrirPainelDeEditarVendedor(Vendedor vendedor) {

        // Busque o cliente atualizado do banco de dados
        Vendedor updateVendedor = CRUDvendedor.getVendedorByID(vendedor.getIdVendedor());

        // Se o cliente atualizado for encontrado, atualize os campos de texto
        if (updateVendedor != null) {
            vendedor = updateVendedor;
        }

        // Mudando a data de admissão para poder coloca-la em um textField
        Timestamp dataAdmissao = vendedor.getDataAdmissao();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAdmissaoFormatada = dateFormat.format(dataAdmissao);

        // Mudando a data de demissao para poder coloca-la em um textField
        Date dataDemissao = vendedor.getDataDemissao();

        LocalDate localDate = null;
        String dataFormatada = null;

        if (dataDemissao != null) {
            localDate = new java.sql.Date(dataDemissao.getTime()).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataFormatada = localDate.format(formatter);
            atualizaDesativacaoVendedor.setText(dataFormatada);
        }

        // Convertendo a imagem do banco de Byte para Image para poder colocar no imageView
        byte[] fotoVendedor = vendedor.getFotoVendedor();
        Image imagem = null;
        if (fotoVendedor != null) {
            imagem = new Image(new ByteArrayInputStream(fotoVendedor));
        }

        //Abrindo a modal e colocando pane cinza no fundo
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();
        paneAtualizaVendedor.setVisible(true);
        paneAtualizaVendedor.toFront();

        //Criando ObservableList, necessária para eu conseguir consultar os vendedores no banco de dados
        ObservableList<Vendedor> vendedores = CRUDvendedor.getVendedores();

        //Populando textFields com os valores do cliente selecionado
        atualizaNomeVendedor.setText(vendedor.getNomeVendedor());
        atualizaCPFVendedor.setText(vendedor.getCpfVendedor());
        atualizaRGVendedor.setText(vendedor.getRgVendedor());
        atualizaCelularVendedor.setText(vendedor.getCelularVendedor());
        atualizaEmailVendedor.setText(vendedor.getEmailVendedor());
        atualizaEnderecoVendedor.setText(vendedor.getEnderecoVendedor());
        atualizaObservacaoVendedor.setText(vendedor.getobservacaoVendedor());
        atualizaAtivacaoVendedor.setText(dataAdmissaoFormatada);
        atualizaDesativacaoVendedor.setText(dataFormatada);
        atualizaUsuarioVendedor.setText(vendedor.getUsuarioVendedor());
        atualizaSenhaVendedor.setText(vendedor.getSenhaVendedor());
        atualizaConfirmaSenhaVendedor.setText(vendedor.getSenhaVendedor());
        atualizaImagemVendedor.setImage(imagem);
        atualizaComissaoVendedor.setText(Double.toString(vendedor.getComissaoVendedor()));

        //Passando atributos temporários para comparações futuras
        nomeTemp = atualizaNomeVendedor.getText();
        cpfTemp = atualizaCPFVendedor.getText();
        rgTemp = atualizaRGVendedor.getText();
        celularTemp = atualizaCelularVendedor.getText();
        emailTemp = atualizaEmailVendedor.getText();
        enderecoTemp = atualizaEnderecoVendedor.getText();
        observacaoTemp = atualizaObservacaoVendedor.getText();
        ativacaoTemp = atualizaAtivacaoVendedor.getText();
        comissaoTemp = atualizaComissaoVendedor.getText();
        usuarioTemp = atualizaUsuarioVendedor.getText();
        senhaTemp = atualizaSenhaVendedor.getText();
        confirmaSenhaTemp = atualizaConfirmaSenhaVendedor.getText();
        comissaoTemp = atualizaComissaoVendedor.getText();

        // Centraliza a imagem do cliente vinda do Banco de Dados.
        atualizaImagemBotaoVendedor.layoutXProperty().bind(paneAtualizaVendedor.widthProperty().subtract(atualizaImagemBotaoVendedor.widthProperty()).divide(2));
    }

    //Faz comparacoes antes de chamar a confirmacao de SIM e NAO
    public void salvarAlteracaoVendedor() {

        //Confirmações
        Boolean confirmaCPFAlterado = true;
        Boolean confirmaCPFJaCadastrado = true;
        Boolean confirmaCampoAlterado = false;
        Boolean confirmaCamposNulos = true;
        Boolean confirmaSenhasIguais = true;

        //Capturando os valores dos campos de texto - Para futuras comparações
        String nome = atualizaNomeVendedor.getText();
        String cpf = atualizaCPFVendedor.getText();
        String rg = atualizaRGVendedor.getText();
        String comissao = atualizaComissaoVendedor.getText();
        String celular = atualizaCelularVendedor.getText();
        String email = atualizaEmailVendedor.getText();
        String endereco = atualizaEnderecoVendedor.getText();
        String observacao = atualizaObservacaoVendedor.getText();
        String ativacao = atualizaAtivacaoVendedor.getText();
        String usuario = atualizaUsuarioVendedor.getText();
        String senha = atualizaSenhaVendedor.getText();
        String confirmaSenha = atualizaConfirmaSenhaVendedor.getText();

        //Verifica se o CPF foi alterado, se sim - Confere se é valido - Confere se já é cadastrado
        if (!cpf.equals(cpfTemp)) {
            if (!Validador.isCPF(cpf)) {
                confirmaCPFAlterado = false;
            }
            if (Validador.isCpfCadastrado(cpf)) {
                confirmaCPFJaCadastrado = false;
            }
        }

        if (!senha.equals(confirmaSenha)) {
            confirmaSenhasIguais = false;
        }

        //Confere se existe algum campo nulo
        if (nome.isEmpty() || cpf.isEmpty() || celular.isEmpty() || email.isEmpty() || endereco.isEmpty() || observacao.isEmpty()
                || usuario.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty() || comissao.isEmpty()) {
            confirmaCamposNulos = false;
        }

        //Confere se houve alguma mudança nos campos preenchidos
        if (!nomeTemp.equals(nome) || !cpfTemp.equals(cpf) || !rgTemp.equals(rg) || !celularTemp.equals(celular) || !enderecoTemp.equals(endereco) || !emailTemp.equals(email) || !observacaoTemp.equals(observacao)
                || !ativacaoTemp.equals(ativacao) || !usuarioTemp.equals(usuario) || !senhaTemp.equals(senha) || !confirmaSenhaTemp.equals(confirmaSenha) || !comissaoTemp.equals(comissao)) {
            confirmaCampoAlterado = true;
        }

        //Caso as confirmacoes passem pelos testes ele atualiza o cliente normalmente - Caso não passe em algum dos testes ele informa uma mensagem
        if (confirmaCPFAlterado && confirmaCPFJaCadastrado && confirmaCampoAlterado && confirmaCamposNulos && confirmaSenhasIguais) {
            abrirConfirmaAlteracaoVendedor();
            vendedorAtualizado = new Vendedor(vendedorSelecionado.getIdVendedor(), nome, cpf, rg, Double.parseDouble(comissao), celular, email, endereco, observacao, usuario, senha);
        } else {
            //Exibe uma mensagem de erro adequada e muda a cor do unfocus para vermelho
            if (!confirmaCampoAlterado) {
                labelMensagem.setText("Nenhum campo foi alterado");
            } else if (!confirmaCPFJaCadastrado) {

                labelMensagem.setText("O CPF informado está cadastrado");

                atualizaCPFVendedor.setUnFocusColor(Color.RED);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
                    atualizaCPFVendedor.setUnFocusColor(Color.WHITE);
                }));
                timeline.play();

            } else if (!confirmaCPFAlterado) {

                labelMensagem.setText("O CPF informado é inválido");

                atualizaCPFVendedor.setUnFocusColor(Color.RED);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
                    atualizaCPFVendedor.setUnFocusColor(Color.WHITE);
                }));
                timeline.play();

            } else if (!confirmaCamposNulos) {
                labelMensagem.setText("Existem campos nulos");
            } else if (!confirmaSenhasIguais) {
                labelMensagem.setText("As senhas não se coincidem");
            }
            //Depois de receber a mensagem, ela é pintada de vermelho - Junto com o textField de CPF - Durante 2.5 segundos
            labelMensagem.setVisible(true);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
                labelMensagem.setVisible(false);
            }));

            timeline.play();
        }
        //Centraliza a mensagem na tela
        labelMensagem.layout();
        labelMensagem.layoutXProperty().bind(paneAtualizaVendedor.widthProperty().subtract(labelMensagem.widthProperty()).divide(2));
        labelMensagem.layoutYProperty().bind(paneAtualizaVendedor.heightProperty().subtract(71));
    }

    //Campo de SIM ou NAO para alterar os dados do vendedor
    public void abrirConfirmaAlteracaoVendedor() {
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();
        modalConfirmaAlterarVendedor.setLayoutX((paneFundoDaModal.getWidth() - modalConfirmaAlterarVendedor.getWidth()) / 2);
        modalConfirmaAlterarVendedor.setLayoutY((paneFundoDaModal.getHeight() - modalConfirmaAlterarVendedor.getHeight()) / 2);
        modalConfirmaAlterarVendedor.setVisible(true);
        modalConfirmaAlterarVendedor.toFront();
    }

    //Caso escolha NAO a modal fecha e nao altera os valores
    public void fechaConfirmaAlteracaoVendedor() {
        modalConfirmaAlterarVendedor.setVisible(false);
        modalConfirmaAlterarVendedor.toBack();
        abrirPainelDeEditarVendedor(vendedorSelecionado);
    }

    //Caso escolha SIM chama o método que atualiza o vendedor
    public void confirmaAlteracaoVendedor() {
        fechaConfirmaAlteracaoVendedor();
        fecharTelaEditorDeVendedor();
        CRUDvendedor.updateVendedor(vendedorAtualizado);
        atualizaVendedores();
    }

    //Método para mudar a foto do Vendedor (UPDATE)
    public void atualizaFotoVendedor() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione a imagem do Vendedor");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));

        File arquivoSelecionado = fileChooser.showOpenDialog(anexarImagemButton.getScene().getWindow());

        if (arquivoSelecionado != null) {
            try {
                BufferedImage originalImage = ImageIO.read(arquivoSelecionado);

                //Calculando as dimensões do recorte
                int croppedSize = Math.min(Math.min(originalImage.getWidth(), originalImage.getHeight()), 500);

                //Calculando as coordenadas x e y para o recorte no centro da imagem
                int x = (originalImage.getWidth() - croppedSize) / 2;
                int y = (originalImage.getHeight() - croppedSize) / 2;

                //Recortando a imagem
                BufferedImage croppedImage = originalImage.getSubimage(x, y, croppedSize, croppedSize);

                // Redimensionando para 94x94
                java.awt.Image tmp = croppedImage.getScaledInstance(94, 94, java.awt.Image.SCALE_SMOOTH);
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
                atualizaImagemVendedor.setImage(imagem);

                // Criar objeto Vendedor com ID e foto atualizados
                Vendedor vendedor = new Vendedor(vendedorSelecionado.getIdVendedor(), imagemBytes);

                // Chamar o método updateImagemVendedor() com o objeto Vendedor
                CRUDvendedor.updateImagemVendedor(vendedor);
            } catch (IOException e) {
                System.out.println("Erro ao converter imagem: " + e.getMessage());
            }
        }

        // Vincula a propriedade layoutX do botão à metade da largura do painel.
        abrirPainelDeEditarVendedor(vendedorSelecionado);
        anexarImagemButton.layoutXProperty().bind(paneFundoDaModal.widthProperty().subtract(anexarImagemButton.widthProperty()).divide(2));
    }

    //Método para anexar a imagem do Vendedor que será atualizada (UPDATE)
    @FXML
    public void anexarImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione a imagem do Vendedor");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png"));

        File arquivoSelecionado = fileChooser.showOpenDialog(anexarImagemButton.getScene().getWindow());

        if (arquivoSelecionado != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(arquivoSelecionado);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteOutput);
                imagemBytes = byteOutput.toByteArray();

                Image imagem = new Image(arquivoSelecionado.toURI().toString());
                atualizaImagemVendedor.setImage(imagem);
            } catch (IOException e) {
                System.out.println("Erro ao converter imagem: " + e.getMessage());
            }
        }

        // Vincula a propriedade layoutX do botão à metade da largura do painel.
        anexarImagemButton.layoutXProperty().bind(paneFundoDaModal.widthProperty().subtract(anexarImagemButton.widthProperty()).divide(2));

    }

    public void abrirConfirmDemitirVendedor() {
        paneFundoDaModal.setVisible(true);
        paneFundoDaModal.toFront();

        Vendedor vendedorSelecionado = tabelaVendedores.getSelectionModel().getSelectedItem();

        vendedorDesabilitado = new Vendedor(vendedorSelecionado.getIdVendedor(), 0);

        paneConfirmDemitirVendedor.setLayoutX((paneFundoDaModal.getWidth() - paneConfirmDemitirVendedor.getWidth()) / 2);
        paneConfirmDemitirVendedor.setLayoutY((paneFundoDaModal.getHeight() - paneConfirmDemitirVendedor.getHeight()) / 2);

        paneConfirmDemitirVendedor.setVisible(true);
        paneConfirmDemitirVendedor.toFront();

    }

    public void fechaConfirmDemitirVendedor() {
        paneFundoDaModal.setVisible(false);
        paneFundoDaModal.toBack();

        paneConfirmDemitirVendedor.setVisible(false);
        paneConfirmDemitirVendedor.toBack();
    }

    //Desabilitando Vendedor 
    @FXML
    public void atualizaStatusVendedor() {
        
        if (vendedorDesabilitado.getIdVendedor() != 1) {
            CRUDvendedor.updateStatusVendedor(vendedorDesabilitado);
        }

        fechaConfirmDemitirVendedor();
        atualizaVendedores();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {

        //Chamando a Tela Cadastro logo no começo
        mudaTela("cadastro");

        //Métodos para Formatar, CPF, Celular e RG
        formatacao.formataCPFEnquantoDigita(cpfField);
        formatacao.formataCelularEnquantoDigita(telField);
        formatacao.formataRGEnquantoDigita(rgField);

        formatacao.formataCPFEnquantoDigita(atualizaCPFVendedor);
        formatacao.formataCelularEnquantoDigita(atualizaCelularVendedor);
        formatacao.formataRGEnquantoDigita(atualizaRGVendedor);

        atualizaVendedores();
        atualizaAtributos();

    }
}
