package br.com.livroandroid.bluetooth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by Thiago Dal Pont on 07/04/2016.
 * Referência:
 * http://androidsurya.blogspot.com.br/2012/11/insert-and-retrieve-image-from-sqlite.html
 */
public class Util {

    /**
     * Retorna os bytes de uma imagem
     */
    public static byte[] getBytes(String path) throws Exception {

        FileInputStream inputStream = new FileInputStream(path);
        BufferedInputStream buffer = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[buffer.available()];

        buffer.read(bytes);

        return bytes;

    }

    /**
     * Retorna a imagem correspondente ao array de bytes.
     */
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * Conversor de stream para string
     */
    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Verifica o estado da conexão wifi.
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}