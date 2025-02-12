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
package org.l2j.gameserver.network.clientpackets;

import org.l2j.gameserver.model.WorldObject;
import org.l2j.gameserver.engine.item.Item;
import org.l2j.gameserver.network.serverpackets.ExRpItemLink;
import org.l2j.gameserver.world.World;

import static org.l2j.gameserver.util.GameUtils.isItem;

/**
 * @author KenM
 */
public class RequestExRqItemLink extends ClientPacket {
    private int _objectId;

    @Override
    public void readImpl() {
        _objectId = readInt();
    }

    @Override
    public void runImpl() {
        final WorldObject object = World.getInstance().findObject(_objectId);
        if (isItem(object)) {
            final Item item = (Item) object;
            if (item.isPublished()) {
                client.sendPacket(new ExRpItemLink(item));
            }
        }
    }
}
