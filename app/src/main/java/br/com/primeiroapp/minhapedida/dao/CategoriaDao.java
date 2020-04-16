package br.com.primeiroapp.minhapedida.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedida.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedida.model.Categoria;

public class CategoriaDao extends DaoHelper<Categoria> {

    public CategoriaDao(Context c) {
        super(c, Categoria.class);
    }
}
