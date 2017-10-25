package edu.cnm.deepdive.ca.rps.util;

// TODO - Update javadoc (include package-info.java)
// TODO - tweak formatting, add label for choice box.
// TODO - Extract model size to be a constant




import edu.cnm.deepdive.ca.rps.models.Breed;
import edu.cnm.deepdive.ca.rps.models.Terrain.Neighborhood;

public class Constants {

  public static final String UI_STRINGS = "resources/ui-strings";
  public static final String MAIN_FXML = "resources/main.fxml";
  public static final String WINDOW_TITLE_KEY = "windowTitle";

  public static final int WINDOW_WIDTH = 700;
  public static final int WINDOW_HEIGHT = 850;
  public static final int SPEED_CONVERT = 20;
  public static final int TERRAIN_SIZE = 100;
  public static final String NEIGHBORHOOD_CHOICES = "neighborhoodChoices";
  public static final int DEFAULT_MIXING_NUMBER = 0;
  /** Default height and width of square lattice. */
  public static final int DEFAULT_SIZE = 50;
  /** Default number of iterationsPerStep performed in each high-level step of the system. */
  public static final int DEFAULT_ITERATIONS_PER_STEP = 500;
  /** Default neighborhood type used in selecting pairs of adjacent {@link Breed} instances. */
  public static final Neighborhood DEFAULT_NEIGHBORHOOD = Neighborhood.VON_NEUMANN;
  public static final int DEFAULT_RUNNER_THREAD_REST = 20;
}
