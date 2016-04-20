package br.com.livroandroid.bluetooth;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * Exemplo de bluetooth
 *
 * @author Carlos
 */

/*
 [0] param1
 [1] param2
 [2] param3
*/
public class ExecutaThreadServidor extends AsyncTask<String, Void, ObjetoDeRetorno> {

    private final String TAG = "EXECUTA_THREAD_SERVIDOR";
    /*
    AsyncTask<String, Void, ObjetoDeRetorno>

    String,  = Seus paramentros para execucao serao em String;
    Void,    = Metodo de callback para atualizacao de interface, utilizado em Progress Dialog, caso necessario atualizar o percentual remanescente;
    ObjetoDeRetorno = Seu tipo de retorno, no caso um Objeto == POJO;
    Todos os paramentros podem ser Void, ou o que preferir;
    */
    private ObjetoDeRetorno respostaSetic;
    private ProgressDialog dialog;

    public ExecutaThreadServidor(Context c) {
        dialog = new ProgressDialog(c);
    }

    // @Override
    protected void onPreExecute() {
        //if you want, start progress dialog here

	/*
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    dialog.setTitle("Sincronizando Dados, aguarde um momento.");
        dialog.setCancelable(false);
        dialog.setMessage("Conectando...");
        dialog.show();*/
    }

    // @Override
    protected synchronized ObjetoDeRetorno doInBackground(String... urls) {

        String param1 = params[0];
        String param2 = Integer.valueOf(params[1]);
        String param3 = params[2];

        respostaSetic = new ObjetoDeRetorno();
        try {
            /* Aqui estara seu servico de acesso ao meio externo  podendo setar seus param's*/
            Log.i(respostaSetic.toString());
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
        return respostaSetic;
    }

    //  @Override
    protected void onPostExecute(ObjetoDeRetorno result) {
        //if you started progress dialog dismiss it here
        /*try{
            if (dialog.isShowing()) {
                dialog.dismiss();
                Log.i(mensagemRetorno);
            }
        }catch (IllegalArgumentException ae){
            Log.e(ae.getMessage());
        }*/
    }
}