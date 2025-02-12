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
package org.l2j.gameserver.network.authcomm.gs2as;

import io.github.joealisson.mmocore.WritableBuffer;
import org.l2j.gameserver.network.GameClient;
import org.l2j.gameserver.network.authcomm.AuthServerClient;
import org.l2j.gameserver.network.authcomm.SendablePacket;

public class PlayerAuthRequest extends SendablePacket
{
	private String account;
	private int playOkID1, playOkID2, loginOkID1, loginOkID2;

	public PlayerAuthRequest(GameClient client)
	{
		account = client.getAccountName();
		playOkID1 = client.getSessionKey().getGameServerSessionId();
		playOkID2 = client.getSessionKey().getGameServerAccountId();
		loginOkID1 = client.getSessionKey().getAuthAccountId();
		loginOkID2 = client.getSessionKey().getAuthKey();
	}

	protected void writeImpl(AuthServerClient client, WritableBuffer buffer) {
		buffer.writeByte(0x02);
		buffer.writeString(account);
		buffer.writeInt(playOkID1);
		buffer.writeInt(playOkID2);
		buffer.writeInt(loginOkID1);
		buffer.writeInt(loginOkID2);
	}
}