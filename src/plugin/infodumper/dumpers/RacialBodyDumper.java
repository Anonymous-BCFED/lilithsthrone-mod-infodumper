package plugin.infodumper.dumpers;

import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.types.*;
import com.lilithsthrone.game.character.body.valueEnums.BodyMaterial;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.persona.PersonalityTrait;
import com.lilithsthrone.game.character.race.RacialBody;
import org.json.JSONException;
import org.json.JSONWriter;
import plugin.infodumper.npcs.TestFemaleNPC;
import plugin.infodumper.npcs.TestMaleNPC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class RacialBodyDumper {

    private static NPC malenpc;
    private static NPC femalenpc;

    public static void dump() {
        malenpc = new TestMaleNPC();
        femalenpc = new TestFemaleNPC();
        dumpPerks();
    }

    private static void dumpPerks() {
        Path newFilePath = Paths.get("data", "internalData", "racialBodies.json");
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
            json.key("racial-bodies");
            json.object();
            RacialBody.getAllRacialBodies().forEach(rb -> {
                if (rb == null)
                    return;
                json.key(RacialBody.getIdFromRacialBody(rb));
                json.object();
                {
                    //json.key("id").value(sfx.getID());
                    json.key("antennaTypes").array();rb.getAntennaTypes(false).forEach(ant->json.value(AntennaType.getIdFromAntennaType(ant)));json.endArray();
                    json.key("anusCapacity").value(rb.getAnusCapacity());
                    json.key("anusDepth").value(rb.getAnusDepth());
                    json.key("anusElasticity").value(rb.getAnusElasticity());
                    json.key("anusPlasticity").value(rb.getAnusPlasticity());
                    json.key("anusWetness").value(rb.getAnusWetness());
                    json.key("armRows").value(rb.getArmRows());
                    json.key("armType").value(ArmType.getIdFromArmType(rb.getArmType()));
                    json.key("assType").value(AssType.getIdFromAssType(rb.getAssType()));
                    json.key("bodyHairType").value(BodyCoveringType.getIdFromBodyCoveringType(rb.getBodyHairType()));
                    json.key("bodyMaterial").value(rb.getBodyMaterial().name());
                    json.key("breastCrotchAreolaeShape").value(rb.getBreastCrotchAreolaeShape());
                    json.key("breastCrotchAreolaeSize").value(rb.getBreastCrotchAreolaeSize());
                    json.key("breastCrotchCapacity").value(rb.getBreastCrotchCapacity());
                    json.key("breastCrotchCount").value(rb.getBreastCrotchCount());
                    json.key("breastCrotchDepth").value(rb.getBreastCrotchDepth());
                    json.key("breastCrotchElasticity").value(rb.getBreastCrotchElasticity());
                    json.key("breastCrotchLactationRate").value(rb.getBreastCrotchLactationRate());
                    json.key("breastCrotchNippleShape").value(rb.getBreastCrotchNippleShape().name());
                    json.key("breastCrotchNippleSize").value(rb.getBreastCrotchNippleSize());
                    json.key("breastCrotchPlasticity").value(rb.getBreastCrotchPlasticity());
                    json.key("breastCrotchShapes").array();rb.getBreastCrotchShapes().forEach(shape -> json.value(shape.name()));json.endArray();
                    json.key("breastCrotchSize").value(rb.getBreastCrotchSize());
                    json.key("breastCrotchType").value(BreastType.getIdFromBreastType(rb.getBreastCrotchType()));
                    json.key("breastShapes").array();rb.getBreastShapes().forEach(shape -> json.value(shape.name()));json.endArray();
                    json.key("breastSize").value(rb.getBreastSize());
                    json.key("breastType").value(BreastType.getIdFromBreastType(rb.getBreastType()));
                    json.key("clitGirth").value(rb.getClitGirth());
                    json.key("clitSize").value(rb.getClitSize());
                    json.key("cumProduction").value(rb.getCumProduction());
                    json.key("earType").value(EarType.getIdFromEarType(rb.getEarType()));
                    json.key("eyeType").value(EyeType.getIdFromEyeType(rb.getEyeType()));
                    json.key("faceType").value(FaceType.getIdFromFaceType(rb.getFaceType()));
                    json.key("genitalArrangement").value(rb.getGenitalArrangement().name());
                    json.key("hairType").value(HairType.getIdFromHairType(rb.getHairType()));
                    json.key("hornTypes").array();rb.getHornTypes(false).forEach(hornType->json.value(HornType.getIdFromHornType(hornType)));json.endArray();
                    json.key("legConfiguration").value(rb.getLegConfiguration().name());
                    json.key("legType").value(LegType.getIdFromLegType(rb.getLegType()));
                    json.key("nippleCountPerBreastCrotch").value(rb.getNippleCountPerBreastCrotch());
                    json.key("noBreastSize").value(rb.getNoBreastSize());
                    json.key("penisGirth").value(rb.getPenisGirth());
                    json.key("penisSize").value(rb.getPenisSize());
                    json.key("penisType").value(PenisType.getIdFromPenisType(rb.getPenisType()));
                    json.key("personalityTraitChances");dumpPersonalityTraitChances(json, rb.getPersonalityTraitChances());
                    json.key("tailTypes").array();rb.getTailType().forEach(t->json.value(TailType.getIdFromTailType(t)));json.endArray();
                    json.key("tentacleType").value(TentacleType.getIdFromTentacleType(rb.getTentacleType()));
                    json.key("testicleQuantity").value(rb.getTesticleQuantity());
                    json.key("testicleSize").value(rb.getTesticleSize());
                    json.key("torsoType").value(TorsoType.getIdFromTorsoType(rb.getTorsoType()));
                    json.key("vaginaCapacity").value(rb.getVaginaCapacity());
                    json.key("vaginaDepth").value(rb.getVaginaDepth());
                    json.key("vaginaElasticity").value(rb.getVaginaElasticity());
                    json.key("vaginaPlasticity").value(rb.getVaginaPlasticity());
                    json.key("vaginaType").value(VaginaType.getIdFromVaginaType(rb.getVaginaType()));
                    json.key("vaginaWetness").value(rb.getVaginaWetness());
                    json.key("wingTypes").array();rb.getWingTypes().forEach(t->json.value(WingType.getIdFromWingType(t)));json.endArray();
                    json.key("female").object();
                    {
                        json.key("breastCount").value(rb.getBreastCountFemale());
                        json.key("antennaLength").value(rb.getFemaleAntennaLength());
                        json.key("areolaeShape").value(rb.getFemaleAreolaeShape().name());
                        json.key("areolaeSize").value(rb.getFemaleAreolaeSize());
                        json.key("assSize").value(rb.getFemaleAssSize());
                        json.key("bodySize").value(rb.getFemaleBodySize());
                        json.key("breastCapacity").value(rb.getFemaleBreastCapacity());
                        json.key("breastDepth").value(rb.getFemaleBreastDepth());
                        json.key("breastElasticity").value(rb.getFemaleBreastElasticity());
                        json.key("breastPlasticity").value(rb.getFemaleBreastPlasticity());
                        json.key("femininity").value(rb.getFemaleFemininity());
                        json.key("hairLength").value(rb.getFemaleHairLength());
                        json.key("height").value(rb.getFemaleHeight());
                        json.key("hipSize").value(rb.getFemaleHipSize());
                        json.key("hornLength").value(rb.getFemaleHornLength());
                        json.key("lactationRate").value(rb.getFemaleLactationRate());
                        json.key("lipSize").value(rb.getFemaleLipSize());
                        json.key("muscle").value(rb.getFemaleMuscle());
                        json.key("nippleCountPerBreast").value(rb.getFemaleNippleCountPerBreast());
                        json.key("nippleShape").value(rb.getFemaleNippleShape().name());
                        json.key("nippleSize").value(rb.getFemaleNippleSize());
                        json.key("wingSize").value(rb.getFemaleWingSize());
                    }
                    json.endObject();
                    json.key("male").object();
                    {
                        json.key("breastCount").value(rb.getBreastCountMale());
                        json.key("antennaLength").value(rb.getMaleAntennaLength());
                        json.key("areolaeShape").value(rb.getMaleAreolaeShape().name());
                        json.key("areolaeSize").value(rb.getMaleAreolaeSize());
                        json.key("assSize").value(rb.getMaleAssSize());
                        json.key("bodySize").value(rb.getMaleBodySize());
                        json.key("breastCapacity").value(rb.getMaleBreastCapacity());
                        json.key("breastDepth").value(rb.getMaleBreastDepth());
                        json.key("breastElasticity").value(rb.getMaleBreastElasticity());
                        json.key("breastPlasticity").value(rb.getMaleBreastPlasticity());
                        json.key("femininity").value(rb.getMaleFemininity());
                        json.key("hairLength").value(rb.getMaleHairLength());
                        json.key("height").value(rb.getMaleHeight());
                        json.key("hipSize").value(rb.getMaleHipSize());
                        json.key("hornLength").value(rb.getMaleHornLength());
                        json.key("lactationRate").value(rb.getMaleLactationRate());
                        json.key("lipSize").value(rb.getMaleLipSize());
                        json.key("muscle").value(rb.getMaleMuscle());
                        json.key("nippleCountPerBreast").value(rb.getMaleNippleCountPerBreast());
                        json.key("nippleShape").value(rb.getMaleNippleShape().name());
                        json.key("nippleSize").value(rb.getMaleNippleSize());
                        json.key("wingSize").value(rb.getMaleWingSize());
                    }
                    json.endObject();
/*
    public Map<PersonalityTrait, Float> getPersonalityTraitChances() {
        Map<PersonalityTrait, Float> map = new HashMap();
        PersonalityTrait[] var2 = PersonalityTrait.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            PersonalityTrait trait = var2[var4];
            if (trait.getPersonalityCategory() == PersonalityCategory.SPEECH) {
                if (trait == PersonalityTrait.MUTE) {
                    map.put(trait, 0.001F);
                } else {
                    map.put(trait, 0.01F);
                }
            } else if (trait.getPersonalityCategory() == PersonalityCategory.SEX && trait != PersonalityTrait.LEWD) {
                map.put(trait, 0.025F);
            } else {
                map.put(trait, 0.05F);
            }
        }

        if (this.fromExternalFile) {
            Iterator var6 = this.personalityChanceOverrides.entrySet().iterator();

            while(var6.hasNext()) {
                Map.Entry<PersonalityTrait, Float> entry = (Map.Entry)var6.next();
                map.put((PersonalityTrait)entry.getKey(), (Float)entry.getValue());
            }
        }

        return map;
    }

    public SexualOrientation getSexualOrientation(Gender gender) {
        if (this.fromExternalFile) {
            return gender.isFeminine() ? SexualOrientationPreference.getSexualOrientationFromUserPreferences(this.orientationFeminineGynephilic, this.orientationFeminineAmbiphilic, this.orientationFeminineAndrophilic) : SexualOrientationPreference.getSexualOrientationFromUserPreferences(this.orientationMasculineGynephilic, this.orientationMasculineAmbiphilic, this.orientationMasculineAndrophilic);
        } else {
            return gender.isFeminine() ? SexualOrientationPreference.getSexualOrientationFromUserPreferences(20, 40, 40) : SexualOrientationPreference.getSexualOrientationFromUserPreferences(60, 30, 10);
        }
    }

    public boolean isMod() {
        return this.mod;
    }

    public boolean isFromExternalFile() {
        return this.fromExternalFile;
    }

    public AbstractAntennaType getRandomrAntennaType(boolean includeTypeNONE) {
        List<AbstractAntennaType> antennaList = new ArrayList(this.antennaTypes);
        if (!includeTypeNONE && this.antennaTypes.size() != 1) {
            antennaList.remove(AntennaType.NONE);
            return (AbstractAntennaType)antennaList.get(Util.random.nextInt(antennaList.size()));
        } else {
            return (AbstractAntennaType)this.antennaTypes.get(Util.random.nextInt(this.antennaTypes.size()));
        }
    }

    public List<AbstractAntennaType> getAntennaTypes(boolean removeTypeNone) {
        if (removeTypeNone) {
            List<AbstractAntennaType> antennaList = new ArrayList(this.antennaTypes);
            antennaList.remove(AntennaType.NONE);
            return antennaList;
        } else {
            return this.antennaTypes;
        }
    }

    public int getMaleAntennaLength() {
        return this.maleAntennaLength;
    }

    public int getFemaleAntennaLength() {
        return this.femaleAntennaLength;
    }

    public AbstractArmType getArmType() {
        return this.armType;
    }

    public AbstractAssType getAssType() {
        return this.assType;
    }

    public AbstractBreastType getBreastType() {
        return this.breastType;
    }

    public List<BreastShape> getBreastShapes() {
        return this.breastShapes;
    }

    public AbstractFaceType getFaceType() {
        return this.faceType;
    }

    public AbstractEyeType getEyeType() {
        return this.eyeType;
    }

    public AbstractEarType getEarType() {
        return this.earType;
    }

    public AbstractHairType getHairType() {
        return this.hairType;
    }

    public AbstractLegType getLegType() {
        return this.getLegType(this.getLegConfiguration());
    }

    public AbstractLegType getLegType(LegConfiguration configuration) {
        return this.legType;
    }

    public LegConfiguration getLegConfiguration() {
        return this.legConfiguration;
    }

    public AbstractTorsoType getTorsoType() {
        return this.torsoType;
    }

    public BodyMaterial getBodyMaterial() {
        return this.bodyMaterial;
    }

    public AbstractBodyCoveringType getBodyHairType() {
        return this.isFromExternalFile() ? BodyCoveringType.getBodyCoveringTypeFromId(this.bodyHairId) : this.bodyHairType;
    }

    public AbstractHornType getRandomHornType(boolean includeTypeNONE) {
        List<AbstractHornType> hornList = new ArrayList(this.hornTypes);
        if (!includeTypeNONE && this.hornTypes.size() != 1) {
            hornList.remove(HornType.NONE);
            return (AbstractHornType)hornList.get(Util.random.nextInt(hornList.size()));
        } else {
            return (AbstractHornType)this.hornTypes.get(Util.random.nextInt(this.hornTypes.size()));
        }
    }

    public List<AbstractHornType> getHornTypes(boolean removeTypeNone) {
        if (removeTypeNone) {
            List<AbstractHornType> hornList = new ArrayList(this.hornTypes);
            hornList.remove(HornType.NONE);
            return hornList;
        } else {
            return this.hornTypes;
        }
    }

    public AbstractPenisType getPenisType() {
        return this.penisType;
    }

    public AbstractTailType getRandomTailType(boolean includeTypeNONE) {
        List<AbstractTailType> tailList = new ArrayList(this.tailTypes);
        if (!includeTypeNONE && this.tailTypes.size() != 1) {
            tailList.remove(TailType.NONE);
            return (AbstractTailType)tailList.get(Util.random.nextInt(tailList.size()));
        } else {
            return (AbstractTailType)this.tailTypes.get(Util.random.nextInt(this.tailTypes.size()));
        }
    }

    public List<AbstractTailType> getTailType() {
        return this.tailTypes;
    }

    public AbstractTentacleType getTentacleType() {
        return this.tentacleType;
    }

    public AbstractVaginaType getVaginaType() {
        return this.vaginaType;
    }

    public AbstractWingType getRandomWingType(boolean includeTypeNONE) {
        List<AbstractWingType> wingList = new ArrayList(this.wingTypes);
        if (!includeTypeNONE && this.wingTypes.size() != 1) {
            wingList.remove(WingType.NONE);
            return (AbstractWingType)wingList.get(Util.random.nextInt(wingList.size()));
        } else {
            return (AbstractWingType)this.wingTypes.get(Util.random.nextInt(this.wingTypes.size()));
        }
    }

    public List<AbstractWingType> getWingTypes() {
        return this.wingTypes;
    }

    public int getArmRows() {
        return this.armRows;
    }

    public float getAnusCapacity() {
        return this.anusCapacity;
    }

    public int getAnusDepth() {
        return this.anusDepth;
    }

    public int getAnusWetness() {
        return this.anusWetness;
    }

    public int getAnusElasticity() {
        return this.anusElasticity;
    }

    public int getAnusPlasticity() {
        return this.anusPlasticity;
    }

    public int getMaleHeight() {
        return this.maleHeight;
    }

    public int getMaleFemininity() {
        return this.maleFemininity;
    }

    public int getMaleMuscle() {
        return this.maleMuscle;
    }

    public int getMaleBodySize() {
        return this.maleBodySize;
    }

    public int getFemaleHeight() {
        return this.femaleHeight;
    }

    public int getFemaleFemininity() {
        return this.femaleFemininity;
    }

    public int getFemaleBodySize() {
        return this.femaleBodySize;
    }

    public int getFemaleMuscle() {
        return this.femaleMuscle;
    }

    public int getMaleAssSize() {
        return this.maleAssSize;
    }

    public int getFemaleAssSize() {
        return this.femaleAssSize;
    }

    public int getMaleHipSize() {
        return this.maleHipSize;
    }

    public int getFemaleHipSize() {
        return this.femaleHipSize;
    }

    public int getMaleHairLength() {
        return this.maleHairLength;
    }

    public int getFemaleHairLength() {
        return this.femaleHairLength;
    }

    public int getMaleHornLength() {
        return this.maleHornLength;
    }

    public int getFemaleHornLength() {
        return this.femaleHornLength;
    }

    public int getNoBreastSize() {
        return this.noBreastSize;
    }

    public int getBreastSize() {
        return this.breastSize;
    }

    public int getMaleLactationRate() {
        return this.maleLactationRate;
    }

    public int getFemaleLactationRate() {
        return this.femaleLactationRate;
    }

    public int getFemaleBreastElasticity() {
        return this.femaleBreastElasticity;
    }

    public int getMaleBreastElasticity() {
        return this.maleBreastElasticity;
    }

    public int getFemaleBreastPlasticity() {
        return this.femaleBreastPlasticity;
    }

    public int getMaleBreastPlasticity() {
        return this.maleBreastPlasticity;
    }

    public float getFemaleBreastCapacity() {
        return this.femaleBreastCapacity;
    }

    public float getMaleBreastCapacity() {
        return this.maleBreastCapacity;
    }

    public int getFemaleBreastDepth() {
        return this.femaleBreastDepth;
    }

    public int getMaleBreastDepth() {
        return this.maleBreastDepth;
    }

    public int getMaleNippleSize() {
        return this.maleNippleSize;
    }

    public int getFemaleNippleSize() {
        return this.femaleNippleSize;
    }

    public NippleShape getMaleNippleShape() {
        return this.maleNippleShape;
    }

    public NippleShape getFemaleNippleShape() {
        return this.femaleNippleShape;
    }

    public int getMaleNippleCountPerBreast() {
        return this.maleNippleCountPerBreast;
    }

    public int getFemaleNippleCountPerBreast() {
        return this.femaleNippleCountPerBreast;
    }

    public int getMaleAreolaeSize() {
        return this.maleAreolaeSize;
    }

    public int getFemaleAreolaeSize() {
        return this.femaleAreolaeSize;
    }

    public AreolaeShape getMaleAreolaeShape() {
        return this.maleAreolaeShape;
    }

    public AreolaeShape getFemaleAreolaeShape() {
        return this.femaleAreolaeShape;
    }

    public AbstractBreastType getBreastCrotchType() {
        return this.breastCrotchType;
    }

    public List<BreastShape> getBreastCrotchShapes() {
        return this.breastCrotchShapes;
    }

    public int getBreastCrotchSize() {
        return this.breastCrotchSize;
    }

    public int getBreastCrotchLactationRate() {
        return this.breastCrotchLactationRate;
    }

    public float getBreastCrotchCapacity() {
        return this.breastCrotchCapacity;
    }

    public int getBreastCrotchDepth() {
        return this.breastCrotchDepth;
    }

    public int getBreastCrotchElasticity() {
        return this.breastCrotchElasticity;
    }

    public int getBreastCrotchPlasticity() {
        return this.breastCrotchPlasticity;
    }

    public int getNippleCountPerBreastCrotch() {
        return this.nippleCountPerBreastCrotch;
    }

    public int getBreastCrotchNippleSize() {
        return this.breastCrotchNippleSize;
    }

    public NippleShape getBreastCrotchNippleShape() {
        return this.breastCrotchNippleShape;
    }

    public int getBreastCrotchAreolaeSize() {
        return this.breastCrotchAreolaeSize;
    }

    public AreolaeShape getBreastCrotchAreolaeShape() {
        return this.breastCrotchAreolaeShape;
    }

    public int getBreastCrotchCount() {
        return this.breastCrotchCount;
    }

    public int getClitSize() {
        return this.clitSize;
    }

    public int getClitGirth() {
        return this.clitGirth;
    }

    public int getPenisSize() {
        return this.penisSize;
    }

    public int getPenisGirth() {
        return this.penisGirth;
    }

    public int getTesticleSize() {
        return this.testicleSize;
    }

    public int getCumProduction() {
        return this.cumProduction;
    }

    public float getVaginaCapacity() {
        return this.vaginaCapacity;
    }

    public int getVaginaDepth() {
        return this.vaginaDepth;
    }

    public int getVaginaWetness() {
        return this.vaginaWetness;
    }

    public int getVaginaElasticity() {
        return this.vaginaElasticity;
    }

    public int getVaginaPlasticity() {
        return this.vaginaPlasticity;
    }

    public int getBreastCountMale() {
        return this.breastCountMale;
    }

    public int getBreastCountFemale() {
        return this.breastCountFemale;
    }

    public int getTesticleQuantity() {
        return this.testicleQuantity;
    }

    public int getMaleLipSize() {
        return this.maleLipSize;
    }

    public int getFemaleLipSize() {
        return this.femaleLipSize;
    }

    public int getMaleWingSize() {
        return this.maleWingSize;
    }

    public int getFemaleWingSize() {
        return this.femaleWingSize;
    }

    public GenitalArrangement getGenitalArrangement() {
        return this.genitalArrangement;
    }
 */
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

    private static void dumpPersonalityTraitChances(JSONWriter json, Map<PersonalityTrait, Float> personalityTraitChances) {
        json.object();
        personalityTraitChances.forEach((k,v)->{
            json.key(k.name()).value(v);
        });
        json.endObject();
    }

}
