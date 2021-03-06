package com.example.asus.android_webservice_client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends Activity {

    String urlGet = "http://192.168.1.11:8080/WebService/resources/greeting/2";
//    String test = "http://192.168.0.41/hocrestful/api/lophoc/?malop=1";
//    String urlGet1="http://10.179.4.163:81/androidwebservice/getdata.php";
    String urlGetCauHoi="http://192.168.1.11:8080/WebService/resources/greeting/gettoanbo";
    String urlSoCauHoi="http://192.168.1.11:8080/WebService/resources/greeting/getall";
    TextView txtNdch,txtA,txtB,txtC,txtD,txtDA,txtSoCauHoi;
    ArrayList<Integer> arrayListIDCH;
    ArrayList<CauHoi> arrayListCH;
    Integer soCauHoi=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetControl();

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new ReadJSON().execute(urlGet);
//
//            }
//        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadSoCauHoi().execute(urlSoCauHoi);
            }
        });


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadCauHoiTheoID().execute(urlGetCauHoi);
            }
        });

    }
    class ReadCauHoiTheoID extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            String chuoi = docNoiDung_Tu_URL(strings[0]);
            return chuoi;
        }
        //tiến hành đọc JSON
        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i = 0; i<jsonArray.length();i++)
                {
                    JSONObject root = new JSONObject(s);
                    Integer mach = root.getInt("mach");
                    String ch = root.getString("ndch");
                    String a = root.getString("a");
                    String b = root.getString("b");
                    String c = root.getString("c");
                    String d = root.getString("d");
                    String da = root.getString("da");

                    CauHoi cauHoi = new CauHoi(mach,ch,a,b,c,d,da);
                    arrayListCH.add(cauHoi);

                }
                Toast.makeText(MainActivity.this,"so luong ngoai"+arrayListCH.size(),Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    class ReadSoCauHoi extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String integer) {
            txtSoCauHoi.setText(integer);
            soCauHoi=Integer.parseInt(integer.toString().trim());
//            Toast.makeText(MainActivity.this,"so Cau hoi"+soCauHoi,Toast.LENGTH_SHORT).show();
            random(soCauHoi);

            super.onPostExecute(integer);
        }

        @Override
        protected String doInBackground(String... strings) {
            String soCauHoi1 = docNoiDung_Tu_URL(strings[0]);
            return soCauHoi1;
        }
    }


    class ReadJSON extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            String chuoi = docNoiDung_Tu_URL(strings[0]);
            return chuoi;
        }
        //tiến hành đọc JSON
        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            try {
                JSONObject root = new JSONObject(s);
                String ch = root.getString("ndch");
                String a = root.getString("a");
                String b = root.getString("b");
                String c = root.getString("c");
                String d = root.getString("d");
                String da = root.getString("da");

                txtNdch.setText(ch);
                txtA.setText(a);
                txtB.setText(b);
                txtC.setText(c);
                txtD.setText(d);
                txtDA.setText(da);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_xoa_sua,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        ......................
        return super.onOptionsItemSelected(item);
    }

    private  void SetControl(){
        txtA = findViewById(R.id.textViewA);
        txtB = findViewById(R.id.textViewB);
        txtC = findViewById(R.id.textViewC);
        txtD = findViewById(R.id.textViewD);
        txtDA = findViewById(R.id.textViewDA);
        txtNdch = findViewById(R.id.txtNd);
        txtSoCauHoi=findViewById(R.id.txtSoCaHoi);
        arrayListCH=new ArrayList<>();
        arrayListIDCH=new ArrayList<>();
    }

    public void random(Integer soCauHoi){

        for (int i=1; i<=soCauHoi; i++){
            arrayListIDCH.add(i);
        }
        Collections.shuffle(arrayListIDCH);
//        Toast.makeText(MainActivity.this,"so Cau hoi"+soCauHoi,Toast.LENGTH_SHORT).show();
//        Toast.makeText(MainActivity.this, arrayListIDCH.get(6)+"", Toast.LENGTH_SHORT).show();
    }

}
