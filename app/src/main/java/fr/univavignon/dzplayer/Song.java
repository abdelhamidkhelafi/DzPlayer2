package fr.univavignon.dzplayer;

public class Song {
    private String name;
    private String singer;
    private String url;

    public Song(String name, String singer, String url) {
        this.name = name;
        this.singer = singer;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
