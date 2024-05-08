package UserManagement;
public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
  
    public User(String name, String pass, String email,String first_name, String last_name, String phone_number){
      this.username = name;
      this.password = pass;
      this.email = email;
      this.firstName = first_name;
      this.lastName = last_name;
      this.phoneNumber = phone_number;
    }
    public String getUserName() {
      return username;
    }
    public void setUserName(String userName) {
      this.username = userName;
    }
    public String getPassword() {
      return password;
    }
    public void setUserPass(String userPass) {
      this.password = userPass;
    }
    public String getUserEmail() {
      return email;
    }
    public void setUserEmail(String userEmail) {
      this.email = userEmail;
    }
    public String getFirstName() {
      return firstName;
    }
    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }
    public String getLastName() {
      return lastName;
    }
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }
    public String getPhoneNumber() {
      return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }
}
