package br.com.primeiroapp.minhapedidadb.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedidadb.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedidadb.model.Produto;

public class ProdutoDao extends DaoHelper<Produto> {

    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }
}
