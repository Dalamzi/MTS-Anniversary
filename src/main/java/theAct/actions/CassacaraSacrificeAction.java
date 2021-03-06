package theAct.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class CassacaraSacrificeAction extends AbstractGameAction {

    private AbstractMonster cassacara;
    private AbstractMonster carcassSack;
    private int strengthGain;

    public CassacaraSacrificeAction(AbstractMonster cassacara, AbstractMonster carcassSack, int strengthGain) {
        this.cassacara = cassacara;
        this.carcassSack = carcassSack;
        this.strengthGain = strengthGain;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.cassacara, this.cassacara, new StrengthPower(this.cassacara, this.strengthGain), this.strengthGain));
        AbstractDungeon.actionManager.addToTop(new HealAction(this.cassacara, this.cassacara, this.carcassSack.currentHealth));
        AbstractDungeon.actionManager.addToTop(new WaitAction(1.0F));
        AbstractDungeon.actionManager.addToTop(new SuicideAction(this.carcassSack, true));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new BiteEffect(this.carcassSack.hb.cX, this.carcassSack.hb.cY, Color.CHARTREUSE.cpy()), 0.2F));
        this.isDone = true;
    }
}
