package br.com.livroandroid.bluetooth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thiago Dal Pont on 24/04/2016.
 */
public class AsyncTaskBeacon extends AsyncTask<Beacon, Void, List<Produto>> {
    private final String TAG = "ASYNC_TASK";
    private Context context;
    private View view;

    public AsyncTaskBeacon(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    protected void onPreExecute() {
        // Exibir o progress
    }

    protected List<Produto> doInBackground(Beacon... beacons) {

        String nome, major, minor, rssi, distance, urlBeacon;

        nome = "";
        major = "-1";
        minor = "-1";
        rssi = "0d";
        distance = "0.0";

        Log.d(TAG, "Comunicando com o servidor.");

        try {
            nome = URLEncoder.encode(beacons[0].getBluetoothName() == null ? "Beacon" : beacons[0].getBluetoothName(), "UTF-8");
            major = URLEncoder.encode(beacons[0].getId2().toString(), "UTF-8");
            minor = URLEncoder.encode(beacons[0].getId3().toString(), "UTF-8");
            rssi = URLEncoder.encode(String.valueOf(beacons[0].getRssi()), "UTF-8");
            distance = URLEncoder.encode(Util.getFormattedDistance(beacons[0].getDistance()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, ":ERRO:" + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

        urlBeacon = "http://54.207.46.165:8081/apsearch/APService?";
        urlBeacon += "device=" + nome;
        urlBeacon += "&major=" + major;
        urlBeacon += "&minor" + minor;
        urlBeacon += "&distance" + distance;
        urlBeacon += "&signal=" + rssi + "d";
        urlBeacon += "&option=beacon";

        try {
            URL url = new URL(urlBeacon);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = Util.convertStreamToString(urlConnection.getInputStream());
                List<Produto> produtoJSON = Util.parserJason(response);

                BancoController controller = new BancoController(context);
                List<Produto> produtoBanco = new ArrayList<>();

                for (Produto produto : produtoJSON) {
                    Log.d(TAG, "PRODUTO:\tID:\t" + produto.getId() + "\tDESCRICAO:\t'" + produto.getDescricao() + "'");

                    byte[] image = controller.selectImagem(produto.getId());

                    // Se a imagem existe no banco.
                    if (image != null) {
                        Bitmap bitmap = Util.getPhoto(image);   // Converte o array de bytes em imagem bitmap.
                        Log.d(TAG, "DEBUG5");
                        produto.setImagem(bitmap);

                        produtoBanco.add(produto);
                    }
                }

                return produtoBanco;
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, ":ERRO:" + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, ":ERRO:" + e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(List<Produto> produtos) {
        final TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tabela);

        Log.d(TAG, "IMG_FINAL\t" + String.valueOf(produtos.size()));

        for (final Produto produto : produtos) {


            TableRow tableRow = new TableRow(context);

            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView tvId = new TextView(context);
            tvId.setWidth(Util.dpToPx(20, context));
            tvId.setText(String.valueOf(produto.getId()));
            tvId.setTextColor(Color.BLACK);
            tvId.setTextSize(20);
            tvId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));

            TextView tvDescricao = new TextView(context);
            tvId.setWidth(Util.dpToPx(50, context));
            tvDescricao.setText(produto.getDescricao());
            tvDescricao.setTextColor(Color.BLACK);
            tvDescricao.setTextSize(20);
            tvDescricao.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));


            ImageView imageView = new ImageButton(context);
            imageView.setBackgroundColor(Color.TRANSPARENT);
            imageView.setImageBitmap(
                    Bitmap.createScaledBitmap(
                            produto.getImagem(),
                            Util.dpToPx(50, context),
                            Util.dpToPx(50, context), false));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            tableRow.addView(tvId);
            tableRow.addView(tvDescricao);
            tableRow.addView(imageView);

            tableLayout.addView(tableRow,
                    new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
