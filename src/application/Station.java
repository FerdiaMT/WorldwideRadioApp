package application;

public class Station {
    private String name;
    private String url;
    private String iconPath;

    public Station(String name, String url, String iconPath) {
        this.name = name;
        this.url = url;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIconPath() {
        return iconPath;
    }
}
