package kr.hs.dgsw.dalseolibrary.Beans;

import java.util.List;

public class BookBean {

    private String _id;
    private String bookName;
    private String author;
    private String publisher;
    private String bookImage;
    private String description;
    private List<LibraryBean> libraries;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LibraryBean> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<LibraryBean> libraries) {
        this.libraries = libraries;
    }
}
