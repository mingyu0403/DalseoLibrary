package kr.hs.dgsw.dalseolibrary.Beans;

//대출 Bean
public class LoanBean {

    private String _id;
    private UserBean user;
    private HoldBean book;
    private long loanStart;
    private long loanEnd;
    private boolean returned;
    private boolean extended;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public HoldBean getBook() {
        return book;
    }

    public void setBook(HoldBean book) {
        this.book = book;
    }

    public long getLoanStart() {
        return loanStart;
    }

    public void setLoanStart(long loanStart) {
        this.loanStart = loanStart;
    }

    public long getLoanEnd() {
        return loanEnd;
    }

    public void setLoanEnd(long loanEnd) {
        this.loanEnd = loanEnd;
    }


    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }


    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }
}
