package domain;

/**
 * Abstract UserEntity base class.
 * @author Vishal Hirani
 */
public abstract class UserEntity {
    protected String id;
    protected String uname;
    protected String pwd;
    protected String fName;
    protected String lName;
    protected String contact;
    protected String userRole;
    protected String mail;
    
    public UserEntity() {
        this.id = "";
        this.uname = "";
        this.pwd = "";
        this.fName = "";
        this.lName = "";
        this.contact = "";
        this.userRole = "";
        this.mail = "";
    }
    
    public UserEntity(String id, String uname, String pwd, String fName, 
                     String lName, String contact, String userRole, String mail) {
        this.id = id;
        this.uname = uname;
        this.pwd = pwd;
        this.fName = fName;
        this.lName = lName;
        this.contact = contact;
        this.userRole = userRole;
        this.mail = mail;
    }
    
    // Accessors
    public String getId() { return id; }
    public String getUname() { return uname; }
    public String getPwd() { return pwd; }
    public String getFName() { return fName; }
    public String getLName() { return lName; }
    public String getContact() { return contact; }
    public String getUserRole() { return userRole; }
    public String getMail() { return mail; }
    
    // Mutators
    public void setId(String id) { this.id = id; }
    public void setUname(String uname) { this.uname = uname; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public void setFName(String fName) { this.fName = fName; }
    public void setLName(String lName) { this.lName = lName; }
    public void setContact(String contact) { this.contact = contact; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    public void setMail(String mail) { this.mail = mail; }
    
    public String getCompleteName() {
        return fName + " " + lName;
    }
    
    public abstract boolean verifyCredentials();
    
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + getCompleteName() + ", Role: " + userRole;
    }
}
