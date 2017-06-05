package com.example.user.jsonparser;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.jsonparser.utilities.InternetUtility;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mQueryEditText;
    private Button mSearchButton;
    private TextView mUrlTextView;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueryEditText = (EditText)findViewById(R.id.et_query);
        mSearchButton = (Button)findViewById(R.id.btn_search);
        mUrlTextView = (TextView)findViewById(R.id.tv_url);
        mResultTextView = (TextView)findViewById(R.id.tv_result);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuery();
            }
        });
    }

    private void makeSearchQuery() {
        String urlQuery = mQueryEditText.getText().toString();
        URL searchUrl = InternetUtility.buildUrl(urlQuery);
        mUrlTextView.setText(searchUrl.toString());
        new QueryTask().execute(searchUrl);
    }

    public class QueryTask extends AsyncTask<URL, Void, String>{
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String searchResult = null;
            try {
                searchResult = InternetUtility.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResult;
        }

        @Override
        protected void onPostExecute(String searchResult) {
            if(searchResult != null && !searchResult.equals("")){
                mResultTextView.setText(searchResult);
            }
        }
    }
}
