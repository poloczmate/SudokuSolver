package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.Random;

public class Controller {
    @FXML
    Pane pane;
    @FXML
    Button solveButton;
    @FXML
    Button resetButton;

    private TextField[][] grid = new TextField[9][9];
    private Alert a = new Alert(Alert.AlertType.CONFIRMATION);



    public void reset(){
        for(int i = 0; i < 9 ; i++){
            for(int j = 0; j <9; j++){
                grid[i][j].setText("");
            }
        }
        if (!a.getAlertType().equals(Alert.AlertType.CONFIRMATION)) a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Reset complete.");
        a.show();
    }

    public void onClick(){
        readGrid();
        if(solve()){
            if (!a.getAlertType().equals(Alert.AlertType.CONFIRMATION)) a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("Sudoku solved.");
            a.show();
        }else{
            if (!a.getAlertType().equals(Alert.AlertType.ERROR)) a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Impossible to solve.");
            a.show();
        }
    }

    private boolean solve(){
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                if (grid[row][col].getText().equals("")) {
                    for(int number = 1; number <= 9; number++){
                        if (checkCol(col,number) && checkRow(row, number) && checkOwnBox(row, col, number)){
                            grid[row][col].setText(Integer.toString(number));
                            if (solve()){
                                return true;
                            }else{
                                grid[row][col].setText("");
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    //return true if the number is free to choose in own 3x3 box
    private boolean checkOwnBox(int row, int col, int number){
        int r = row - row % 3;
        int c = col - col % 3;
        for (int i = r; i < r+3; i++){
            for(int j = c; j < c+3; j++){
                if (!grid[i][j].getText().equals("") && Integer.valueOf(grid[i][j].getText()) == number) return false;
            }
        }
        return true;
    }

    //returns true if the number is free to choose
    private boolean checkCol(int col, int choosenNumber){
        for (int i = 0; i < 9; i++){
            if (!grid[i][col].getText().equals("")){
                if (Integer.valueOf(grid[i][col].getText()) == choosenNumber){
                    return false;
                }
            }
        }
        return true;
    }

    //returns true if the number is free to choose
    private boolean checkRow(int row, int choosenNumber){
        for (int i = 0; i < 9; i++){
            if (!grid[row][i].getText().equals("")){
                if(Integer.valueOf(grid[row][i].getText()) == choosenNumber) return false;
            }
        }
        return true;
    }

    private void readGrid(){
        int i = 0;
        for (Node node : pane.getChildren()){
            if (node instanceof TextField){
                int row = (int)i/9;
                int col = i-(row*9);
                grid[row][col] = (TextField) node;
                i++;
            }
        }
    }
}
