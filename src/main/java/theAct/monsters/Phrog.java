package theAct.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAct.TheActMod;
import theAct.actions.PhrogLickAction;

import javax.smartcardio.Card;

public class Phrog extends AbstractMonster {
	/*
		Turn one he takes a card and then keeps it for 3 turns.
	 */
	public static final String ID = TheActMod.makeID("Phrog");
	private static final MonsterStrings STRINGS = CardCrawlGame.languagePack.getMonsterStrings(ID);

	private int maxHP = 113;
	private int minHP = 97;
	private int tackleDamage = 25;

	AbstractCard heldCard;

	public Phrog() {
		super(STRINGS.NAME, ID, 75, 0, 0, 300, 300, null, 0, 0);

		this.img = ImageMaster.loadImage(TheActMod.assetPath("/images/monsters/phrog/temp.png"));

		switch(AbstractDungeon.ascensionLevel){
			case 7:
				this.minHP += 5;
				this.maxHP += 5;
			case 2:
				this.tackleDamage += 2;
		}

		this.damage.add(new DamageInfo(this, tackleDamage));

		this.setHp(minHP, maxHP);
	}

	@Override
	public void takeTurn() {
		AbstractPlayer p = AbstractDungeon.player;

		switch(this.nextMove) {
			case 0:
				AbstractDungeon.actionManager.addToBottom(new PhrogLickAction(this, 3));
				break;
			case 1:
				AbstractDungeon.actionManager.addToBottom(new DamageAction(p, damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
				break;
		}
	}

	public void setHeldCard(AbstractCard card) {
		this.heldCard = card;
	}

	@Override
	protected void getMove(int roll) {
		if(this.heldCard == null) {
			this.setMove(STRINGS.MOVES[0], MoveBytes.LICK, Intent.MAGIC);
		}
	}

	private static class MoveBytes {
		private static final byte LICK = 0;
	}
}