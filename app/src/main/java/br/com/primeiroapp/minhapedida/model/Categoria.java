package br.com.primeiroapp.minhapedida.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "categoria")
public class Categoria implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @ForeignCollectionField(eager = true)
    private Collection<Produto> produtos;


    public Categoria() {
    }

    public Categoria(Integer id, String nome, Collection<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.produtos = produtos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Collection<Produto> produtos) {
        this.produtos = produtos;
    }

    private String getQtdProdutos(){
        if (produtos == null){
            return "(0)";
        }else{
            return "("+produtos.size()+")";
        }
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " +  getQtdProdutos();
    }
}
