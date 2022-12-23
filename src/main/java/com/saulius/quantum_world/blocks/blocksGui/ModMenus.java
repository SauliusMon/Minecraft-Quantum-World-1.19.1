package com.saulius.quantum_world.blocks.blocksGui;

import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.blocks.blocksGui.BasicElectricityGeneratorGUI.BasicElectricityGeneratorMenu;
import com.saulius.quantum_world.blocks.blocksGui.BasicElectricityHolderGUI.BasicElectricityHolderMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, QuantumWorld.MODID);

    public static final RegistryObject<MenuType<BasicElectricityHolderMenu>> BASIC_ELECTRICITY_HOLDER_MENU = registerMenuType(
            "basic_electricity_holder_menu", BasicElectricityHolderMenu::new ) ;

    public static final RegistryObject<MenuType<BasicElectricityGeneratorMenu>> BASIC_ELECTRICITY_GENERATOR_MENU = registerMenuType(
            "basic_electricity_generator_menu", BasicElectricityGeneratorMenu::new ) ;

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType (String name, IContainerFactory<T> fact) {
        return MENUS.register(name, () -> IForgeMenuType.create(fact));
    }

    public static void register(IEventBus iEventBus) {
        MENUS.register(iEventBus);
    }
}
