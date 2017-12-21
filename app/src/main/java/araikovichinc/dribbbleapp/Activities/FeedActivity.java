package araikovichinc.dribbbleapp.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import araikovichinc.dribbbleapp.API.APIs;
import araikovichinc.dribbbleapp.Adapters.ShotRecyclerViewAdapter;
import araikovichinc.dribbbleapp.R;
import araikovichinc.dribbbleapp.API.Shot;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public final String acces_token = "0389ed60e325f32c3082709b3cbfdb06b0d6aabdd786fb3926acdb29f7017e96";
    ArrayList<Shot> shots = null;
    ShotRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    Context context = this;
    int page = 1;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.feed_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.feed_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(!isOnline()){
            SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            String savedList = sharedPreferences.getString("shots", "");
            Log.d("MyLogs", "SavedList" + savedList);
            if(savedList!=null){
                java.lang.reflect.Type type = new TypeToken<ArrayList<Shot>>() {}.getType();
                shots = new Gson().fromJson(savedList, type);
                adapter = new ShotRecyclerViewAdapter(shots, getResources().getDisplayMetrics().widthPixels, context);
                recyclerView.setAdapter(adapter);
            }
        }else {
            LoadShotsTask task = new LoadShotsTask();
            task.execute();
        }
    }

    @Override
    public void onRefresh() {
        if (!isOnline()) {
            Toast.makeText(this, "Check conntection!!!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        recyclerView.setVisibility(View.GONE);
        page++;
        LoadShotsTask task = new LoadShotsTask();
        task.execute();
    }


    class LoadShotsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (shots == null) {
                adapter = new ShotRecyclerViewAdapter(shots, getResources().getDisplayMetrics().widthPixels, context);
                recyclerView.setAdapter(adapter);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.dribbble.com/v1/")
                    .build();
            APIs apIs = retrofit.create(APIs.class);
            Call<ArrayList<Shot>> call = apIs.getShots(acces_token, page, 70);
            try {
                shots = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < shots.size(); i++) {
                if (shots.get(i).getAnimated()) {
                    shots.remove(i);
                }
            }
            String saveList = new Gson().toJson(shots);
            SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("shots", saveList);
            editor.commit();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ShotRecyclerViewAdapter(shots, getResources().getDisplayMetrics().widthPixels, context);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }

    }


}
