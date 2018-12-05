package nuu.quocl.rssreader.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Item implements Parcelable {
    private String title;
    private String pubDate;
    private String link;
    private String guid;
    private String author;
    private String thumbnail;
    private String description;
    private String content;
    private Enclosure enclosure;
    private List<String> categories;


    public Item(String title, String pubDate, String link, String guid, String author, String thumbnail, String description, String content, Enclosure enclosure, List<String> categories) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
        this.author = author;
        this.thumbnail = thumbnail;
        this.description = description;
        this.content = content;
        this.enclosure = enclosure;
        this.categories = categories;
    }

    protected Item(Parcel in) {
        title = in.readString();
        pubDate = in.readString();
        link = in.readString();
        guid = in.readString();
        author = in.readString();
        thumbnail = in.readString();
        description = in.readString();
        content = in.readString();
        categories = in.createStringArrayList();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String value) {
        this.pubDate = value;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String value) {
        this.link = value;
    }

    public String getGUID() {
        return guid;
    }

    public void setGUID(String value) {
        this.guid = value;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String value) {
        this.thumbnail = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure value) {
        this.enclosure = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pubDate);
        dest.writeString(link);
        dest.writeString(guid);
        dest.writeString(author);
        dest.writeString(thumbnail);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeStringList(categories);
    }
}