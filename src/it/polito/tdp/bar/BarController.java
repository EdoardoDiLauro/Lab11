package it.polito.tdp.bar;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class BarController {
	
	private Simulator sim = new Simulator();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Simula;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSimula(ActionEvent event) {
    	
    	
    	sim.init();
		sim.run();
		
		txtResult.appendText(String.format("Totali  : %d\n", sim.getNumTotClienti()));
		txtResult.appendText(String.format("Soddisfatti: %d\n", sim.getNumSoddisfatti()));
		txtResult.appendText(String.format("Insoddisfatti      : %d\n", sim.getNumInsoddisfatti()));
		txtResult.appendText("\n");

    }

    @FXML
    void initialize() {
        assert Simula != null : "fx:id=\"Simula\" was not injected: check your FXML file 'Bar.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Bar.fxml'.";

    }
}
