package plugin.infodumper.dumpers;

import com.lilithsthrone.game.inventory.ColourReplacement;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.clothing.AbstractClothingType;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.utils.colours.Colour;
import org.json.JSONException;
import org.json.JSONWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClothingDumper extends  BaseDumper{

    // private static NPC malenpc;
    // private static NPC femalenpc;

    public static void dump() {
        // malenpc = new TestMaleNPC();
        // femalenpc = new TestFemaleNPC();
        dumpPerks();
    }

    private static void dumpPerks() {
        Path newFilePath = Paths.get("data", "internalData", "clothing.json");
        File parentDir = newFilePath.getParent().toFile();
        if (!parentDir.isDirectory())
            parentDir.mkdirs();
        try (FileWriter w = new FileWriter(newFilePath.toFile())) {
            // I tried using JSONObject and it just ran out of memory. :(
            JSONWriter json = new JSONWriter(w);
            // Start global object
            json.object();
            // Fart out our header
            json.key("version").value(2);

            // Begin the perks dict
            json.key("clothing");
            json.object();
            for (AbstractClothingType c : ClothingType.getAllClothing()) {
                if (c == null)
                    continue;
                json.key(ClothingType.getIdFromClothingType(c));
                json.object();
                //json.key("id").value(ClothingType.getIdFromClothingType(c));
                json.key("isAbleToBeDropped").value(c.isAbleToBeDropped());
                json.key("isAbleToBeSold").value(c.isAbleToBeSold());
                json.key("isAppendColourName").value(c.isAppendColourName());
                json.key("isColourDerivedFromPattern").value(c.isColourDerivedFromPattern());
                json.key("isDefaultSlotCondom").value(c.isDefaultSlotCondom());
                json.key("isPatternAvailable").value(c.isPatternAvailable());
                json.key("authorDescription").value(c.getAuthorDescription());
                json.key("description").value(c.getDescription());
                json.key("determiner").value(c.getDeterminer());
                //json.key("id").value(c.getId());
                json.key("name").value(c.getName());
                json.key("namePlural").value(c.getNamePlural());
                // Path base = Paths.get(".");
                // Path filePath = Paths.get(c.getPathName());
                // json.key("pathName").value(base.relativize(filePath).toString());
                json.key("baseValue").value(c.getBaseValue());
                json.key("isPlural").value(c.isPlural());
                json.key("tags");
                {
                    json.array();
                    {
                        c.getDefaultItemTags().stream().map(ItemTag::name).sorted().forEachOrdered(json::value);
                    }
                    json.endArray();
                }
                dumpItemEffects(json, c.getEffects());
                json.key("colourReplacements");
                {
                    json.array();
                    for (ColourReplacement cr : c.getColourReplacements()) {
                        json.object();
                        {
                            json.key("replacements");
                            json.array();
                            {
                                cr.getColourReplacements().stream().sorted().forEachOrdered(json::value);
                            }
                            json.endArray();
                            json.key("defaults");
                            json.array();
                            {
                                cr.getDefaultColours().stream().map(Colour::getId).sorted().forEachOrdered(json::value);
                            }
                            json.endArray();
                            json.key("extras");
                            json.array();
                            {
                                cr.getExtraColours().stream().map(Colour::getId).sorted().forEachOrdered(json::value);
                            }
                            json.endArray();
                            json.key("all");
                            json.array();
                            {
                                cr.getAllColours().stream().map(Colour::getId).sorted().forEachOrdered(json::value);
                            }
                            json.endArray();
                        }
                        json.endObject();
                    }
                    json.endArray();
                }
                json.endObject();
            }
            json.endObject();
            json.endObject();
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
