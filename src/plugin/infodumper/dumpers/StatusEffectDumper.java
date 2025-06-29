package plugin.infodumper.dumpers;

import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.utils.Util.Value;
import org.json.JSONException;
import org.json.JSONWriter;
import plugin.infodumper.npcs.TestFemaleNPC;
import plugin.infodumper.npcs.TestMaleNPC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StatusEffectDumper {

    private static NPC malenpc;
    private static NPC femalenpc;

    public static void dump() {
        malenpc = new TestMaleNPC();
        femalenpc = new TestFemaleNPC();
        dumpPerks();
    }

    private static void dumpPerks() {
        Path newFilePath = Paths.get("data", "internalData", "statusEffects.json");
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
            json.key("sfx");
            json.object();
            StatusEffect.getAllStatusEffects().forEach(sfx -> {
                if (sfx == null)
                    return;
                json.key(StatusEffect.getIdFromStatusEffect(sfx));
                json.object();
                {
                    // json.key("id").value(sfx.getID());
                    json.key("getAdditionalDescriptions");
                    json.object();
                    {
                        json.key("female");
                        json.array();
                        {
                            try {
                                List<Value<Integer, String>> dl = sfx.getAdditionalDescriptions(femalenpc);
                                if (dl == null) {
                                    json.value(null);
                                } else {
                                    dl.forEach(vp -> {
                                        json.array();
                                        {
                                            json.value(vp.getKey()).value(vp.getValue());
                                        }
                                        json.endArray();
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        json.endArray();
                        json.key("male");
                        json.array();
                        {
                            try {
                                List<Value<Integer, String>> dl = sfx.getAdditionalDescriptions(malenpc);
                                if (dl == null) {
                                    json.value(null);
                                } else {
                                    dl.forEach(vp -> {
                                        json.array();
                                        {
                                            json.value(vp.getKey()).value(vp.getValue());
                                        }
                                        json.endArray();
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        json.endArray();
                    }
                    json.endObject();
                    json.key("isCombat").value(sfx.isCombatEffect());
                    json.key("isConstantRefresh").value(sfx.isConstantRefresh());
                    json.key("isFromExternalFile").value(sfx.isFromExternalFile());
                    json.key("isMod").value(sfx.isMod());
                    json.key("isRemoveAtEndOfSex").value(sfx.isRemoveAtEndOfSex());
                    json.key("isRequiresApplicationCheck").value(sfx.isRequiresApplicationCheck());
                    json.key("isSexEffect").value(sfx.isSexEffect());
                    json.key("renderInEffectsPanel").value(sfx.renderInEffectsPanel());
                    json.key("effectInterval").value(sfx.getEffectInterval());
                    json.key("beneficial").value(sfx.getBeneficialStatus().toString());
                    json.key("attributeModifiers");
                    json.object();
                    {
                        json.key("male");
                        json.object();
                        {
                            sfx.getAttributeModifiers(malenpc).forEach((k, v) -> {
                                json.key(k.toString()).value(v);
                            });
                        }
                        json.endObject();
                        json.key("female");
                        json.object();
                        {
                            sfx.getAttributeModifiers(femalenpc).forEach((k, v) -> {
                                json.key(k.toString()).value(v);
                            });
                        }
                        json.endObject();
                    }
                    json.endObject();
                    json.key("renderingPriority").value(sfx.getRenderingPriority());
                    json.key("name");
                    json.object();
                    {
                        json.key("male").value(sfx.getName(malenpc));
                        json.key("female").value(sfx.getName(femalenpc));
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
