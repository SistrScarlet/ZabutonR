package net.sistr.zabutonr.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sistr.zabutonr.setup.Registration;

import java.util.List;

public class ZabutonEntity extends Entity {

    public ZabutonEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public ZabutonEntity(World world) {
        super(Registration.WHITE_ZABUTON_ENTITY.get(), world);
    }

    public ZabutonEntity(World world, double x, double y, double z) {
        this(world);
        this.refreshPositionAndAngles(x, y, z, 0, 0);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public boolean collidesWith(Entity other) {
        return BoatEntity.canCollide(this, other);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public double getMountedHeightOffset() {
        return -0.1;
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isRemoved()) {
            this.tickMovement();
        }
    }

    public void tickMovement() {
        if (!this.canMoveVoluntarily()) {
            double drag = 0.98;
            if (this.isSubmergedInWater()) {
                drag = 0.6;
            }
            this.setVelocity(this.getVelocity().multiply(drag));
        }
        if (!this.hasNoGravity() && !noClip) {
            Vec3d vec3d4 = this.getVelocity();
            this.setVelocity(vec3d4.x, vec3d4.y - 0.05, vec3d4.z);
        }
        var velocity = this.getVelocity();
        double vx = velocity.x;
        double vy = velocity.y;
        double vz = velocity.z;
        if (Math.abs(velocity.x) < 0.003) {
            vx = 0.0;
        }
        if (Math.abs(velocity.y) < 0.003) {
            vy = 0.0;
        }
        if (Math.abs(velocity.z) < 0.003) {
            vz = 0.0;
        }
        this.setVelocity(vx, vy, vz);
        this.move(MovementType.SELF, this.getVelocity());
        if (this.getWorld().getOtherEntities(this, getBoundingBox())
                .stream().anyMatch(e -> e instanceof ZabutonEntity)) {
            this.refreshPositionAndAngles(getX(), getY() + getHeight(), getZ(), getYaw(), getPitch());
        }

        List<Entity> entityList = this.getWorld().getOtherEntities(
                this,
                this.getBoundingBox().expand(0.1f, 0.1f, 0.1f),
                EntityPredicates.canBePushedBy(this));
        if (!entityList.isEmpty()) {
            boolean serverAndNonPlayerControl = !this.getWorld().isClient
                    && !(this.getControllingPassenger() instanceof PlayerEntity);
            for (Entity entity : entityList) {
                if (entity.hasPassenger(this)) continue;
                if (serverAndNonPlayerControl
                        && this.getPassengerList().isEmpty()
                        && !entity.hasVehicle()
                        && entity instanceof LivingEntity
                        && !(entity instanceof PlayerEntity)) {
                    entity.startRiding(this);
                    continue;
                }
                this.pushAwayFrom(entity);
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.getWorld().isClient || this.isRemoved()) {
            return true;
        }

        this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
        boolean isCreative = source.getAttacker() instanceof PlayerEntity
                && ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode;
        if (!isCreative && this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.dropItems();
        }
        this.discard();
        return true;
    }

    public void dropItems() {
        this.dropItem(this.asItem());
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        }
        if (!this.getWorld().isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        }
        return ActionResult.SUCCESS;
    }

    public Item asItem() {
        return Registration.WHITE_ZABUTON_ITEM.get();
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(this.asItem());
    }
}
