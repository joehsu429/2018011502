package com.example.student.a2018011502;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    TextView tv,tv2,tv3;
    ProgressBar pb;//2 進度條顯示
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=findViewById(R.id.imageView);
        tv=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
        tv3=findViewById(R.id.textView3);
        pb=findViewById(R.id.progressBar);//2 進度條顯示
    }
    public void click1(View v)
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String str_url="https://5.imimg.com/data5/UH/ND/MY-4431270/red-rose-flower-500x500.jpg";
                URL url;
                try{
                    url = new URL(str_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream inputStream=conn.getInputStream();
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buf=new byte[1024];
                    final int totallength=conn.getContentLength();
                    int sum=0;
                    int length;
                    while((length=inputStream.read(buf)) !=-1)
                    {
                        sum +=length;
                        final int tmp=sum;
                        bos.write(buf,0,length);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(String.valueOf(tmp)+"/"+totallength);
                                pb.setProgress(1000 * tmp / totallength);//2 進度條顯示
                            }
                        });
                    }
                    byte[] results=bos.toByteArray();
                    final Bitmap bmp= BitmapFactory.decodeByteArray(
                            results,0,results.length
                    );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bmp);
                        }
                    });
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void click2(View v)//3
    {
        MyTask task=new MyTask();//af1
        task.execute(10);
    }
    class MyTask extends AsyncTask<Integer,Integer,String>
    {
        @Override
        protected void onPostExecute(String s)//af1
        {
            super.onPostExecute(s);
            tv3.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values)//af1
        {
            super.onProgressUpdate(values);
            tv2.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Integer... integers)//af1 ..可輸入不確定數量的陣列,一定要放在最後面
        {
            int i;
            for(i=0;i<=integers[0];i--)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Task","doInBackground,i:"+i);
                publishProgress(i);
            }
            return "wow";
        }
    }
    public void click3(View v)//af2
    {
        MyImageTask task=new MyImageTask();
        task.execute("https://5.imimg.com/data5/UH/ND/MY-4431270/red-rose-flower-500x500.jpg");
    }
    class MyImageTask extends AsyncTask<String,Integer,Bitmap>
    {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str_url="https://5.imimg.com/data5/UH/ND/MY-4431270/red-rose-flower-500x500.jpg";
            URL url;
            try{
                url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream=conn.getInputStream();
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                byte[] buf=new byte[1024];
                final int totallength=conn.getContentLength();
                int sum=0;
                int length;
                while((length=inputStream.read(buf)) !=-1)
                {
                    sum +=length;
                    final int tmp=sum;
                    bos.write(buf,0,length);
                }
                byte[] results=bos.toByteArray();
                final Bitmap bmp= BitmapFactory.decodeByteArray(
                        results,0,results.length
                );

                return bmp;
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
