package fr.lpprism.Main;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

public class PopUp {

    public static void showPopupWindow(final View view, String title, String description) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);
        int width = 1000;
        int height = 500;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 200);

        TextView titleT = popupView.findViewById(R.id.titleText);
        TextView desc = popupView.findViewById(R.id.descText);
        titleT.setText(title);
        desc.setText(description);


        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();
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
