package br.com.livroandroid.bluetooth;

import java.sql.Blob;

/**
 * Created by Thiago Dal Pont on 12/04/2016.
 */
public class Produto {

    private int id;
    private String descricao;
    private Blob imagem;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Blob getImagem() {
        return imagem;
    }

    public void setImagem(Blob imagem) {
        this.imagem = imagem;
    }
}
