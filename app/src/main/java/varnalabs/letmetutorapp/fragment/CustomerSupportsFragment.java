package varnalabs.letmetutorapp.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import varnalabs.letmetutorapp.R;
import varnalabs.letmetutorapp.activity.TeacherActivity;

public class CustomerSupportsFragment extends Fragment implements TeacherActivity.OnBackPressedListener{

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_cust, container, false);
       Typeface  font_text = Typeface.createFromAsset(getActivity().getAssets(),
                "fontRegular.ttf");

        TextView txtmessage = (TextView) v.findViewById(R.id.txtmessage);
        TextView txt_title = (TextView) v.findViewById(R.id.txt_title);
        TextView txtnumber1 = (TextView) v.findViewById(R.id.txt_title);
        TextView txtnumber2 = (TextView) v.findViewById(R.id.txt_title);

        txtmessage.setTypeface(font_text);
        txt_title.setTypeface(font_text);
        txtnumber1.setTypeface(font_text);
        txtnumber2.setTypeface(font_text);
        txtmessage.setText(R.string.customer_support);




        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TeacherActivity) getActivity()).setOnBackPressedListener(this);
    }

    @Override
    public void doBack() {
        TeacherDashboardFragment fragment = new TeacherDashboardFragment();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

}





