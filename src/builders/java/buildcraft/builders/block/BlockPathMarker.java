/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL. Please check the contents
 * of the license located in http://www.mod-buildcraft.com/MMPL-1.0.txt */
package buildcraft.builders;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import buildcraft.builders.tile.TilePathMarker;

public class BlockPathMarker extends BlockMarker {

    private IIcon activeMarker;

    public BlockPathMarker() {}

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TilePathMarker();
    }

    @Override
    public IIcon getIconAbsolute(IBlockAccess iblockaccess, int x, int y, int z, int side, int metadata) {
        TilePathMarker marker = (TilePathMarker) iblockaccess.getTileEntity(x, y, z);

        if (side == 1 || (marker != null && marker.tryingToConnect)) {
            return activeMarker;
        } else {
            return super.getIconAbsolute(side, metadata);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        super.registerBlockIcons(par1IconRegister);
        activeMarker = par1IconRegister.registerIcon("buildcraftbuilders:pathMarkerBlock/active");
    }
}
