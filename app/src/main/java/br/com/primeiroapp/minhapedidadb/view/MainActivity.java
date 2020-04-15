package br.com.primeiroapp.minhapedidadb.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.primeiroapp.minhapedidadb.R;
import br.com.primeiroapp.minhapedidadb.control.MainControl;

public class MainActivity extends AppCompatActivity {

    private MainControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        control = new MainControl(this);
    }


    public void adicionarComanda(View v){
        control.adicionarComanda();
    }


    @Override
    protected void onResume() {
        super.onResume();
        control.configListViewItens();
    }
}
