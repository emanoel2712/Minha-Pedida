package br.com.primeiroapp.minhapedida.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "comanda")
public class Comanda implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField(canBeNull = false)
    private String mesa;

    @ForeignCollectionField(eager = true)
    private Collection<ItemComanda> itensComanda;


    public Comanda() {
    }

    public Comanda(Integer id, String nome, String mesa, Collection<ItemComanda> itensComanda) {
        this.id = id;
        this.nome = nome;
        this.mesa = mesa;
        this.itensComanda = itensComanda;
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

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public Collection<ItemComanda> getItensComanda() {
        return itensComanda;
    }

    public void setItensComanda(Collection<ItemComanda> itensComanda) {
        this.itensComanda = itensComanda;
    }

    @Override
    public String toString() {
        return "Mesa: " + mesa +"\nLocal: " + nome;
    }
}
