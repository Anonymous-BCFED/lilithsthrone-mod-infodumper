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

public class TestMaleNPC extends NPC {
    public TestMaleNPC() {
        super(false, new NameTriplet("Male", "Male", "Male"), "Character",
                "You will never see this character, so why are you reading this?",
                21,
                Month.JANUARY,
                1,
                1,
                Gender.M_P_MALE,
                Subspecies.HUMAN,
                RaceStage.HUMAN,
                new CharacterInventory(0),
                WorldType.EMPTY,
                PlaceType.GENERIC_HOLDING_CELL,
                false);
    }
    @Override
    public void setStartingBody(boolean b) {
        // Based on PrologueMale since he's fairly average

        // Body:

        // Core:
        this.setHeight(185);
        this.setFemininity(10);
        this.setMuscle(Muscle.THREE_MUSCULAR.getMedianValue());
        this.setBodySize(BodySize.TWO_AVERAGE.getMedianValue());

        // Coverings:

        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HUMAN, PresetColour.EYE_BROWN));
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, PresetColour.COVERING_BROWN_DARK), true);
        this.setHairLength(HairLength.TWO_SHORT.getMedianValue());
        this.setHairStyle(HairStyle.SLICKED_BACK);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BROWN_DARK), false);
        this.setUnderarmHair(BodyHair.FOUR_NATURAL);
        this.setAssHair(BodyHair.FOUR_NATURAL);
        this.setPubicHair(BodyHair.FOUR_NATURAL);
        this.setFacialHair(BodyHair.ZERO_NONE);

//		this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED));
//		this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_RED));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
//		this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED));
//		this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PURPLE));

        // Face:
        this.setFaceVirgin(true);
        this.setLipSize(LipSize.ONE_AVERAGE);
        this.setFaceCapacity(Capacity.ZERO_IMPENETRABLE, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.FLAT.getMeasurement());
        this.setBreastShape(BreastShape.WIDE);
        this.setNippleSize(NippleSize.ZERO_TINY);
        this.setAreolaeSize(AreolaeSize.ZERO_TINY);
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        this.setAssSize(AssSize.TWO_SMALL);
        this.setHipSize(HipSize.TWO_NARROW);
        this.setAssCapacity(Capacity.ZERO_IMPENETRABLE, true);
        this.setAssWetness(Wetness.ZERO_DRY);
        this.setAssElasticity(OrificeElasticity.ONE_RIGID.getValue());
        this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());
        // Anus modifiers

        // Penis:
        this.setPenisVirgin(false);
        this.setPenisSize(15);
        this.setTesticleSize(TesticleSize.TWO_AVERAGE);
        // Leave cum as normal value

        // Vagina:
        // No vagina

        // Feet:
        // Foot shape
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
