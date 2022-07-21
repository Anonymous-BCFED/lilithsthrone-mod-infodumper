package plugin.infodumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONWriter;

import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.misc.GenericFemaleNPC;
import com.lilithsthrone.game.character.npc.misc.GenericMaleNPC;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;

public class ClothingDumper {

	private static NPC malenpc;
	private static NPC femalenpc;

	public static void dump() {
		malenpc = new GenericMaleNPC(false);
		femalenpc = new GenericFemaleNPC(false);
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
			json.key("version").value(1);

			// Begin the perks dict
			json.key("clothing");
			json.object();
			ClothingType.getAllClothing().forEach(c -> {
				if (c == null)
					return;
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
				json.key("pathName").value(c.getPathName());
				json.key("baseValue").value(c.getBaseValue());
				json.key("isPlural").value(c.isPlural());
				json.key("effects");
				{
					json.array();
					for(ItemEffect e : c.getEffects()) {
						json.object();
						{
							json.key("type").value(e.getId());
							json.key("mod_primary").value(e.getPrimaryModifier().toString());
							json.key("mod_secondary").value(e.getSecondaryModifier().toString());
							json.key("potency").value(e.getPotency().toString());
							json.key("limit").value(e.getLimit());
							json.key("timer").value(e.getTimer().getSecondsPassed());
						}
						json.endObject();
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
