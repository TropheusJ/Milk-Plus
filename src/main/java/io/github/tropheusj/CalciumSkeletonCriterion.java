package io.github.tropheusj;

import com.google.gson.JsonObject;
import io.github.tropheusj.CalciumSkeletonCriterion.Conditions;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate.Extended;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CalciumSkeletonCriterion extends AbstractCriterion<Conditions> {
	public static final Identifier ID = MilkPlus.id("calcium_skeleton");

	@Override
	protected Conditions conditionsFromJson(JsonObject obj, Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
		return new Conditions();
	}

	@Override
	public Identifier getId() {
		return ID;
	}

	public void trigger(ServerPlayerEntity player) {
		super.trigger(player, c -> true);
	}

	public static class Conditions extends AbstractCriterionConditions {
		public Conditions() {
			super(ID, Extended.EMPTY);
		}
	}
}
