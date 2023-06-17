package jordanarocha.Tabelas;

import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Blob;

public class Vendedor {

    private int idVendedor;
    private double comissaoVendedor;
    private String nomeVendedor;
    private String cpfVendedor;
    private String rgVendedor;
    private String enderecoVendedor;
    private String celularVendedor;
    private byte[] fotoVendedor;
    private String emailVendedor;
    private String usuarioVendedor;
    private Date dataDemissao;
    private Timestamp dataAdmissao;
    private String senhaVendedor;
    private String observacaoVendedor;
    private int statusVendedor;

    public Vendedor(int idVendedor, String nomeVendedor, String cpfVendedor, String usuarioVendedor, String senhaVendedor, byte[] fotoVendedor, int statusVendedor) {
        this.idVendedor = idVendedor;
        this.nomeVendedor = nomeVendedor;
        this.cpfVendedor = cpfVendedor;
        this.fotoVendedor = fotoVendedor;
        this.usuarioVendedor = usuarioVendedor;
        this.senhaVendedor = senhaVendedor;
        this.statusVendedor = statusVendedor;
    }

    // Construtor sem id e sem datas para criar um novo objeto
    public Vendedor(double comissaoVendedor, String nomeVendedor, String cpfVendedor, String rgVendedor,
            String enderecoVendedor, String celularVendedor, byte[] fotoVendedor, String emailVendedor,
            String usuarioVendedor, String senhaVendedor, String observacaoVendedor) {
        this.comissaoVendedor = comissaoVendedor;
        this.nomeVendedor = nomeVendedor;
        this.cpfVendedor = cpfVendedor;
        this.rgVendedor = rgVendedor;
        this.enderecoVendedor = enderecoVendedor;
        this.celularVendedor = celularVendedor;
        this.fotoVendedor = fotoVendedor;
        this.emailVendedor = emailVendedor;
        this.usuarioVendedor = usuarioVendedor;
        this.senhaVendedor = senhaVendedor;
        this.observacaoVendedor = observacaoVendedor;
    }

    //Construtor sem id, datas, fotos, status, para a atualização do Vendedor
    public Vendedor(int idVendedor, String nomeVendedor, String cpfVendedor, String rgVendedor, double comissaoVendedor, String celularVendedor, String emailVendedor, String enderecoVendedor, String observacaoVendedor, String usuarioVendedor, String senhaVendedor) {
        this.idVendedor = idVendedor;
        this.nomeVendedor = nomeVendedor;
        this.cpfVendedor = cpfVendedor;
        this.rgVendedor = rgVendedor;
        this.comissaoVendedor = comissaoVendedor;
        this.celularVendedor = celularVendedor;
        this.emailVendedor = emailVendedor;
        this.enderecoVendedor = enderecoVendedor;
        this.observacaoVendedor = observacaoVendedor;
        this.usuarioVendedor = usuarioVendedor;
        this.senhaVendedor = senhaVendedor;
    }

    //Construtor com id e foto para atualizar foto vendedor
    public Vendedor(int idVendedor, byte[] fotoVendedor) {
        this.idVendedor = idVendedor;
        this.fotoVendedor = fotoVendedor;
    }

    //Construtor com id e status para desabilitar vendedo
    public Vendedor(int idVendedor, int statusVendedor) {
        this.idVendedor = idVendedor;
        this.statusVendedor = statusVendedor;
    }

    // Construtor completo
    public Vendedor(int idVendedor, double comissaoVendedor, String nomeVendedor, String cpfVendedor, String rgVendedor, String enderecoVendedor, String celularVendedor, byte[] fotoVendedor, String emailVendedor,
            String usuarioVendedor, Date dataDemissao, Timestamp dataAdmissao, String senhaVendedor, String observacaoVendedor, int statusVendedor) {
        this.idVendedor = idVendedor;
        this.comissaoVendedor = comissaoVendedor;
        this.nomeVendedor = nomeVendedor;
        this.cpfVendedor = cpfVendedor;
        this.rgVendedor = rgVendedor;
        this.enderecoVendedor = enderecoVendedor;
        this.celularVendedor = celularVendedor;
        this.fotoVendedor = fotoVendedor;
        this.emailVendedor = emailVendedor;
        this.usuarioVendedor = usuarioVendedor;
        this.dataDemissao = dataDemissao;
        this.dataAdmissao = dataAdmissao;
        this.senhaVendedor = senhaVendedor;
        this.observacaoVendedor = observacaoVendedor;
        this.statusVendedor = statusVendedor;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public double getComissaoVendedor() {
        return comissaoVendedor;
    }

    public void setComissaoVendedor(double comissaoVendedor) {
        this.comissaoVendedor = comissaoVendedor;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public String getCpfVendedor() {
        return cpfVendedor;
    }

    public void setCpfVendedor(String cpfVendedor) {
        this.cpfVendedor = cpfVendedor;
    }

    public String getRgVendedor() {
        return rgVendedor;
    }

    public void setRgVendedor(String rgVendedor) {
        this.rgVendedor = rgVendedor;
    }

    public String getEnderecoVendedor() {
        return enderecoVendedor;
    }

    public void setEnderecoVendedor(String enderecoVendedor) {
        this.enderecoVendedor = enderecoVendedor;
    }

    public String getCelularVendedor() {
        return celularVendedor;
    }

    public void setCelularVendedor(String celularVendedor) {
        this.celularVendedor = celularVendedor;
    }

    public byte[] getFotoVendedor() {
        return fotoVendedor;
    }

    public void setFotoVendedor(byte[] fotoVendedor) {
        this.fotoVendedor = fotoVendedor;
    }

    public String getEmailVendedor() {
        return emailVendedor;
    }

    public void setEmailVendedor(String emailVendedor) {
        this.emailVendedor = emailVendedor;
    }

    public String getUsuarioVendedor() {
        return usuarioVendedor;
    }

    public void setUsuarioVendedor(String usuarioVendedor) {
        this.usuarioVendedor = usuarioVendedor;
    }

    public Date getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Date dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Timestamp getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Timestamp dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getSenhaVendedor() {
        return senhaVendedor;
    }

    public void setSenhaVendedor(String senhaVendedor) {
        this.senhaVendedor = senhaVendedor;
    }

    public String getobservacaoVendedor() {
        return observacaoVendedor;
    }

    public void setobservacaoVendedor(String observacaoVendedor) {
        this.observacaoVendedor = observacaoVendedor;
    }

    public int getStatusVendedor() {
        return statusVendedor;
    }

    public void setStatusVendedor(int statusVendedor) {
        this.statusVendedor = statusVendedor;
    }

    @Override
    public String toString() {
        return "Vendedor{" + "idVendedor=" + idVendedor + ", comissaoVendedor=" + comissaoVendedor + ", nomeVendedor=" + nomeVendedor + ", cpfVendedor=" + cpfVendedor + ", rgVendedor=" + rgVendedor + ", enderecoVendedor=" + enderecoVendedor + ", celularVendedor=" + celularVendedor + ", fotoVendedor=" + fotoVendedor + ", emailVendedor=" + emailVendedor + ", usuarioVendedor=" + usuarioVendedor + ", dataDemissao=" + dataDemissao + ", dataAdmissao=" + dataAdmissao + ", senhaVendedor=" + senhaVendedor + ", observacaoVendedor=" + observacaoVendedor + ", statusVendedor=" + statusVendedor + '}';
    }

}
