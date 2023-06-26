package jordanarocha.Tabelas;

import java.sql.Timestamp;

public class Venda {

    private int idVendas;
    private int idVendedor;
    private int idCliente;
    private double valorTotal;
    private String formaPagamento;
    private Timestamp dataVenda;
    private double comissaoVenda;
    private String nomeVendedor;
    private String nomeCliente;

    public Venda() {

    }

    public Venda(int idVendas, int idVendedor, int idCliente, double valorTotal, String formaPagamento, Timestamp dataVenda, double comissaoVenda) {
        this.idVendas = idVendas;
        this.idVendedor = idVendedor;
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.dataVenda = dataVenda;
        this.comissaoVenda = comissaoVenda;
    }

    public Venda(int idVendas, int idVendedor, int idCliente, double valorTotal, String formaPagamento, Timestamp dataVenda, double comissaoVenda, String nomeVendedor, String nomeCliente) {
        this.idVendas = idVendas;
        this.idVendedor = idVendedor;
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.dataVenda = dataVenda;
        this.comissaoVenda = comissaoVenda;
        this.nomeVendedor = nomeVendedor;
        this.nomeCliente = nomeCliente;
    }

    public int getIdVendas() {
        return idVendas;
    }

    public void setIdVendas(int idVendas) {
        this.idVendas = idVendas;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Timestamp getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Timestamp dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getComissaoVenda() {
        return comissaoVenda;
    }

    public void setComissaoVenda(double comissaoVenda) {
        this.comissaoVenda = comissaoVenda;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    @Override
    public String toString() {
        return "Venda{" + "idVendas=" + idVendas + ", idVendedor=" + idVendedor + ", idCliente=" + idCliente + ", valorTotal=" + valorTotal + ", formaPagamento=" + formaPagamento + ", dataVenda=" + dataVenda + ", comissaoVenda=" + comissaoVenda + ", nomeVendedor=" + nomeVendedor + ", nomeCliente=" + nomeCliente + '}';
    }

}
