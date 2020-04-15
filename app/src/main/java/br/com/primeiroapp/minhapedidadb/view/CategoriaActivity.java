package br.com.primeiroapp.minhapedidadb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import br.com.primeiroapp.minhapedidadb.R;
import br.com.primeiroapp.minhapedidadb.control.CategoriaControl;

public class CategoriaActivity extends AppCompatActivity {

    private CategoriaControl control;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        control = new CategoriaControl(this);
    }

    public void salvar(View v){
        control.salvarAction();
    }

}
