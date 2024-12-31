package data;

public enum Data {

    //ICON("src/client/resources/images/applicationIcon.png"),
    FONT("src/resources/fonts/Montserrat-Bold.ttf");

    private final String path;

    Data(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}