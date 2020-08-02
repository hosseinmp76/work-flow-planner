package ir.hosseinmp76.workFlowPlanner.ui;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Platform;

public class Fuck {

    public static void main(final String[] args) {
	FXApp.main(args);
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		CSSFX.start();
	    }
	});

    }

}
