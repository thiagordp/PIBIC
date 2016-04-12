package br.com.livroandroid.bluetooth;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


/**
 * ConfiguraçãoActivity
 * <p/>
 * Gerencia as configurações de URL para o servidor provedor de recomendações.
 */
public class ConfiguracaoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
