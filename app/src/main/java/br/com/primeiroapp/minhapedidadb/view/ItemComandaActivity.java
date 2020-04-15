package br.com.primeiroapp.minhapedidadb.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.primeiroapp.minhapedidadb.R;
import br.com.primeiroapp.minhapedidadb.control.ItemComandaControl;

public class ItemComandaActivity extends AppCompatActivity {

    private ItemComandaControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_comanda);
        control = new ItemComandaControl(this);
    }

    public void addItem(View v){
        control.addProdutoComanda();
    }

    public void fecharConta(View v){
        control.fecharConta();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        control.onActivityResult(requestCode, resultCode, data);
    }


}
