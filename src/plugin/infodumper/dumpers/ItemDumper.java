package plugin.infodumper.dumpers;

import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.utils.colours.Colour;
import org.json.JSONException;
import org.json.JSONWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ItemDumper extends BaseDumper {

    // private static NPC malenpc;
    // private static NPC femalenpc;

    public static void dump() {
        // malenpc = new TestMaleNPC();
        // femalenpc = new TestFemaleNPC();
        dumpItems();
    }

    private static void dumpItems() {
        Path newFilePath = Paths.get("data", "internalData", "items.json");
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
            json.key("items");
            json.object();
            ItemType.getAllItems().forEach(abstractItemType -> {
                if (abstractItemType == null)
                    return;
                json.key(ItemType.getIdFromItemType(abstractItemType));
                json.object();
                json.key("id").value(ItemType.getIdFromItemType(abstractItemType));
                json.key("authorDescription").value(abstractItemType.getAuthorDescription());
                json.key("description").value(abstractItemType.getDescription());
                json.key("determiner").value(abstractItemType.getDeterminer());
                json.key("isAbleToBeDropped").value(abstractItemType.isAbleToBeDropped());
                json.key("isAbleToBeSold").value(abstractItemType.isAbleToBeSold());
                json.key("isAbleToBeUsedFromInventory").value(abstractItemType.isAbleToBeUsedFromInventory());
                json.key("isAbleToBeUsedInCombatAllies").value(abstractItemType.isAbleToBeUsedInCombatAllies());
                json.key("isAbleToBeUsedInCombatEnemies").value(abstractItemType.isAbleToBeUsedInCombatEnemies());
                json.key("isAbleToBeUsedInSex").value(abstractItemType.isAbleToBeUsedInSex());
                json.key("isConsumedOnUse").value(abstractItemType.isConsumedOnUse());
                json.key("isFetishGiving").value(abstractItemType.isFetishGiving());
                json.key("isFromExternalFile").value(abstractItemType.isFromExternalFile());
                json.key("isGift").value(abstractItemType.isGift());
                json.key("isMod").value(abstractItemType.isMod());
                json.key("isPlural").value(abstractItemType.isPlural());
                json.key("isTransformative").value(abstractItemType.isTransformative());
                json.key("name").value(abstractItemType.getName(false, false));
                json.key("namePlural").value(abstractItemType.getNamePlural(false, false));
                json.key("value").value(abstractItemType.getValue());
                json.key("rarity").value(abstractItemType.getRarity().name());
                dumpItemEffects(json, abstractItemType.getEffects());
                json.key("colourShades");
                {
                    json.array();
                    {
                        // abstractItemType.getColourShades().stream().map(Colour::getId).sorted().forEachOrdered(json::value);
                        abstractItemType.getColourShades().stream().map(Colour::getId).forEachOrdered(json::value);
                    }
                    json.endArray();
                }
                json.key("tags");
                {
                    json.array();
                    {
                        abstractItemType.getItemTags().stream().map(ItemTag::name).sorted().forEachOrdered(json::value);
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

}
