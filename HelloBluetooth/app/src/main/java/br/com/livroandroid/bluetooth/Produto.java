package br.com.livroandroid.bluetooth;

import android.graphics.Bitmap;

/**
 * Created by Thiago Dal Pont on 12/04/2016.
 */
public class Produto {

    private int id;
    private String descricao;
    private Bitmap imagem;


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

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}
