package fr.lpprism.Main;

import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import fr.lpprism.Main.Map.PopUpView;
import fr.lpprism.Main.R;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import fr.lpprism.Main.API.InterfaceAPI;
import fr.lpprism.Main.API.EntityAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopUpFormAdd {

    public static void showPopupWindow(final MapView mapView, double latitude, double longitude) {
        LayoutInflater inflater = (LayoutInflater) mapView.getContext().getSystemService(mapView.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_form_add, null);
        int width = 1000;
        int height = 1500;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(mapView, Gravity.BOTTOM, 0, 200);


        String[] list = {"Publique", "Privée", "Restaurant", "Commerce"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mapView.getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = popupView.findViewById(R.id.spinnerTypeToilettes);
        spinner.setAdapter(adapter);
        spinner.setSelection(0); // Default value is "Publique"

        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button buttonGo = popupView.findViewById(R.id.goBtn);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://projets.iut-orsay.fr/prj-prism-rcastro/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                InterfaceAPI varPlaceHolderAPI = retrofit.create(InterfaceAPI.class);
                pushToilettesToBDD(varPlaceHolderAPI, latitude, longitude, popupView, mapView);
                popupWindow.dismiss();
            }
        });
    }


    private static void pushToilettesToBDD(InterfaceAPI varPlaceHolderAPI, double latitude, double longitude, View popupView, MapView map) {
        // get popupView to get values
        EditText adresse = popupView.findViewById(R.id.inputTextAdresse);
        String adresseValue = adresse.getText().toString();
        Spinner spinner = popupView.findViewById(R.id.spinnerTypeToilettes);
        String selectedType = (String) spinner.getSelectedItem();
        Switch accesHandicape = popupView.findViewById(R.id.form_accessPMR);
        boolean accesHandicapeValue = accesHandicape.isChecked();
        Switch accesRelaisBB = popupView.findViewById(R.id.form_relaisBB);
        boolean accesRelaisBBValue = accesRelaisBB.isChecked();
        Switch accesGratuit = popupView.findViewById(R.id.form_Free);
        boolean accesGratuitValue = accesGratuit.isChecked();

        Call<EntityAPI> call = varPlaceHolderAPI.addToilette((float) longitude, (float) latitude, adresseValue, selectedType, "test", accesHandicapeValue, accesRelaisBBValue, accesGratuitValue);
        call.enqueue(
                new Callback<EntityAPI>() {
                    @Override
                    public void onResponse(Call<EntityAPI> call,
                                           Response<EntityAPI> response) {
                        if (response.isSuccessful()) {
                            EntityAPI posts = response.body();
                            GeoPoint point = new GeoPoint(latitude, longitude);
                            Marker startMarker2 = new Marker(map);
                            startMarker2.setPosition(point);
                            startMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            map.getOverlays().add(startMarker2);
                            map.invalidate();
                            startMarker2.setOnMarkerClickListener((marker, mapView) -> {
                                String switchString = "";
                                if (accesHandicapeValue == true) {
                                    switchString += "Accès Mobilité Réduite\n";
                                }
                                if (accesRelaisBBValue == true) {
                                    switchString += "Relais bébé\n";
                                }
                                if (accesGratuitValue == true) {
                                    switchString += "Gratuit\n";
                                }
                                PopUpView.showPopupWindow(mapView, adresseValue, "Type : " + selectedType, switchString);
                                return true;
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityAPI> call,
                                          Throwable t) {
                        Log.d("UWU", t.getMessage());
                    }
                }
        );
    }

}
