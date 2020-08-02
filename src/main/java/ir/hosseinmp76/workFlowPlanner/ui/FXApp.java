package ir.hosseinmp76.workFlowPlanner.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import ir.hosseinmp76.workFlowPlanner.utills.UIUtills;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXApp extends Application {

    static ResourceBundle langs;

    static FXApp app;

    public static String getI18nTranslation(final String key) {
	return FXApp.langs.getString(key);
    }

    public static int main(final String[] args) {

	try (var instance = UIUtills.getInstance()) {
	    final var context = instance.getContext();
	    Application.launch(args);

	} catch (final IOException e) {
	    e.printStackTrace();
	}
	return 0;
    }

    public static void openLink(final String link) {
	FXApp.app.getHostServices().showDocument(link);
    }

    public static Object openStage(final Stage stage, final URL mainSceneLoc)
	    throws IOException {
	return openStage(stage, mainSceneLoc, null);
    }

    public static Object openStage(final Stage stage, final URL mainSceneLoc,
	    Object controler) throws IOException {
	final var res = FXApp.class.getResource("HM_XNiloofar.ttf");
	Font.loadFont(res.toExternalForm(), 14);

//	there is some bug in jdk for this. in windows it causes a blank window
//	i found some openjdk bug but it was solved ...
//	stage.initStyle(StageStyle.UNIFIED);

	final Locale locale = new Locale("fa", "IR");

	final String bundle = "ir/hosseinmp76/workFlowPlanner/i18n/MessagesBundle";
	FXApp.langs = ResourceBundle.getBundle(bundle, locale);
	final FXMLLoader fxmlLoader = new FXMLLoader(mainSceneLoc, FXApp.langs);
	if (controler != null)
	    fxmlLoader.setController(controler);
	final Parent root = fxmlLoader.load();

	controler = fxmlLoader.getController();

	final Scene scene = new Scene(root);
	scene.getStylesheets()
		.add(FXApp.class.getResource("styles.css").toExternalForm());
	scene.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	stage.setTitle("Work flow plaanner");
	stage.setScene(scene);
	stage.show();
	return controler;
    }

    FXMLController fxmlController;

    @Override
    public void start(final Stage stage) throws Exception {
	final var mainSceneLoc = this.getClass().getResource("MainScene.fxml");
	this.fxmlController = (FXMLController) FXApp.openStage(stage,
		mainSceneLoc);
	FXApp.app = this;
    }

}
