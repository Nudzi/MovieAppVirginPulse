package com.example.moviesappbootcampv;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class data extends AsyncTask<String, Integer, ArrayList<Movie>> {
    String data = "";
    ProgressDialog dialog;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    ArrayList<Movie> movies;
    private boolean isEmpty = true;

    public data(Context context) {
        this.context = context;
        movies = new ArrayList<Movie>();
    }

    public boolean getEmpty(){
        return isEmpty;
    }
    public void setEmpty(boolean isEmpty){
        this.isEmpty = isEmpty;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("Just a moment...");
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });
        dialog.show();//must dissmis it on postExec
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {
        int page = 0;
        while (!isCancelled()) {
            try {
                page++;
                data = "";
                String stringPage = Integer.toString(page);
                String action = "https://api.themoviedb.org/3/search/movie?api_key=2696829a81b1b5827d515ff121700838&query=" +
                        strings[0] + "&page=" + stringPage;
                URL url = new URL(action);

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONArray JA = new JSONObject(data).getJSONArray("results");
                if (JA.length() == 0) {
                    if (page == 1) {
                        setEmpty(true);
                        MyCustomDialog dialog = new MyCustomDialog();
                        dialog.show(MainActivity.fragmentManager, "My Custom");
                        return null;
                    }
                    setEmpty(false);
                    return movies;
                }
                setEmpty(false);
                for (int i = 0; i < JA.length(); i++) {
                    Movie movie;
                    JSONObject JO = (JSONObject) JA.get(i);
                    if (!JO.get("poster_path").toString().equals("null")) {
                        movie = new Movie(JO.get("original_title").toString(),
                                JO.get("release_date").toString(), JO.get("overview").toString(),
                                "https://image.tmdb.org/t/p/w92" + JO.get("poster_path").toString());
                    } else {
                        movie = new Movie(JO.get("original_title").toString(),
                                JO.get("release_date").toString(), JO.get("overview").toString(),
                                null);
                    }
                    if (JO.get("release_date").toString().equals("")) {
                        movie.setReleaseDate("no data");
                    }
                    if (JO.get("overview").toString().equals("")) {
                        movie.setOverview("no data");
                    }
                    movies.add(movie);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        ApplicationClass.movies = (movies);
        cancel(true);
        MainActivity.stringNames.add(MainActivity.etSearch.getText().toString().trim());
        MainActivity.stringValues.add(getEmpty());
        dialog.dismiss();
    }

    public ArrayList<Movie> setData() {
        return movies;
    }
}
