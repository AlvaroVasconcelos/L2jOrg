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
package org.l2j.gameserver.network.clientpackets.compound;

import org.l2j.gameserver.data.xml.CombinationItemsManager;
import org.l2j.gameserver.model.actor.instance.Player;
import org.l2j.gameserver.model.actor.request.CompoundRequest;
import org.l2j.gameserver.model.item.combination.CombinationItem;
import org.l2j.gameserver.engine.item.Item;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.clientpackets.ClientPacket;
import org.l2j.gameserver.network.serverpackets.compound.ExEnchantOneFail;
import org.l2j.gameserver.network.serverpackets.compound.ExEnchantOneOK;

import java.util.List;

/**
 * @author UnAfraid
 */
public class RequestNewEnchantPushOne extends ClientPacket {
    private int _objectId;

    @Override
    public void readImpl() {
        _objectId = readInt();
    }

    @Override
    public void runImpl() {
        final Player player = client.getPlayer();
        if (player == null) {
            return;
        } else if (player.isInStoreMode()) {
            client.sendPacket(SystemMessageId.YOU_CANNOT_DO_THAT_WHILE_IN_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
            client.sendPacket(ExEnchantOneFail.STATIC_PACKET);
            return;
        } else if (player.isProcessingTransaction() || player.isProcessingRequest()) {
            client.sendPacket(SystemMessageId.YOU_CANNOT_USE_THIS_SYSTEM_DURING_TRADING_PRIVATE_STORE_AND_WORKSHOP_SETUP);
            client.sendPacket(ExEnchantOneFail.STATIC_PACKET);
            return;
        }

        final CompoundRequest request = new CompoundRequest(player);
        if (!player.addRequest(request)) {
            client.sendPacket(ExEnchantOneFail.STATIC_PACKET);
            return;
        }

        // Make sure player owns this item.
        request.setItemOne(_objectId);
        final Item itemOne = request.getItemOne();
        if (itemOne == null) {
            client.sendPacket(ExEnchantOneFail.STATIC_PACKET);
            player.removeRequest(request.getClass());
            return;
        }

        final List<CombinationItem> combinationItems = CombinationItemsManager.getInstance().getItemsByFirstSlot(itemOne.getId());

        // Not implemented or not able to merge!
        if (combinationItems.isEmpty()) {
            client.sendPacket(ExEnchantOneFail.STATIC_PACKET);
            player.removeRequest(request.getClass());
            return;
        }

        client.sendPacket(ExEnchantOneOK.STATIC_PACKET);
    }
}
