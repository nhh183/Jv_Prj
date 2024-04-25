import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserManagement{

  private static List<User> UserList = new ArrayList<>();
  private static User loginUser = null;
  private static boolean isInLogin = false;

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

  // Check if user exists
  static boolean checkExist(String user_name){
    return UserList.stream().anyMatch(user -> user.getUserName().equals(user_name));
  }
  
  // Search users by first/last name 
  static List<User> searchUser(String args){
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
  static boolean checkLogin(String user_name, String pass){
    for(User user : UserList){
      if(user.getUserName().equals(user_name) && user.getUserPass().equals(pass)){
        isInLogin = true;
        loginUser = user;
        return true;
      }
    }
    return false;
  }

  static boolean checkLogout(){
    if(isInLogin){
      isInLogin = false;
      loginUser = null;
      return true;
    }
    return false;
  }

  // --Update User--
  static boolean deleteUser(User user){
    if(isInLogin){
      return UserList.remove(user);
    }
    return false;
  }

  // Print users information
  static void printUserInformation(List<User> user_list){
    UserList.sort(Comparator.comparing(User::getFirstName));
    String leftAlignFormat = "| %-20s | %-16s | %-23s | %-12s | %-12s | %-15s   |%n";
    System.out.format("+----------------------+------------------+-------------------------+--------------+--------------+-------------------+%n");
    System.out.format("|  Username            |  Password        |  Email                  |  First name  |  Last name   |  Phone number     |%n");
    System.out.format("+----------------------+------------------+-------------------------+--------------+--------------+-------------------+%n");
    for(User user : user_list){
      System.out.format(leftAlignFormat, user.getUserName(), user.getUserPass(), user.getUserEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber());
    }
    System.out.format("+----------------------+------------------+-------------------------+--------------+--------------+-------------------+%n");
  }

  //
  static void encryptPass(String pass){

  }

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

  public static void main(String[] args) {

    getUserFromFile("user.dat","/");

    checkLogin("kaio123", "kaio123@");
    loginUser.setLastName("Long");


    printUserInformation(UserList);
    
    checkLogout();



  }
}