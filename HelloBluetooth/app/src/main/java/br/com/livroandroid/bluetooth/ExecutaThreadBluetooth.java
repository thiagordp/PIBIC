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
 [0] Primeiro Parametro (Nome)
 [1] Segundo Parametro  (Codigo Dispositivo)
 [2] Terceiro Parametro (Senha)
 [n] N' Parametro
*/
public class ExecutaThreadBluetooth extends AsyncTask<String, Void, ObjetoDeRetorno> {

	/*
	AsyncTask<String, Void, ObjetoDeRetorno>
	
	String,  = Seus paramentros para execucao serao em String;
	Void,    = Metodo de callback para atualizacao de interface, utilizado em Progress Dialog, caso necessario atualizar o percentual remanescente;
	ObjetoDeRetorno = Seu tipo de retorno, no caso um Objeto == POJO;
	Todos os paramentros podem ser Void, ou o que preferir;
	
	*/

	
	/*  A chamada para Execucao desta classe se da seguinte forma
         ExecutaThreadBluetooth blueTask = new ExecutaThreadBluetooth(context);
		 blueTask.execute("carlos","26","vilarinho");
		*/


    private ObjetoDeRetorno objetoPareado;
    private ProgressDialog dialog;
    private Context context;

    public ExecutaThreadBluetooth(Context c) {
        dialog = new ProgressDialog(c);
        context = c;
    }

    @Override
    protected void onPreExecute() {
        //if you want, start progress dialog here
		
	/*  
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    dialog.setTitle("Sincronizando Dados, aguarde um momento.");
        dialog.setCancelable(false);
        dialog.setMessage("Conectando...");
        dialog.show();*/
    }

    @Override
    protected synchronized ObjetoDeRetorno doInBackground(String... urls) {

        String nome = params[0];
        int codigoDispositivo = Integer.valueOf(params[1]);
        String senha = params[2];

        objetoPareado = new ObjetoDeRetorno();
        try {
            /* Aqui estara seu servico de acesso ao meio externo podendo setar seus parametros */
            Log.i(objetoPareado.toString());
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
        return objetoPareado;
    }

    @Override
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
		
		/* Aqui vc vai chamar seu N' servico de acesso ao meio externo que depende do result deste aqui*/
		
		/*
		 ExecutaThreadServidor servidorTask = new ExecutaThreadServidor(context);
		 servidorTask.execute("param1","param2","param3");
		*/

    }