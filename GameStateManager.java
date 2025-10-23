import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class GameStateManager {
    private static final String SAVE_FILE = "game_save.json";
    private ObjectMapper mapper;
    
    public GameStateManager() {
        this.mapper = new ObjectMapper();
    }
    
    public void saveGame(GameState gameState) throws IOException {
        mapper.writeValue(new File(SAVE_FILE), gameState);
        System.out.println("Game saved successfully to " + SAVE_FILE);
    }
    
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