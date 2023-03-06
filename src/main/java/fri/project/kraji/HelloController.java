package fri.project.kraji;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Label message;
    public Label status;

    public TextField userCity;

    public ComboBox<String> selectedCity;
    public Spinner<Integer> spinnerCityIndex;

    public ToggleGroup action;
    public RadioButton actionAdd;
    public RadioButton actionDeleteFirst;
    public RadioButton actionDeleteSelected;

    public CheckBox forbidDuplicates;


    public void updateStatus(ActionEvent actionEvent) {
        char letter;
        if (actionEvent.getSource().getClass().toString().contains("MenuItem")) {
            MenuItem menuItem = (MenuItem) actionEvent.getTarget();
            letter = Character.toUpperCase(menuItem.getId().charAt(0));
        } else {
            Button button = (Button) actionEvent.getTarget();
            letter = Character.toUpperCase(button.getId().charAt(0));
        }
        status.setText(status.getText() + letter);
    }

    public void initiateAction(ActionEvent actionEvent) {
        if (actionAdd.isSelected())
            addCity();
        else if (actionDeleteFirst.isSelected())
            deleteFirstCity();
        else if (actionDeleteSelected.isSelected())
            deleteSelectedCity();
    }

    private void addCity() {
        String city = userCity.getText().toUpperCase();
        if (selectedCity.getItems().size() > 21)
            message.setText("Napaka: mesto ni bilo dodano, kapaciteta mest je zapolnjena (OMEJITEV 21)");
        else if (city.isEmpty())
            message.setText("Napaka: prosimo vpišite mesto v okence za dodajanje");
        else if (selectedCity.getItems().contains(city) && forbidDuplicates.isSelected())
            message.setText("Napaka: vnešeno mesto se že nahaja v seznamu (gumb za duplikate?)");
        else {
            message.setText(String.format("Dodano je bilo mesto %s", city));
            selectedCity.getItems().add(city);
        }
    }

    private void deleteFirstCity() {
        if (!selectedCity.getItems().isEmpty()) {
            String city = selectedCity.getItems().remove(0);
            message.setText(String.format("Odstranjujem prvega (%s)", city));
        } else {
            message.setText("Napaka: nobenega mesta ni na voljo za odstranitev");
        }
    }

    private void deleteSelectedCity() {
        if (selectedCity.getSelectionModel().getSelectedIndex() != -1 && !selectedCity.getItems().isEmpty()) {
            String city = selectedCity.getItems().remove(selectedCity.getSelectionModel().getSelectedIndex());
            selectedCity.getSelectionModel().select(-1);  // Okno poenostavimo na začetno izbiro (prazno)
            message.setText(String.format("Odstranjujem izbranega (%s)", city));
        } else {
            message.setText("Napaka: prosimo izberite mesto za odstranitev");
        }
    }

    public void openDocument(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            message.setText("Izbrana datoteka je: " + file.getAbsolutePath());
        }
    }

    public void clearStatus(ActionEvent actionEvent) {
        status.setText("Status: ");

    }

    public void exitApp(ActionEvent actionEvent) {
        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setText("Dobrodošli!");
        status.setText("Status: ");

        selectedCity.getItems().addAll(
                "LJUBLJANA",
                "KRANJ",
                "MARIBOR",
                "INDIJA KOROMANDIJA"
        );

        spinnerCityIndex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));
        spinnerCityIndex.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (selectedCity.getItems().size() > newValue)
                        message.setText("Izbrano mesto: " + selectedCity.getItems().get(newValue));
                    else
                        message.setText("Izbrano mesto: ni elementa");
                }
        );
    }


}