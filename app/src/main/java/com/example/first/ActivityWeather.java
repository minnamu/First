package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ActivityWeather extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private EditText editText;
    private String city;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        new Thread(new MyHttpRequest()).start();


        //搜索框
        editText = (EditText) findViewById(R.id.weather_et);
        button = (Button) findViewById(R.id.wb);


        //获取文本框，并连接网络数据
        textView1 = (TextView) findViewById(R.id.weather_weather);
        textView2 = (TextView) findViewById(R.id.weather_temp);
        textView3 = (TextView) findViewById(R.id.weather_wind);
        textView4 = (TextView) findViewById(R.id.weather_tv4);


    }

    private class MyHttpRequest implements Runnable {
        @Override
        public void run() {
            try {
                //准备请求的地址
                URL url = new URL("http://v.juhe.cn/weather/index?format=2&key=a95f4761381587458485a997e442a915&cityname=" + URLEncoder.encode(city, "UTF-8"));
                //   URL url=new URL("http://v.juhe.cn/weather/index?format=2&cityname="+ URLEncoder.encode(city,"UTF-8")+"&key=a95f4761381587458485a997e442a915");
                //得到HttpURLConnection对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置HTTP请求属性
                connection.setConnectTimeout(100000);//超过这个时间后断开连接（单位是毫秒）
                connection.setRequestMethod("GET");//设置HTTP请求方法
                connection.setDoInput(true);//是否向服务器进行数据输入
                connection.setDoOutput(true);//是否从服务器获得数据
                connection.connect();//发出请求
                //如果响应码不是500的错误，获取数据
                if (connection.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));//获取输入流,读取返回内容
                    String temp = "";
                    final StringBuilder response = new StringBuilder();
                    while ((temp = bufferedReader.readLine()) != null) { //用readLine读取返回的字符串，每次读一行
                        response.append(temp);
                    }
//
                    Log.d(MyHttpRequest.class.toString(), response.toString());
                    //解析JSON
                    JSONObject jsonObject = new JSONObject(response.toString());
                    //200 ok
                    final String resultcode = jsonObject.getString("resultcode");
                    if (!resultcode.equals("200")) {
                        //输入不正确
                    }

                    final String result = jsonObject.getString("result");
                    JSONObject jsonObject1 = new JSONObject(result);
                    //today
                    String today = jsonObject1.getString("today");
                    String sk = jsonObject1.getString("sk");

                    JSONObject jsonObject2 = new JSONObject(today);
                    final String temp1 = jsonObject2.getString("temperature");
                    final String weather1 = jsonObject2.getString("weather");
                    JSONObject sk1 = new JSONObject(sk);
                    final String wind = sk1.getString("wind_direction");
                    /**
                     * 天气预报
                     */
                    // JSONArray jsonArray=jsonObject1.getJSONArray("future");
                    //JSONObject jsonObject3=(JSONObject)jsonArray.get(0);//数组里的第一项数据
                    //weather2=jsonObject3.getString("weather");//获取未来一天的天气

                    Log.d(MyHttpRequest.class.toString(), temp1 + "," + weather1);
                    //把内容显示到界面
                    //显示天气
                    textView1.post(new Runnable() {
                        @Override
                        public void run() {
                            textView1.setText(weather1);
                        }
                    });
                    //显示温度
                    textView2.post(new Runnable() {
                        @Override
                        public void run() {
                            textView2.setText(temp1);
                        }
                    });
                    //显示风向
                    textView3.post(new Runnable() {
                        @Override
                        public void run() {
                            textView3.setText("风向：" + wind);
                        }
                    });

                    bufferedReader.close();
                }
                connection.disconnect();//断开连接

            } catch (Exception e) {
                Log.e(ActivityWeather.class.toString(), e.toString());
            }
        }
    }

    //show
    public void show(View view) {
        String s = editText.getText().toString();
        city = s;
        if (city.isEmpty()) {
            ActivityWeather.this.city = "北京";
        }
        new Thread(new MyHttpRequest()).start();
    }


}