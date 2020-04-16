package br.com.primeiroapp.minhapedida.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import br.com.primeiroapp.minhapedida.R;
import br.com.primeiroapp.minhapedida.control.ProdutoControl;

public class NovoProdutoActivity extends AppCompatActivity {

    private ProdutoControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_produto);
        control = new ProdutoControl(this);
    }

    public void addCategoria(View v){
        control.adicionarCategoria();
    }


    public void salvar(View v){
        control.salvarAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        control.configSpinner();
    }
}
