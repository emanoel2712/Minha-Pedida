package br.com.primeiroapp.minhapedida.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import br.com.primeiroapp.minhapedida.R;
import br.com.primeiroapp.minhapedida.control.MainControl;

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
