package br.com.primeiroapp.minhapedida.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedida.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedida.model.Produto;

public class ProdutoDao extends DaoHelper<Produto> {

    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }
}
