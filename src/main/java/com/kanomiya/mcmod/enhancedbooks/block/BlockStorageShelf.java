package com.kanomiya.mcmod.enhancedbooks.block;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityStorageShelf;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// BlockChest
public class BlockStorageShelf extends BlockContainer {
	/*
	public static final PropertyBool[] HAS_BOOK = new PropertyBool[] {
		PropertyBool.create("has_book_0"), PropertyBool.create("has_book_1"), PropertyBool.create("has_book_2"),
		PropertyBool.create("has_book_3"), PropertyBool.create("has_book_4"), PropertyBool.create("has_book_5"),
		PropertyBool.create("has_book_6"), PropertyBool.create("has_book_7"), PropertyBool.create("has_book_8"),
		PropertyBool.create("has_book_9"), PropertyBool.create("has_book_10"), PropertyBool.create("has_book_11"),
		PropertyBool.create("has_book_12"), PropertyBool.create("has_book_13"), PropertyBool.create("has_book_14"),
		PropertyBool.create("has_book_15"),

};
   */
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockStorageShelf() {
		super(Material.wood);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		/*
				.withProperty(HAS_BOOK[0], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[1], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[2], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[3], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[4], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[5], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[6], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[7], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[8], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[9], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[10], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[11], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[12], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[13], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[14], Boolean.valueOf(false))
				.withProperty(HAS_BOOK[15], Boolean.valueOf(false))
		);
		*/

		setCreativeTab(EnhancedBooks.tabEB);

		setHardness(1.5F);
		setStepSound(soundTypeWood);

	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) { return false; }

		player.openGui(EnhancedBooks.instance, EnhancedBooks.GUIID_STORAGESHELF, world, pos.getX(), pos.getY(), pos.getZ());

		return true;

	}



	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		onBlockAdded(world, pos, state);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) { return ; }

		TileEntityStorageShelf te = (TileEntityStorageShelf) world.getTileEntity(pos);
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
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}




	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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




	@Override public float getEnchantPowerBonus(World world, BlockPos pos) {
		TileEntityStorageShelf te = (TileEntityStorageShelf) world.getTileEntity(pos);
		if (te == null) { return 0; }

		return te.getEnchantPowerBonus();
	}


	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	}


	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FACING }); //, HAS_BOOK[0], HAS_BOOK[1], HAS_BOOK[2]});
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityStorageShelf();
	}

	@Override
	public boolean isOpaqueCube() { return true; }

	@Override
	public boolean isFullCube() { return false; }

	@Override
	public boolean isFullBlock() { return false; }

	@Override
	public boolean getUseNeighborBrightness() { return true; }


}
