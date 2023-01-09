package com.example.example_openstreetmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Pre-charging your preferences
        Configuration.getInstance().load(
                getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        );
        setContentView(R.layout.activity_main);
        //Get the map in the xml file
        map = findViewById(R.id.map);

        //Set the style of the map to "classic", used by us
        map.setTileSource(TileSourceFactory.MAPNIK);

        //Authorize zoom
        map.setBuiltInZoomControls(true);
        GeoPoint begin = new GeoPoint(48.852707, 2.350899);

        //Getting a controller to control map
        IMapController mapController = map.getController();

        mapController.setZoom(20.0); //Setting zoom
        mapController.setCenter(begin); //Setting center of the map

        //List of points over the map
        ArrayList<OverlayItem> items = new ArrayList<>();

        //Creating a point
        OverlayItem toilet_1 = new OverlayItem(
                "Cathédrale Notre Dame",
                "toilet_1",
                new GeoPoint(48.852707, 2.350899));

        Drawable drawable = toilet_1.getMarker(0);
        items.add(toilet_1);
        items.add(new OverlayItem("Cathédrale Notre Dame","toilet_2",new GeoPoint(48.85371978249333, 2.349259015345873)));

        //Doing different things when tapping or long pressing
        ItemizedOverlayWithFocus<OverlayItem> myOverlay = new ItemizedOverlayWithFocus<OverlayItem>(
                getApplicationContext(),
                items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                }
        );
        //Give the focus to the item that has been tapped
        myOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(myOverlay); //adding the overlay
    }

    @Override
    public void onPause(){
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        map.onResume();
    }
}