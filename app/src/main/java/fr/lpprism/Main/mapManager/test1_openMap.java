package fr.lpprism.Main.mapManager;

import android.util.Log;

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

import fr.lpprism.Main.PopUp;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.util.ArrayList;

import fr.lpprism.Main.PopUpFormAdd;
import fr.lpprism.Main.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test1_openMap extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private PLaceHolderAPI varPlaceHolderAPI;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_test1_open_map);
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

        varPlaceHolderAPI = retrofit.create(PLaceHolderAPI.class);

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
        Call<PlaceHolderPost[]> call = varPlaceHolderAPI.getAllToilettes();
        call.enqueue(
                new Callback<PlaceHolderPost[]>() {
                    @Override
                    public void onResponse(Call<PlaceHolderPost[]> call,
                                           Response<PlaceHolderPost[]> response) {
                        if (response.isSuccessful()) {
                            PlaceHolderPost[] posts = response.body();
                            for (PlaceHolderPost uneToilette : posts) {
                                Log.d("UWU", String.valueOf(uneToilette.getLatitude() + " " + uneToilette.getLongitude()));

                                GeoPoint point = new GeoPoint(Double.parseDouble(uneToilette.getLatitude()), Double.parseDouble(uneToilette.getLongitude()));
                                Marker startMarker2 = new Marker(map);
                                startMarker2.setPosition(point);
                                startMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                startMarker2.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble_black, map));
                                map.getOverlays().add(startMarker2);
                                map.invalidate();
                                startMarker2.setOnMarkerClickListener((marker, mapView) -> {
                                    PopUp.showPopupWindow(mapView, String.valueOf(uneToilette.getAdresse()), "Type : " + uneToilette.getType(), uneToilette.getSwitch());
                                    map.getController().animateTo(marker.getPosition());
                                    return true;
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PlaceHolderPost[]> call,
                                          Throwable t) {
                        Log.d("UWU", t.getMessage());
                    }
                }
        );
    }
}

