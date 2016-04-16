package br.com.livroandroid.bluetooth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thiago Dal Pont on 07/04/2016.
 * Referência:
 * http://androidsurya.blogspot.com.br/2012/11/insert-and-retrieve-image-from-sqlite.html
 * Livro
 */
public class Util {


    public final static boolean LOG_ENABLED = true;
    private final String TAG = "UTIL";

    /**
     * Retorna os bytes de um arquivo do caminho
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


    /**
     * Converte JSON para uma lista de produtos
     */
    public static List<Produto> parserJason(String json) {
        List<Produto> produtoList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);

            Iterator<String> iterator = jsonObject.keys();

            while (iterator.hasNext()) {

                Produto produto = new Produto();
                String id = iterator.next();
                String descricao = jsonObject.optString(id);

                produto.setId(Integer.valueOf(id));
                produto.setDescricao(descricao);

                produtoList.add(produto);
            }
        } catch (JSONException e) {
            Log.d("UTIL", e.getMessage());
        }

        return produtoList;
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}