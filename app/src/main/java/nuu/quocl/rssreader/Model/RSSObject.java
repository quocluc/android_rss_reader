package nuu.quocl.rssreader.Model;

import java.util.List;

public class RSSObject {
    private String status;
    private Feed feed;
    public List<Item> items;


    public RSSObject(String status, Feed feed, List<Item> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed value) {
        this.feed = value;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}