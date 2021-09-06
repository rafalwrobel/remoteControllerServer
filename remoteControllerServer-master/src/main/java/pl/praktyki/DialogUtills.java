package pl.praktyki;

import javafx.scene.control.Alert;

public class DialogUtills {

    public static void showInformation(){
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle("O aplikacji");
        informationAlert.setContentText("Uwaga: Do poprawnego działania aplikacji\n (czyli do korzystania z funkcji głośności) \nkonieczne jest posiadanie programu \"nircmd.exe\".");
        informationAlert.setHeaderText("Aplikacje (mobilna i desktopowa) do zdalnego \nwykonywania akcji na komputerze, praktyki 2021.\n\nAutorzy aplikacji: Rafał Wróbel oraz Sebastian Dyjach.\nOpiekun: dr inż. Jakub Smołka. ");
        informationAlert.showAndWait();
    }
}
