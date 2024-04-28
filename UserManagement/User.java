package UserManagement;
public class User {
    private String userName;
    private String userPass;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String phoneNumber;
  
    public User(String name, String pass, String email,String first_name, String last_name, String phone_number){
      this.userName = name;
      this.userPass = pass;
      this.userEmail = email;
      this.firstName = first_name;
      this.lastName = last_name;
      this.phoneNumber = phone_number;
    }
    public String getUserName() {
      return userName;
    }
    public void setUserName(String userName) {
      this.userName = userName;
    }
    public String getUserPass() {
      return userPass;
    }
    public void setUserPass(String userPass) {
      this.userPass = userPass;
    }
    public String getUserEmail() {
      return userEmail;
    }
    public void setUserEmail(String userEmail) {
      this.userEmail = userEmail;
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
