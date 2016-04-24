package br.com.livroandroid.bluetooth;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Thiago Dal Pont on 06/04/2016.
 * <p/>
 * Referência:
 * http://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
 * http://stackoverflow.com/questions/6090170/cannot-save-image-as-blob-to-sqlite
 */
public class BancoController {

    private final String TAG = "BANCO_CONTROLLER";
    private SQLiteDatabase db;
    private CriaBanco criaBanco;


    /**
     * Construtor
     */
    public BancoController(Context context) {
        criaBanco = new CriaBanco(context);
    }

    /**
     * Inserção da imagem
     */
    public void insereImagem(String caminhoImagem) throws Exception {
        ContentValues valor;
        long resultado;
        byte[] image;

        db = criaBanco.getWritableDatabase(); // Recebe uma instância do BD para R/W.
        valor = new ContentValues();

        image = Util.getBytes(caminhoImagem);
        valor.put(CriaBanco.IMAGEM, image);
        resultado = db.insert(CriaBanco.TABELA, null, valor); // Inserção
        Log.d("BANCO_CONTROLLER", "resultado " + resultado);
        db.close();

        if (resultado == -1) {
            Log.d("BANCO_CONTROLLER", "Erro ao inserir img");
        }
    }

    /**
     * Seleciona a imagem correspondente ao ID fornecido
     */
    public byte[] selectImagem(int id) {
        // String sql = "SELECT " + CriaBanco.ID + " WHERE " + CriaBanco.ID + " = " + id;
        Log.d(TAG, "Getting db");
        db = criaBanco.getWritableDatabase();
        Log.d(TAG, "DB gotted");

        Cursor cursor = db.query(CriaBanco.TABELA, null, CriaBanco.ID + "= '" + id + "'", null, null, null, null, null);

        byte[] bytes = null;
        if (cursor != null && cursor.moveToFirst()) {

            Log.d(TAG, String.valueOf(cursor.getCount()));

            do {
                bytes = cursor.getBlob(cursor.getColumnIndex(CriaBanco.IMAGEM));
            } while (cursor.moveToNext());
        }

        return bytes;
    }

    public void closeDB() {
        this.db.close();
    }

    /*
    public Cursor carregaDados() {

        Cursor cursor;
        String[] campos = {criaBanco.ID};

        cursor = db.query(criaBanco.TABELA, campos, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Log.d(TAG, String.valueOf(cursor.getCount()));

        db.close();
        return cursor;
    }
    */
}
