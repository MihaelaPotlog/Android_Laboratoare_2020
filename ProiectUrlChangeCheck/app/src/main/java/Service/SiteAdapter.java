package Service;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.example.proiecturlchangecheck.MainActivity;
import com.example.proiecturlchangecheck.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class SiteAdapter extends RealmBaseAdapter<SiteModel> implements ListAdapter {
    private MainActivity activity;

    private static class ViewHolder {
        TextView siteName;
        TextView lastModifiedDate;

    }
    public SiteAdapter(MainActivity activity, OrderedRealmCollection<SiteModel> data) {
        super(data);
        this.activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.site_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.siteName = (TextView) convertView.findViewById(R.id.site_name);
            viewHolder.lastModifiedDate = (TextView) convertView.findViewById(R.id.site_last_modified_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            SiteModel siteModel = adapterData.get(position);
            viewHolder.siteName.setText(siteModel.getName());
            if(siteModel.isModified())
                convertView.setBackgroundColor(Color.GREEN);
//            else convertView.setBackgroundColor(Color.YELLOW);

            if(siteModel.getLastModifiedDate() != null)
                viewHolder.lastModifiedDate.setText(siteModel.getLastModifiedDate().toString());

        }

        return convertView;
    }

}
