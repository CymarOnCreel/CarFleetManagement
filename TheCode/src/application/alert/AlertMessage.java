package application.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertMessage {
<<<<<<< Updated upstream

	public void numberFormatAlert() {
		Alert a = new Alert(AlertType.NONE, "Ebbe a mezőbe csak számot lehet megadni!!!", ButtonType.OK);
		a.setTitle("Hibás adat!!!");
		a.show();
	}

	public void saveToDatabaseAlert() {
		Alert a = new Alert(AlertType.NONE, "Valami hiba történt! Az adatok nem lettek elmentve!", ButtonType.OK);
		a.setTitle("Hiba!!!");
		a.show();
	}

=======
	
>>>>>>> Stashed changes
	public void emptyTextFieldAlert() {
		Alert a = new Alert(AlertType.NONE, "Nem lett minden kötelező mező kitöltve!", ButtonType.OK);
		a.setTitle("Hiányzó adat!!!");
		a.show();
	}
<<<<<<< Updated upstream
=======
	
	public void saveToDatabaseAlert() {
		Alert a = new Alert(AlertType.NONE, "Valami hiba történt a mentés során!", ButtonType.OK);
		a.setTitle("Hiba!!!");
		a.show();
	}

>>>>>>> Stashed changes
}
