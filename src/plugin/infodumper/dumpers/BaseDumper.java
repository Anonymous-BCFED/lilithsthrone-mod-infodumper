package plugin.infodumper.dumpers;

import com.lilithsthrone.game.inventory.clothing.AbstractClothingType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import org.json.JSONWriter;

import java.util.List;

public class BaseDumper {

    protected static void dumpItemEffects(JSONWriter json, List<ItemEffect> effects) {
        json.key("effects");
        {
            json.array();
            for (ItemEffect e : effects) {
                json.object();
                {
                    json.key("type").value(ItemEffectType.getIdFromItemEffectType(e.getItemEffectType()));
                    json.key("primary_modifier").value(e.getPrimaryModifier() == null ? null : e.getPrimaryModifier().toString());
                    json.key("secondary_modifier").value(e.getSecondaryModifier() == null ? null : e.getSecondaryModifier().toString());
                    json.key("potency").value(e.getPotency() == null ? null : e.getPotency().toString());
                    json.key("limit").value(e.getLimit());
                    json.key("timer").value(0);//e.getTimer().getSecondsPassed()
                }
                json.endObject();
            }
            json.endArray();
        }
    }
}
