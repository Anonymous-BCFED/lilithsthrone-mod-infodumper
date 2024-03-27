package plugin.infodumper.dumpers;

import com.lilithsthrone.game.character.fetishes.AbstractFetish;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJob;
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
import java.util.EnumSet;

public class JobsDumper {
    static AbstractItem dildo;
    private static NPC male;
    private static NPC female;

    public static void dump() {
        male = new TestMaleNPC();
        female = new TestFemaleNPC();
        dildo = Main.game.getItemGen().generateItem("norin_dildos_realistic_dildo");
        dumpJobs();
    }

    private static NPC prepCharacter(NPC character, AbstractFetish fetish) {
        character.clearFetishes();
        character.addFetish(fetish);
        return character;
    }

    private static void dumpJobs() {
        Path newFilePath = Paths.get("data", "internalData", "slavejobs.json");
        File parentDir = newFilePath.getParent().toFile();
        if (!parentDir.isDirectory())
            parentDir.mkdirs();
        NPC[] chars = new NPC[]{male, female};
        try (FileWriter w = new FileWriter(newFilePath.toFile())) {
            // I tried using JSONObject and it just ran out of memory. :(
            JSONWriter json = new JSONWriter(w);
            // Start global object
            json.object();
            // Fart out our header
            json.key("version").value(1);

            // Begin the effects dict
            json.key("slave_jobs");
            json.object();
            EnumSet.allOf(SlaveJob.class).forEach(job -> {
                json.key(job.name());
                json.object();
                {
                    json.key("colour").value(job.getColour().getId());
                    json.key("colourWebHex").value(job.getColour().toWebHexString());
                    json.key("hourlyEventChance").value(job.getHourlyEventChance());
                    json.key("slaveLimit").value(job.getSlaveLimit());
                    json.key("hourlyStaminaDrain").value(job.getHourlyStaminaDrain());
                    json.key("nameFeminine").value(job.getNameFeminine());
                    json.key("nameMasculine").value(job.getNameMasculine());
                    json.key("description").value(job.getDescription());
                    json.key("obedienceGain").value(job.getObedienceGain(male));
                    json.key("affectionGain").value(job.getAffectionGain(male));
                    json.key("obedienceAndAffectionBySexAndFetish");
                    json.object();
                    {
                        for (NPC c : chars) {
                            json.key(c.getName() == "Female" ? "f" : "m");
                            json.object();
                            for (AbstractFetish f : Fetish.getAllFetishes()) {
                                prepCharacter(c, f);
                                json.key(Fetish.getIdFromFetish(f));
                                json.object();
                                {
                                    json.key("affection").value(job.getAffectionGain(c));
                                    json.key("obedience").value(job.getObedienceGain(c));
                                }
                                json.endObject();
                            }
                            json.endObject();
                        }
                    }
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
