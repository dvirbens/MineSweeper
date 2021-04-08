package mines;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MyController {
	private int width, height, mines;

	@FXML
	public GridPane backgroundGrid;

	@FXML
	private TextField widthTextField;

	@FXML
	private TextField heightTextField;

	@FXML
	private TextField minesTextField;

	@FXML
	private TextField startMines;

	@FXML
	private TextField stratWidth;

	@FXML
	private TextField startHeight;

	// when the user click on reset button then create a new board with new
	// variables
	@FXML
	void resetButtonPress(ActionEvent event) {
		//check if he text boxes not empty.
		if (widthTextField.getText().isEmpty() || heightTextField.getText().isEmpty()
				|| minesTextField.getText().isEmpty()) {
			message(AlertType.ERROR, "Worng input", "Dude, you enter the worng input");
		} else {

			width = Integer.valueOf(widthTextField.getText());
			height = Integer.valueOf(heightTextField.getText());
			mines = Integer.valueOf(minesTextField.getText());

			if (mines > height * width) // if there is more mines then press button in the game
				message(AlertType.ERROR, "Too many mines", "Dude, too many mines try again");
			else {
				backgroundGrid.getChildren().remove(1); // remove the old game board
				Mines M = new Mines(height, width, mines);
				backgroundGrid.add(MinesFX.crateGrid(height, width, M), 1, 0);
				Window myWindow = ((Node) event.getSource()).getScene().getWindow();
				myWindow.sizeToScene();
				myWindow.centerOnScreen();
			}
		}
	}

	// Message window
	public static Optional<ButtonType> message(AlertType alert, String win, String mess) {
		Alert alert2 = new Alert(alert);
		alert2.setTitle(win);
		alert2.setHeaderText(mess);
		return alert2.showAndWait();
	}

	
	//when lets play button press crate a new game scene
	@FXML
	void gamestart(ActionEvent event) {
		
		//check if he text boxes not empty.
		if (startHeight.getText().isEmpty() || startMines.getText().isEmpty() || stratWidth.getText().isEmpty()) { 
			message(AlertType.ERROR, "Worng input", "Dude, you enter the worng input");
		} else {

			width = Integer.valueOf(stratWidth.getText());
			height = Integer.valueOf(startHeight.getText());
			mines = Integer.valueOf(startMines.getText());
			if (mines > height * width) // if there is more mines then press button in the game
				message(AlertType.ERROR, "Too many mines", "Dude, too many mines try again");
			else {

				Mines board = new Mines(height, width, mines);
				GridPane grid = new GridPane();
				MyController controller;
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("minesSB.fxml"));
					grid = loader.load();
					controller = loader.getController();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				grid.add(MinesFX.crateGrid(height, width, board), 1, 0);
				Scene s = new Scene(grid);
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(s);
				window.setTitle("Minesweeper by Dvirbens");

				window.show();
			}
		}
	}
}