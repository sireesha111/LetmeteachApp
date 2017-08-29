
package varnalabs.letmetutorapp.adapter;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import varnalabs.letmetutorapp.R;
import varnalabs.letmetutorapp.app.AppConfig;
import varnalabs.letmetutorapp.app.AppController;
import varnalabs.letmetutorapp.model.SearchStudent;

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

        TextView txtid = (TextView) convertView.findViewById(R.id.txtid);

        txtName.setTypeface(font);
        txtCode.setTypeface(font);
        txtDob.setTypeface(font);

        SearchStudent searchStudent = birthdayItems.get(position);

        String pht = searchStudent.getSt_photo();
        pht = pht.replaceAll(" ", "%20");


        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        thumbNail.setImageUrl(AppConfig.IMAGES+pht, imageLoader);

        //Loading image from below url into imageView
        Picasso.with(activity).load(AppConfig.IMAGES+pht).resize(500, 500).into(thumbNail);

        String strname = capitalize(searchStudent.getSt_name());

        txtid.setText(searchStudent.getSt_id());

        txtName.setText(String.valueOf(strname));
        txtCode.setText("Student Code : "+String.valueOf(searchStudent.getSt_code()));
        txtDob.setText("Sex / Age : "+String.valueOf(searchStudent.getSt_gander()+" "+searchStudent.getSt_age()));

        return convertView;
    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

}
