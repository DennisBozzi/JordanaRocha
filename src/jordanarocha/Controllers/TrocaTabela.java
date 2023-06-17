package jordanarocha.Controllers;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import jordanarocha.Tabelas.Cliente;
import jordanarocha.Tabelas.Produto;

public class TrocaTabela {

    //Variáveis para o método construtor
    public TableView<Produto> tabelaProdutos;
    public TableView<Cliente> tabelaClientes;
    public TableView<String> tabelaVendas;

    public Label labelClientes;
    public Label labelProdutos;
    public Label labelVendas;

    //Método construtor
    public TrocaTabela(TableView<Produto> tabelaProdutos, TableView<Cliente> tabelaClientes, TableView<String> tabelaVendas, Label labelClientes, Label labelProdutos, Label labelVendas) {
        this.tabelaProdutos = tabelaProdutos;
        this.tabelaClientes = tabelaClientes;
        this.tabelaVendas = tabelaVendas;

        this.labelClientes = labelClientes;
        this.labelProdutos = labelProdutos;
        this.labelVendas = labelVendas;
    }

    //Método para alterar as tabelas de consulta
    public void trocaTabela(String tabela) {

        switch (tabela) {
            case "clientes":
                tabelaClientes.setVisible(true);
                tabelaProdutos.setVisible(false);
                tabelaVendas.setVisible(false);

                labelClientes.setVisible(true);
                labelProdutos.setVisible(false);
                labelVendas.setVisible(false);
                break;

            case "produtos":
                tabelaClientes.setVisible(false);
                tabelaProdutos.setVisible(true);
                tabelaVendas.setVisible(false);

                labelClientes.setVisible(false);
                labelProdutos.setVisible(true);
                labelVendas.setVisible(false);
                break;

            case "vendas":
                tabelaClientes.setVisible(false);
                tabelaProdutos.setVisible(false);
                tabelaVendas.setVisible(true);

                labelClientes.setVisible(false);
                labelProdutos.setVisible(false);
                labelVendas.setVisible(true);
                break;
        }
    }

}
