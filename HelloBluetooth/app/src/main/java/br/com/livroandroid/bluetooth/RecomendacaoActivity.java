package br.com.livroandroid.bluetooth;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class RecomendacaoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
