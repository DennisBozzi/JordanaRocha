package jordanarocha.Tabelas;

public class Produto {

    private int idProduto;
    private String nomeProduto;
    private double valorProduto;
    private String acessorioProduto;
    private String ligaProduto;
    private String pedraProduto;
    private String tamanhoProduto;
    private byte[] fotoProduto;

    public Produto(int idProduto, String nomeProduto, double valorProduto, String acessorioProduto, String ligaProduto, String pedraProduto, String tamanhoProduto, byte[] fotoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.acessorioProduto = acessorioProduto;
        this.ligaProduto = ligaProduto;
        this.pedraProduto = pedraProduto;
        this.tamanhoProduto = tamanhoProduto;
        this.fotoProduto = fotoProduto;
    }

    public Produto(String nomeProduto, double valorProduto, String acessorioProduto, String ligaProduto, String pedraProduto, String tamanhoProduto, byte[] fotoProduto) {
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.acessorioProduto = acessorioProduto;
        this.ligaProduto = ligaProduto;
        this.pedraProduto = pedraProduto;
        this.tamanhoProduto = tamanhoProduto;
        this.fotoProduto = fotoProduto;
    }

    public Produto(int idProduto, String nomeProduto, double valorProduto, String acessorioProduto, String ligaProduto, String pedraProduto, String tamanhoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.acessorioProduto = acessorioProduto;
        this.ligaProduto = ligaProduto;
        this.pedraProduto = pedraProduto;
        this.tamanhoProduto = tamanhoProduto;
    }

    public Produto(int idProduto, byte[] fotoProduto) {
        this.idProduto = idProduto;
        this.fotoProduto = fotoProduto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public String getAcessorioProduto() {
        return acessorioProduto;
    }

    public void setAcessorioProduto(String acessorioProduto) {
        this.acessorioProduto = acessorioProduto;
    }

    public String getLigaProduto() {
        return ligaProduto;
    }

    public void setLigaProduto(String ligaProduto) {
        this.ligaProduto = ligaProduto;
    }

    public String getPedraProduto() {
        return pedraProduto;
    }

    public void setPedraProduto(String pedraProduto) {
        this.pedraProduto = pedraProduto;
    }

    public String getTamanhoProduto() {
        return tamanhoProduto;
    }

    public void setTamanhoProduto(String tamanhoProduto) {
        this.tamanhoProduto = tamanhoProduto;
    }

    public byte[] getFotoProduto() {
        return fotoProduto;
    }

    public void setFotoProduto(byte[] fotoProduto) {
        this.fotoProduto = fotoProduto;
    }

    @Override
    public String toString() {
        return "Produto{" + "idProduto=" + idProduto + ", nomeProduto=" + nomeProduto + ", valorProduto=" + valorProduto + ", acessorioProduto=" + acessorioProduto + ", ligaProduto=" + ligaProduto + ", pedraProduto=" + pedraProduto + ", tamanhoProduto=" + tamanhoProduto + ", fotoProduto=" + fotoProduto + '}';
    }

}
