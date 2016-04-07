package br.com.livroandroid.bluetooth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by Thiago Dal Pont on 07/04/2016.
 * Referência:
 * http://androidsurya.blogspot.com.br/2012/11/insert-and-retrieve-image-from-sqlite.html
 */
public class Util {

    /*Retorna os bytes de uma imagem */
    public static byte[] getBytes(String path) throws Exception {

        FileInputStream inputStream = new FileInputStream(path);
        BufferedInputStream buffer = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[buffer.available()];

        buffer.read(bytes);

        return bytes;

    }



    /*Retorna a imagem correspondente ao array de bytes.*/
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
