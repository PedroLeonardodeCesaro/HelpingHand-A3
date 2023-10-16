package Model;

import DAO.EmprestimoDAO;
import java.sql.SQLException;
import java.util.*;

public class Emprestimo {
    
    // Atributos
    private int ID_emprestimo;
    private int ID_amigo;
    private int ID_ferramenta;
    private String data_ocorreu;
    private String data_devolucao;
    private Boolean status_emprestimo = true;
    private final EmprestimoDAO dao;

    // Método Construtor de Objeto Vazio
    public Emprestimo() {
        this.dao = new EmprestimoDAO(); //// inicializado não importa em qual construtor
    }
    
    // Método Construtor de Objeto, inserindo dados
    public Emprestimo(int ID_amigo, int ID_ferramenta, String data_ocorreu, String data_devolucao) {
        this.ID_amigo = ID_amigo;
        this.ID_ferramenta = ID_ferramenta;
        this.data_ocorreu = data_ocorreu;
        this.data_devolucao = data_devolucao;
        this.status_emprestimo = true;
        this.dao = new EmprestimoDAO(); // inicializado não importa em qual construtor
    }
    
        public Emprestimo(int ID_emprestimo, int ID_amigo, int ID_ferramenta, String data_ocorreu, String data_devolucao) {
        this.ID_emprestimo = ID_emprestimo;
        this.ID_amigo = ID_amigo;
        this.ID_ferramenta = ID_ferramenta;
        this.data_ocorreu = data_ocorreu;
        this.data_devolucao = data_devolucao;
        this.status_emprestimo = true;
        this.dao = new EmprestimoDAO(); // inicializado não importa em qual construtor
    }
    
   // Métodos GET e SET
    public int getID_emprestimo() {
        return ID_emprestimo;
    }

    public void setID_emprestimo(int ID_emprestimo) {
        this.ID_emprestimo = ID_emprestimo;
    }

    public int getID_amigo() {
        return ID_amigo;
    }

    public void setID_amigo(int ID_amigo) {
        this.ID_amigo = ID_amigo;
    }

    public int getID_ferramenta() {
        return ID_ferramenta;
    }

    public void setID_ferramenta(int ID_ferramenta) {
        this.ID_ferramenta = ID_ferramenta;
    }

    public String getData_ocorreu() {
        return data_ocorreu;
    }

    public void setData_ocorreu(String data_ocorreu) {
        this.data_ocorreu = data_ocorreu;
    }

    public String getData_devolucao() {
        return data_devolucao;
    }

    public void setData_devolucao(String data_devolucao) {
        this.data_devolucao = data_devolucao;
    }

    public Boolean getStatus_emprestimo() {
        return status_emprestimo;
    }

    public void setStatus_emprestimo(Boolean status_emprestimo) {
        this.status_emprestimo = status_emprestimo;
    }  

    @Override
    public String toString() {
        return "Emprestimo{" + "ID_emprestimo=" + ID_emprestimo + ", ID_amigo=" + ID_amigo + ", ID_ferramenta=" + ID_ferramenta + ", data_ocorreu=" + data_ocorreu + ", data_devolucao=" + data_devolucao + '}';
    }
    
    // Retorna a Lista de Emprestimos(objetos)
    public ArrayList visualizaEmprestimos() {
        return dao.visualizaEmprestimos();
    }
    
    // Cadastra novo emprestimo
    public boolean InsertEmprestimoDB(int ID_emprestimo, int ID_amigo, int ID_ferramenta, String data_ocorreu, String data_devolucao) throws SQLException {
        int id = this.maiorID() + 1;
        Emprestimo objeto = new Emprestimo(ID_emprestimo, ID_amigo, ID_ferramenta, data_ocorreu, data_devolucao);
        dao.InsertEmprestimoDB(objeto);
        return true;
    }
    
    // Deleta um emprestimo específico pelo seu campo ID
    public boolean DeleteEmprestimoBD(int id) {
        dao.DeleteEmprestimoBD(id);
        return true;
    }
    
    // Edita um emprestimo específico pelo seu campo ID
    public boolean UpdateEmprestimoBD(int ID_emprestimo, int ID_amigo, int ID_ferramenta, String data_ocorreu, String data_devolucao, Boolean status_emprestimo1) {
        Emprestimo objeto = new Emprestimo(ID_emprestimo, ID_amigo, ID_ferramenta, data_ocorreu, data_devolucao);
        dao.UpdateEmprestimoBD(objeto);
        return true;
    }
    
    // carrega dados de um emprestimo específico pelo seu ID
    public Emprestimo carregaEmprestimo(int id) {
        dao.carregaEmprestimo(id);
        return null;
    }
    
    // retorna o maior ID da nossa base de dados
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}
