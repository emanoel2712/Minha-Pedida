package br.com.primeiroapp.minhapedidadb.dao.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import br.com.primeiroapp.minhapedidadb.model.Categoria;
import br.com.primeiroapp.minhapedidadb.model.Comanda;
import br.com.primeiroapp.minhapedidadb.model.ItemComanda;
import br.com.primeiroapp.minhapedidadb.model.Produto;

public class MyORMLiteHelper extends OrmLiteSqliteOpenHelper {
        private static final String DATABASE_NAME = "minha_pedida";
        private static final int DATABASE_VERSION = 3;

    public MyORMLiteHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Categoria.class);
            TableUtils.createTable(connectionSource, Comanda.class);
            TableUtils.createTable(connectionSource, ItemComanda.class);
            TableUtils.createTable(connectionSource, Produto.class);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        try {
            TableUtils.dropTable(connectionSource, Categoria.class , true);
            TableUtils.dropTable(connectionSource, Comanda.class , true);
            TableUtils.dropTable(connectionSource, ItemComanda.class , true);
            TableUtils.dropTable(connectionSource, Produto.class , true);
            onCreate(sqLiteDatabase, connectionSource);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
