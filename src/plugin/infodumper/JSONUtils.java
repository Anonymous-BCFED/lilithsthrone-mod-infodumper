package plugin.infodumper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;

public class JSONUtils {
    public static void dumpToInternalDataFile(JSONObject envelope, String filename) {
        Path newFilePath = Paths.get("data", "internalData", "perks.json");
        if (!newFilePath.getParent().toFile().isDirectory())
            newFilePath.getParent().toFile().mkdirs();
        try {
            PrintWriter outfile = new PrintWriter(newFilePath.toFile());
            envelope.write(outfile, 2, 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
