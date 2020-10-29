package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Writes JSON to application json
// Copying format of JsonSerializationDemo
public class JsonWriter {
    private String destination;
    private PrintWriter writer;

    // EFFECTS: Creates the writer that will write to the given destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this

    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //


}
