package co.pawate.shrishtiapp.searchpackage;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ApplicationInfo> appListInfo = null;
    private PackageManager pmInfo;
    private PkgAdapter listAdapter;
    AutoCompleteTextView autoCompleteTextView;

    void set() {
//        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i("autoCompleteTextView", "%%onItemSelected: %%");
                ApplicationInfo app = appListInfo.get(position);
                try {
                    Intent intent = pmInfo.getLaunchIntentForPackage(app.packageName);
                    if(intent!=null) {
                        startActivity(intent);
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        pmInfo = getPackageManager();
        new showApplications().execute();

    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != pmInfo.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        return applist;
    }

    private class showApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = new ProgressDialog(MainActivity.this);

        @Override
        protected Void doInBackground(Void... params) {
            appListInfo = checkForLaunchIntent(pmInfo.getInstalledApplications(PackageManager.GET_META_DATA));
//            listAdapter = new PkgAdapter(MainActivity.this,
//                    R.layout.item, appListInfo);
            listAdapter = new PkgAdapter(MainActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, appListInfo);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            autoCompleteTextView.setAdapter(listAdapter);
            Log.e("---->onPostExecute----<", "onPostExecute: ");
            set();
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this, null,
                    "Loading Application information Please wait...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
