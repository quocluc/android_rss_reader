package nuu.quocl.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import nuu.quocl.rssreader.Adapter.FeedAdapter;
import nuu.quocl.rssreader.Common.HTTPDataHandler;
import nuu.quocl.rssreader.Model.RSSObject;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    RSSObject rssObject;
    private final String RSS_link = "http://vietnamnet.vn/rss/home.rss";
    private final String RSS_Key = "&api_key=nxzrurjy2ezthazwpbk9e1ypdyou0vd0ciy3e4da";
    private final String RSS_Count = "&count=20";
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";
    private String title = "";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) getView().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (isNetworkConnected()) {
            loadRss(RSS_link);
        } else {
            Toast.makeText(getActivity(), "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.newTinhte:
                loadRss("https://tinhte.vn/rss");
                break;
            case R.id.new24h:
                loadRss("https://www.24h.com.vn/upload/rss/trangchu24h.rss");
                break;
            case R.id.newVnexpress:
                loadRss("https://vnexpress.net/rss/tin-moi-nhat.rss");
                break;
            default:

        }
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadRss(String RSS_link) {
        AsyncTask<String, String, String> loadRSSAsync = new AsyncTask<String, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Vui lòng đợi....");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s, RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject, getActivity().getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(rssObject.getFeed().getTitle());
            }

        };
        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSS_link);
        url_get_data.append(RSS_Key);
        url_get_data.append(RSS_Count);
        loadRSSAsync.execute(url_get_data.toString());
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
