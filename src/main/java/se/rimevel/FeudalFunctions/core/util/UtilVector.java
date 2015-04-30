package se.rimevel.FeudalFunctions.core.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class UtilVector
{
	public static MovingObjectPosition clip(World world, Vec3 vec_one, Vec3 vec_two)
    {
        return rayTraceBlocks(world, vec_one, vec_two, false, false);
    }
	
	public static MovingObjectPosition clip(World world, Vec3 vec_one, Vec3 vec_two, boolean par3)
    {
        return rayTraceBlocks(world, vec_one, vec_two, par3, false);
    }
	
	public static MovingObjectPosition rayTraceBlocks(World world, Vec3 vec_one, Vec3 vec_two, boolean par3, boolean par4)
    {
        if (!Double.isNaN(vec_one.xCoord) && !Double.isNaN(vec_one.yCoord) && !Double.isNaN(vec_one.zCoord))
        {
            if (!Double.isNaN(vec_two.xCoord) && !Double.isNaN(vec_two.yCoord) && !Double.isNaN(vec_two.zCoord))
            {
                int x2 = MathHelper.floor_double(vec_two.xCoord);
                int y2 = MathHelper.floor_double(vec_two.yCoord);
                int z2 = MathHelper.floor_double(vec_two.zCoord);
                int x = MathHelper.floor_double(vec_one.xCoord);
                int y = MathHelper.floor_double(vec_one.yCoord);
                int z = MathHelper.floor_double(vec_one.zCoord);
                int meta = world.getBlockMetadata(x, y, z);
                Block block = world.getBlock(x, y, z);

                if (block != null && (!par4 || block == null || block.getCollisionBoundingBoxFromPool(world, x, y, z) != null) && block != null && block != Blocks.air && block.canCollideCheck(meta, par3))
                {
                    MovingObjectPosition movingobjectposition = block.collisionRayTrace(world, x, y, z, vec_one, vec_two);

                    if (movingobjectposition != null)
                    {
                        return movingobjectposition;
                    }
                }

                int i = 200;

                while (i-- >= 0)
                {
                    if (Double.isNaN(vec_one.xCoord) || Double.isNaN(vec_one.yCoord) || Double.isNaN(vec_one.zCoord))
                    {
                        return null;
                    }

                    if (x == x2 && y == y2 && z == z2)
                    {
                        return null;
                    }

                    boolean flag2 = true;
                    boolean flag3 = true;
                    boolean flag4 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if (x2 > x)
                    {
                        d0 = (double)x + 1.0D;
                    }
                    else if (x2 < x)
                    {
                        d0 = (double)x + 0.0D;
                    }
                    else
                    {
                        flag2 = false;
                    }

                    if (y2 > y)
                    {
                        d1 = (double)y + 1.0D;
                    }
                    else if (y2 < y)
                    {
                        d1 = (double)y + 0.0D;
                    }
                    else
                    {
                        flag3 = false;
                    }

                    if (z2 > z)
                    {
                        d2 = (double)z + 1.0D;
                    }
                    else if (z2 < z)
                    {
                        d2 = (double)z + 0.0D;
                    }
                    else
                    {
                        flag4 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = vec_two.xCoord - vec_one.xCoord;
                    double d7 = vec_two.yCoord - vec_one.yCoord;
                    double d8 = vec_two.zCoord - vec_one.zCoord;

                    if (flag2)
                    {
                        d3 = (d0 - vec_one.xCoord) / d6;
                    }

                    if (flag3)
                    {
                        d4 = (d1 - vec_one.yCoord) / d7;
                    }

                    if (flag4)
                    {
                        d5 = (d2 - vec_one.zCoord) / d8;
                    }

                    boolean flag5 = false;
                    byte b0;

                    if (d3 < d4 && d3 < d5)
                    {
                        if (i > x)
                        {
                            b0 = 4;
                        }
                        else
                        {
                            b0 = 5;
                        }

                        vec_one.xCoord = d0;
                        vec_one.yCoord += d7 * d3;
                        vec_one.zCoord += d8 * d3;
                    }
                    else if (d4 < d5)
                    {
                        if (y2 > y)
                        {
                            b0 = 0;
                        }
                        else
                        {
                            b0 = 1;
                        }

                        vec_one.xCoord += d6 * d4;
                        vec_one.yCoord = d1;
                        vec_one.zCoord += d8 * d4;
                    }
                    else
                    {
                        if (z2 > z)
                        {
                            b0 = 2;
                        }
                        else
                        {
                            b0 = 3;
                        }

                        vec_one.xCoord += d6 * d5;
                        vec_one.yCoord += d7 * d5;
                        vec_one.zCoord = d2;
                    }

                    Vec3 vec32 = Vec3.createVectorHelper(vec_one.xCoord, vec_one.yCoord, vec_one.zCoord);
                    x = (int)(vec32.xCoord = (double)MathHelper.floor_double(vec_one.xCoord));

                    if (b0 == 5)
                    {
                        --x;
                        ++vec32.xCoord;
                    }

                    y = (int)(vec32.yCoord = (double)MathHelper.floor_double(vec_one.yCoord));

                    if (b0 == 1)
                    {
                        --y;
                        ++vec32.yCoord;
                    }

                    z = (int)(vec32.zCoord = (double)MathHelper.floor_double(vec_one.zCoord));

                    if (b0 == 3)
                    {
                        --z;
                        ++vec32.zCoord;
                    }

                    int meta2 = world.getBlockMetadata(x, y, z);
                    Block block2 = world.getBlock(x, y, z);

                    if ((!par4 || block2 == null || block2.getCollisionBoundingBoxFromPool(world, x, y, z) != null) && block2 != Blocks.air && block2.canCollideCheck(meta2, par3))
                    {
                        MovingObjectPosition movingobjectposition1 = block2.collisionRayTrace(world, x, y, z, vec_one, vec_two);

                        if (movingobjectposition1 != null)
                        {
                            return movingobjectposition1;
                        }
                    }
                }

                return null;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
}
