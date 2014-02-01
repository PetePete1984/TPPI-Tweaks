package tppitweaks.lib;

import java.io.File;

public class Reference
{
	public static final String CHANNEL = "tppitweaks";
	public static final String TAB_NAME = "tabTPPI";
	public static String thaumcraftFilename = "Thaumcraft4.0.5b.zip";
	public static String TTFilename = "ThaumicTinkerer 2.1-67.jar";
	public static String KAMIFilename = "ThaumicTinkererKAMI_j6.jar";
	
	public static final String DEFAULT_THAUMCRAFT_FILENAME = "Thaumcraft4.0.5b.zip";
	public static final String DEFAULT_TT_FILENAME = "ThaumicTinkerer 2.1-67.jar";
	public static final String DEFAULT_KAMI_FILENAME = "ThaumicTinkererKAMI_j6.jar";
	
	public static File modsFolder;
	
	public static final String DEPENDENCIES = 
			"before:ThaumicTinkerer;"
			+ "after:Thaumcraft;"
			+ "after:TwilightForest;"
			+ "after:AppliedEnergistics;"
			+ "after:StevesFactoryManager;"
			+ "after:DimensionalAnchors;"
			+ "after:DartCraft;"
			+ "after:arsmagica2;"
			+ "after:ThermalExpansion;"
			+ "after:magicalcrops;"
			+ "after:gregtech_addon;"
			+ "after:ExtraUtilities;"
			+ "after:EnderStorage;"
			+ "after:BigReactors;"
			+ "after:BuildCraft|Core;"
			+ "after:powersuitaddons;"
			+ "after:powersuits";
	
	
	public static final String PACK_VERSION = "0.2.0";
	
}
