package com.embeddedelectronic.hr.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BaseAdapter mAdapter;
    TcpClient mTcpClient;
    TextView tv1;
    Button conn_btn;
    Button reverse_btn;
    Button forward_btn;
    EditText edTx;
    Switch camera_switch;

    ConnectTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);


        tv1 = (TextView) findViewById(R.id.textView);
        conn_btn = (Button) findViewById(R.id.conn_btn);
        forward_btn = (Button) findViewById(R.id.forward_btn);
        reverse_btn = (Button) findViewById(R.id.reverse_btn);
        edTx = (EditText) findViewById(R.id.editText);
        camera_switch = (Switch) findViewById(R.id.switch1);


        final WebView mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);




        camera_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    mWebView.loadUrl("http://"+edTx.getText().toString()+":8080/?action=stream");
                }
                else {
                    mWebView.loadUrl(" ");
                }
            }
        });

        conn_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mTcpClient == null) {
                    task = (ConnectTask) new ConnectTask().execute("");
                    conn_btn.setText("DSCN");
                    //mTcpClient.sendMessage(edTx.getText().toString());
                }
                else{
                    mTcpClient.stopClient();
                    mTcpClient = null;
                    task.cancel(true);
                    conn_btn.setText("Connect");
                }
            }
        });

        forward_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mTcpClient != null) {

                    mTcpClient.sendMessage("FORWARD");
                }
            }
        });
        reverse_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mTcpClient != null) {

                    mTcpClient.sendMessage("REVERSE");
                }
            }
        });

    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            }, edTx.getText().toString());
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            tv1.setText(values[0]);
            //process server response here....
        }
    }



}


