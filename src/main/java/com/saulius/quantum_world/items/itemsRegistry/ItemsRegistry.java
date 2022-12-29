package com.saulius.quantum_world.items.itemsRegistry;

import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.items.itemsGeneration.ItemPropertiesGeneration;
import com.saulius.quantum_world.items.itemsGeneration.ItemTierGeneration;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegistry {

    private static final float AXE_BASE_DAMAGE = 5.0F;
    private static final float AXE_BASE_ATTACK_SPEED = -3F;
    private static final float HOE_BASE_ATTACK_SPEED  = 0.0F;
    private static final int PICKAXE_BASE_DAMAGE = 1;
    private static final float PICKAXE_BASE_ATTACK_SPEED = -3F;
    private static final float SHOVEL_BASE_ATTACK_SPEED = -3.0F;
    private static final int SWORD_BASE_DAMAGE = 3;
    private static final float SWORD_BASE_ATTACK_SPEED = -2.4F;


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QuantumWorld.MODID);

    public static final RegistryObject<Item> COSMIC_INGOT = ITEMS.register("cosmic_ingot",
            () -> new Item(ItemPropertiesGeneration.itemProperties));
    public static final RegistryObject<Item> ENERGIUM_INGOT = ITEMS.register("energium_ingot",
            () -> new Item(ItemPropertiesGeneration.itemProperties));

    private static final RegistryObject<TieredItem> COSMIC_AXE = ITEMS.register("cosmic_axe",
            () -> new AxeItem(ItemTierGeneration.COSMIC, AXE_BASE_DAMAGE, AXE_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> COSMIC_HOE = ITEMS.register("cosmic_hoe",
            () -> new HoeItem(ItemTierGeneration.COSMIC, -3, HOE_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> COSMIC_PICKAXE = ITEMS.register("cosmic_pickaxe",
            () -> new PickaxeItem(ItemTierGeneration.COSMIC, PICKAXE_BASE_DAMAGE, PICKAXE_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> COSMIC_SHOVEL= ITEMS.register("cosmic_shovel",
            () -> new ShovelItem(ItemTierGeneration.COSMIC, 1, SHOVEL_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> COSMIC_SWORD = ITEMS.register("cosmic_sword",
            () -> new SwordItem(ItemTierGeneration.COSMIC, SWORD_BASE_DAMAGE, SWORD_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));

    private static final RegistryObject<TieredItem> ENERGIUM_AXE = ITEMS.register("energium_axe",
            () -> new AxeItem(ItemTierGeneration.ENERGIUM, AXE_BASE_DAMAGE, AXE_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> ENERGIUM_HOE = ITEMS.register("energium_hoe",
            () -> new HoeItem(ItemTierGeneration.ENERGIUM, -6, HOE_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> ENERGIUM_PICKAXE = ITEMS.register("energium_pickaxe",
            () -> new PickaxeItem(ItemTierGeneration.ENERGIUM, PICKAXE_BASE_DAMAGE, PICKAXE_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> ENERGIUM_SHOVEL= ITEMS.register("energium_shovel",
            () -> new ShovelItem(ItemTierGeneration.ENERGIUM, 2, SHOVEL_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));
    private static final RegistryObject<TieredItem> ENERGIUM_SWORD = ITEMS.register("energium_sword",
            () -> new SwordItem(ItemTierGeneration.ENERGIUM, SWORD_BASE_DAMAGE, SWORD_BASE_ATTACK_SPEED, ItemPropertiesGeneration.toolProperties));

    public static final RegistryObject<Item> IRON_WRENCH = ITEMS.register("iron_wrench", () -> new Item(ItemPropertiesGeneration.toolProperties));

    public static void register (IEventBus iEventBus) {
        ITEMS.register(iEventBus);
    }
}
