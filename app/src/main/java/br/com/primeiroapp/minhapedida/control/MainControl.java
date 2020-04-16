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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.primeiroapp.minhapedida.R;
import br.com.primeiroapp.minhapedida.dao.ComandaDao;
import br.com.primeiroapp.minhapedida.model.Comanda;
import br.com.primeiroapp.minhapedida.model.ComandaBO;
import br.com.primeiroapp.minhapedida.uteis.Constantes;
import br.com.primeiroapp.minhapedida.view.ItemComandaActivity;

public class MainControl {

    private Activity activity;

    private EditText editNrMesa;

    private EditText editNomeBar;

    private ListView lvComandas;

    private Comanda comanda;

    private ComandaDao comandaDao;

    private List<Comanda> comandas;

    private ArrayAdapter<Comanda> adapterComandas;


    public MainControl(Activity activity) {
        this.activity = activity;
        comandaDao = new ComandaDao(activity);

        initComponents();
    }

    private void initComponents() {

        editNrMesa = activity.findViewById(R.id.editNrMesa);

        editNomeBar = activity.findViewById(R.id.editNomeBar);

        lvComandas = activity.findViewById(R.id.lvComandas);

        configListViewItens();

    }

    public void configListViewItens() {

        try {
            comandas = comandaDao.getDao().queryForAll();
        } catch (Exception e) {
            comandas = new ArrayList<>();
        }

        adapterComandas = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                comandas
        );

        lvComandas.setAdapter(adapterComandas);

        setCliqueLongo();
        setCliqueCurto();
    }

    private void setCliqueLongo() {

        lvComandas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                comanda = adapterComandas.getItem(i);
                confirmaExclusao(comanda);
                return true;
            }
        });
    }

    private void confirmaExclusao(final Comanda coma) {
        final AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo estado");
        alerta.setMessage("Deseja excluir o item " + coma.getNome() + " ?");
        alerta.setNegativeButton("Não", null);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (comandaDao.getDao().delete(coma) > 0) {
                        adapterComandas.remove(coma);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                comanda = null;
            }
        });
        alerta.show();

    }

    private void setCliqueCurto() {

        lvComandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int indexList, long l) {
                comanda = adapterComandas.getItem(indexList);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Visualizando Item");
                alerta.setMessage(comanda.getNome());

                alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        comanda = null;
                    }
                });
                alerta.setPositiveButton("Adicionar Produto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        comanda = adapterComandas.getItem(indexList);
                        Intent it = new Intent(activity, ItemComandaActivity.class);
                        it.putExtra(Constantes.PARAM_COMANDA, comanda);
                        activity.startActivity(it);
                    }
                });
                alerta.show();
            }
        });
    }


    public void adicionarComanda() {
        chamaTelaItemComanda();

    }

    private void chamaTelaItemComanda() {
        comanda = getDadosForm();
        if (validaCampos(comanda)) {

            Intent it = new Intent(activity, ItemComandaActivity.class);
            try {
                comandaDao.getDao().create(comanda);
                adapterComandas.add(comanda);
                adapterComandas.notifyDataSetChanged();

                it.putExtra(Constantes.PARAM_COMANDA, comanda);
                activity.startActivity(it);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            limparCampos();
            comanda = null;
        }
    }

    private boolean validaCampos(Comanda c) {

        if (!ComandaBO.validaNome(c)) {
            editNomeBar.setError("Prencha o campo nome");
            editNomeBar.requestFocus();
            return false;
        }
        if (!ComandaBO.validaNr(c)) {
            editNrMesa.setError("Preencha o numero da mesa");
            editNrMesa.requestFocus();
            return false;
        }
        return true;

    }

    private Comanda getDadosForm() {

        Comanda c = new Comanda();
        c.setMesa(editNrMesa.getText().toString());
        c.setNome(editNomeBar.getText().toString());

        return c;

    }


    public void limparCampos() {
        editNomeBar.setText("");
        editNrMesa.setText("");
    }

}
