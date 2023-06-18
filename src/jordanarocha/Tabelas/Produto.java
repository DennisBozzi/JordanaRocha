package jordanarocha.Tabelas;

import java.util.Arrays;
import java.util.Objects;

public class Produto {

    private int idProduto;
    private String nomeProduto;
    private double valorProduto;
    private String acessorioProduto;
    private String ligaProduto;
    private String pedraProduto;
    private String tamanhoProduto;
    private byte[] fotoProduto;
    private int statusProduto;

    public Produto(){
        
    }
    
    public Produto(int idProduto, String nomeProduto, double valorProduto, String acessorioProduto, String ligaProduto, String pedraProduto, String tamanhoProduto, byte[] fotoProduto, int statusProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.acessorioProduto = acessorioProduto;
        this.ligaProduto = ligaProduto;
        this.pedraProduto = pedraProduto;
        this.tamanhoProduto = tamanhoProduto;
        this.fotoProduto = fotoProduto;
        this.statusProduto = statusProduto;
    }

    
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

    //Método para atualizar
    public Produto(int idProduto, String nomeProduto, double valorProduto, String acessorioProduto, String ligaProduto, String pedraProduto, String tamanhoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.acessorioProduto = acessorioProduto;
        this.ligaProduto = ligaProduto;
        this.pedraProduto = pedraProduto;
        this.tamanhoProduto = tamanhoProduto;
    }

    public Produto(int idProduto, String nomeProduto, double valorProduto, byte[] fotoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.fotoProduto = fotoProduto;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.idProduto;
        hash = 61 * hash + Objects.hashCode(this.nomeProduto);
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.valorProduto) ^ (Double.doubleToLongBits(this.valorProduto) >>> 32));
        hash = 61 * hash + Objects.hashCode(this.acessorioProduto);
        hash = 61 * hash + Objects.hashCode(this.ligaProduto);
        hash = 61 * hash + Objects.hashCode(this.pedraProduto);
        hash = 61 * hash + Objects.hashCode(this.tamanhoProduto);
        hash = 61 * hash + Arrays.hashCode(this.fotoProduto);
        hash = 61 * hash + this.statusProduto;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (this.idProduto != other.idProduto) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorProduto) != Double.doubleToLongBits(other.valorProduto)) {
            return false;
        }
        if (this.statusProduto != other.statusProduto) {
            return false;
        }
        if (!Objects.equals(this.nomeProduto, other.nomeProduto)) {
            return false;
        }
        if (!Objects.equals(this.acessorioProduto, other.acessorioProduto)) {
            return false;
        }
        if (!Objects.equals(this.ligaProduto, other.ligaProduto)) {
            return false;
        }
        if (!Objects.equals(this.pedraProduto, other.pedraProduto)) {
            return false;
        }
        if (!Objects.equals(this.tamanhoProduto, other.tamanhoProduto)) {
            return false;
        }
        return Arrays.equals(this.fotoProduto, other.fotoProduto);
    }

}
