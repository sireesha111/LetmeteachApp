package catalogitsolutions.letmetutorapp.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import catalogitsolutions.letmetutorapp.R;
import catalogitsolutions.letmetutorapp.activity.StudentActivity;

public class CustomerSupportFragment extends Fragment implements StudentActivity.OnBackPressedListener{

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
        ((StudentActivity) getActivity()).setOnBackPressedListener(this);
    }

    @Override
    public void doBack() {
        StudentDashboardFragment fragment = new StudentDashboardFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

}





