package co.pawate.shrishtiapp.searchpackage;



import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;

public class PkgAdapter extends ArrayAdapter<ApplicationInfo>{
    private List<ApplicationInfo> applist = null;
    private Context context;
    private PackageManager pm;
    int resource;
    public PkgAdapter(@NonNull Context context, int resource, @NonNull List<ApplicationInfo> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.applist = objects;
        pm = context.getPackageManager();
    }

    @Override
    public int getCount() {
        if(applist!=null){
            return applist.size();
        }
        else
            return 0;
    }

    @Override
    public ApplicationInfo getItem(int position) {
        if(applist!=null){
            return applist.get(position);
        }
        else
            return null;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item,null);

        }

        ApplicationInfo info = applist.get(position);
        if(info!=null){
            TextView applicationName = (TextView)view.findViewById(R.id.textview);
           // TextView pacakageName = (TextView)view.findViewById(R.id.pkgname);
            ImageView iconv = (ImageView)view.findViewById(R.id.app_icon);

            applicationName.setText(info.loadLabel(pm));
           // pacakageName.setText(info.packageName);
            iconv.setImageDrawable(info.loadIcon(pm));

        }
        return view;
    }
}



