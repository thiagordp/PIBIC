package br.com.livroandroid.bluetooth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Thiago Dal Pont on 07/04/2016.
 * <p/>
 * Referência:
 * http://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
 */
public class CriaBanco extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "pibic.db";
    public static final String TABELA = "imagem";
    public static final String ID = "id_imagem";
    public static final String IMAGEM = "blob_imagem";
    public static final int VERSAO = 3;
    public final String TAG = "CRIA_BANCO";

    public CriaBanco(Context context) {

        super(context, NOME_BANCO, null, VERSAO); // parâmetro null aciona o Cursor padrão do sqlite;

        Log.d(TAG, "Aberto");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(TAG, "Criando banco");
        String sql = "CREATE TABLE if not exists " + TABELA + "(" +
                ID + " integer primary key autoincrement, " +
                IMAGEM + " blob" +
                ");";

        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Criado");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("drop table if exists " + TABELA);
            Log.d("CRIA_BANCO", "Update");
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        Log.d(TAG, "ON_OPEN");
    }
}
