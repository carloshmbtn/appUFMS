package carlos.facom.ufms.br.appufms;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    MapFragment mMap;
    ListView pontos;
    ArrayList<String> pontosArray;
    GoogleMap map;
    Marker posicaoAtual;
    Marker destino;
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mMap.getMapAsync(this);
        pontos = (ListView) this.findViewById(R.id.pontos);
        pontosArray = new ArrayList<>();
        pontosArray.add("Biblioteca");
        pontosArray.add("FACOM");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pontosArray);
        pontos.setAdapter(adapter);
        pontos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    LatLng lt = new LatLng(-20.499016, -54.612018);
                    destino.setPosition(lt);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 15));
                }
                else if(i == 1) {
                    LatLng lt = new LatLng(-20.502774, -54.613434);
                    destino.setPosition(lt);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 15));
                }
            }
        });
        lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);


    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                    map.setMyLocationEnabled(true);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                }
            }
        }
        else{
            map.setMyLocationEnabled(true);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
        posicaoAtual = map.addMarker(new MarkerOptions().position(new LatLng(-20.49696, -54.61283)));
        destino = map.addMarker(new MarkerOptions().position(new LatLng(-20.49696, -54.61283)));
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        posicaoAtual.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        Log.d("location", "mudou posicao" + location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}