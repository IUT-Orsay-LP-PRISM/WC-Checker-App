package fr.lpprism.Main.Map;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.lpprism.Main.R;

public class PopUpView {

    public static void showPopupWindow(final View view, String adresse, String Type, ArrayList<Boolean> Switch, String distance) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);
        int width = 1000;
        int height = 850;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 200);

        TextView txtV_adress = popupView.findViewById(R.id.txtV_adress);
        TextView txtV_type = popupView.findViewById(R.id.id_badge_type);
        TextView txtV_distance = popupView.findViewById(R.id.id_distance);
        TextView badge_AMR = popupView.findViewById(R.id.id_badges_AMR);
        TextView badge_FREE = popupView.findViewById(R.id.id_badges_FREE);
        TextView badge_BEBE = popupView.findViewById(R.id.id_badges_BEBE);

        txtV_adress.setText(adresse);
        txtV_type.setText(Type);
        txtV_distance.setText(distance);

        if (Switch.get(0) == true) {
            badge_AMR.setBackground(view.getResources().getDrawable(R.drawable.back_green));
            badge_AMR.setTextColor(view.getResources().getColor(R.color.green));
        }
        if (Switch.get(1) == true) {
            badge_FREE.setBackground(view.getResources().getDrawable(R.drawable.back_green));
            badge_FREE.setTextColor(view.getResources().getColor(R.color.green));
        }
        if (Switch.get(2) == true) {
            badge_BEBE.setBackground(view.getResources().getDrawable(R.drawable.back_green));
            badge_BEBE.setTextColor(view.getResources().getColor(R.color.green));
        }


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
