package jordanarocha.Controllers;

import javafx.scene.layout.Pane;

public class TrocaTela {

    //Variáveis para criar o método construtor
    public final Pane paneComeco;
    public final Pane paneVender;
    public final Pane paneCadastrarProduto;
    public final Pane paneCadastrarCliente;
    public final Pane paneCadastrarAtributo;
    public final Pane paneRelatorios;
    public final Pane paneSubMenu;
    public final Pane markComeco;
    public final Pane markVender;
    public final Pane markRelatorios;
    public final Pane markCadastrar;

    //Método construtor que pede todos os panes como parametro
    public TrocaTela(Pane paneComeco, Pane paneVender, Pane paneCadastrarProduto, Pane paneCadastrarCliente, Pane paneCadastrarAtributo, Pane paneRelatorios, Pane paneSubMenu, Pane markComeco, Pane markVender, Pane markRelatorios, Pane markCadastrar) {
        this.paneComeco = paneComeco;
        this.paneVender = paneVender;
        this.paneCadastrarProduto = paneCadastrarProduto;
        this.paneCadastrarCliente = paneCadastrarCliente;
        this.paneCadastrarAtributo = paneCadastrarAtributo;
        this.paneRelatorios = paneRelatorios;
        this.paneSubMenu = paneSubMenu;

        this.markComeco = markComeco;
        this.markVender = markVender;
        this.markRelatorios = markRelatorios;
        this.markCadastrar = markCadastrar;
    }

    //Método para alternar os panes
    public void TrocaPane(String nomePane) {

        switch (nomePane) {

            case "comeco":
                paneComeco.setVisible(true);
                paneVender.setVisible(false);
                paneCadastrarProduto.setVisible(false);
                paneCadastrarCliente.setVisible(false);
                paneCadastrarAtributo.setVisible(false);
                paneRelatorios.setVisible(false);
                paneSubMenu.setVisible(false);

                //Marcacoes
                markComeco.setVisible(true);
                markVender.setVisible(false);
                markRelatorios.setVisible(false);
                markCadastrar.setVisible(false);
                break;

            case "vender":
                paneComeco.setVisible(false);
                paneVender.setVisible(true);
                paneCadastrarProduto.setVisible(false);
                paneCadastrarCliente.setVisible(false);
                paneCadastrarAtributo.setVisible(false);
                paneRelatorios.setVisible(false);
                paneSubMenu.setVisible(false);

                //Marcacoes
                markComeco.setVisible(false);
                markVender.setVisible(true);
                markRelatorios.setVisible(false);
                markCadastrar.setVisible(false);
                break;

            case "adicionarProduto":
                paneComeco.setVisible(false);
                paneVender.setVisible(false);
                paneCadastrarProduto.setVisible(true);
                paneCadastrarCliente.setVisible(false);
                paneCadastrarAtributo.setVisible(false);
                paneRelatorios.setVisible(false);
                paneSubMenu.setVisible(false);

                //Marcacoes
                markComeco.setVisible(false);
                markVender.setVisible(false);
                markRelatorios.setVisible(false);
                markCadastrar.setVisible(true);
                break;

            case "adicionarCliente":
                paneComeco.setVisible(false);
                paneVender.setVisible(false);
                paneCadastrarProduto.setVisible(false);
                paneCadastrarCliente.setVisible(true);
                paneCadastrarAtributo.setVisible(false);
                paneRelatorios.setVisible(false);
                paneSubMenu.setVisible(false);

                //Marcacoes
                markComeco.setVisible(false);
                markVender.setVisible(false);
                markRelatorios.setVisible(false);
                markCadastrar.setVisible(true);
                break;

            case "adicionarAtributo":
                paneComeco.setVisible(false);
                paneVender.setVisible(false);
                paneCadastrarProduto.setVisible(false);
                paneCadastrarCliente.setVisible(false);
                paneCadastrarAtributo.setVisible(true);
                paneRelatorios.setVisible(false);
                paneSubMenu.setVisible(false);

                //Marcacoes
                markComeco.setVisible(false);
                markVender.setVisible(false);
                markRelatorios.setVisible(false);
                markCadastrar.setVisible(true);
                break;

            case "relatorios":
                paneComeco.setVisible(false);
                paneVender.setVisible(false);
                paneCadastrarProduto.setVisible(false);
                paneCadastrarCliente.setVisible(false);
                paneCadastrarAtributo.setVisible(false);
                paneRelatorios.setVisible(true);
                paneSubMenu.setVisible(false);

                //Marcacoes
                markComeco.setVisible(false);
                markVender.setVisible(false);
                markRelatorios.setVisible(true);
                markCadastrar.setVisible(false);
                break;

            case "submenu":

                paneSubMenu.setVisible(true);

                //Marcacoes
                markComeco.setVisible(false);
                markVender.setVisible(false);
                markRelatorios.setVisible(false);
                markCadastrar.setVisible(true);

                break;

        }

    }

}
