package me.liongames6000.tutorial.init;

import me.liongames6000.tutorial.container.ModFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
/**
 * Holds a list of all our {@link ContainerType}s.
 * Suppliers that create ContainerTypes are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the ContainerType Registry Event is fired by Forge and it is time for the mod to
 * register its ContainerTypes, our ContainerTypes are created and registered by the DeferredRegister.
 * The ContainerType Registry Event will always be called after the Block and Item registries are filled.
 * Note: This supports registry overrides.
 */
public class ModContainerTypes {

    public static final RegistryObject<ContainerType<ModFurnaceContainer>> MOD_FURNACE = Registration.CONTAINER_TYPES.register(
            "mod_furnace", () -> IForgeContainerType.create(ModFurnaceContainer::new));

    static void register() {}
}
