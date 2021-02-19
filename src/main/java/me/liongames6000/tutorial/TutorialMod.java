package me.liongames6000.tutorial;

import me.liongames6000.tutorial.init.Registration;
import me.liongames6000.tutorial.utils.ModResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutorialMod.MOD_ID)
public class TutorialMod {
    public static final String MOD_ID = "tutorial";
    public static final String MOD_NAME = "Tutorial Mod";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public TutorialMod() {
        Registration.register();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ModResourceLocation getId(String path) {
        if(path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ModResourceLocation(path);
    }
}
