package jordanarocha.Tabelas;

public class Atributo {

    public int id;
    public String nome;
    public String valor;

    public Atributo(String nome, String valor) {  // Usado quando inserindo novos atributos
        this.nome = nome;
        this.valor = valor;
    }

    public Atributo(int id, String nome, String valor) {  // Usado quando recuperando atributos do banco de dados
        this.id = id;
        this.nome = nome;
        this.valor = valor;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Atributos{" + "Nome=" + nome + ", Valor=" + valor + '}';
    }

}
