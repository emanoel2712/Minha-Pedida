package br.com.primeiroapp.minhapedida.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.primeiroapp.minhapedida.R;
import br.com.primeiroapp.minhapedida.dao.CategoriaDao;
import br.com.primeiroapp.minhapedida.dao.ProdutoDao;
import br.com.primeiroapp.minhapedida.model.Categoria;
import br.com.primeiroapp.minhapedida.model.Produto;
import br.com.primeiroapp.minhapedida.model.ProdutoBO;
import br.com.primeiroapp.minhapedida.view.CategoriaActivity;

public class ProdutoControl {

    private Activity activity;

    private EditText editNome;
    private EditText editValor;
    private Spinner spCategoria;
    private ListView lvProdutos;

    private Produto produto;
    private List<Produto> produtos;
    private ArrayAdapter<Categoria> adapterCategoria;
    private ArrayAdapter<Produto> adapterProduto;

    private ProdutoDao produtoDao;
    private CategoriaDao categoriaDao;

    public ProdutoControl(Activity activity) {
        this.activity = activity;
        produtoDao = new ProdutoDao(activity);
        categoriaDao = new CategoriaDao(activity);
        initConponents();
    }

    private void initConponents() {
        spCategoria = activity.findViewById(R.id.spCategoria);
        lvProdutos = activity.findViewById(R.id.lvProduto);
        editNome = activity.findViewById(R.id.editNome);
        editValor = activity.findViewById(R.id.editPreco);
        configSpinner();
        configListViewItens();
    }

    private void configListViewItens() {

        try {
            produtos = produtoDao.getDao().queryForAll();
        } catch (Exception e) {
            produtos = new ArrayList<>();
        }

        adapterProduto = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                produtos
        );

        lvProdutos.setAdapter(adapterProduto);
        setCliqueCurto();
        setCliqueLongo();
    }

    public void configSpinner() {
        try {
            List<Categoria> categorias = categoriaDao.getDao().queryForAll();

            if (categorias == null) {
                categorias = new ArrayList<>();
            }

            adapterCategoria = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_item,
                    categorias
            );
            spCategoria.setAdapter(adapterCategoria);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setCliqueLongo() {
        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = adapterProduto.getItem(position);
                dialogExcluir(produto);
                return true;
            }
        });

    }

    private void dialogExcluir(final Produto p) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo item");
        alerta.setMessage(p.toString());
        alerta.setNeutralButton("Fechar",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                produto = null;
            }
        });
        alerta.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (produtoDao.getDao().delete(p) > 0) {
                        adapterProduto.remove(p);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                produto = null;
            }
        });
        alerta.show();
    }

    public void salvarAction() {
        if (produto == null) {
            produto = getDadosForm();
        } else {
            produto.setNome(editNome.getText().toString());
            produto.setValor(editValor.getText().toString());
        }

        if (validaCampos(produto)) {

            try {

                Dao.CreateOrUpdateStatus res = produtoDao.getDao().createOrUpdate(produto);
                if (res.isCreated()) {
                    adapterProduto.add(produto);
                } else {

                    Produto p = getDadosForm();

                    atualizarProduto(p);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            configListViewItens();
            configSpinner();
            editNome.setText("");
            editValor.setText("");
            produto = null;
        }
    }

    private boolean validaCampos(Produto p) {

        if (!ProdutoBO.validaNome(p)) {
            editNome.setError("Preencha o campo nome");
            editNome.requestFocus();
            return false;
        }

        if (!ProdutoBO.validaPreco(p)) {
            Toast.makeText(activity, "Preencha com um preço valido", Toast.LENGTH_SHORT).show();
            editValor.requestFocus();
            return false;
        }

        return true;

    }

    private void atualizarProduto(Produto p) {
        produto.setNome(p.getNome());
        produto.setValor(p.getValor());

        adapterProduto.notifyDataSetChanged();
    }

    private void setCliqueCurto() {


        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                produto = adapterProduto.getItem(i);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Visualizando Produto");
                alerta.setMessage(produto.toString());
                alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        produto = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        carregarForm(produto);
                    }
                });
                alerta.show();
            }
        });

    }

    private void populaSpinner() {

        for (int i = 0; i < adapterCategoria.getCount(); i++) {
            Categoria c = adapterCategoria.getItem(i);
            if (c.getId() == produto.getCategoria().getId()) {
                spCategoria.setSelection(i);
                break;
            }
        }

    }

    private void carregarForm(Produto p) {
        editNome.setText(p.getNome());
        editValor.setText(p.getValor().toString());
        populaSpinner();
    }

    private Produto getDadosForm() {

        Produto p = new Produto();
        p.setNome(editNome.getText().toString());
        p.setValor(editValor.getText().toString());
        p.setCategoria((Categoria) spCategoria.getSelectedItem());
        return p;
    }

    public void adicionarCategoria() {

        Intent it = new Intent(activity, CategoriaActivity.class);
        activity.startActivity(it);

    }

}
