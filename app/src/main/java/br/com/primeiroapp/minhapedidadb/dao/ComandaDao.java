package br.com.primeiroapp.minhapedidadb.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedidadb.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedidadb.model.Comanda;

public class ComandaDao extends DaoHelper<Comanda> {
    public ComandaDao(Context c) {
        super(c, Comanda.class);
    }
}
