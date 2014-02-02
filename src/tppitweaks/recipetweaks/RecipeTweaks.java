package tppitweaks.recipetweaks;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tppitweaks.config.ConfigurationHandler;
import tppitweaks.recipetweaks.modTweaks.AM2Tweaks;
import tppitweaks.recipetweaks.modTweaks.BigReactorsTweaks;
import tppitweaks.recipetweaks.modTweaks.DATweaks;
import tppitweaks.recipetweaks.modTweaks.DCTweaks;
import tppitweaks.recipetweaks.modTweaks.DCandIC2Tweaks;
import tppitweaks.recipetweaks.modTweaks.EnderStorageTweaks;
import tppitweaks.recipetweaks.modTweaks.ExUTweaks;
import tppitweaks.recipetweaks.modTweaks.GregtechTweaks;
import tppitweaks.recipetweaks.modTweaks.IC2Tweaks;
import tppitweaks.recipetweaks.modTweaks.MPSATweaks;
import tppitweaks.recipetweaks.modTweaks.MagicropsTweaks;
import tppitweaks.recipetweaks.modTweaks.MekanismTweaks;
import tppitweaks.recipetweaks.modTweaks.OpenBlocksTweaks;
import tppitweaks.recipetweaks.modTweaks.SFMTweaks;
import tppitweaks.recipetweaks.modTweaks.TweakerBase;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeTweaks
{

	public static boolean recipesInitialized;

	private static boolean okayToTweakIC2;
	private static boolean okayToTweakGT;
	private static boolean okayToTweakEnderStorage;
	private static boolean okayToTweakBigReactors;
	private static boolean okayToTweakDA;
	private static boolean okayToTweakSFM;
	private static boolean okayToTweakOpenBlocks;
	private static boolean okayToTweakAM2;
	private static boolean okayToTweakMagicalCrops;
	private static boolean okayToTweakDartCraft;
	private static boolean okayToTweakExU;
	private static boolean okayToTweakMPSA;
	private static boolean okayToTweakMekanism;

	public static void doPostInitRecipeTweaks()
	{

		recipesInitialized = false;

		checkWhatWeCanTweak();
		initRemovableRecipesMap();

		if (okayToTweakGT)
			GregtechTweaks.doStuff();

		if (okayToTweakExU)
			ExUTweaks.fixRecipes();

		doOreDictTweaks();

		if (okayToTweakIC2)
			IC2Tweaks.registerOres();

		if (okayToTweakMagicalCrops)
			MagicropsTweaks.registerOres();

		removeSomeRecipes();
		addRevisedRecipes();

	}

	public static void doPlayerJoinRecipeTweaks()
	{
		if (okayToTweakGT)
		{
			GregtechTweaks.addRecipes();
		}
		recipesInitialized = true;
	}

	@SuppressWarnings({ "unchecked" })
	private static void removeSomeRecipes()
	{
		ListIterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().listIterator();
		while (iterator.hasNext())
		{
			IRecipe r = iterator.next();
			if (canRemoveRecipe(r))
			{
				iterator.remove();
			}
		}
	}

	private static void checkWhatWeCanTweak()
	{
		okayToTweakIC2 = Loader.isModLoaded("IC2");
		okayToTweakGT = Loader.isModLoaded("gregtech_addon");
		okayToTweakEnderStorage = Loader.isModLoaded("EnderStorage") && Loader.isModLoaded("ThermalExpansion");
		okayToTweakBigReactors = Loader.isModLoaded("BigReactors") && !OreDictionary.getOres("ingotSteel").isEmpty()
				&& (ConfigurationHandler.steelReactorCasings || ConfigurationHandler.glassFuelRods);
		okayToTweakDA = Loader.isModLoaded("DimensionalAnchors") && ConfigurationHandler.tweakDA;
		okayToTweakSFM = Loader.isModLoaded("AppliedEnergistics") && Loader.isModLoaded("StevesFactoryManager") && ConfigurationHandler.tweakSFM;
		okayToTweakOpenBlocks = Loader.isModLoaded("OpenBlocks") && ConfigurationHandler.eloraamBreakersAndDeployers;
		okayToTweakAM2 = Loader.isModLoaded("arsmagica2") && ConfigurationHandler.tweakAM2;
		okayToTweakMagicalCrops = Loader.isModLoaded("magicalcrops") && ConfigurationHandler.registerMagicalCropsOre;
		okayToTweakDartCraft = Loader.isModLoaded("DartCraft") && ConfigurationHandler.removeStupidEnergyCrystalRecipe;
		okayToTweakExU = Loader.isModLoaded("ExtraUtilities") && ConfigurationHandler.fixExURecipes;
		okayToTweakMPSA = Loader.isModLoaded("powersuitaddons") && ConfigurationHandler.changeMPSARecipes;
		okayToTweakMekanism = Loader.isModLoaded("Mekanism") && ConfigurationHandler.harderDisassemblerRecipe;
	}

	private static void initRemovableRecipesMap()
	{
		/*
		 * Value -1 for key means no metadata sensitivity. Value = metadata for
		 * metadata sensitivity.
		 */

		if (okayToTweakEnderStorage)
		{
			EnderStorageTweaks.init();
		}
		if (okayToTweakBigReactors)
		{
			BigReactorsTweaks.init();
		}
		if (okayToTweakDA)
		{
			DATweaks.init();
		}
		if (okayToTweakSFM)
		{
			SFMTweaks.init();
		}
		if (okayToTweakOpenBlocks)
		{
			OpenBlocksTweaks.init();
		}
		if (okayToTweakDartCraft)
		{
			DCTweaks.init();
		}
		if (okayToTweakDartCraft && okayToTweakIC2)
		{
			DCandIC2Tweaks.init();
		}
		if (okayToTweakMPSA)
		{
			MPSATweaks.init();
		}
		if (okayToTweakMekanism)
		{
			MekanismTweaks.init();
		}
	}

	private static boolean canRemoveRecipe(IRecipe r)
	{
		try
		{
			ItemStack output = r.getRecipeOutput();
			HashSet<Integer> validMetas = TweakerBase.getDamageValuesToRemove(output.itemID);
			return validMetas.contains(-1) || validMetas.contains(output.getItemDamage());
		}
		catch (Throwable e)
		{
			return false;
		}
	}

	private static void addRevisedRecipes()
	{

		if (okayToTweakEnderStorage)
			EnderStorageTweaks.addRecipes();

		if (okayToTweakBigReactors)
			BigReactorsTweaks.addRecipes();

		if (okayToTweakDA)
			DATweaks.addRecipes();

		if (okayToTweakSFM)
			SFMTweaks.addRecipes();

		if (okayToTweakOpenBlocks)
			OpenBlocksTweaks.addRecipes();

		if (okayToTweakAM2)
			AM2Tweaks.addRecipes();

		if (okayToTweakMPSA)
			MPSATweaks.addRecipes();
		
		if (okayToTweakMekanism)
			MekanismTweaks.addRecipes();

	}

	/**
	 * Adds aluminIUM oreDict registries to aluminUM as well
	 */
	private static void doOreDictTweaks()
	{
		for (ItemStack s : OreDictionary.getOres("dustAluminium"))
		{
			OreDictionary.registerOre("dustAluminum", s);
		}
		
		List<ItemStack> stacks = OreDictionary.getOres("dustBrass");
		if (!stacks.isEmpty())
		{
			ItemStack stack = stacks.get(0);
			stack.stackSize++;
			GameRegistry.addRecipe(new ShapelessOreRecipe(stack, new Object[]{
					"dustCopper", "dustCopper", "dustCopper", "dustImpureZinc"
			}));			
		}
	}
}
