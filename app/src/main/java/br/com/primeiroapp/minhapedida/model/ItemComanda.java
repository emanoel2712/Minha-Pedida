package br.com.primeiroapp.minhapedida.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = "item_comanda")
public class ItemComanda implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private Integer quantidade;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Comanda comanda;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Produto produto;

    public ItemComanda() {
    }

    public ItemComanda(Integer id, Integer quantidade, Comanda comanda, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.comanda = comanda;
        this.produto = produto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getSubTotal(){
        return produto.getValor() * quantidade;
    }

    public void addMaisUm(){
        this.quantidade++;
    }

    @Override
    public String toString() {
        return produto.getNome() + " - R$ " + produto.getValor() + " - " + quantidade + "\nR$ " + getSubTotal();
    }
}
