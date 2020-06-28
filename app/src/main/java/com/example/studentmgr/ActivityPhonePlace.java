package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityPhonePlace extends AppCompatActivity implements View.OnClickListener{
    /*
    StringBuilder result;
    EditText editText;
    TextView textView;
    String debug="phonedebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_place);
        View btn=findViewById(R.id.query_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFindClick(null);
            }
        });

    }
    */

    private static final String TAG = "ActivityPhonePlace";
    private EditText editNum;
    private Button queryBtn;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_place);
        contentInit();

    }

    private void  contentInit(){
        editNum = findViewById(R.id.phone_sec);
        queryBtn = findViewById(R.id.query_btn);
        resultText = findViewById(R.id.result_text);
        queryBtn.setOnClickListener(this);
    }

 @Override
 public void onClick(View view)
 {

             String num = editNum.getText().toString().trim();
             final String queryUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_name=guishudi&query="+num;

              Handler handler = new Handler(){
                 @Override
                 public void handleMessage(Message msg) {
                     super.handleMessage(msg);
                     resultText.setText(msg.getData().getString("queryResult"));
                     resultText.setVisibility(View.VISIBLE);

                     ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                     ClipData cd = ClipData.newPlainText("result", msg.getData().getString("queryResult"));
                     clipboardManager.setPrimaryClip(cd);
                     Toast.makeText(ActivityPhonePlace.this,"已复制到粘贴板",Toast.LENGTH_SHORT);
                 }
             };
             parseJSONWithJSONObject(queryUrl,handler);
 }

    private void parseJSONWithJSONObject(final String queryUrl, final Handler handler)  {
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    String queryResult="";
                    String city="";
                    String prov="";
                    String type = "";

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(queryUrl)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = null;
                    jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {


                        city = jsonArray.getJSONObject(i).getString("city");
                        prov = jsonArray.getJSONObject(i).getString("prov");
                        type = jsonArray.getJSONObject(i).getString("type");

                    }
                    queryResult ="省份：" + prov + ",城市:" + city + ",服务商:" + type;
                    Log.d(TAG, "run: "+queryResult);
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString("queryResult",queryResult);
                    message.setData(data);
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("activityphoneplace",e.getLocalizedMessage());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("activityphoneplace",e.getLocalizedMessage());
                }

            }
        }).start();

        /*
    public void onFindClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    editText = findViewById(R.id.phone_sec);
                    String phoneNumber = editText.getText().toString();
                    //HttpURLConnection connection = null;
                    BufferedReader reader = null;
                    //http://WebXml.com.cn/
                    URL url = new URL("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?op=getMobileCodeInfo");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    Log.d(debug,"onclick22");
                    connection.connect();
                    Log.d(debug,"onclick33");
                    String body = "phone="+phoneNumber+"&dtype=json&key=f7b23b1c754516b26368521df06ff58e";
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();
                    InputStream in = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(in));
                    result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    Runnable runnable=new Runnable(){
                        @Override
                        public void run() {
                            textView=findViewById(R.id.result_text);
                            textView.setText(result.toString());

                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData mClipData = ClipData.newPlainText("Label", result);
                            cm.setPrimaryClip(mClipData);
                            //Toast.makeText(,result.toString(),Toast.LENGTH_SHORT).show();
                            Log.d(debug,"post");
                        }
                    };
                    Log.d(debug,"run");
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(runnable);
                    reader.close();
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
*/
    }
}
