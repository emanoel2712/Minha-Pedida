package br.com.primeiroapp.minhapedida.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedida.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedida.model.ItemComanda;

public class ItemComandaDao extends DaoHelper<ItemComanda> {

    public ItemComandaDao(Context c) {
        super(c, ItemComanda.class);
    }
}
