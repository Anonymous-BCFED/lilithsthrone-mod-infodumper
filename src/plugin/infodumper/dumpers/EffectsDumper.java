package plugin.infodumper.dumpers;

import com.lilithsthrone.game.character.effects.AbstractStatusEffect;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.ItemType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectsDumper {
    public class EffectHolder {

        public class ValuesHolder {

            public int limit;
            public List<String> descriptionForSelf;
            public List<String> descriptionForOthers;
            public String descriptionForPotions;
            public Map<String, Integer> appliedStatusEffects = new HashMap<>();
        }

        public String type;
        public TFModifier mod1;
        public TFModifier mod2;
        public TFPotency potency;
        public ValuesHolder valuesForClothing = new ValuesHolder();
        public ValuesHolder valuesForPotions = new ValuesHolder();

        public EffectHolder() {
        }
    }

    private static NPC user;
    private static NPC target;
    private static AbstractItem potion;
    private static AbstractClothing clothing;

    public static void dump() {
        user = new TestMaleNPC();
        target = new TestFemaleNPC();
        clothing = Main.game.getItemGen().generateClothing(ClothingType.getClothingTypeFromId("norin_dildos_realistic_dildo"));
        potion = Main.game.getItemGen().generateItem(ItemType.POTION);
        dumpEffects();
    }

    private static void dumpEffects() {
        EffectsDumper why = new EffectsDumper();
        Path newFilePath = Paths.get("data", "internalData", "effects.json");
        File parentDir = newFilePath.getParent().toFile();
        if (!parentDir.isDirectory())
            parentDir.mkdirs();
        Map<String, EffectHolder> effectHolders = new HashMap<String, EffectsDumper.EffectHolder>();
        ItemEffectType.allEffectTypes.forEach(e -> {
            if (e == null)
                return;
            String typ = ItemEffectType.getIdFromItemEffectType(e);
            for (TFModifier mod1 : e.getPrimaryModifiers()) {
                for (TFModifier mod2 : e.getSecondaryModifiers(clothing, mod1)) {
                    for (TFPotency pot : e.getPotencyModifiers(mod1, mod2)) {
                        buildValuesHolderFrom(e, mod1, mod2, pot, typ, effectHolders, why, true);
                    }
                }
                for (TFModifier mod2 : e.getSecondaryModifiers(potion, mod1)) {
                    for (TFPotency pot : e.getPotencyModifiers(mod1, mod2)) {
                        buildValuesHolderFrom(e, mod1, mod2, pot, typ, effectHolders, why, false);
                    }
                }
            }
        });
        try (FileWriter w = new FileWriter(newFilePath.toFile())) {
            // I tried using JSONObject and it just ran out of memory. :(
            JSONWriter json = new JSONWriter(w);
            // Start global object
            json.object();
            // Fart out our header
            json.key("version").value(2);

            json.key("effects");
            json.array();
            effectHolders.forEach((_k, holder) -> {
                json.object();
                {
                    json.key("type").value(holder.type);
                    json.key("mod1").value(holder.mod1.name());
                    json.key("mod2").value(holder.mod2.name());
                    json.key("pot").value(holder.potency.name());
                    json.key("clothing");
                    json.object();
                    {
                        buildJSONFromValues(json, holder.valuesForClothing);
                    }
                    json.endObject();
                    json.key("potion");
                    json.object();
                    {
                        buildJSONFromValues(json, holder.valuesForPotions);
                    }
                    json.endObject();
                }
                json.endObject();
            });
            json.endArray();
            json.endObject();
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void buildJSONFromValues(JSONWriter json, EffectHolder.ValuesHolder v) {
        json.key("limit").value(v.limit);
        if (v.appliedStatusEffects != null) {
            json.key("appliedStatusEffects");
            json.object();
            {
                v.appliedStatusEffects.keySet().stream().sorted().forEachOrdered((k) -> {
                    json.key(k).value(v.appliedStatusEffects.get(k));
                });
            }
            json.endObject();
        }
        json.key("descriptions");
        json.object();
        {
            if (v.descriptionForSelf != null) {
                json.key("self");
                json.array();
                {
                    v.descriptionForSelf.stream().forEachOrdered(json::value);
                }
                json.endArray();
            }
            if (v.descriptionForOthers != null) {
                json.key("other");
                json.array();
                {
                    v.descriptionForOthers.stream().forEachOrdered(json::value);
                }
                json.endArray();
            }
            if (v.descriptionForPotions != null) {
                json.key("potion").value(v.descriptionForPotions);
            }
        }
        json.endObject();
    }

    private static void buildValuesHolderFrom(AbstractItemEffectType e, TFModifier mod1, TFModifier mod2, TFPotency pot, String typ, Map<String, EffectHolder> effectHolders, EffectsDumper why, boolean forClothing) {
        String k = typ + mod1.name() + mod2.name() + pot.name();
        EffectHolder eh = null;
        if (!effectHolders.containsKey(k)) {
            eh = why.new EffectHolder();
            eh.type = typ;
            eh.mod1 = mod1;
            eh.mod2 = mod2;
            eh.potency = pot;
            effectHolders.put(k, eh);
        } else {
            eh = effectHolders.get(k);
        }
        int limit = e.getLimits(mod1, mod2);
        EffectHolder.ValuesHolder values = forClothing ? eh.valuesForClothing : eh.valuesForPotions;
        for (Map.Entry<AbstractStatusEffect, Integer> entry : e.getAppliedStatusEffects().entrySet()) {
            values.appliedStatusEffects.put(entry.getKey().getId(), entry.getValue());
        }
        values.limit = limit;
        values.descriptionForSelf = e.getEffectsDescription(mod1, mod2, pot, limit, user, user);
        values.descriptionForOthers = e.getEffectsDescription(mod1, mod2, pot, limit, user, target);
        values.descriptionForPotions = e.getPotionDescriptor();
    }

}
