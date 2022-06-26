package plugin.infodumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;

import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.character.effects.AbstractPerk;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.misc.GenericMaleNPC;
import com.lilithsthrone.utils.colours.Colour;

public class PerkDumper {

	private static NPC malenpc;

	public static void dump() {
		malenpc = new GenericMaleNPC(false);
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
				json.key("renderingPriority").value(p.getRenderingPriority());
				json.key("name").value(p.getName(malenpc));
				json.key("colours").value(convertColours(p));
				json.key("category").value(p.getPerkCategory().name());
				json.key("equippableTrait").value(p.isEquippableTrait());
				json.key("attributeModifiers").value(convertAttributeMods(p));
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
		HashMap<AbstractAttribute, Integer> mods = p.getAttributeModifiers(malenpc);
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
