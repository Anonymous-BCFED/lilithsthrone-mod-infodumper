package plugin.infodumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONException;
import org.json.JSONWriter;

import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.misc.GenericFemaleNPC;
import com.lilithsthrone.game.character.npc.misc.GenericMaleNPC;

public class FetishDumper {

	private static NPC malenpc;
	private static NPC femalenpc;

	public static void dump() {
		malenpc = new GenericMaleNPC(false);
		femalenpc = new GenericFemaleNPC(false);
		dumpFetishes();
	}

	private static void dumpFetishes() {
		Path newFilePath = Paths.get("data", "internalData", "fetishes.json");
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
			json.key("fetishes");
			json.object();
			Fetish.getAllFetishes().forEach(f -> {
				if (f == null)
					return;
				json.key(f.getID());
				json.object();
				//json.key("id").value(f.getID());
				json.key("name"); {
					json.object();
					json.key("male").value(f.getName(malenpc));
					json.key("female").value(f.getName(femalenpc));
					json.endObject();
				}
				json.key("associatedCorruptionLevel").value(f.getAssociatedCorruptionLevel().toString());
				json.key("attributeModifiers"); 
				{
					json.object();
					if(f.getAttributeModifiers()!=null) {
						f.getAttributeModifiers().forEach((k,v) -> {
							json.key(k.toString());
							json.value(v);
						});
					}
					json.endObject();
				}
				json.key("cost").value(f.getCost());
				json.key("description"); {
					json.object();
					json.key("male").value(f.getDescription(malenpc));
					json.key("female").value(f.getDescription(femalenpc));
					json.endObject();
				}
				json.key("experienceGainFromSexAction").value(f.getExperienceGainFromSexAction());
				json.key("extraEffects");{
					json.object();
					json.key("male"); {
						json.array();
						f.getExtraEffects(malenpc).forEach(l -> json.value(l));
						json.endArray();
					}
					json.key("female"); {
						json.array();
						f.getExtraEffects(femalenpc).forEach(l -> json.value(l));
						json.endArray();
					}
					json.endObject();
				}
				json.key("name");{
					json.object();
					json.key("male").value(f.getName(malenpc));
					json.key("female").value(f.getName(femalenpc));
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


}
