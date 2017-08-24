
package catalogitsolutions.letmetutorapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import catalogitsolutions.letmetutorapp.R;
import catalogitsolutions.letmetutorapp.app.AppController;
import catalogitsolutions.letmetutorapp.model.SearchStudent;

public class SearchStudentListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<SearchStudent> birthdayItems;

    public SearchStudentListAdapter(Activity activity, List<SearchStudent> birthdayItems) {

        this.activity = activity;
        this.birthdayItems = birthdayItems;
    }

    @Override
    public int getCount() {
        return birthdayItems.size();
    }

    @Override
    public Object getItem(int location) {
        return birthdayItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.layout_teachersearch, null);
        //Used for Typeface fontstyles
        Typeface font = Typeface.createFromAsset(activity.getAssets(),
                "fontRegular.ttf");

        NetworkImageView thumbNail=  (NetworkImageView) convertView.findViewById(R.id.imgsrc);
        thumbNail.setDefaultImageResId(R.drawable.ic_profile);
        thumbNail.setErrorImageResId(R.drawable.ic_profile);
        thumbNail.setAdjustViewBounds(true);



        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtCode = (TextView) convertView.findViewById(R.id.txtCode);
        TextView txtDob = (TextView) convertView.findViewById(R.id.txtDob);

        txtName.setTypeface(font);
        txtCode.setTypeface(font);
        txtDob.setTypeface(font);

        SearchStudent searchStudent = birthdayItems.get(position);

        String pht = searchStudent.getSt_photo();
        pht = pht.replaceAll(" ", "%20");


        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        thumbNail.setImageUrl(pht, imageLoader);

        //Loading image from below url into imageView
        Picasso.with(activity).load(pht).resize(500, 500).into(thumbNail);

        txtName.setText(String.valueOf(searchStudent.getSt_name()));
        txtCode.setText("Student Code : "+String.valueOf(searchStudent.getSt_code()));
        txtDob.setText("Sex / Age : "+String.valueOf(searchStudent.getSt_gander()+"("+searchStudent.getSt_age()+")"));







        /*es_preadmissionid.setText("Reg No. : "+String.valueOf(birthdays.getEs_preadmissionid()));
        pre_name.setText("Name : "+String.valueOf(birthdays.getPre_name()));
        pre_class_name.setText("Class : "+String.valueOf(birthdays.getPre_class_name()));
        pre_dateofbirth.setText("D.O.B : "+String.valueOf(birthdays.getPre_dateofbirth()));
        pre_fathername.setText("Father Name : "+String.valueOf(birthdays.getPre_fathername()));*/

        return convertView;
    }

}
