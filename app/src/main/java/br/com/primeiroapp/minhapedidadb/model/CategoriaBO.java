package br.com.primeiroapp.minhapedidadb.model;

public class CategoriaBO {

    public static boolean validaCategoria(Categoria c){

        return c.getNome() != null && !c.getNome().isEmpty();
    }
}
