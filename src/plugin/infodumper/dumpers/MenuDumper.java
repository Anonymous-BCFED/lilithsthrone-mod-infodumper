package plugin.infodumper.dumpers;

import com.lilithsthrone.main.Main;
import javafx.geometry.Bounds;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuDumper {

    public static void dump() {
        dumpMenus();
    }

    private static void dumpMenus() {
        Path newFilePath = Paths.get("data", "internalData", "menus.json");
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
            json.key("responses");
            dumpResponsePositions(json);
            json.endObject();
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void dumpResponsePositions(JSONWriter json) {
        WebEngine webEngine = Main.mainController.getWebEngine();
        // Document doc = webEngine.getDocument();
        Double windowScreenX = ((Number) webEngine.executeScript("window.screenX")).doubleValue();
        Double windowScreenY = ((Number) webEngine.executeScript("window.screenY")).doubleValue();
        json.object();
        {
            json.key("viewport").object();
            {
                Bounds b = Main.mainController.getWebViewMain().getBoundsInParent();
                json.key("x").value(b.getMinX() + windowScreenX);
                json.key("y").value(b.getMinY() + windowScreenY);
                json.key("h").value(b.getHeight());
                json.key("w").value(b.getWidth());
            }
            json.endObject();
            json.key("items");
            json.array();
            {
                for (int i = 0; i < 15; i++) {
                    // System.out.println(i);
                    JSObject bounds = (JSObject) webEngine
                            .executeScript("document.getElementById('option_" + i + "').getBoundingClientRect()");
                    // System.out.println(bounds.getClass().getCanonicalName());

                    Double left = ((Number) bounds.getMember("left")).doubleValue();
                    Double top = ((Number) bounds.getMember("top")).doubleValue();
                    Double width = ((Number) bounds.getMember("width")).doubleValue();
                    Double height = ((Number) bounds.getMember("height")).doubleValue();
                    json.object();
                    json.key("x").value(left + windowScreenX);
                    json.key("y").value(top + windowScreenY);
                    json.key("w").value(width);
                    json.key("h").value(height);
                    json.endObject();

                }
            }
            json.endArray();
        }
        json.endObject();
    }

}
