/*
 ** 2013 July 28
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.dragon.server.entity.ai.air;

import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.BlockPos;

/**
 * Dragon AI for instant landing, if left unmounted in air.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityAILand extends EntityAIBase {
    
    private final EntityTameableDragon dragon;
    private BlockPos landingBlock;

    public EntityAILand(EntityTameableDragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public boolean shouldExecute() {
        if (!dragon.isFlying() || !dragon.isTamed() || dragon.getRidingPlayer() != null) {
            return false;
        }
        
        BlockPos pos = dragon.worldObj.getHeight(new BlockPos(RandomPositionGenerator.findRandomTarget(dragon, 16, 1)));

        if (!dragon.worldObj.getBlockState(pos.down()).getBlock().getMaterial().isSolid()) {
            return false;
        }
        
        landingBlock = pos;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !dragon.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        dragon.getNavigator().tryMoveToXYZ(landingBlock.getX(), landingBlock.getY(), landingBlock.getZ(), 1);
    }
}
