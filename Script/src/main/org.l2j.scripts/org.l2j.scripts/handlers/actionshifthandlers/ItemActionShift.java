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
package org.l2j.scripts.handlers.actionshifthandlers;

import org.l2j.gameserver.enums.InstanceType;
import org.l2j.gameserver.handler.IActionShiftHandler;
import org.l2j.gameserver.model.WorldObject;
import org.l2j.gameserver.model.actor.instance.Player;
import org.l2j.gameserver.engine.item.Item;
import org.l2j.gameserver.network.serverpackets.html.NpcHtmlMessage;

public class ItemActionShift implements IActionShiftHandler
{
	@Override
	public boolean action(Player activeChar, WorldObject target, boolean interact)
	{
		if (activeChar.isGM())
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(0, 1, "<html><body><center><font color=\"LEVEL\">Item Info</font></center><br><table border=0><tr><td>Object ID: </td><td>" + target.getObjectId() + "</td></tr><tr><td>Item ID: </td><td>" + target.getId() + "</td></tr><tr><td>Owner ID: </td><td>" + ((Item) target).getOwnerId() + "</td></tr><tr><td>Location: </td><td>" + target.getLocation() + "</td></tr><tr><td><br></td></tr><tr><td>Class: </td><td>" + target.getClass().getSimpleName() + "</td></tr></table></body></html>");
			activeChar.sendPacket(html);
		}
		return true;
	}
	
	@Override
	public InstanceType getInstanceType()
	{
		return InstanceType.L2ItemInstance;
	}
}
