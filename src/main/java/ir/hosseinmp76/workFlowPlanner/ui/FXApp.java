package ir.hosseinmp76.workFlowPlanner.ui;

import java.util.Locale;
import java.util.ResourceBundle;

import fr.brouillard.oss.cssfx.CSSFX;
import ir.hosseinmp76.workFlowPlanner.utills.UIUtills;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.log4j.Log4j2;

public class FXApp extends Application {

    public static void main(final String[] args) {
	UIUtills.getContext();
	CSSFX.start();
	Application.launch(args);
    }

    FXMLController fxmlController;

    @Override
    public void start(final Stage stage) throws Exception {
	Font.loadFont(this.getClass().getResource("HM_XNiloofar.ttf").toExternalForm(),
		14);

//	stage.initStyle(StageStyle.UNIFIED);

	
	final Locale locale = new Locale("fa", "IR");

	final String bundle = "ir/hosseinmp76/workFlowPlanner/i18n/MessagesBundle";
	final ResourceBundle langs = ResourceBundle.getBundle(bundle, locale);
	final var loc = this.getClass().getResource("MainScene.fxml");
	final FXMLLoader fxmlLoader = new FXMLLoader(loc, langs);
	final Parent root = fxmlLoader.load();
	this.fxmlController = (FXMLController) fxmlLoader.getController();

	final Scene scene = new Scene(root);
	scene.getStylesheets().add(
		this.getClass().getResource("styles.css").toExternalForm());
	scene.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	stage.setTitle("Work flow plaanner");
	stage.setScene(scene);
	stage.show();

    }

}
