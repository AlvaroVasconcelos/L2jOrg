/*
 * Copyright © 2019-2021 L2JOrg
 *
 * This file is part of the L2JOrg project.
 *
 * L2JOrg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * L2JOrg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2j.scripts.handlers.effecthandlers;

import org.l2j.gameserver.ai.CtrlIntention;
import org.l2j.gameserver.engine.skill.api.Skill;
import org.l2j.gameserver.engine.skill.api.SkillEffectFactory;
import org.l2j.gameserver.model.StatsSet;
import org.l2j.gameserver.model.actor.Creature;
import org.l2j.gameserver.model.effects.AbstractEffect;
import org.l2j.gameserver.model.effects.EffectFlag;
import org.l2j.gameserver.engine.item.Item;

import static org.l2j.gameserver.util.GameUtils.isPlayer;
import static org.l2j.gameserver.util.GameUtils.isSummon;

/**
 * Betray effect implementation.
 * @author decad
 * @author JoeAlisson
 */
public final class Betray extends AbstractEffect {

    private Betray() {
    }

    @Override
    public boolean canStart(Creature effector, Creature effected, Skill skill)
    {
        return isPlayer(effector) && isSummon(effected);
    }

    @Override
    public long getEffectFlags()
    {
        return EffectFlag.BETRAYED.getMask();
    }

    @Override
    public void onStart(Creature effector, Creature effected, Skill skill, Item item)
    {
        effected.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, effected.getActingPlayer());
    }

    @Override
    public void onExit(Creature effector, Creature effected, Skill skill)
    {
        effected.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
    }

    public static class Factory implements SkillEffectFactory {
        private static final Betray INSTANCE = new Betray();

        @Override
        public AbstractEffect create(StatsSet data) {
            return INSTANCE;
        }

        @Override
        public String effectName() {
            return "Betray";
        }
    }
}
