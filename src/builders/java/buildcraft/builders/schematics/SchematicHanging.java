/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.builders.schematics;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicEntity;
import buildcraft.api.blueprints.Translation;

public class SchematicHanging extends SchematicEntity {

	private Item baseItem;

	public SchematicHanging (Item baseItem) {
		this.baseItem = baseItem;
	}

	@Override
	public void translateToBlueprint(Translation transform) {
		super.translateToBlueprint(transform);

		Vec3 pos = new Vec3 (entityNBT.getInteger("TileX"), entityNBT.getInteger("TileY"), entityNBT.getInteger("TileZ"));
		pos = transform.translate(pos);
		entityNBT.setInteger("TileX", (int) pos.x);
		entityNBT.setInteger("TileY", (int) pos.y);
		entityNBT.setInteger("TileZ", (int) pos.z);
	}

	@Override
	public void translateToWorld(Translation transform) {
		super.translateToWorld(transform);

		Vec3 pos = new Vec3 (entityNBT.getInteger("TileX"), entityNBT.getInteger("TileY"), entityNBT.getInteger("TileZ"));
		pos = transform.translate(pos);
		entityNBT.setInteger("TileX", (int) pos.x);
		entityNBT.setInteger("TileY", (int) pos.y);
		entityNBT.setInteger("TileZ", (int) pos.z);
	}

	@Override
	public void rotateLeft(IBuilderContext context) {
		super.rotateLeft(context);

		Vec3 pos = new Vec3 (entityNBT.getInteger("TileX"), entityNBT.getInteger("TileY"), entityNBT.getInteger("TileZ"));
		pos = context.rotatePositionLeft(pos);
		entityNBT.setInteger("TileX", (int) pos.x);
		entityNBT.setInteger("TileY", (int) pos.y);
		entityNBT.setInteger("TileZ", (int) pos.z);

		int direction = entityNBT.getByte("Direction");
		direction = direction < 3 ? direction + 1 : 0;
		entityNBT.setInteger("Direction", direction);
	}

	@Override
	public void readFromWorld(IBuilderContext context, Entity entity) {
		super.readFromWorld(context, entity);

		if (baseItem == Items.item_frame) {
			NBTTagCompound tag = entityNBT.getCompoundTag("Item");
			ItemStack stack = ItemStack.loadItemStackFromNBT(tag);

			if (stack != null) {
				storedRequirements = new ItemStack [2];
				storedRequirements [0] = new ItemStack(baseItem);
				storedRequirements [1] = stack;
			} else {
				storedRequirements = new ItemStack [1];
				storedRequirements [0] = new ItemStack(baseItem);
			}
		} else {
			storedRequirements = new ItemStack [1];
			storedRequirements [0] = new ItemStack(baseItem);
		}
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context) {
		Vec3 newPosition = new Vec3 (entityNBT.getInteger("TileX"), entityNBT.getInteger("TileY"), entityNBT.getInteger("TileZ"));

		int dir = entityNBT.getInteger("Direction");

		for (Object o : context.world().loadedEntityList) {
			Entity e = (Entity) o;

			if (e instanceof EntityHanging) {
				EntityHanging h = (EntityHanging) e;
				Vec3 existingPositon = new Vec3(h.chunkCoordX, h.chunkCoordY, h.chunkCoordZ);

				if (existingPositon.isClose(newPosition, 0.1F) && EnumFacing.getFront(dir) == ((EntityHanging) e).facingDirection) {
					return true;
				}
			}
		}

		return false;
	}
}
