package net.novagalaxy.sufferance.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import java.util.stream.Collectors;
import java.util.Random;
import java.util.List;
import java.util.Comparator;

public class DusksEpitaphRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double particleRadius = 0;
		double particleAmount = 0;
		particleAmount = 250;
		particleRadius = 4;
		for (int index0 = 0; index0 < (int) (particleAmount); index0++) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if (entity instanceof LivingEntity _entity)
						_entity.hurt(new DamageSource("divinity").bypassArmor(), 15);
				}
			}
			world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, (x + 0 + Mth.nextDouble(new Random(), -1, 1) * particleRadius), (y + 0 + Mth.nextDouble(new Random(), -0.5, 0.5)), (z + 0 + Mth.nextDouble(new Random(), -1, 1) * particleRadius), 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", new TextComponent(""), _level.getServer(), null).withSuppressedOutput(),
						"playsound block.end_portal.spawn master @a ~ ~ ~ 1 0.5");
		}
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(itemstack.getItem(), 500);
	}
}
