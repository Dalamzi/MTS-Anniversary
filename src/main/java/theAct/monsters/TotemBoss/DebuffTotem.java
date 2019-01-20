//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theAct.monsters.TotemBoss;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import theAct.TheActMod;

import java.util.Iterator;

public class DebuffTotem extends AbstractTotemSpawn {
    public static final String ID = TheActMod.makeID("DebuffTotem");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public Integer attackDmg;
    public Integer secondaryEffect;


    public DebuffTotem(TotemBoss boss) {
        super(NAME, ID, boss);

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.attackDmg = 4;
            this.secondaryEffect = 3;
        } else if (AbstractDungeon.ascensionLevel >= 4) {
            this.attackDmg = 3;
            this.secondaryEffect = 2;
        } else {
            this.attackDmg = 3;
            this.secondaryEffect = 2;
        }
        this.damage.add(new DamageInfo(this, this.attackDmg));

        this.intentType = Intent.ATTACK_DEBUFF;

    }



    public void takeTurn() {
        breakp:

        switch (this.nextMove) {
            case 1:
                // AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));

                Integer randomizer = AbstractDungeon.cardRng.random(2);

                switch (randomizer){
                    case 0:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                        break;

                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                        break;

                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                        break;

            }




                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        }
    }

    protected void getMove(int num)
    {
        this.setMove((byte)1, intentType, this.attackDmg);
    }
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;

    }




}