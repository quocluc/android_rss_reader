package nuu.quocl.rssreader;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import nuu.quocl.rssreader.Model.Item;

import static android.content.ContentValues.TAG;


public class DetailNewFragment extends Fragment {

    private TextView tvTitle, tvContent, tvAuthor;
    private ImageView imgView;
    private Button btnOpen;

    public DetailNewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarDetailNew);
        toolbar.setTitle(getArguments().getString("title"));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        final Item item = getArguments().getParcelable("new");
        tvTitle = view.findViewById(R.id.tvDetailNewTittle);
        tvContent = view.findViewById(R.id.tvDetailnewContent);
        tvAuthor = view.findViewById(R.id.tvDetailNewAuthor);
        imgView = view.findViewById(R.id.imgDetailNew);
        btnOpen = view.findViewById(R.id.btnOpen);

//        String author = getArguments().getString("title") + "/" + item.getAuthor() + "/" + item.getPubDate();
        StringBuffer authorTit = new StringBuffer(getArguments().getString("title"));
        if (item.getAuthor() != null)
            if (!item.getAuthor().isEmpty()) {
                authorTit.append("/ " + item.getAuthor());
            }
        if (item.getPubDate() != null)
            if (!item.getPubDate().isEmpty()) {
                authorTit.append("/ " + item.getPubDate());
            }
        tvAuthor.setText(authorTit.toString());
        tvTitle.setText(item.getTitle());

        String htmlBody = item.getDescription().replaceAll("(<(/)img>)|(<img.+?>)", "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvContent.setText(Html.fromHtml(htmlBody, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvContent.setText(Html.fromHtml(htmlBody));
        }
        if (!item.getThumbnail().isEmpty()) {
            Picasso.get().load(item.getThumbnail()).error(R.drawable.no_image_available_180x180).into(imgView, new Callback() {
                @Override
                public void onSuccess() {
                    Log.i(TAG, "onSuccess: " + item.getThumbnail());
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "onError: ", e);
                }
            });
        }
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("link", item.getLink());
                bundle.putString("title", item.getTitle());
                ViewFragment viewFragment = new ViewFragment();
                viewFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.homeFragment, viewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
