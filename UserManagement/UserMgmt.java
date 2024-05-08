package UserManagement;

import java.util.List;

interface UserMgmt {
    public void createUser();
    public List<User> searchUser(String args);
    public void updateInfor(User user);
    public boolean deleteUser(User user);
    public void printUserInformation(List<User> user_list);
    public void getUserFromFile(String pathName, String regex);
    public void saveToFile(String pathName, String regex);

}
