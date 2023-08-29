package com.vicious.mixinexample;

import com.vicious.mixinexample.util.GameResourceHelper;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

public class MixinCore implements IFMLLoadingPlugin {
    private static final Logger logger = LogManager.getLogger();

    public MixinCore(){
        setup();
    }

    public void setup(){
        try {
            try {
                Class.forName("org.spongepowered.asm.launch.MixinBootstrap");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("No Mixin Bootstrap. Install MixinBootstrap: https://www.curseforge.com/minecraft/mc-mods/mixinbootstrap");
            }
            logger.info("Initializing Mixin Injector.");
            ensureMixinInitialized();
            attemptLoadMixin("sponge");
            ensureMixinInitialized();
            logger.info("Made Mixin modifications to game code.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ensureMixinInitialized(){
        try {
            MixinBootstrap.init();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean alwaysLoadMixin(String mixinjson){
        logger.info("Loading " +  mixinjson);
        Mixins.addConfiguration(mixinjson);
    }

    public static boolean attemptLoadMixin(String modid) {
        if(GameResourceHelper.load(modid)){
            logger.info("Loading " + modid + " mixins.");
            Mixins.addConfiguration("mixins/modmixins." + modid + ".json");
            return true;
        }
        return false;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
