package nuu.quocl.rssreader.Model;

public class Feed {
    private String url;
    private String title;
    private String link;
    private String author;
    private String description;
    private String image;

    public Feed(String url, String title, String link, String author, String description, String image) {
        this.url = url;
        this.title = title;
        this.link = link;
        this.author = author;
        this.description = description;
        this.image = image;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String value) {
        this.url = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String value) {
        this.link = value;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String value) {
        this.image = value;
    }
}
