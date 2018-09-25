//package com.embeddedelectronic.hr.myapplication;
//
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.webkit.WebView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    BaseAdapter mAdapter;
//    TcpClient mTcpClient;
//    TextView tv1;
//    Button snd_btn;
//    EditText edTx;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        tv1 = (TextView) findViewById(R.id.textView);
//        snd_btn = (Button) findViewById(R.id.snd_btn);
//        edTx = (EditText) findViewById(R.id.editText);
//        WebView mWebView = (WebView) findViewById(R.id.webView);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
//        mWebView.getSettings().setUseWideViewPort(true);
//        mWebView.getSettings().setJavaScriptEnabled(true);
//
//        //mWebView.loadUrl("http://192.168.5.1:8080/?action=stream");
//        new ConnectTask().execute("");
//
//        snd_btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (mTcpClient != null) {
//
//                    mTcpClient.sendMessage(edTx.getText().toString());
//                }
//            }
//        });
//
//    }
//
//
//    public class ConnectTask extends AsyncTask<String, String, TcpClient> {
//
//        @Override
//        protected TcpClient doInBackground(String... message) {
//
//            //we create a TCPClient object
//            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
//                @Override
//                //here the messageReceived method is implemented
//                public void messageReceived(String message) {
//                    //this method calls the onProgressUpdate
//                    publishProgress(message);
//                }
//            });
//            mTcpClient.run();
//
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//            //response received from server
//            Log.d("test", "response " + values[0]);
//            tv1.setText(values[0]);
//            //process server response here....
//        }
//    }
//
//}
//
//
//
//
