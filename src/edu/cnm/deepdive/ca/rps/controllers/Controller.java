package edu.cnm.deepdive.ca.rps.controllers;

import edu.cnm.deepdive.ca.rps.models.Terrain;
import edu.cnm.deepdive.ca.rps.models.Terrain.Neighborhood;
import edu.cnm.deepdive.ca.rps.views.TerrainView;
import edu.cnm.deepdive.ca.rps.views.Timer;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

public class Controller {

  private static final int DEFAULT_RUNNER_THREAD_REST = 10;

  @FXML
  TerrainView terrainView;
  @FXML
  Slider speedSlider;
  @FXML
  ChoiceBox neighborhoodChoice;
  @FXML
  Button runButton;
  @FXML
  Button stopButton;
  @FXML
  Button resetButton;

  private ResourceBundle bundle;
  private Terrain.Neighborhood neighborhood = Terrain.DEFAULT_NEIGHBORHOOD;
  private Terrain.Neighborhood[] neighborhoodChoices;
  private Timer timer;
  private Boolean running = false;
  private Terrain terrain;
  private int runnerThreadRest = DEFAULT_RUNNER_THREAD_REST;


  @FXML
  private void initialize() {
    // initialilze waits until the constructor is done loading
    timer = new Timer(terrainView);
    terrain = new Terrain();
    terrain.setSize(300);
    int runnerThreadRest = DEFAULT_RUNNER_THREAD_REST;
    resetModel();
  }

  @FXML
  private void runModel() {
    speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        runnerThreadRest = (int) Math.round(10 / newValue.doubleValue());
      }
    });
    runButton.setDisable(true);
    stopButton.setDisable(false);
    resetButton.setDisable(true);
    timer.start();
    setRunning(true);
    new Runner().start();

  }

  @FXML
  private void stopModel() {
    runButton.setDisable(false);
    stopButton.setDisable(true);
    resetButton.setDisable(false);
    setRunning(false);
    timer.stop();

  }

  @FXML
  private void resetModel() {
    runButton.setDisable(false);
    stopButton.setDisable(false);
    resetButton.setDisable(true);
    terrain.initialize();
    terrainView.setSource(terrain.getSnapshot());
    terrainView.draw();

  }

  @FXML
  private void controlModel(ActionEvent event) {
    System.out.println(((Button) event.getSource()).getText());
  }

  @FXML
  private void changeNeighborhood() {
    int index = neighborhoodChoice.getItems().indexOf(neighborhoodChoice.getValue());
    terrain.setNeighborhood(neighborhoodChoices[index]);
  }

  public ResourceBundle getBundle() {
    return bundle;
  }

  public void setBundle(ResourceBundle bundle) {
    this.bundle = bundle;
    String neighborhoodChoices = bundle.getString("neighborhoodChoices");
    String choices[] = neighborhoodChoices.split("\\|");
    this.neighborhoodChoices = new Neighborhood[choices.length];
    for (int i = 0; i < choices.length; i++) {
      String choice = choices[i].split("@")[0];
      this.neighborhoodChoices[i] = Terrain.Neighborhood.valueOf(choices[i].split("@")[1]);
      neighborhoodChoice.getItems().add(choice);
      if (this.neighborhoodChoices[i] == Terrain.DEFAULT_NEIGHBORHOOD) {
        neighborhoodChoice.setValue(choice);
      }
    }
  }

  private synchronized Boolean getRunning() {
    return running;
  }

  private synchronized void setRunning(Boolean running) {
    this.running = running;
  }

  private class Runner extends Thread {

    @Override
    public void run() {
      while (running) {
        terrain.step();
        terrainView.setSource(terrain.getSnapshot());
        try {
          Thread.sleep(runnerThreadRest);
        } catch (InterruptedException ex) {
          // Do nothing.
        }
      }
    }
  }

}