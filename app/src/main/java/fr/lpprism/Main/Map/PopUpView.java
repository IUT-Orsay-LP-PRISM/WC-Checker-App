package fr.lpprism.Main.Map;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import fr.lpprism.Main.R;

public class PopUpView {

    public static void showPopupWindow(final View view, String adresse, String Type, String Switch) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);
        int width = 1000;
        int height = 800;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 200);

        TextView txtV_adress = popupView.findViewById(R.id.txtV_adress);
        TextView txtV_type = popupView.findViewById(R.id.txtV_type);
        TextView txtV_switch = popupView.findViewById(R.id.txtV_switch);
        txtV_adress.setText(adresse);
        txtV_type.setText(Type);
        txtV_switch.setText(Switch);


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
                Toast.makeText(view.getContext(), "Normalement là tu met l'itinéraire", Toast.LENGTH_LONG).show();
            }
        });
    }
}
