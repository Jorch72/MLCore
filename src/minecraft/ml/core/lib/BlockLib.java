package ml.core.lib;

import ml.core.Vector3;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.ForgeDirection;

public class BlockLib {

	public static int[] atCCW = new int[]{4,5,4,5,3,2, 6};
	public static int[] atCW = new int[]{5,4,5,4,2,3, 6};
	
	/**
	 * Gets the y-axis rotation for a {@link ForgeDirection}
	 * Useful for {@link TileEntitySpecialRenderer}s when your block has a rotation
	 * 
	 * @param fd Input {@link ForgeDirection}
	 * @return Angle in degrees for the {@link ForgeDirection}
	 */
	public static float getRotationFromDirection(ForgeDirection fd){
		switch (fd){
		case NORTH:
			return 0F;
		case SOUTH:
			return 180F;
		case WEST:
			return 90F;
		case EAST:
			return -90F;
		}
		return 0F;
	}
	
	/**
	 * Gets the ForgeDirection that is counter-clockwise from the input
	 */
	public static ForgeDirection getCClockwise(ForgeDirection fd){
		return ForgeDirection.getOrientation(atCCW[fd.ordinal()]);
	}
	
	/**
	 * Gets the ForgeDirection that is clockwise from the input
	 */
	public static ForgeDirection getClockwise(ForgeDirection fd){
		return ForgeDirection.getOrientation(atCW[fd.ordinal()]);
	}
	
	/**
	 * Gets the {@link ForgeDirection} that is closest to facing the player.
	 * Assumes all {@link ForgeDirection} (except UNKNOWN) are accepted.
	 * 
	 * @param placer The entity placing the block
	 * @param x The x-coord of the block being placed
	 * @param y The y-coord of the block being placed
	 * @param z	The z-coord of the block being placed
	 * @return The nearest allowed {@link ForgeDirection}
	 */
	public static ForgeDirection getPlacedForgeDir(Entity placer, int x, int y, int z){
		return getPlacedForgeDir(placer, x, y, z, ForgeDirection.VALID_DIRECTIONS);
	}
	
	/**
	 * Gets the {@link ForgeDirection} that is closest to facing the player.
	 * May be a bit overkill... but eh.
	 * 
	 * @param placer The entity placing the block
	 * @param x The x-coord of the block being placed
	 * @param y The y-coord of the block being placed
	 * @param z	The z-coord of the block being placed
	 * @param allowedDirs Array of {@link ForgeDirection} that are acceptable as returns
	 * @return The nearest allowed {@link ForgeDirection}
	 */
	public static ForgeDirection getPlacedForgeDir(Entity placer, int x, int y, int z, ForgeDirection[] allowedDirs){
		
		Vector3 look = (new Vector3(placer.posX, placer.posY, placer.posZ).minus(new Vector3((float)x+0.5F, (float)y+0.5F, (float)z+0.5F))).normalize(); 
		
		ForgeDirection cfd = ForgeDirection.UNKNOWN;
		double loang = Math.PI;
		for (ForgeDirection fd : allowedDirs){
			double secDot = Math.acos(look.dotProd(new Vector3(fd.offsetX, fd.offsetY, fd.offsetZ)));
			if (secDot<loang){
				loang = secDot;
				cfd = fd;
			}
		}
		
		return cfd;
	}
}