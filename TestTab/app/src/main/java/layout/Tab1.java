package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.bryan.testtab.ChooseResidencyActivity;
import com.example.bryan.testtab.ChooseTransportActivity;
import com.example.bryan.testtab.ChooseWeaponActivity;
import com.example.bryan.testtab.R;

public class Tab1 extends Fragment {
    ImageButton imageButton2;

    public Tab1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab1, container, false);
        imageButton2 = getView().findViewById(R.id.imageButtonTransport);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(getActivity(), ChooseTransportActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        return view;
    }
}
