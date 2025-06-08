package plugin.infodumper.npcs;

import com.lilithsthrone.game.character.CharacterImportSetting;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.Covering;
import com.lilithsthrone.game.character.body.valueEnums.*;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.persona.NameTriplet;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.inventory.CharacterInventory;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.Month;

public class TestFemaleNPC extends NPC {
    public TestFemaleNPC() {
        super(false, new NameTriplet("Female", "Female", "Female"), "Character",
                "You will never see this character, so why are you reading this?",
                21,
                Month.JANUARY,
                1,
                1,
                Gender.F_V_B_FEMALE,
                Subspecies.HUMAN,
                RaceStage.HUMAN,
                new CharacterInventory(false,0),
                WorldType.EMPTY,
                PlaceType.GENERIC_HOLDING_CELL,
                false);
    }
    @Override
    public void setStartingBody(boolean b) {
        // Based on PrologueFemale since she's fairly average

        // Body:

        // Core:
        this.setHeight(168);
        this.setFemininity(85);
        this.setMuscle(Muscle.TWO_TONED.getMedianValue());
        this.setBodySize(BodySize.ONE_SLENDER.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HUMAN, PresetColour.EYE_BLUE));
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, PresetColour.COVERING_BLONDE), true);
        this.setHairLength(HairLength.FOUR_MID_BACK.getMedianValue());
        this.setHairStyle(HairStyle.WAVY);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BLACK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_FELINE_FUR, PresetColour.COVERING_BLACK), false);
        this.setUnderarmHair(BodyHair.ZERO_NONE);
        this.setAssHair(BodyHair.ZERO_NONE);
        this.setPubicHair(BodyHair.TWO_MANICURED);
        this.setFacialHair(BodyHair.ZERO_NONE);


        this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED));
        this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
        this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED));
        this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PINK));

        // Face:
        this.setFaceVirgin(false);
        this.setLipSize(LipSize.THREE_PLUMP);
        this.setFaceCapacity(Capacity.THREE_SLIGHTLY_LOOSE, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.DD.getMeasurement());
        this.setBreastShape(BreastShape.ROUND);
        this.setNippleSize(NippleSize.THREE_LARGE.getValue());
        this.setAreolaeSize(AreolaeSize.THREE_LARGE.getValue());
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        this.setAssSize(AssSize.FOUR_LARGE.getValue());
        this.setHipSize(HipSize.FOUR_WOMANLY.getValue());
        this.setAssCapacity(Capacity.TWO_TIGHT, true);
        this.setAssWetness(Wetness.ZERO_DRY);
        this.setAssElasticity(OrificeElasticity.FOUR_LIMBER.getValue());
        this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());
        // Anus modifiers

        // Penis:
        // No penis

        // Vagina:
        this.setVaginaVirgin(false);
        this.setVaginaClitorisSize(ClitorisSize.ZERO_AVERAGE);
        this.setVaginaLabiaSize(LabiaSize.TWO_AVERAGE);
        this.setVaginaSquirter(false);
        this.setVaginaCapacity(Capacity.TWO_TIGHT, true);
        this.setVaginaWetness(Wetness.THREE_WET);
        this.setVaginaElasticity(OrificeElasticity.THREE_FLEXIBLE.getValue());
        this.setVaginaPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());

        // Feet:
        // 		this.setFootStructure(FootStructure.PLANTIGRADE);
    }

    @Override
    public void loadFromXML(Element element, Document document, CharacterImportSetting... characterImportSettings) {
// no
    }

    @Override
    public void changeFurryLevel() {

    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    @Override
    public boolean isUnique() {
        return true;
    }
}
