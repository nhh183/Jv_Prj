import UserManagement.UserManagement;
import BookStore.Management;
public class Main {
    public static void main(String[] args) {
        UserManagement manager = new UserManagement();
        manager.createLayout();
        new Management();
    }
}
