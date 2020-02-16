package kr.hs.dgsw.dalseolibrary.Beans;

public class LibraryBean {

    private String _id;
    private String libraryName;
    private String location;
    private String closedDay;
    private String libraryImage;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClosedDay() {
        return closedDay;
    }

    public void setClosedDay(String closedDay) {
        this.closedDay = closedDay;
    }

    public String getLibraryImage() {
        return libraryImage;
    }

    public void setLibraryImage(String libraryImage) {
        this.libraryImage = libraryImage;
    }
}
