package me.liongames6000.tutorial.init;

import me.liongames6000.tutorial.TutorialMod;
import me.liongames6000.tutorial.tileentity.ModFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.TILE_ENTITIES, TutorialMod.MOD_ID);

    public static final RegistryObject<TileEntityType<ModFurnaceTileEntity>> MOD_FURNACE =
            TILE_ENTITY_TYPES.register("mod_furnace", () -> TileEntityType.Builder.create(
                    ModFurnaceTileEntity::new, ModBlocks.MOD_FURNACE.get()).build(null));
}
