package kr.hs.dgsw.dalseolibrary.Beans;

//도서관이 보유하고 있는 책들에 관한 Bean
public class HoldBean {
    private LibraryBean library;
    private BookBean book;

    public LibraryBean getLibrary() {
        return library;
    }

    public void setLibrary(LibraryBean library) {
        this.library = library;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }
}
