package application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.dao.CarDao;
import application.dto.CarDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CarHandlerFrameController implements Initializable {

	@FXML
	private VBox vBoxHolder = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listItemFrameFillWithCarData();
	}

	public void listItemFrameFillWithCarData() {
		CarDao carDao = new CarDao();
		List<CarDto> cars = carDao.getAll();
		int carCount = cars.size();
		
		vBoxHolder.getChildren().clear();
		
		Node[] nodes = new Node[carCount];
		
		for (int i = 0; i < nodes.length; i++) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/Carlistitemframepass.fxml"));
		        nodes[i] = loader.load();
		        vBoxHolder.getChildren().add(nodes[i]);

		        CarListItemFrameController controller = loader.getController();
		        controller.setCarHandlerFrameController(this);
		        controller.setCar(cars.get(i));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
