package UserManagement;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UserManagement{

  private static List<User> UserList = new ArrayList<>();
  private static User loginUser = null;
  private static boolean isInLogin = false;
  static Scanner input;

  static boolean isValid(String input, int key){
    boolean check = false;
    Pattern pattern;
    switch (key) {
      case 1:
        pattern = Pattern.compile("^[A-Za-z0-9]{5,}$");
        check = pattern.matcher(input).matches() && !isExist(input);
        break;
      case 2:
        pattern = Pattern.compile("^[^\\s]{6,}$");
        check = pattern.matcher(input).matches();
        break;
      case 3:
        pattern = Pattern.compile("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
        check = pattern.matcher(input).matches();
        break;
      case 4:
        pattern = Pattern.compile("^[0-9]{10}");
        check = pattern.matcher(input).matches();
        break;
      case 5:
        pattern = Pattern.compile("^[A-Za-z]{1,}$");
        check = pattern.matcher(input).matches();
        break;
    }
    return check;
  }

  static void createUser(){
    String name,pass,confirm,email,first_name,last_name,phone_number;
    input = new Scanner(System.in);
    System.out.println("Username must be at least five characters and no spaces.");
    System.out.print("Enter username : ");
    name = input.nextLine();
    while(!isValid(name, 1)){
      System.out.println("Username is invalid!");
      System.out.print("Please enter new username : ");
      name = input.nextLine();
    }

    System.out.println("Password must be at least six characters and no spaces.");
    System.out.print("Enter password : ");
    pass = input.nextLine();
    while(!isValid(pass, 2)){
      System.out.println("Password is invalid!");
      System.out.print("Please enter new password : ");
      pass = input.nextLine();
    }

    System.out.print("Enter password again : ");
    confirm = input.nextLine();
    while(!pass.equals(confirm)){
      System.out.println("Please make sure the password you enter both times is the same.");
      System.out.print("Please enter password again : ");
      confirm = input.nextLine();
    }
    pass = encryptPass(pass);
    
    System.out.print("Enter email : ");
    email = input.nextLine();
    while(!isValid(email, 3)){
      System.out.println("Email is invalid!\n");
      System.out.print("Please enter new email : ");
      email = input.nextLine();
    }

    System.out.print("Enter first name : ");
    first_name = input.nextLine();
    while(!isValid(first_name, 5)){
      System.out.println("First name is invalid!");
      System.out.print("Please enter first name again : ");
      first_name = input.nextLine();
    }
    first_name = first_name.substring(0, 1).toUpperCase() + first_name.substring(1);

    System.out.print("Enter last name : ");
    last_name = input.nextLine();
    while(!isValid(last_name, 5)){
      System.out.println("Last name is invalid!");
      System.out.print("Please enter last name again : ");
      last_name = input.nextLine();
    }
    last_name = last_name.substring(0,1).toUpperCase() + last_name.substring(1);

    System.out.print("Enter phone number : ");
    phone_number = input.nextLine();
    while(!isValid(phone_number, 4)){
      System.out.println("Phone number is invalid!");
      System.out.print("Please enter new phone number : ");
      phone_number = input.nextLine();
    }
    UserList.add(new User(name,pass,email,first_name,last_name,phone_number));
  }

  // Check if user exists
  static boolean isExist(String user_name){
    return UserList.stream().anyMatch(user -> user.getUserName().equals(user_name));
  }
  
  // Search users by first/last name 
  static List<User> searchUser(String args){
    args = args.substring(0,1).toUpperCase()+args.substring(1);
    List<User> result = new ArrayList<>();
    for(User user : UserList){
      if(user.getFirstName().contains(args) || user.getLastName().contains(args)){
        result.add(user);
      }
    }
    // Sort by first name
    result.sort(Comparator.comparing(User::getFirstName));
    return result.isEmpty() ? null : result;
  }
  
  // // Interact with user
  // static User getUser(String user_name){
  //   for(User user : UserList){
  //     if(user.getUserName().equals(user_name)) return user;
  //   }
  //   return null;
  // }

  // Check valid login
  static boolean Login(){
    String user_name, pass;
    System.out.print("\033\143");
    System.out.format("-------------------LOGIN-------------------\n");
    System.out.print("Enter username : ");
    user_name = input.next();
    System.out.print("Enter password : ");
    pass = input.next();
    if(!isExist(user_name)){
      System.out.println("Username does not exist.");
      return false;
    }
    for(User user : UserList){
      System.out.println(user.getUserName());
      if(user.getUserName().equals(user_name) && user.getUserPass().equals(encryptPass(pass))){
        isInLogin = true;
        loginUser = user;
        return true;
      }
    }
    System.out.println("Username or Password is invalid.");
    return false;
  }

  static boolean Logout(){
    if(isInLogin){
      isInLogin = false;
      loginUser = null;
      return true;
    }
    return false;
  }

  //----UPDATE USER----
  // Update infor
  static void updateInfor(User user){
    String new_pass, new_email, new_fname, new_lname, new_phonenumb;
    input = new Scanner(System.in);
    System.out.println("Change Password.");
    System.out.print("Enter new password : ");
    new_pass = input.nextLine();
    while(!isValid(new_pass, 2) && !new_pass.isBlank()){
      System.out.println("Password is invalid!");
      System.out.print("Please enter another new password : ");
      new_pass = input.nextLine(); 
    }
    if(!new_pass.isBlank()) user.setUserPass(encryptPass(new_pass));

    System.out.print("Enter new email : ");
    new_email = input.nextLine();
    while(!isValid(new_email, 3) && !new_email.isBlank()){
      System.out.println("Email is invalid!\n");
      System.out.print("Please enter another new email : ");
      new_email = input.nextLine();
    }
    if(!new_email.isBlank()) user.setUserEmail(new_email);

    System.out.print("Enter new first name : ");
    new_fname = input.nextLine();
    while(!isValid(new_fname, 5) && !new_fname.isBlank()){
      System.out.println("First name is invalid!");
      System.out.print("Please enter first name again : ");
      new_fname = input.nextLine();
    }
    if(!new_fname.isBlank()){
      new_fname = new_fname.substring(0, 1).toUpperCase() + new_fname.substring(1);
      user.setFirstName(new_fname);
    }
    System.out.print("Enter last name : ");
    new_lname = input.nextLine();
    while(!isValid(new_lname, 5) && !new_lname.isBlank()){
      System.out.println("Last name is invalid!");
      System.out.print("Please enter last name again : ");
      new_lname = input.nextLine();
    }
    
    if(!new_lname.isBlank()){
      new_lname = new_lname.substring(0, 1).toUpperCase() + new_lname.substring(1);
      user.setLastName(new_lname);
    }

    System.out.print("Enter new phone number : ");
    new_phonenumb = input.nextLine();
    while(!isValid(new_phonenumb, 4) && !new_phonenumb.isBlank()){
      System.out.println("Phone number is invalid!");
      System.out.print("Please enter another new phone number : ");
      new_phonenumb = input.nextLine();
    }
    if(!new_phonenumb.isBlank()) user.setPhoneNumber(new_phonenumb);
    
    Logout();
  }
  
  // Delete user
  static boolean deleteUser(User user){
    if(isInLogin){
      return UserList.remove(user);
    }
    return false;
  }

  // Print users information
  static void printUserInformation(List<User> user_list){
    UserList.sort(Comparator.comparing(User::getFirstName));
    String leftAlignFormat = "| %-20s | %-23s | %-12s | %-12s | %-15s   |%n";
    System.out.format("+----------------------+-------------------------+--------------+--------------+-------------------+%n"); //------------------ %-16s
    System.out.format("|  Username            |  Email                  |  First name  |  Last name   |  Phone number     |%n");
    System.out.format("+----------------------+-------------------------+--------------+--------------+-------------------+%n");
    for(User user : user_list){
      System.out.format(leftAlignFormat, user.getUserName(), user.getUserEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber());
    }
    System.out.format("+----------------------+-------------------------+--------------+--------------+-------------------+%n");
  }

  // Hash password
  static String encryptPass(String pass) {
    try {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = digest.digest(pass.getBytes("UTF-8"));
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            final String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        System.err.println("Error: SHA-256 algorithm not supported.");
        return null;
    } catch (UnsupportedEncodingException e) {
        System.err.println("Error: Unsupported encoding (UTF-8).");
        return null;
    }
  }
  // Read user info from file
  static void getUserFromFile(String pathName, String regex){
    try{
      File file = new File(pathName);
      Scanner reader = new Scanner(file);
      UserList.clear();
      while (reader.hasNextLine()) {
        String dataList = reader.nextLine();
        String[] data = dataList.split(regex);
        UserList.add(new User(data[0],data[1],data[2],data[3],data[4],data[5]));
      }
      reader.close();
      System.out.println("Loading user success!");
    }catch(FileNotFoundException err){
      System.out.println("Loading user failed!\nFile not found.");
    }
  }

  // Save user infor to file
  static void saveToFile(String pathName, String regex){
    try{
      File file = new File(pathName);
      if(file.createNewFile()){
        System.out.println("File created : " + file.getName());
      }else{
        System.out.println("File already exists.");
      }
      FileWriter writer = new FileWriter(pathName);
      for(User user : UserList){
        writer.write(user.getUserName() + regex + user.getUserPass() + regex + user.getUserEmail() + regex +  user.getFirstName() + regex + user.getLastName() + regex + user.getPhoneNumber() + '\n');
      }
      writer.close();
      System.out.println("Successfully wrote to the file.");
    }catch(IOException err){
      System.out.println("Process failed!");
    }
  }

  // Create a console menu
  public void createLayout(){
    getUserFromFile("Usermanagement/user.dat", "/");
    Scanner scanner = new Scanner(System.in);
    boolean gate = true;
    boolean save = false;
    char back;
    do{
      System.out.print("\033\143");
      System.out.format("-------------------MAIN MENU-------------------\n");
      System.out.format("  1. Create User Account\n");
      System.out.format("  2. Check Exist User\n");
      System.out.format("  3. Search User Information By Name\n");
      System.out.format("  4. Update User\n");
      System.out.format("  5. Save Accout To File\n");
      System.out.format("  6. Print List User From File\n");
      System.out.format("  Others- Quit\n");
      System.out.format("Enter a choice : ");
      char key = scanner.next().charAt(0);
      switch (key) {
        case '1':
          boolean gate1 = true;
          do{
            System.out.print("\033\143");
            System.out.format("-------------------CREATE USER ACCOUNT-------------------\n");
            createUser();
            System.out.print("\033\143");
            System.out.println("Successfully create user account.");
            System.out.print("Back to main menu? (y/n) : ");
            back = input.next().charAt(0);
            if(back == 'y' || back == 'Y'){
              gate1 = false;
            }
          }while(gate1);
          break;
        case '2':
          boolean gate2 = true;
          do{
            String username;
            System.out.print("\033\143");
            System.out.format("-------------------CHECK EXIST USER-------------------\n");
            System.out.print("Enter username : ");
            username = scanner.next();
            if(isExist(username)){
              System.out.println("Exist User.");
            }else{
              System.out.println("No User Found!");
            }
            System.out.print("Back to main menu? (y/n) : ");
            back = scanner.next().charAt(0);
            if(back == 'y' || back == 'Y'){
              gate2 = false;
            }
          }while(gate2);
          break;
        case '3':
          boolean gate3 = true;
          do{
            String args;
            System.out.print("\033\143");
            System.out.format("-------------------SEARCH USER INFORMATION BY NAME-------------------\n");
            System.out.print("Enter a search string : ");
            args = scanner.next();
            List<User> users = searchUser(args);
            if(users != null){
              printUserInformation(users);
            }else{
              System.out.println("Have no any user.");
            }
            System.out.print("Back to main menu? (y/n) : ");
            back = scanner.next().charAt(0);
            if(back == 'y' || back == 'Y'){
              gate3 = false;
            }
          }while(gate3);
          break;
        case '4':
          char subkey;
          boolean subgate = true;
          do{
            System.out.print("\033\143");
            System.out.format("-------------------UPDATE USER-------------------\n");
            System.out.format("  1. Update User Information\n");
            System.out.format("  2. Delete User\n");
            System.out.format("  Others- Back To Main Menu\n");
            System.out.format("Enter a choice : ");
            subkey = input.next().charAt(0);
              switch(subkey) {
                case '1':
                  if(Login()){
                    System.out.print("\033\143");
                    System.out.format("-------------------UPDATE USER INFORMATION-------------------\n");
                    updateInfor(loginUser);
                    System.out.println("Update user information success.");
                  }else{
                    System.out.println("Update user information failed.");
                  }
                  System.out.print("Back to main menu? (y/n) : ");
                  back = scanner.next().charAt(0);
                  if(back == 'y' || back == 'Y'){
                    subgate = false;
                  }
                  break;
                case '2':
                if(Login()){
                  System.out.print("\033\143");
                  System.out.format("-------------------DELETE USER-------------------\n");
                  deleteUser(loginUser);
                  System.out.println("User deleted.");
                }else{
                  System.out.println("User deletion failed.");
                }
                System.out.print("Back to main menu? (y/n) : ");
                back = scanner.next().charAt(0);
                if(back == 'y' || back == 'Y'){
                  subgate = false;
                }
                break;
                default:
                  subgate = false;
                  break;
            }
          }while(subgate);
          break;
        case '5':
          boolean gate5 = true;
          do{
            System.out.print("\033\143");
            saveToFile("Usermanagement/user.dat", "/");
            save = true;
            System.out.print("Back to main menu? (y/n) : ");
            back = scanner.next().charAt(0);
            if(back == 'y' || back == 'Y'){
              gate5 = false;
            }
          }while(gate5);
          break;
        case '6':
          boolean gate6 = true;
          do{
          System.out.print("\033\143");
          System.out.format("---------------------------------------------USER LIST----------------------------------------------%n");
          printUserInformation(UserList);
          System.out.print("Back to main menu? (y/n) : ");
          back = scanner.next().charAt(0);
          if(back == 'y' || back == 'Y'){
            gate6 = false;
          }
          }while(gate6);
          break;
         default:
          scanner.close();
          gate = false;
          break;
      }
    }while(gate);
    if(!save){
      saveToFile("Usermanagement/user.dat", "/");
    }
  }

}