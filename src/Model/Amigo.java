package Model;

import DAO.AmigoDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Amigo {

    // Atributos
    private int ID_amigo;
    private String nome_amigo;
    private String telefone;
    private String email;
    private int quantidade_emprestimo;
    private final AmigoDAO dao;

    // Método Construtor de Objeto Vazio
    public Amigo() {
        this.dao = new AmigoDAO(); // inicializado não importa em qual construtor
    }

    // Método Construtor de Objeto, inserindo dados
    public Amigo(int ID_amigo, String nome_amigo, String telefone, String email, int quantidade_emprestimo) {
        this.ID_amigo = ID_amigo;
        this.nome_amigo = nome_amigo;
        this.telefone = telefone;
        this.email = email;
        this.quantidade_emprestimo = quantidade_emprestimo;
        this.dao = new AmigoDAO(); // inicializado não importa em qual construtor
    }

    // Métodos GET e SET
    public int getID_amigo() {
        return ID_amigo;
    }

    public void setID_amigo(int ID_amigo) {
        this.ID_amigo = ID_amigo;
    }

    public String getNome_amigo() {
        return nome_amigo;
    }

    public void setNome_amigo(String nome_amigo) {
        this.nome_amigo = nome_amigo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuantidade_emprestimo() {
        return quantidade_emprestimo;
    }

    public void setQuantidade_emprestimo(int quantidade_emprestimo) {
        this.quantidade_emprestimo = quantidade_emprestimo;
    }

    @Override
    public String toString() {
        return "Amigo{" + "ID_amigo=" + ID_amigo + ", nome_amigo=" + nome_amigo + ", telefone=" + telefone + ", email=" + email + ", quantidade_emprestimo=" + quantidade_emprestimo + '}';
    }

    // Retorna a Lista de Amigos(objetos)
    public ArrayList getVisualizaAmigos() {
        return dao.getVisualizaAmigos();
    }

    // Cadastra novo Amigo
    public boolean InsertAmigoDB(String nome_amigo, String telefone, String email) throws SQLException {
        int id = this.maiorID() + 1;
        Amigo objeto = new Amigo(id, nome_amigo, telefone, email, quantidade_emprestimo);
        dao.InsertAmigoDB(objeto);
        return true;

    }

    // Deleta um amigo específico pelo seu campo ID
    public boolean DeleteAmigoBD(int ID_amigo) {
        dao.DeleteAmigoBD(ID_amigo);
        return true;
    }

    // Edita um amigo específico pelo seu campo ID
    public boolean UpdateAmigoBD(int ID_amigo, String nome_amigo, String telefone, String email) {
        Amigo objeto = new Amigo(ID_amigo, nome_amigo, telefone, email, quantidade_emprestimo);
        dao.UpdateAmigoBD(objeto);
        return true;
    }

    // carrega dados de um amigo específico pelo seu ID
    public Amigo carregaAmigo(int id) {
        dao.carregaAmigo(id);
        return null;
    }

    // retorna o maior ID da nossa base de dados
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }

    // retorna amigo que mais fez empréstimo
    public Amigo visualizar_locatario_que_mais_fez_emprestimos() {
        return dao.visualizar_locatario_que_mais_fez_emprestimos();
    }

    public ArrayList<Amigo> visualizar_quem_nunca_devolveu_ferramenta() throws ParseException {
        return dao.visualizar_quem_nunca_devolveu_ferramenta();
    }   
}
