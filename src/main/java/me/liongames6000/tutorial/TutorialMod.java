package me.liongames6000.tutorial;

import me.liongames6000.tutorial.init.ModBlocks;
import me.liongames6000.tutorial.init.ModContainerTypes;
import me.liongames6000.tutorial.init.ModItems;
import me.liongames6000.tutorial.init.ModTileEntityTypes;
import me.liongames6000.tutorial.utils.ModResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutorialMod.MOD_ID)
public class TutorialMod {
    public static final String MOD_ID = "tutorial";
    public static final String MOD_NAME = "Tutorial Mod";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public TutorialMod() {
        MinecraftForge.EVENT_BUS.register(this);

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);


    }

    public static ModResourceLocation getId(String path) {
        if(path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ModResourceLocation(path);
    }
}
