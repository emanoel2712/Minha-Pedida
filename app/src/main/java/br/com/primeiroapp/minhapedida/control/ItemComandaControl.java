package br.com.primeiroapp.minhapedida.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.primeiroapp.minhapedida.R;
import br.com.primeiroapp.minhapedida.dao.ItemComandaDao;
import br.com.primeiroapp.minhapedida.model.Comanda;
import br.com.primeiroapp.minhapedida.model.ItemComanda;
import br.com.primeiroapp.minhapedida.model.Produto;
import br.com.primeiroapp.minhapedida.uteis.Constantes;
import br.com.primeiroapp.minhapedida.view.ProdutoItemComandaActivity;

public class ItemComandaControl {

    private Activity activity;

    private ListView lvItensComanda;

    private TextView tvTotal;

    private Produto produto;

    private ItemComanda itemComanda;

    private ItemComandaDao itemComandaDao;

    private List<ItemComanda> itemComandas;

    private ArrayAdapter<ItemComanda> adapterItemComanda;

    private Comanda comanda;

    public ItemComandaControl(Activity activity) {
        this.activity = activity;
        itemComandaDao = new ItemComandaDao(activity);
        comanda = getComanda();
        initComponents();
        getComanda();
        atualizarTvTotal();
    }

    private void initComponents(){

        tvTotal = activity.findViewById(R.id.tvTotal);
        lvItensComanda = activity.findViewById(R.id.lvItensComanda);

        configListView();

    }

    private void configListView(){

        if(comanda.getItensComanda() != null){
          itemComandas = new ArrayList<>(comanda.getItensComanda());
        }else {
            itemComandas = new ArrayList<>();
        }

        adapterItemComanda = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                itemComandas
        );

        lvItensComanda.setAdapter(adapterItemComanda);

        setCliqueLongo();
        setCliqueCurto();
    }

    private void setCliqueLongo(){
        lvItensComanda.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemComanda = adapterItemComanda.getItem(position);
                dialogExcluir(itemComanda);
                return true;
            }
        });
    }

    private void dialogExcluir(final ItemComanda item){

        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo item");
        alerta.setMessage(item.toString());
        alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemComanda = null;
            }
        });
        alerta.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if(itemComandaDao.getDao().delete(item)>0) {
                        adapterItemComanda.remove(item);
                        atualizarTvTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                itemComanda = null;
            }
        });
        alerta.show();
    }

    private void setCliqueCurto(){
        lvItensComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemComanda = adapterItemComanda.getItem(position);
                addMaisUm(itemComanda);
            }
        });
    }

    private void addMaisUm(final ItemComanda item){
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Mostrando item");
        alerta.setMessage("Adicionar  +1");
        alerta.setPositiveButton("+1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item.addMaisUm();
                try {
                    if(itemComandaDao.getDao().update(item)>0) {
                        adapterItemComanda.notifyDataSetChanged();
                        atualizarTvTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                itemComanda = null;
            }
        });
        alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemComanda = null;
            }
        });
        alerta.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == activity.RESULT_OK){
            if(requestCode== Constantes.REQUEST_ITEM){
                ItemComanda item = (ItemComanda) data.getSerializableExtra(Constantes.PARAM_ITEM);
                item.setComanda(comanda);
                try {
                    if(itemComandaDao.getDao().create(item)>0) {
                        adapterItemComanda.add(item);
                        atualizarTvTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if(resultCode==activity.RESULT_CANCELED){
            Toast.makeText(activity, "Ação cancelada", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarTvTotal(){
        tvTotal.setText(getTotal().toString());
    }

    private Double getTotal(){
        double total = 0;
        for(ItemComanda item : itemComandas){
            total += item.getSubTotal();
        }
        return total;
    }

    public void addProdutoComanda(){

        Intent it = new Intent(activity, ProdutoItemComandaActivity.class);
        activity.startActivityForResult(it, Constantes.REQUEST_ITEM);

    }

    private Comanda getComanda(){

        return (Comanda) activity.getIntent().getSerializableExtra(Constantes.PARAM_COMANDA);
    }

    public void fecharConta(){


        Intent it = new Intent();
        it.putExtra(Constantes.PARAM_COMANDA , comanda);
        activity.setResult(Activity.RESULT_OK, it);
        activity.finish();

    }

}
