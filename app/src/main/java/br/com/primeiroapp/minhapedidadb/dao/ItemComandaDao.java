package br.com.primeiroapp.minhapedidadb.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedidadb.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedidadb.model.ItemComanda;

public class ItemComandaDao extends DaoHelper<ItemComanda> {

    public ItemComandaDao(Context c) {
        super(c, ItemComanda.class);
    }
}
