package br.com.primeiroapp.minhapedida.dao.helpers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

public class DaoHelper<T> {

    protected Dao<T, Integer> dao;

    protected Class className;

    public static MyORMLiteHelper mInstance = null;

    public DaoHelper(Context c , Class className) {
        this.className = className;
        if(mInstance == null){

            mInstance = new MyORMLiteHelper(c.getApplicationContext());
        }
    }

    public Dao<T, Integer> getDao() {
        try{
            return mInstance.getDao(className);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return dao;
    }
}
