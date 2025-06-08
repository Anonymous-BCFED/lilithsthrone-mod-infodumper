package plugin.infodumper.dumpers;

import com.lilithsthrone.world.Bearing;
import com.lilithsthrone.world.places.AbstractPlaceUpgrade;
import com.lilithsthrone.world.places.PlaceType;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class PlaceDumper extends BaseDumper {
    public static void dump() {
        Path newFilePath = Paths.get("data", "internalData", "places.json");
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
            json.key("places");
            json.object();
            {
                PlaceType.getAllPlaceTypes().forEach(placeType -> {
                    if (placeType == null)
                        return;
                    json.key(PlaceType.getIdFromPlaceType(placeType));
                    json.object();
                    {
                        json.key("id").value(PlaceType.getIdFromPlaceType(placeType));
                        json.key("aquatic").value(placeType.getAquatic().name());
                        json.key("author").value(placeType.getAuthor());
                        json.key("backgroundColour").value(placeType.getBackgroundColour().getId());
                        Bearing bearing = placeType.getBearing();
                        json.key("bearing").value(bearing != null ? bearing.name() : null);
                        json.key("colour").value(placeType.getColour().getId());
                        json.key("darkness").value(placeType.getDarkness().name());
                        json.key("deskName").value(placeType.getDeskName());
                        json.key("name").value(placeType.getName());
//                        json.key("parent");
//                        json.object();
//                        {
//                            EntranceType pa = placeType.getParentAlignment();
//                            json.key("alignment").value(pa!=null?pa.name():null);
//                            AbstractPlaceType pp = placeType.getParentPlaceType();
//                            json.key("placeType").value(pp!=null?pp.getId():null);
//                            json.key("worldType").value(placeType.getParentWorldType());
//                        }
//                        json.endObject();
                        json.key("appendFormat").value(placeType.getPlaceNameAppendFormat(0));
                        json.key("svgString").value(placeType.getSVGString(new HashSet<AbstractPlaceUpgrade>()));
                        json.key("teleportPermissions").value(placeType.getTeleportPermissions().name());
                        json.key("tooltipDescription").value(placeType.getTooltipDescription());
                        json.key("virginityLossDescription").value(placeType.getVirginityLossDescription());
                        json.key("wallName").value(placeType.getWallName());
                        json.key("worldRegion").value(placeType.getWorldRegion().name());
                        json.key("flags");
                        json.array();
                        {
                            if (placeType.isAbleToBeUpgraded()) json.value("ableToBeUpgraded");
                            if (placeType.isDangerous()) json.value("dangerous");
                            if (placeType.isDeskNameOverride()) json.value("deskNameOverride");
                            if (placeType.isFromExternalFile()) json.value("fromExternalFile");
                            if (placeType.isFurniturePresent()) json.value("furniturePresent");
                            if (placeType.isFurniturePresentOverride()) json.value("furniturePresentOverride");
                            if (placeType.isGlobalMapTile()) json.value("globalMapTile");
                            if (placeType.isItemsDisappear()) json.value("itemsDisappear");
                            if (placeType.isLand()) json.value("land");
                            if (placeType.isLoiteringEnabled()) json.value("loiteringEnabled");
                            if (placeType.isLoiteringEnabledOverride()) json.value("loiteringEnabledOverride");
                            if (placeType.isMod()) json.value("mod");
//                            if(placeType.isPopulated()) json.value("populated");
                            if (placeType.isStormImmune()) json.value("stormImmune");
                            if (placeType.isWallNameOverride()) json.value("wallNameOverride");
                            if (placeType.isWallsPresent()) json.value("wallsPresent");
                            if (placeType.isWallsPresentOverride()) json.value("wallsPresentOverride");
                            if (placeType.isWater()) json.value("water");
                        }
                        json.endArray();
                    }
                    json.endObject(); // PlaceType

                });
                json.endObject(); // places
            }
            json.endObject(); // global
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
