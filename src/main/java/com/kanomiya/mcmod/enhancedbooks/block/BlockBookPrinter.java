package com.kanomiya.mcmod.enhancedbooks.block;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityBookPrinter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

// BlockChest
public class BlockBookPrinter extends BlockContainer {

	public BlockBookPrinter() {
        super(Material.wood);

		setCreativeTab(EnhancedBooks.tabEB);

		setHardness(1.5F);
		setStepSound(soundTypeWood);

	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(EnhancedBooks.instance, EnhancedBooks.GUIID_BOOKPRINTER, world, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}




	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		onBlockAdded(world, pos, state);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) { return ; }

		TileEntityBookPrinter te = (TileEntityBookPrinter) world.getTileEntity(pos);
		if (te == null) { return ; }

		if (world.isBlockPowered(pos)) {
			te.setPrevRSState(true);
		} else {
			te.setPrevRSState(false);
		}

	}



    @Override
	public int getRenderType()
    {
        return 3;
    }


	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}


	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBookPrinter();
	}


}
