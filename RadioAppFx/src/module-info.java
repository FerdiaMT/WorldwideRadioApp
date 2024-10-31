module RadioAppFx {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
