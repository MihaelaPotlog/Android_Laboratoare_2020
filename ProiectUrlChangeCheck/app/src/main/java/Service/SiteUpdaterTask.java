package Service;
import java.security.MessageDigest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.proiecturlchangecheck.MainActivity;
import com.example.proiecturlchangecheck.R;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Dictionary;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SiteUpdaterTask extends AsyncTask<String, Void , String> {
    private MainActivity Context;

    public SiteUpdaterTask(Context context){
        Context = (MainActivity)context;
    }
    protected void onPreExecute() {
        // Runs on the UI thread before doInBackground
        // Good for toggling visibility of a progress indicator

    }

    protected String doInBackground(String... strings) {
        Realm realm = Realm.getDefaultInstance();

        final OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(strings[0])
//                .build();
//        final String[] siteParams = strings;
//        System.out.println("COUNTTT" + strings.length);
//        System.out.println("name" + siteParams[1]);
//
//        System.out.println("url" + siteParams[2]);
//        Response response;
//        try {
//            response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//            System.out.println(responseBody);

//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//            messageDigest.update(responseBody.getBytes());
//            String encryptedString = new String(messageDigest.digest());
//            System.out.println("HASH" + encryptedString);
//            realm.executeTransactionAsync(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//
//                    SiteModel newSite = realm.createObject(SiteModel.class, UUID.randomUUID().toString());
//                    newSite.setName(siteParams[1]);
//                    System.out.println("name" + siteParams[1]);
//                    newSite.setUrl(siteParams[2]);
//                    System.out.println("url" + siteParams[2]);
//                    newSite.setLastModifiedDate(new Date());
//                }
//            });
//            return responseBody;
//        } catch (IOException excep) {
//            System.out.println(excep);
//        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SiteModel> sites = realm.where(SiteModel.class).findAll();

                for (SiteModel siteModel : sites) {
                    String siteHtml = getSiteHtml(client, siteModel.getUrl());
                    if(siteHtml != null){
                        String siteHash = HashCalculator.getHash(siteHtml);
                        if(!siteHash.equals(siteModel.getHash())){
                            siteModel.setModified(true);
                            siteModel.setHash(siteHash);
                        }
            }
        }}}
        );




        return "hello";
    }

    private String getSiteHtml(OkHttpClient client, String siteUrl) {
        Request request = new Request.Builder()
                .url(siteUrl)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return  response.body().string();
        }
        catch(IOException excep){
            System.out.println("Exception:" + excep);
         }
        catch(NullPointerException excep){
            System.out.println("Exception:" + excep);
            return null;
        }
        return null;
    }

    protected void onProgressUpdate(String values) {
        // Executes whenever publishProgress is called from doInBackground
        // Used to update the progress indicator

    }

    protected void onPostExecute(String result) {
        // This method is executed in the UIThread
        // with access to the result of the long running task
        Realm realm = Realm.getDefaultInstance();
        RealmResults<SiteModel> sites = realm.where(SiteModel.class).findAll();

        final SiteAdapter adapter = new SiteAdapter(Context, sites);

        ListView listView = (ListView) Context.findViewById(R.id.task_list);
        listView.setAdapter(adapter);
    }


}
