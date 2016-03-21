package br.com.livroandroid.bluetooth;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;


public class BeaconTest extends ActionBarActivity implements BeaconConsumer {
    private final String TAG = "BeaconTest";
    private final String REGION1 = "Region1";                               // Região
    private final String UUID1 = "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0";    // UUID do sensor beacon

    private TextView textView;
    private BeaconManager beaconManager;
    // -------- Activity methods -------- //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // Exibe o ícone de voltar à activity principal

        //bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        textView = (TextView) findViewById(R.id.textView);

        // ---- Beacon cfg ---- //
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.setBackgroundMode(true);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {

        beaconManager.unbind(this);
        super.onDestroy();
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
                    for (Beacon beacon : collection) {
                        String message = beacon.getBluetoothName() + "/" + beacon.getBluetoothAddress() + "/" +
                                beacon.getId1() + "/" + beacon.getId2() + "/" + beacon.getId3() + "/" + beacon.getRssi() + "/" + beacon.getDistance();
                        String msg = message.replace('/', '\n');

                        Log.d(TAG, message);
                        setText(textView, msg);
                    }
                }
            }
        });

        try {
            Identifier identifier = Identifier.parse(UUID1); //beacon 1

            beaconManager.startRangingBeaconsInRegion(new Region(REGION1, identifier, null, null));
            beaconManager.startMonitoringBeaconsInRegion(new Region(REGION1, identifier, null, null));
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


    // -------- Other methods -------- //


    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }
}
