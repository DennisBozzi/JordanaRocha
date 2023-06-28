package jordanarocha.Tabelas;

public class Cliente {

    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private String celular;
    private String email;
    private String observacao;

    public Cliente(int id, String nome, String cpf, String endereco, String celular, String email, String observacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.celular = celular;
        this.email = email;
        this.observacao = observacao;
    }

    public Cliente(String nome, String cpf, String email, String celular, String endereco, String observacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.celular = celular;
        this.email = email;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nome=" + nome + ", cpf=" + cpf + ", endereco=" + endereco + ", celular=" + celular + ", email=" + email + ", observacao=" + observacao + '}';
    }
}
