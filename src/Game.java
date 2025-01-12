import view.View;
import model.Model;
import controller.Controller;


/**
 * The main entry point for the game.
 * 
 * @author Gia Bao Tran
 */
public class Game {

    /**
     * Main method to start the game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // SwingUtilities.invokeLater(App::runGame);
    	View view = new View();
        Model model = new Model();
        Controller controller = new Controller(view, model);
        controller.setUpController();
        model.setController(controller);
    }

}
