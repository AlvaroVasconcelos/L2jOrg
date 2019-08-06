package org.l2j.gameserver.world.zone.form;

import org.l2j.commons.util.Rnd;
import org.l2j.gameserver.geoengine.GeoEngine;
import org.l2j.gameserver.model.Location;
import org.l2j.gameserver.model.items.CommonItem;
import org.l2j.gameserver.world.zone.ZoneForm;

/**
 * A primitive circular zone
 *
 * @author durgus
 */
public class ZoneCylinderForm extends ZoneForm {
    private final int centerX;
    private final int centerY;
    private final int minZ;
    private final int maxZ;
    private final int radius;
    private final int _radS;

    public ZoneCylinderForm(int x, int y, int minZ, int maxZ, int radius) {
        centerX = x;
        centerY = y;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.radius = radius;
        _radS = radius * radius;
    }

    @Override
    public boolean isInsideZone(int x, int y, int z) {
        return ((Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2)) <= _radS) && (z >= minZ) && (z <= maxZ);
    }

    @Override
    public boolean intersectsRectangle(int ax1, int ax2, int ay1, int ay2) {
        // Circles point inside the rectangle?
        if ((centerX > ax1) && (centerX < ax2) && (centerY > ay1) && (centerY < ay2)) {
            return true;
        }

        // Any point of the rectangle intersecting the Circle?
        if ((Math.pow(ax1 - centerX, 2) + Math.pow(ay1 - centerY, 2)) < _radS) {
            return true;
        }
        if ((Math.pow(ax1 - centerX, 2) + Math.pow(ay2 - centerY, 2)) < _radS) {
            return true;
        }
        if ((Math.pow(ax2 - centerX, 2) + Math.pow(ay1 - centerY, 2)) < _radS) {
            return true;
        }
        if ((Math.pow(ax2 - centerX, 2) + Math.pow(ay2 - centerY, 2)) < _radS) {
            return true;
        }

        // Collision on any side of the rectangle?
        if ((centerX > ax1) && (centerX < ax2)) {
            if (Math.abs(centerY - ay2) < radius) {
                return true;
            }
            if (Math.abs(centerY - ay1) < radius) {
                return true;
            }
        }
        if ((centerY > ay1) && (centerY < ay2)) {
            if (Math.abs(centerX - ax2) < radius) {
                return true;
            }
            if (Math.abs(centerX - ax1) < radius) {
                return true;
            }
        }

        return false;
    }

    @Override
    public double getDistanceToZone(int x, int y) {
        return Math.hypot(centerX - x, centerY - y) - radius;
    }

    // getLowZ() / getHighZ() - These two functions were added to cope with the demand of the new fishing algorithms, wich are now able to correctly place the hook in the water, thanks to getHighZ(). getLowZ() was added, considering potential future modifications.
    @Override
    public int getLowZ() {
        return minZ;
    }

    @Override
    public int getHighZ() {
        return maxZ;
    }

    @Override
    public void visualizeZone(int z) {
        final int count = (int) ((2 * Math.PI * radius) / STEP);
        final double angle = (2 * Math.PI) / count;
        for (int i = 0; i < count; i++) {
            dropDebugItem(CommonItem.ADENA, 1, centerX + (int) (Math.cos(angle * i) * radius), centerY + (int) (Math.sin(angle * i) * radius), z);
        }
    }

    @Override
    public Location getRandomPoint() {
        final int q = (int) (Rnd.nextDouble() * 2 * Math.PI);
        final int r = (int) Math.sqrt(Rnd.nextDouble());
        final int x = (int) ((radius * r * Math.cos(q)) + centerX);
        final int y = (int) ((radius * r * Math.sin(q)) + centerY);

        return new Location(x, y, GeoEngine.getInstance().getHeight(x, y, minZ));
    }
}