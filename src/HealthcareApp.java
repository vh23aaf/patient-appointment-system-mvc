import service.DataService;
import ui.AppWindow;
import javax.swing.*;

/**
 * Main entry point - Healthcare System.
 * @author Vishal Hirani
 */
public class HealthcareApp {
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("HEALTHCARE MANAGEMENT SYSTEM");
        System.out.println("Student: Vishal Hirani");
        System.out.println("Architecture: MVC with Feature-based Organization");
        System.out.println("Pattern: Lazy Singleton for Referral Management");
        System.out.println("=".repeat(80));
        
        DataService dataSvc = new DataService("data");
        
        SwingUtilities.invokeLater(() -> {
            AppWindow win = new AppWindow(dataSvc);
            win.setVisible(true);
        });
    }
}
