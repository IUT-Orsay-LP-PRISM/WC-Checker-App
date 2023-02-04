package fr.lpprism.Main.Map;

import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

import fr.lpprism.Main.API.EntityAPI;
import fr.lpprism.Main.API.InterfaceAPI;
import fr.lpprism.Main.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenMap extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private InterfaceAPI varPlaceHolderAPI;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.open_map);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.setMinZoomLevel(3.0);
        map.getZoomController().setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER);

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Marker startMarker = new Marker(map);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        startMarker.setInfoWindow(null);
        startMarker.setIcon(ctx.getDrawable(R.drawable.currentpos));

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                startMarker.setPosition(startPoint);
                map.getOverlays().add(startMarker);
                map.invalidate();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);


        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
        mapController.setCenter(startPoint);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://projets.iut-orsay.fr/prj-prism-rcastro/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        varPlaceHolderAPI = retrofit.create(InterfaceAPI.class);

        // how to place custom marker
        placeMarkerAllToilettes();


        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                PopUpFormAdd.showPopupWindow(map, p.getLatitude(), p.getLongitude());
                return false;
            }
        };

        map.getOverlays().add(new MapEventsOverlay(mReceive));
        //Refreshing the map to draw the new overlay
        map.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }


    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void placeMarkerAllToilettes() {
        Call<EntityAPI[]> call = varPlaceHolderAPI.getAllToilettes();
        call.enqueue(
                new Callback<EntityAPI[]>() {
                    @Override
                    public void onResponse(Call<EntityAPI[]> call,
                                           Response<EntityAPI[]> response) {
                        if (response.isSuccessful()) {
                            EntityAPI[] posts = response.body();
                            for (EntityAPI uneToilette : posts) {
                                Context ctx = map.getContext();
                                GeoPoint point = new GeoPoint(Double.parseDouble(uneToilette.getLatitude()), Double.parseDouble(uneToilette.getLongitude()));
                                Marker startMarker2 = new Marker(map);
                                startMarker2.setPosition(point);
                                startMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                if (uneToilette.getType().equals("Publique"))
                                    startMarker2.setIcon(ctx.getDrawable(R.drawable.marker_public));
                                else if(uneToilette.getType().equals("Privee")){
                                    startMarker2.setIcon(ctx.getDrawable(R.drawable.marker_private));
                                } else {
                                    startMarker2.setIcon(ctx.getDrawable(R.drawable.marker_other));
                                }
                                map.getOverlays().add(startMarker2);
                                map.invalidate();
                                startMarker2.setOnMarkerClickListener((marker, mapView) -> {
                                    String Type = uneToilette.getType().equals("Privee") ? "Privée" : uneToilette.getType();
                                    PopUpView.showPopupWindow(mapView, String.valueOf(uneToilette.getAdresse()), "Type : " + Type, uneToilette.getSwitch());
                                    map.getController().animateTo(marker.getPosition());
                                    return true;
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityAPI[]> call,
                                          Throwable t) {
                        Log.d("UWU", t.getMessage());
                    }
                }
        );
    }
}

