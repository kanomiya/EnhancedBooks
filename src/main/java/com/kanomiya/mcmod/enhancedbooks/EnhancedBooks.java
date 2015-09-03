package com.kanomiya.mcmod.enhancedbooks;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.enhancedbooks.bookloader.BookInfo;
import com.kanomiya.mcmod.enhancedbooks.bookloader.BookLoader;
import com.kanomiya.mcmod.enhancedbooks.gui.GuiHandler;
import com.kanomiya.mcmod.enhancedbooks.proxy.CommonProxy;
import com.kanomiya.mcmod.enhancedbooks.proxy.PacketHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = EnhancedBooks.MODID, name = EnhancedBooks.MODID, version = EnhancedBooks.VERSION)
public class EnhancedBooks {
	public static final String MODID = "enhancedbooks";
	public static final String VERSION = "0.22d";

	public static final int GUIID_STORAGESHELF = 0;
	public static final int GUIID_EXPBOOK = 1;
	public static final int GUIID_HOLLOWBOOK = 2;
	public static final int GUIID_BOOKPRINTER = 3;


	@Mod.Instance("enhancedbooks")
	public static EnhancedBooks instance;

	@SidedProxy(clientSide="com.kanomiya.mcmod.enhancedbooks.proxy.ClientProxy",
				serverSide="com.kanomiya.mcmod.enhancedbooks.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static final EBCreativeTab tabEB = new EBCreativeTab();

	public static BookInfo[] loadedBooks;
	public static String[] titleList;

	public static Logger logger;

	private static File configFile;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		EBBlock.registerBlocks();

		if (event.getSide().isClient()) {
			File confDir = new File(event.getModConfigurationDirectory(), "enhancedbooks-books");
			if (! confDir.exists()) confDir.mkdirs();

			loadedBooks = BookLoader.getBooksInfo(BookLoader.readBooks(confDir));
			titleList = BookLoader.getTitles(loadedBooks);
		}

		EBItem.registerItems();

		configFile = event.getSuggestedConfigurationFile();

		GameRegistry.addRecipe(new ItemStack(EBBlock.storageshelf.getBlock()),
				"PPP",
				"HHH",
				"PPP",
				'P',Blocks.planks,
				'H',Blocks.wooden_slab
		);

		GameRegistry.addShapelessRecipe(new ItemStack(EBBlock.bookPrinter.getBlock()),
				new ItemStack(Blocks.crafting_table),
				new ItemStack(Items.glass_bottle)
		);



		GameRegistry.addShapelessRecipe(new ItemStack(EBItem.expBook.getItem(), 1, EBItem.expBook.getItem().getMaxDamage()),
				new ItemStack(Items.book), new ItemStack(Items.ender_eye), new ItemStack(Items.blaze_rod)
		);

		GameRegistry.addShapelessRecipe(new ItemStack(EBItem.hollowBook.getItem()),
				new ItemStack(Items.book), new ItemStack(Items.stick)
		);

		GameRegistry.addShapelessRecipe(new ItemStack(EBItem.cookieBook.getItem()),
				new ItemStack(Items.book),
				new ItemStack(Items.cookie), new ItemStack(Items.cookie), new ItemStack(Items.cookie),
				new ItemStack(Items.cookie), new ItemStack(Items.cookie), new ItemStack(Items.cookie),
				new ItemStack(Items.cookie), new ItemStack(Items.cookie)
		);

		GameRegistry.addRecipe(new ItemStack(EBItem.fatBook.getItem()),
				"PPL",
				"PPL",
				"PPL",
				'P',Items.paper,
				'L',Items.leather
		);



		PacketHandler.init();
		// MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		if (event.getSide().isClient()) {
			EBBlock.registerModels();
			EBItem.registerModels();
		}

		proxy.registerTileEntity();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		EBConfig.init(configFile);
	}

	// ForgeHooks
	// PlayerEvent
	// PlayerLoggedInEvent

	/*
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onEntityJoinWorld(EntityJoinWorldEvent event)  {
		PacketHandler.INSTANCE.sendToServer(new MessageBookTitleList.Client());

	}
	*/

}




