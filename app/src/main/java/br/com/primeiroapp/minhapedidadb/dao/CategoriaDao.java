package br.com.primeiroapp.minhapedidadb.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedidadb.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedidadb.model.Categoria;

public class CategoriaDao extends DaoHelper<Categoria> {

    public CategoriaDao(Context c) {
        super(c, Categoria.class);
    }
}
