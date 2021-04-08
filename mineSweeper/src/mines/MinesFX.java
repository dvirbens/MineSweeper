package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MinesFX extends Application {
	public static boardBTN gameButtons[][];

	@Override
	public void start(Stage primaryStage) {
		AnchorPane grid;
		MyController controller;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("start.fxml"));
			grid = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene s = new Scene(grid);
		primaryStage.setScene(s);
		primaryStage.setTitle("Minesweeper by Dvirbens"); 

		primaryStage.show();

	}

	public static GridPane crateGrid(int height, int width, Mines board) {
		gameButtons = new boardBTN[height][width];
		GridPane btnGrid = new GridPane();
		btnGrid.setAlignment(Pos.CENTER);
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				Button btn = new Button(board.get(i, j));
				btn.setPrefSize(45, 45);
				gameButtons[i][j] = new boardBTN(i, j, btn, board);
				btn.setOnMouseClicked(gameButtons[i][j]);
				btnGrid.add(btn, j, i);

			}
		btnGrid.setPadding(new Insets(5));
		return btnGrid;

	}

	public static class boardBTN implements EventHandler<MouseEvent> {
		private int x;
		private int y;
		Button btn;
		Mines g;

		public boardBTN(int x, int y, Button btn, Mines g) {
			this.x = x;
			this.y = y;
			this.btn = btn;
			this.g = g;
		}

		Image flag = new Image(getClass().getResourceAsStream("flag3.png")); 
		Image bomb = new Image(getClass().getResourceAsStream("bomb3.png"));
		Image cross = new Image(getClass().getResourceAsStream("cross.png"));
		@Override
		public void handle(MouseEvent event) {
			if (event.getButton().equals(MouseButton.SECONDARY)) { //if right mouse button clicked
				if (g.toggleFlag(x, y) == true) {
					btn.setText(null);
					btn.setGraphic(new ImageView(flag));
				} else {
					btn.setGraphic(null);
					btn.setText(g.get(x, y));
				}

			} else { // left mouse button clicked
				if (g.open(x, y) == true) {  //show the right places at the board if this boy a mine.
					for (int i = 0; i < g.getHeight(); i++)
						for (int j = 0; j < g.getWidth(); j++) {
							String s = g.get(i, j);
							switch (s) {
							case "X":
								gameButtons[i][j].btn.setGraphic(new ImageView(bomb));
								gameButtons[i][j].btn.setText(null);
								break;

							case "F":
								gameButtons[i][j].btn.setGraphic(new ImageView(flag));
								gameButtons[i][j].btn.setText(null);
								break;
							case "XX":
								gameButtons[i][j].btn.setGraphic(new ImageView(cross));
								gameButtons[i][j].btn.setText(null);
								break;

							case "1":
								gameButtons[i][j].btn.setText(s);
								gameButtons[i][j].btn.setTextFill(Color.DODGERBLUE);
								break;
								
							case "2":
								gameButtons[i][j].btn.setText(s);
								gameButtons[i][j].btn.setTextFill(Color.LIMEGREEN);
								break;
								
							case ".":
								gameButtons[i][j].btn.setText(s);
								break;


							default:
								gameButtons[i][j].btn.setText(s);
								gameButtons[i][j].btn.setTextFill(Color.ORANGERED);

							}

						}
					if (g.isDone()) { //check if the the user wins.
						MyController.message(AlertType.INFORMATION, "Win", "You Win Dude, Congratulations :)");
					}

				} else { // mine was pressed
					g.setShowAll(true); // lose situation show all the board
					for (int i = 0; i < g.getHeight(); i++)
						for (int j = 0; j < g.getWidth(); j++) {
							String s=g.get(i, j);
							switch (s) {
							case "X":
								gameButtons[i][j].btn.setGraphic(new ImageView(bomb));
								gameButtons[i][j].btn.setText(null);
								break;

							case "F":
								gameButtons[i][j].btn.setGraphic(new ImageView(flag));
								gameButtons[i][j].btn.setText(null);
								break;
								
							case "XX":
								gameButtons[i][j].btn.setGraphic(new ImageView(cross));
								gameButtons[i][j].btn.setText(null);
								break;
								
							case "1":
								gameButtons[i][j].btn.setText(s);
								gameButtons[i][j].btn.setTextFill(Color.DODGERBLUE);
								break;
								
							case "2":
								gameButtons[i][j].btn.setText(s);
								gameButtons[i][j].btn.setTextFill(Color.LIMEGREEN);
								break;

							default:
								gameButtons[i][j].btn.setText(s);
								gameButtons[i][j].btn.setTextFill(Color.ORANGERED);

							}
						}
					
					MyController.message(AlertType.ERROR, "Exploded!!", "You lost dude, try again :(");

				}

			
			}

		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
