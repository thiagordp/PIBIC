package br.com.livroandroid.bluetooth;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BeaconTest extends ActionBarActivity implements BeaconConsumer {
    private final String TAG = "BeaconTest";
    private final String REGION1 = "Region1";     // Região
    private Switch aSwitch;

    private long count = 0;
    private BeaconManager beaconManager;

    private ListView listView;
    private List<Beacon> devices;


    // -------- Activity methods -------- //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_devices);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // Exibe o ícone de voltar à activity principal
        listView = (ListView) findViewById(R.id.listView);
        devices = new ArrayList<>();
        aSwitch = (Switch) findViewById(R.id.swAtivaLeitura);

        //bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // ---- Beacon cfg ---- //
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.setBackgroundMode(true);
        beaconManager.setBackgroundScanPeriod(1000l);
        beaconManager.setBackgroundBetweenScanPeriod(1l);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {

        beaconManager.unbind(this);
        super.onDestroy();
    }

    /**
     * Atualização da lista de dispositivos
     */
    private void updateLista() {
        if (aSwitch.isChecked()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<String> infos = new ArrayList<String>();
                    for (Beacon beacon : devices) {

                        infos.add("Device:\t\t" + beacon.getBluetoothName() + "\nMAC:\t\t\t" + beacon.getBluetoothAddress() + "\nUUID:\t\t" + beacon.getId1() +
                                "\nMajor:\t\t" + beacon.getId2() + "\nMinor:\t\t" + beacon.getId3() + "\nRssi:\t\t\t" + beacon.getRssi() +
                                "dB\nDistância:\t" + String.format("%.2f", beacon.getDistance()) + "m");
                    }

                    int layout = android.R.layout.simple_list_item_1;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), layout, infos);

                    listView.setAdapter(adapter);
                }
            });
        }
    }

    // -------- Beacon methods -------- //

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.d(TAG, "I just saw an beacon for the first time!");
                Log.d(TAG, "Region id - " + region.getUniqueId());
            }

            @Override
            public void didExitRegion(Region region) {

                Log.d(TAG, "I no longer see a beacon");
                Log.d(TAG, "Region id - " + region.getUniqueId());
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {

                Log.d(TAG, "SIZE:\t" + collection.size());
                if (collection.size() > 0) {
                    devices.clear();
                    for (Beacon beacon : collection) {
                        String message = beacon.getBluetoothName() + "/" + beacon.getBluetoothAddress() + "/" +
                                beacon.getId1() + "/" + beacon.getId2() + "/" + beacon.getId3() + "/" + beacon.getRssi() + "/" +
                                beacon.getDistance();
                        Log.d(TAG, message);
                        devices.add(beacon);
                    }
                }
                updateLista();
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region(REGION1, null, null, null));
            beaconManager.startMonitoringBeaconsInRegion(new Region(REGION1, null, null, null));
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


}

