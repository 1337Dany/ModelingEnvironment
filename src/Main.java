import ui.View;

/**
 * The Main class serves as the entry point for the application.
 * It initializes the user interface by creating an instance of the View class
 * and invoking the openMainJFrame method to display the main window.
 */
public class Main {
    /**
     * The main method is the entry point of the application.
     * It creates an instance of the View class and opens the main JFrame.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        View view = new View();
        view.openMainJFrame();
    }
}