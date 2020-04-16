package br.com.primeiroapp.minhapedida.model;

public class ProdutoBO {


    public static boolean validaNome(Produto p){
        return p.getNome() != null && !p.getNome().isEmpty();
    }

    public static boolean validaPreco(Produto p){
        return p.getValor() != null && p.getValor() > 0;
    }
}
