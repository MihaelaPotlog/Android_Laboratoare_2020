package Service;

import android.os.AsyncTask;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SiteAdderTask extends AsyncTask<String, Void , String> {
    protected void onPreExecute() {
        // Runs on the UI thread before doInBackground
        // Good for toggling visibility of a progress indicator

    }
    protected void onPostExecute(String result) {
        // This method is executed in the UIThread
        // with access to the result of the long running task


    }
    protected void onProgressUpdate(String values) {
        // Executes whenever publishProgress is called from doInBackground
        // Used to update the progress indicator

    }
    protected String doInBackground(String... strings) {
        Realm realm = Realm.getDefaultInstance();
        final String url ;
        final String name = strings[0];

        final OkHttpClient client = new OkHttpClient();
        if(strings[1].matches("^(http|https|ftp)://.*$") == false)
            url = "https://" + strings[1];
        else url = strings[1];
        Request request = new Request.Builder()
                .url(url)
                .build();
//        final String[] siteParams = strings;
        System.out.println("COUNTTT" + strings.length);
        System.out.println("name" + name);

        System.out.println("url" + url);
        Response response;
        try {
            response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println(responseBody);


            final String encryptedString = HashCalculator.getHash(responseBody);
            System.out.println("HASH" + encryptedString);
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    SiteModel newSite = realm.createObject(SiteModel.class, UUID.randomUUID().toString());
                    newSite.setName(name);
                    System.out.println("name" + name);

                    newSite.setUrl(url);
                    System.out.println("url" +url);

                    newSite.setLastModifiedDate(new Date());
                    newSite.setHash(encryptedString);
                    newSite.setModified(false);
                }
            });
        } catch (IOException excep) {
            System.out.println(excep);
        }
        return "hello";
    }
}
