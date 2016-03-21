package br.com.livroandroid.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Exemplo de bluetooth
 *
 * @author rlecheta
 */

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_main);

        String[] items = new String[] {
                "Verificar e ativar Bluetooth",
                "Buscar devices",
                "Ficar visível",
                "altBeacon Teste",
                "Sair"};

        // Atribuição dos itens ao List View
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            switch (position) {
                case 0:
                    startActivity(new Intent(this, BluetoothCheckActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, ListaDevicesActivity.class));
                    break;
                case 2:
                    // Garante que alguém pode te encontrar
                    BluetoothUtil.makeVisible(this, 300);
                    break;
                case 3:
                    startActivity(new Intent(this, BeaconTest.class));
                    break;
                case 4:
                    finish();
                    break;
                default:

                    finish();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Erro :" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}