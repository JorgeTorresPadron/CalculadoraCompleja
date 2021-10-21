package dad.calculadoracompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {

	private ComboBox<String> signoComboBox;
	private TextField arribaIzquierdaText, arribaDerechaText, medioIzquierdaText, medioDerechaText, abajoIzquierdaText,
			abajoDerechaText;
	private Complejo complejo1, complejo2, resultado;
	private Separator separador = new Separator(); // declaracion separador

	@Override
	public void start(Stage primaryStage) throws Exception {

		arribaIzquierdaText = new TextField();
		arribaIzquierdaText.setPromptText("0");
		arribaIzquierdaText.setAlignment(Pos.CENTER);
		arribaIzquierdaText.setMaxWidth(70);
		arribaDerechaText = new TextField();
		arribaDerechaText.setPromptText("0");
		arribaDerechaText.setAlignment(Pos.CENTER);
		arribaDerechaText.setMaxWidth(70);
		medioIzquierdaText = new TextField();
		medioIzquierdaText.setPromptText("0");
		medioIzquierdaText.setAlignment(Pos.CENTER);
		medioIzquierdaText.setMaxWidth(70);
		medioDerechaText = new TextField();
		medioDerechaText.setPromptText("0");
		medioDerechaText.setAlignment(Pos.CENTER);
		medioDerechaText.setMaxWidth(70);
		abajoIzquierdaText = new TextField();
		abajoIzquierdaText.setPromptText("0");
		abajoIzquierdaText.setAlignment(Pos.CENTER);
		abajoIzquierdaText.setMaxWidth(70);
		abajoIzquierdaText.setDisable(true); // para que no se pueda escribir
		abajoDerechaText = new TextField();
		abajoDerechaText.setPromptText("0");
		abajoDerechaText.setAlignment(Pos.CENTER);
		abajoDerechaText.setMaxWidth(70);
		abajoDerechaText.setDisable(true);

		complejo1 = new Complejo();
		complejo2 = new Complejo();
		resultado = new Complejo();

		signoComboBox = new ComboBox<>();
		signoComboBox.getItems().add("+");
		signoComboBox.getItems().add("-");
		signoComboBox.getItems().add("*");
		signoComboBox.getItems().add("/");
		signoComboBox.getSelectionModel().selectFirst();

		VBox izquierda = new VBox(signoComboBox);
		izquierda.setAlignment(Pos.CENTER_LEFT);
		HBox arriba = new HBox(5, arribaIzquierdaText, new Label("+"), arribaDerechaText, new Label("i"));
		HBox medio = new HBox(5, medioIzquierdaText, new Label("+"), medioDerechaText, new Label("i"));
		HBox abajo = new HBox(5, abajoIzquierdaText, new Label("+"), abajoDerechaText, new Label("i"));
		VBox centro = new VBox(5, arriba, medio, separador, abajo); // aqui va el separador
		centro.setAlignment(Pos.CENTER);
		HBox root = new HBox(5, izquierda, centro);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(5);

		Scene scene = new Scene(root, 640, 480);
		primaryStage.setTitle("CalculadoraCompleja");
		primaryStage.setScene(scene);
		primaryStage.show();

		// bindeos para el primer numero complejo
		Bindings.bindBidirectional(arribaIzquierdaText.textProperty(), complejo1.realProperty(),
				new NumberStringConverter());
		Bindings.bindBidirectional(arribaDerechaText.textProperty(), complejo1.imaginarioProperty(),
				new NumberStringConverter());
		// bindeos para el segundo numero complejo
		Bindings.bindBidirectional(medioIzquierdaText.textProperty(), complejo2.realProperty(),
				new NumberStringConverter());
		Bindings.bindBidirectional(medioDerechaText.textProperty(), complejo2.imaginarioProperty(),
				new NumberStringConverter());
		// bindeos para el resultado
		Bindings.bindBidirectional(abajoIzquierdaText.textProperty(), resultado.realProperty(),
				new NumberStringConverter());
		Bindings.bindBidirectional(abajoDerechaText.textProperty(), resultado.imaginarioProperty(),
				new NumberStringConverter());

		// listener para comprobar si el signo cambia
		signoComboBox.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			switch (nv) {
			case "+":
				resultado.realProperty().bind(complejo1.realProperty().add(complejo2.realProperty()));
				resultado.imaginarioProperty().bind(complejo1.imaginarioProperty().add(complejo2.imaginarioProperty()));
				break;
			case "-":
				resultado.realProperty().bind(complejo1.realProperty().subtract(complejo2.realProperty()));
				resultado.imaginarioProperty()
						.bind(complejo1.imaginarioProperty().subtract(complejo2.imaginarioProperty()));
				break;
			case "*":
				resultado.realProperty().bind(complejo1.realProperty().multiply(complejo2.realProperty()
						.subtract(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty()))));
				resultado.imaginarioProperty().bind(complejo1.realProperty().multiply(complejo2.imaginarioProperty()
						.add(complejo1.imaginarioProperty().multiply(complejo2.realProperty()))));
				break;
			case "/":
				resultado.realProperty()
						.bind(complejo1.realProperty().multiply(complejo2.realProperty()
								.add(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty()))
								.divide(complejo2.realProperty().multiply(complejo2.realProperty()).add(
										complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty())))));
				resultado.imaginarioProperty()
						.bind(complejo1.imaginarioProperty().multiply(complejo2.realProperty()
								.subtract(complejo1.realProperty().multiply(complejo2.imaginarioProperty()))
								.divide(complejo2.realProperty().multiply(complejo2.realProperty()).add(
										complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty())))));
				break;
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
