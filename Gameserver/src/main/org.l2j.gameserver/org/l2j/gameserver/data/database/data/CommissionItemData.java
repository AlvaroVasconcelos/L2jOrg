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
package org.l2j.gameserver.data.database.data;

import org.l2j.commons.database.annotation.Column;
import org.l2j.commons.database.annotation.Table;

import java.time.Instant;

/**
 * @author JoeAlisson
 */
@Table(value = "commission_items", autoGeneratedProperty = "commissionId")
public class CommissionItemData {

    @Column("commission_id")
    private int commissionId;

    @Column("item_object_id")
    private int objectId;

    @Column("price_per_unit")
    private long price;

    @Column("start_time")
    private Instant startTime;

    @Column("duration_in_days")
    private byte duration;

    public int getCommissionId() {
        return commissionId;
    }

    public int getObjectId() {
        return objectId;
    }

    public long getPrice() {
        return price;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public byte getDuration() {
        return duration;
    }

    public static CommissionItemData of(int objectId, long pricePerUnit, byte durationInDays) {
        var data = new CommissionItemData();
        data.objectId = objectId;
        data.price = pricePerUnit;
        data.startTime = Instant.now();
        data.duration = durationInDays;
        return data;
    }
}
