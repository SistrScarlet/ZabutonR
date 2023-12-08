package net.sistr.zabutonr.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sistr.zabutonr.entity.ZabutonEntity;

import java.util.List;
import java.util.function.Predicate;

public class ZabutonItem extends Item {
    private static final Predicate<Entity> RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::canHit);

    public ZabutonItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult hitResult = BoatItem.raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (((HitResult) hitResult).getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        Vec3d vec3d = user.getRotationVec(1.0f);
        double d = 5.0;
        List<Entity> list = world.getOtherEntities(user, user.getBoundingBox()
                .stretch(vec3d.multiply(d))
                .expand(1.0), RIDERS);
        if (!list.isEmpty()) {
            Vec3d vec3d2 = user.getEyePos();
            for (Entity entity : list) {
                Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                if (!box.contains(vec3d2)) continue;
                return TypedActionResult.pass(itemStack);
            }
        }
        if (((HitResult) hitResult).getType() == HitResult.Type.BLOCK) {
            var zabuton = this.createEntity(world, hitResult);
            float deg = 360 / 16f;
            float yaw = Math.round(user.getYaw() / deg) * deg;
            zabuton.setYaw(yaw);
            if (!world.isSpaceEmpty(zabuton, zabuton.getBoundingBox())) {
                return TypedActionResult.fail(itemStack);
            }
            if (!world.isClient) {
                world.spawnEntity(zabuton);
                world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }

    private ZabutonEntity createEntity(World world, HitResult hitResult) {
        var pos = hitResult.getPos();
        double x = Math.round((float) pos.x * 8) / 8.0;
        double z = Math.round((float) pos.z * 8) / 8.0;
        return new ZabutonEntity(world, x, pos.y + 0.2, z);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient()
                && user.shouldCancelInteraction()
                && entity.hasVehicle()
                && entity.getVehicle() instanceof ZabutonEntity zabuton) {
            entity.dismountVehicle();
            entity.refreshPositionAndAngles(
                    zabuton.getX(),
                    zabuton.getY() + zabuton.getHeight(),
                    zabuton.getZ(),
                    0, 0);

            boolean isCreative = user.getAbilities().creativeMode;
            if (!isCreative && user.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                zabuton.dropItems();
            }
            zabuton.discard();
        }

        return super.useOnEntity(stack, user, entity, hand);
    }
}
