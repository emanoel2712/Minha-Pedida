package br.com.primeiroapp.minhapedidadb.control;

import android.app.Activity;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.primeiroapp.minhapedidadb.R;
import br.com.primeiroapp.minhapedidadb.dao.ProdutoDao;
import br.com.primeiroapp.minhapedidadb.model.ItemComanda;
import br.com.primeiroapp.minhapedidadb.model.Produto;
import br.com.primeiroapp.minhapedidadb.uteis.Constantes;
import br.com.primeiroapp.minhapedidadb.view.NovoProdutoActivity;

public class ProdutoComandaControl {

    private Activity activity;
    private Spinner spProdutos;
    private NumberPicker npQuantidade;

    private ProdutoDao produtoDao;
    private List<Produto> produtos;

    private ArrayAdapter<Produto> adapterProduto;

    public ProdutoComandaControl(Activity activity) {
        this.activity = activity;
        produtoDao = new ProdutoDao(activity);
        initComponents();
    }

    private void initComponents(){

        spProdutos = activity.findViewById(R.id.spProduto);
        npQuantidade = activity.findViewById(R.id.npQuantidade);

        configSpinner();
        configNumberPicker();

    }

    public void configSpinner(){

        try {
            produtos = produtoDao.getDao().queryForAll();
            if(produtos == null){
                produtos = new ArrayList<>();
            }

            adapterProduto = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_item,
                    produtos
            );
            spProdutos.setAdapter(adapterProduto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configNumberPicker(){
        npQuantidade.setMinValue(1);
        npQuantidade.setMaxValue(99);
    }

    private ItemComanda getDadosForm(){
        ItemComanda i = new ItemComanda();
        i.setProduto((Produto) spProdutos.getSelectedItem());
        i.setQuantidade(npQuantidade.getValue());
        return i;
    }

    public void enviarItemAction(){
        ItemComanda newItem = getDadosForm();
        Intent it = new Intent();
        it.putExtra(Constantes.PARAM_ITEM, newItem);
        activity.setResult(activity.RESULT_OK, it);
        activity.finish();
    }

    public void cancelarAction(){
        activity.setResult(activity.RESULT_CANCELED);
        activity.finish();
    }

    public void novoProduto(){

        Intent it = new Intent(activity , NovoProdutoActivity.class);
        activity.startActivity(it);
    }
}
