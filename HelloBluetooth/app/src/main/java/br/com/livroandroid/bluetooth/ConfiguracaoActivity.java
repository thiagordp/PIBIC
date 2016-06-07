package br.com.livroandroid.bluetooth;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ConfiguraçãoActivity
 * <p/>
 * Gerencia as configurações de URL para o servidor provedor de recomendações.
 */
public class ConfiguracaoActivity extends ActionBarActivity {

    private Button btnSalvar;
    private EditText edtCaminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // carregar info do arquivo de configurações

        edtCaminho = (EditText) findViewById(R.id.edtConfiguracao);

        getConfigPath();

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = edtCaminho.getText().toString();

                // Salvar URL no arquivo de configurações.
                // E carregar de lá depois que salvou

                try {
                    File arquivo = new File(Util.CONFIG_PATH);
                    FileOutputStream fileOutputStream;
                    byte[] dados = path.getBytes();

                    fileOutputStream = new FileOutputStream(arquivo);

                    fileOutputStream.write(dados);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Arquivo não encontrado!\nE: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Erro ao escrever no arquivo.\nE: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getConfigPath() {
        try {
            FileInputStream fileInputStream = new FileInputStream(Util.CONFIG_PATH);

            byte[] dados = new byte[fileInputStream.available()];

            fileInputStream.read(dados, 0, dados.length);

            String text = new String(dados, 0, dados.length, "UTF-8");
            edtCaminho.setText(text);

            fileInputStream.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Erro ao abrir o arquivo.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
