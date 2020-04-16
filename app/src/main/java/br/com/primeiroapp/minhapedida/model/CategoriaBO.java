package br.com.primeiroapp.minhapedida.model;

public class CategoriaBO {

    public static boolean validaCategoria(Categoria c){

        return c.getNome() != null && !c.getNome().isEmpty();
    }
}
