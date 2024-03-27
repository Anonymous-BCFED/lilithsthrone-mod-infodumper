package plugin.infodumper.dumpers;

import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.character.effects.AbstractPerk;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.effects.PerkManager;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.utils.colours.Colour;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import plugin.infodumper.npcs.TestFemaleNPC;
import plugin.infodumper.npcs.TestMaleNPC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class PerkDumper {

    private static NPC malenpc;
    private static NPC femalenpc;

    public static void dump() {
        malenpc = new TestMaleNPC();
        femalenpc = new TestFemaleNPC();
        dumpPerks();
    }

    private static void dumpPerks() {
        Path newFilePath = Paths.get("data", "internalData", "perks.json");
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
            json.key("perks");
            json.object();
            Perk.getAllPerks().forEach(p -> {
                if (p == null)
                    return;
                json.key(Perk.getIdFromPerk(p));
                json.object();
                //json.key("id").value(p.getID());
                json.key("renderingPriority").value(p.getRenderingPriority());
                json.key("name");
                {
                    json.object();
                    json.key("male").value(p.getName(malenpc));
                    json.key("female").value(p.getName(femalenpc));
                    json.endObject();
                }
                json.key("colours").value(convertColours(p));
                json.key("category").value(p.getPerkCategory().name());
                json.key("equippableTrait").value(p.isEquippableTrait());
                json.key("attributeModifiers").value(convertAttributeMods(p));
                json.key("row");
                {
                    json.object();
                    json.key("male").value(PerkManager.MANAGER.getPerkRow(malenpc, p));
                    json.key("female").value(PerkManager.MANAGER.getPerkRow(femalenpc, p));
                    json.endObject();
                }
                json.endObject();
            });
            json.endObject();
            json.endObject();
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static JSONObject convertAttributeMods(AbstractPerk p) {
        JSONObject o = new JSONObject();
        Map<AbstractAttribute, Integer> mods = p.getAttributeModifiers(malenpc);
        if (mods != null) {
            mods.forEach((k, v) -> {
                o.put(k.getName(), v.intValue());
            });
        }
        return null;
    }

    private static JSONArray convertColours(AbstractPerk p) {
        JSONArray colours = new JSONArray();
        Field fColours;
        try {
            fColours = AbstractPerk.class.getDeclaredField("colours");
            fColours.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return colours;
        }
        try {
            for (Object o : (Iterable<?>) fColours.get(p)) {
                colours.put(((Colour) o).getId());
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return colours;
    }

}
