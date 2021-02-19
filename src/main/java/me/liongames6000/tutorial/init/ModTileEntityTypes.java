package me.liongames6000.tutorial.init;

import me.liongames6000.tutorial.tileentity.ModFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTileEntityTypes {

    public static final RegistryObject<TileEntityType<ModFurnaceTileEntity>> MOD_FURNACE =
            Registration.TILE_ENTITY_TYPES.register("mod_furnace", () -> TileEntityType.Builder.create(
                    ModFurnaceTileEntity::new, ModBlocks.MOD_FURNACE.get()).build(null));

    static void register() {}
}
