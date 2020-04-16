package br.com.primeiroapp.minhapedida.control;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.primeiroapp.minhapedida.R;
import br.com.primeiroapp.minhapedida.dao.CategoriaDao;
import br.com.primeiroapp.minhapedida.model.Categoria;
import br.com.primeiroapp.minhapedida.model.CategoriaBO;

public class CategoriaControl {

    private Activity activity;

    private EditText editNome;

    private ListView lvCategorias;
    private List<Categoria> categorias;

    private Categoria categoria;

    private ArrayAdapter<Categoria> adapterCategorias;

    private CategoriaDao categoriaDao;


    public CategoriaControl(Activity activity) {
        this.activity = activity;
        categoriaDao = new CategoriaDao(activity);
        initComponents();
    }

    private void initComponents() {
        editNome = activity.findViewById(R.id.editNome);
        lvCategorias = activity.findViewById(R.id.lvCategoria);

        configListView();
    }

    private void configListView() {

        try {
            categorias = categoriaDao.getDao().queryForAll();
            if (categorias == null) {
                categorias = new ArrayList<>();
            }

            adapterCategorias = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    categorias
            );

            lvCategorias.setAdapter(adapterCategorias);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        setCliqueLongo();
        setCliqueCurto();
    }

    private void setCliqueLongo() {
        lvCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                categoria = adapterCategorias.getItem(position);
                dialogExcluir(categoria);
                return true;
            }
        });

    }

    private void dialogExcluir(final Categoria cat) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo item");
        alerta.setMessage(cat.toString());
        alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categoria = null;
            }
        });
        alerta.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (categoriaDao.getDao().delete(cat) > 0) {
                        adapterCategorias.remove(cat);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                categoria = null;
            }
        });
        alerta.show();
    }

    private void setCliqueCurto() {
        lvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoria = adapterCategorias.getItem(i);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Visualizando estado");
                alerta.setMessage(categoria.toString());
                alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoria = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        carregarForm(categoria);
                    }
                });
                alerta.show();
            }
        });

    }

    private void carregarForm(Categoria c) {
        editNome.setText(c.getNome());
    }


    public void salvarAction() {


        if (categoria == null) {
            categoria = getDadosForm();
        } else {
            categoria.setNome(editNome.getText().toString());
        }
        if (validaCampos(categoria)) {

            try {
                Dao.CreateOrUpdateStatus res = categoriaDao.getDao().createOrUpdate(categoria);
                if (res.isCreated()) {
                    adapterCategorias.add(categoria);
                } else {

                    Categoria c = getDadosForm();

                    atualizarCategoria(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            configListView();
            editNome.setText("");
            categoria = null;
        } else {
            editNome.setError("Preencha o campo nome");
            editNome.requestFocus();
        }
    }

    private void atualizarCategoria(Categoria c) {
        categoria.setNome(c.getNome());

        adapterCategorias.notifyDataSetChanged();
    }

    private Categoria getDadosForm() {

        Categoria c = new Categoria();
        c.setNome(editNome.getText().toString());

        return c;

    }


    private boolean validaCampos(Categoria c) {
        return CategoriaBO.validaCategoria(c);
    }
}
