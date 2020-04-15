package br.com.primeiroapp.minhapedidadb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import br.com.primeiroapp.minhapedidadb.R;
import br.com.primeiroapp.minhapedidadb.control.ProdutoComandaControl;

public class ProdutoItemComandaActivity extends AppCompatActivity {

    private ProdutoComandaControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_item_comanda);
        control = new ProdutoComandaControl(this);
    }


    public void voltar(View v){
        control.cancelarAction();
    }
    public void novoProduto(View v){
        control.novoProduto();
    }

    public void adicionar(View v){
        control.enviarItemAction();
    }


    @Override
    protected void onResume() {
        super.onResume();
        control.configSpinner();
    }
}
