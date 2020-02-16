package kr.hs.dgsw.dalseolibrary.Beans;

public class UserBean {

    private static UserBean instance;

    public static UserBean getInstance() {
        return instance;
    }

    public static UserBean setInstance(UserBean currentUser) {
        if (instance != null)
            return instance;
        instance = currentUser;
        return instance;
    }

    public static boolean isExistInstance(){
        if(instance != null)
            return true;
        return false;
    }

    private String _id;
    private String id;
    private String password;
    private String name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
