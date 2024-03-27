package plugin.infodumper.dumpers;

import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.body.types.ArmType;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.main.Main;
import org.json.JSONException;
import org.json.JSONWriter;
import plugin.infodumper.npcs.TestFemaleNPC;
import plugin.infodumper.npcs.TestMaleNPC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BodyPartDumper {
    static AbstractItem dildo;
    private static NPC male;

    public static void dump() {
        male = new TestMaleNPC();
        dildo = Main.game.getItemGen().generateItem("norin_dildos_realistic_dildo");
        dumpBodyParts();
    }

    private static void dumpBodyParts() {
        Path newPath = Paths.get("data", "internalData", "bodyparts");
        File newDir = newPath.toFile();
        if (!newDir.isDirectory())
            newDir.mkdirs();
        dumpArms(newPath.resolve("arms.json"));
    }
    private static void dumpArms(Path newFilePath) {
        System.out.println("        Arms...");
        try (FileWriter w = new FileWriter(newFilePath.toFile())) {
            // I tried using JSONObject and it just ran out of memory. :(
            JSONWriter json = new JSONWriter(w);
            // Start global object
            json.object();
            // Fart out our header
            json.key("version").value(1);

            // Begin the effects dict
            json.key("arms");
            json.object();
            ArmType.getAllArmTypes().forEach(armType -> {
                if (armType == null)
                    return;
                String id = ArmType.getIdFromArmType(armType);
                json.key(id);
                json.object();
                {
                    json.key("nameSingular").value(armType.getNameSingular(null));
                    json.key("namePlural").value(armType.getNamePlural(null));
                    json.key("race").value(Race.getIdFromRace(armType.getRace()));
                    json.key("coveringType").value(BodyCoveringType.getIdFromBodyCoveringType(armType.getBodyCoveringType(male)));
                    json.key("transformationName").value(armType.getTransformationNameOverride());
                    json.key("flags");
                    json.array();
                    {
                        if(armType.allowsFlight()) json.value("allowsFlight");
                        if(armType.isFromExternalFile()) json.value("isFromExternalFile");
                        if(armType.isMod()) json.value("isMod");
                        if(armType.isUnderarmHairAllowed()) json.value("isUnderarmHairAllowed");
                    }
                    json.endArray();
                    json.key("tags");
                    json.array();
                    {
                        armType.getTags().stream().map(BodyPartTag::name).sorted().forEachOrdered(json::value);
                    }
                    json.endArray();
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

    public class ItemEffectInfo {
        public String type;
        public String mod1;
        public String mod2;
        public String pot;
        public int limit;
        public String desc;

        public ItemEffectInfo(
                String type,
                String mod1,
                String mod2,
                String pot,
                int limit,
                String desc) {
            this.type = type;
            this.mod1 = mod1;
            this.mod2 = mod2;
            this.pot = pot;
            this.limit = limit;
            this.desc = desc;
        }
    }

}
