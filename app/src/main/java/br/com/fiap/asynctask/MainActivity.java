package br.com.fiap.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String IMG_URL = "http://img12.deviantart.net/d670/i/2015/009/7/7/anivia_volcanic_rebirth___league_of_legends_by_o0dzaka0o-d89pzqq.jpg";
    private ProgressDialog mProgressDialog;
    private ImageView imgDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgDownload = (ImageView)findViewById(R.id.imgDownload);

    }

    public void download(View view) {
        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
        downloadAsyncTask.execute(IMG_URL);

    }

    private class DownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                InputStream inputStream;
                Bitmap imagem;
                URL endereco = new URL( strings[0] );
                inputStream = endereco.openStream();
                imagem = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return imagem;
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if ( bitmap != null ) {
                imgDownload.setImageBitmap(bitmap);
            }
            mProgressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(MainActivity.this, "Aguarde o download...", "Baixando a imagem!");
        }
    }

}
