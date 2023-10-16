package Model;

import DAO.FerramentaDAO;
import java.sql.SQLException;
import java.util.*;

public class Ferramenta {
    
    // Atributos
    private int ID_ferramenta;
    private String nome_ferramenta;
    private String marca;
    private double custo_aquisicao;
    private final FerramentaDAO dao;
    
    // Método Construtor de Objeto Vazio
    public Ferramenta() {
        this.dao = new FerramentaDAO(); // inicializado não importa em qual construtor
    }
    
    // Método Construtor de Objeto, inserindo dados
    public Ferramenta(int ID_ferramenta, String nome_ferramenta, String marca, double custo_aquisicao) {
        this.ID_ferramenta = ID_ferramenta;
        this.nome_ferramenta = nome_ferramenta;
        this.marca = marca;
        this.custo_aquisicao = custo_aquisicao;
        this.dao = new FerramentaDAO(); // inicializado não importa em qual construtor
    }
    
    // Métodos GET e SET
    public int getID_ferramenta() {
        return ID_ferramenta;
    }

    public void setID_ferramenta(int ID_ferramenta) {
        this.ID_ferramenta = ID_ferramenta;
    }

    public String getNome_ferramenta() {
        return nome_ferramenta;
    }

    public void setNome_ferramenta(String nome_ferramenta) {
        this.nome_ferramenta = nome_ferramenta;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getCusto_aquisicao() {
        return custo_aquisicao;
    }

    public void setCusto_aquisicao(double custo_aquisicao) {
        this.custo_aquisicao = custo_aquisicao;
    }
    
    
    @Override
    public String toString() {
        return "Ferramenta{" + "ID_ferramenta=" + ID_ferramenta + ", nome_ferramenta=" + nome_ferramenta + ", marca=" + marca + ", custo_aquisicao=" + custo_aquisicao + '}';
    }
    
    
    
    // Retorna a Lista de Ferramentas(objetos)
    public ArrayList getVisualizaFerramentas() {
        return dao.visualizaFerramentas();
    }

    // Cadastra nova ferramenta
    public boolean InsertFerramentaDB(String nome_ferramenta, String marca, double custo_aquisicao) throws SQLException {
        int id = this.maiorID() + 1;
        Ferramenta objeto = new Ferramenta(id, nome_ferramenta, marca, custo_aquisicao);
        dao.InsertFerramentaDB(objeto);
        return true;
    }

    // Deleta uma ferramenta espec�fica pelo seu campo ID
    public boolean DeleteFerramentaBD(int ID_ferramenta) {
        dao.DeleteFerramentaBD(ID_ferramenta);
        return true;
    }

    // Edita uma ferramenta específica pelo seu campo ID
    public boolean UpdateFerramentaBD(int ID_ferramenta, String nome_ferramenta, String marca, double custo_aquisicao) {
        Ferramenta objeto = new Ferramenta(ID_ferramenta, nome_ferramenta, marca, custo_aquisicao);
        dao.UpdateFerramentaBD(objeto);
        return true;
    }

    // carrega dados de uma ferramenta específica pelo seu ID
    public Ferramenta carregaFerramenta(int id) {
        dao.carregaFerramenta(id);
        return null;
    }

    // retorna o maior ID da nossa base de dados
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}
