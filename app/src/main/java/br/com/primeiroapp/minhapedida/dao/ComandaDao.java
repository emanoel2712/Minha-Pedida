package br.com.primeiroapp.minhapedida.dao;

import android.content.Context;

import br.com.primeiroapp.minhapedida.dao.helpers.DaoHelper;
import br.com.primeiroapp.minhapedida.model.Comanda;

public class ComandaDao extends DaoHelper<Comanda> {
    public ComandaDao(Context c) {
        super(c, Comanda.class);
    }
}
