package br.com.primeiroapp.minhapedidadb.model;

public class ComandaBO {

    public static boolean validaNome(Comanda c){
        return c.getNome() != null && !c.getNome().isEmpty();
    }
    public static boolean validaNr(Comanda c){
        return c.getMesa() != null && !c.getMesa().isEmpty();
    }

}
