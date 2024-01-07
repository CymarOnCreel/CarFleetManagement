package application.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertMessage {

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

	public void emptyTextFieldAlert() {
		Alert a = new Alert(AlertType.NONE, "Nem lett minden kötelező mező kitöltve!", ButtonType.OK);
		a.setTitle("Hiányzó adat!!!");
		a.show();
	}
	
	public void emptyNameTextFieldAlert() {
		Alert a = new Alert(AlertType.NONE, "Nem adott meg egy érvényes nevet!", ButtonType.OK);
		a.setTitle("Hiányzó adat!!!");
		a.show();
	}

	public void carExistAlert() {
		Alert a = new Alert(AlertType.NONE, "Ez a rendszám már létezik az adatbázisban!", ButtonType.OK);
		a.setTitle("Dupla adat!!!");
		a.show();
	}
	
	public void showConfirmationAlertMessage(String title,String message) {
		Alert a = new Alert(AlertType.NONE, message, ButtonType.OK);
		a.setTitle(title);
		a.show();
	}
	public void requiredFieldsEmpty(String title, String message) {
		Alert a =new Alert(AlertType.NONE, message, ButtonType.CLOSE);
		a.setTitle(title);
		a.show();
	}


	public void siteNameExistsAlert() {
		Alert a = new Alert(AlertType.NONE, "Ez a telephely már létezik!", ButtonType.OK);
		a.setTitle("Dupla adat!!!");
		a.show();		
	}

	public void carSaveSuccess() {
		Alert a = new Alert(AlertType.NONE, "Az autó mentese sikeresen megtörtént. \nKérem, rögzítse az utolsó kötelező szerviz adatait is!", ButtonType.OK);
		a.setTitle("Sikeres mentés");
		a.show();}

  public void showUnknownError(String title, String message) {
		Alert a =new Alert(AlertType.ERROR, message, ButtonType.CLOSE);
		a.setTitle(title);
		a.show();
	}

	public void maintenanceSaveSuccess() {
		Alert a = new Alert(AlertType.NONE, "A karbantartás mentese sikeresen megtörtént.", ButtonType.OK);
		a.setTitle("Sikeres mentés");
		a.show();
	}

	public void insuranceSaveSuccess() {
		Alert a = new Alert(AlertType.NONE, "A biztosítás mentese sikeresen megtörtént.", ButtonType.OK);
		a.setTitle("Sikeres mentés");
		a.show();
	}
}
