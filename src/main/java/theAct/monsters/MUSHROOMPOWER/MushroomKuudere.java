package theAct.monsters.MUSHROOMPOWER;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAct.TheActMod;
import theAct.powers.ObsessionPower;
import theAct.powers.ProtectionPower;

public class MushroomKuudere extends AbstractMonster {
    public static final String ID = TheActMod.makeID("MushroomYandere");
    public static final String ENCOUNTER_ID = TheActMod.makeID("MUSHROOMGANG");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    private static final int MIN_HP = 16;
    private static final int MAX_HP = 20;
    private static final int ASC_HP_MODIFIER = 3;
    private static final int PROTECTION_AMT = 3;
    private static final int PROTECTION_AMT_ASC_MODIFIER = 2;
    private static final int HEAL_AMT = 3;
    private static final int HEAL_AMT_ASC_MODIFIER = 2;
    private static final int HEADBUTT_DMG = 6;
    private static final int HEADBUTT_DMG_ASC_MODIFIER = 2;
    private int headbuttDmg;
    private int protectionAmt;
    private int healAmt;

    public MushroomKuudere(float x, float y) {
        super(NAME, ID, MAX_HP, 0.0F, 10.0F, 280.0F, 280.0F, null, x, y);
        this.img = ImageMaster.loadImage(TheActMod.assetPath("/images/monsters/phrog/temp.png"));
        if (AbstractDungeon.ascensionLevel >= 17) {
            protectionAmt = PROTECTION_AMT + PROTECTION_AMT_ASC_MODIFIER;
            healAmt = HEAL_AMT + HEAL_AMT_ASC_MODIFIER;
        } else {
            protectionAmt = PROTECTION_AMT;
            healAmt = HEAL_AMT;
        }
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(MIN_HP + ASC_HP_MODIFIER, MAX_HP + ASC_HP_MODIFIER);
        } else {
            setHp(MIN_HP, MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            headbuttDmg = HEADBUTT_DMG + HEADBUTT_DMG_ASC_MODIFIER;
            damage.add(new DamageInfo(this, headbuttDmg));
        } else {
            headbuttDmg = HEADBUTT_DMG;
            damage.add(new DamageInfo(this, headbuttDmg));
        }
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ProtectionPower(this, protectionAmt)));
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, damage.get(0)));
                break;
            case 1:
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m != this) {
                        AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, healAmt));
                    }
                }
                break;
        }
        rollMove();
    }

    @Override
    protected void getMove(int i) {
        if (lastMove((byte)1)) {
            setMove((byte)0, Intent.ATTACK, damage.get(0).base);
        }
        setMove((byte)1, Intent.BUFF);
    }
}
