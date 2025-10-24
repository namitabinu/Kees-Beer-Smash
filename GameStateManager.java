import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * Manages the saving and loading of game state.
 * Uses JSON serialization to store the data in a file.
 */
public class GameStateManager {
    private static final String SAVE_FILE = "game_save.json";
    private ObjectMapper mapper;

    public GameStateManager() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Saves the current game state to a JSON file.
     * 
     * @param gameState the object containing all current game data to be saved
     * @throws IOException if an I/O error occurs during file writing/serialization
     */
    public void saveGame(GameState gameState) throws IOException {
        mapper.writeValue(new File(SAVE_FILE), gameState);
        System.out.println("Game saved successfully to " + SAVE_FILE);
    }

    /**
     * Loads a previously saved game state from the JSON file.
     * 
     * @return The loaded GameState object, or null if no save file exists
     * @throws IOException if an I/O error occurs during file reading/deserialization
     *      or if file contains invalid JSON data
     */
    public GameState loadGame() throws IOException {
        File saveFile = new File(SAVE_FILE);
        if (!saveFile.exists()) {
            return null; // No save file found
        }
        return mapper.readValue(saveFile, GameState.class);
    }

    public boolean saveFileExists() {
        return new File(SAVE_FILE).exists();
    }
}