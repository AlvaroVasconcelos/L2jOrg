/*
 * Copyright © 2019-2020 L2JOrg
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
package org.l2j.scripts.handlers.playeractions;

import org.l2j.gameserver.data.xml.model.ActionData;
import org.l2j.gameserver.handler.IPlayerActionHandler;
import org.l2j.gameserver.model.actor.Creature;
import org.l2j.gameserver.model.actor.instance.Player;
import org.l2j.gameserver.network.serverpackets.ActionFailed;

import static org.l2j.gameserver.util.GameUtils.isCreature;

/**
 * Tactical Signs setting player action handler.
 * @author Nik
 */
public final class TacticalSignUse implements IPlayerActionHandler {

	@Override
	public void useAction(Player player, ActionData action, boolean ctrlPressed, boolean shiftPressed) {
		if (!player.isInParty() || !isCreature(player.getTarget())) {
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		player.getParty().addTacticalSign(player, action.getOptionId(), (Creature) player.getTarget());
	}
}