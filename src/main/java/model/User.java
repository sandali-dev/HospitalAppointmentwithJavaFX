package model;

// Parent class for Admin, Doctor and Patient
// abstract = cannot create a User directly
public abstract class User {

    private String username;
    private String password;
    private String name;
    private String contact;
    private String role;

    public User(String username, String password, String name, String contact, String role) {
        this.username = username;
        this.password = password;
        this.name     = name;
        this.contact  = contact;
        this.role     = role;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName()     { return name; }
    public String getContact()  { return contact; }
    public String getRole()     { return role; }

    // Every child class must write their own version of this
    public abstract void displayDashboard();
}
