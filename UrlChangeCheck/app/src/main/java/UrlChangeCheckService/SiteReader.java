package UrlChangeCheckService;
import java.io.IOException;
import okhttp3.*;
import okhttp3.OkHttpClient;
public class SiteReader {

    public static void GetHtmlForSite(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://www.vogella.com/index.html")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    System.out.println(response.body().string());
                    // do something wih the result
                }
            }
        });
    }
}

