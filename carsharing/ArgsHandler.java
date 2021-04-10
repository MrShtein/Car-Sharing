package carsharing;

public class ArgsHandler {

    private final String[] data;

    public ArgsHandler(String[] data) {
        this.data = data;
    }

    public String getName() {
        return data.length == 0 || data.length == 1 ? "default" : "carsharing";
    }

}
