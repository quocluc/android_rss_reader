package nuu.quocl.rssreader.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import nuu.quocl.rssreader.Interface.ItemClickListener;
import nuu.quocl.rssreader.Model.RSSObject;
import nuu.quocl.rssreader.R;

import static android.content.ContentValues.TAG;


class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView txtTitle, txtPubDate, txtContent;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView) {
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView) itemView.findViewById(R.id.txtPubDate);
        txtContent = (TextView) itemView.findViewById(R.id.txtContent);
        imageView = (ImageView) itemView.findViewById(R.id.imgContent);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, final int position) {

        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txtContent.setText(Html.fromHtml(rssObject.getItems().get(position).getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.txtContent.setText(Html.fromHtml(rssObject.getItems().get(position).getDescription()));
        }
//        holder.txtContent.setText(Html.fromHtml(rssObject.getItems().get(position).getDescription(), new Html.ImageGetter() {
//
//            @Override
//            public Drawable getDrawable(String source) {
//                Drawable drawable = Drawable.createFromPath(rssObject.getItems().get(position).getThumbnail());
//                try {
//                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                } catch (NullPointerException e) {
//                    Log.e(TAG, "getDrawable: ", e);
//                }
//                return drawable;
//            }
//        }, null));

        new DownloadImageTask(holder.imageView)
                .execute(rssObject.getItems().get(position).getThumbnail());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
//                    mContext.startActivity(browserIntent);
                    String link = rssObject.getItems().get(position).getLink();
                    Toast.makeText(view.getContext(), link, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }

}

