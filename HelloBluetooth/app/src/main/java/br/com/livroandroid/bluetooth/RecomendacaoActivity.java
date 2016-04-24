package br.com.livroandroid.bluetooth;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecomendacaoActivity extends ActionBarActivity implements BeaconConsumer {

    private final String TAG = "RECOMENDACAO_ACTIVITY";
    private final String UTF_8 = "UTF-8";
    private final String REGION1 = "REGIAO_1";
    private BeaconManager beaconManager;
    private boolean CHANGE_BEACON_QTD = true;
    private int lastBeaconCount;
    private List<Produto> produtos;
    private boolean LOG_ENABLED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LOG(TAG, "Activity created");

        // Verificar se há bluetooth e internet //

        // Configuração do detector de beacons.
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.setBackgroundMode(true);
        beaconManager.setBackgroundScanPeriod(1000l);
        beaconManager.setBackgroundBetweenScanPeriod(1l);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        LOG(TAG, "Configuração de beacon efetuada");
    }

    @Override
    protected void onDestroy() {
        beaconManager.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onBeaconServiceConnect() {

        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                CHANGE_BEACON_QTD = true;
                LOG(TAG, "Beacon entrou na região");
            }

            @Override
            public void didExitRegion(Region region) {
                CHANGE_BEACON_QTD = true;

                LOG(TAG, "Beacon saiu na região");
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
            }
        });

        // É chamado uma vez por segundo.
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {

                // Caso tenha mais de um beacon na região e o número em relação à última vez mudou.
                if (collection.size() > 0 && CHANGE_BEACON_QTD == true) {

                    LOG(TAG, "Iniciando detecção de beacons: " + collection.size() + " beacon(s) encontrado(s).");

                    for (Beacon beacon : collection) {

                        enviaDadoBeacon(beacon); // Asynctask
                        CHANGE_BEACON_QTD = false;
                    }
                }
            }
        });

        try {

            // Inicia o monitoramento
            LOG(TAG, "Iniciando monitoramento.");
            beaconManager.startRangingBeaconsInRegion(new Region(REGION1, null, null, null));
            beaconManager.startMonitoringBeaconsInRegion(new Region(REGION1, null, null, null));
        } catch (RemoteException e) {
            LOG(TAG, e.getMessage());
        }
    }

    private void enviaDadoBeacon(Beacon beacon) {
        AsyncTaskBeacon task = new AsyncTaskBeacon(
                getBaseContext(),
                getWindow().getDecorView().getRootView()
        );
        task.execute(beacon);
    }

    //private void
    private void LOG(String tag, String msg) {
        if (Util.LOG_ENABLED) {
            Log.d(tag, msg);
        }
    }


    private void comunicaServidor(String name, int major, int minor, double rssi, double distance) throws Exception {
        String _nome, _major, _minor, _rssi, _distance, _url;

        // Obtenção dos parâmetros que serão passados pra URL.
        _nome = URLEncoder.encode(name, "UTF-8");
        _major = URLEncoder.encode(String.valueOf(major), UTF_8);
        _minor = URLEncoder.encode(String.valueOf(minor), UTF_8);
        _rssi = URLEncoder.encode(String.valueOf(rssi), UTF_8);
        _distance = URLEncoder.encode(Util.getFormattedDistance(distance), UTF_8);

        // Separar a url em outra função.
        _url = "http://54.207.46.165:8081/apsearch/APService?";
        _url += "device=" + _nome;
        _url += "&major=" + _major;
        _url += "&minor" + _minor;
        _url += "&distance" + _distance;
        _url += "&signal=" + _rssi + "d";
        _url += "&option=beacon";

        LOG(TAG, "URL:\t" + _url);

        URL url = new URL(_url);

        // Abre a conexão com o servidor que hospeda os objetos da URL.
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            LOG(TAG, "Conexão efetuada");

            String response = Util.convertStreamToString(urlConnection.getInputStream());
            LOG(TAG, "Resposta:\t" + response);

            // ----------- PROCESSAR RESPOSTAS! ----------- //

            LOG(TAG, "Parsing JSON...");
            LOG(TAG, "-----------------");
            produtos = Util.parserJason(response);

            BancoController controller = new BancoController(getBaseContext());

            LOG(TAG, String.valueOf(produtos.size()));
            final List<Produto> produtosImagem = new ArrayList<>();
        }
    }
}
