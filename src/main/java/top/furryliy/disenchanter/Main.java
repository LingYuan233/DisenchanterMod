package top.furryliy.disenchanter;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class Main implements ModInitializer {
	public static final Item DISENCHANTER_CORE = new Item(new Item.Settings().group(ItemGroup.MISC));

	public static final RecipeSerializer<DisenchanterRecipe> SERIALIZER_DE = RecipeSerializer.register("craft_disenchanter", new SpecialRecipeSerializer<>(DisenchanterRecipe::new));

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("disenchanter", "disenchanter_core"), DISENCHANTER_CORE);
	}
}
