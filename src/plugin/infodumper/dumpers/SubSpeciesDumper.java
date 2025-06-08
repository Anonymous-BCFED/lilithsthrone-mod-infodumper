package plugin.infodumper.dumpers;

import com.lilithsthrone.game.character.race.Subspecies;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SubSpeciesDumper extends BaseDumper {
    public static void dump() {
        Path newFilePath = Paths.get("data", "internalData", "subspecies.json");
        File parentDir = newFilePath.getParent().toFile();
        if (!parentDir.isDirectory())
            parentDir.mkdirs();
        try (FileWriter w = new FileWriter(newFilePath.toFile())) {
            // I tried using JSONObject and it just ran out of memory. :(
            JSONWriter json = new JSONWriter(w);
            // Start global object
            json.object();
            // Fart out our header
            json.key("version").value(1);

            // Begin the perks dict
            json.key("species");
            json.object();
            {
                Subspecies.getAllSubspecies().forEach(subspecies -> {
                    if (subspecies == null)
                        return;
                    json.key(Subspecies.getIdFromSubspecies(subspecies));
                    json.object();
                    {
                        json.key("id").value(Subspecies.getIdFromSubspecies(subspecies));
                        json.key("baseSlaveValue").value(subspecies.getBaseSlaveValue(null));
                    }
                    json.endObject(); // PlaceType

                });
                json.endObject(); // places
            }
            json.endObject(); // global
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
