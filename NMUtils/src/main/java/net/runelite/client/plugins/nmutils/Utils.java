/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package net.runelite.client.plugins.nmutils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.queries.DecorativeObjectQuery;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.GroundObjectQuery;
import net.runelite.api.queries.WallObjectQuery;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
		name = "NMUtils",
		type = PluginType.UTILITY,
		hidden = true
)
@Slf4j
@SuppressWarnings("unused")
@Singleton
public class Utils extends Plugin
{
	@Inject
	private Client client;

	@Override
	protected void startUp() { }

	@Override
	protected void shutDown() { }

	// ANSI Regular http://patorjk.com/software/taag/#p=display&v=1&f=ANSI%20Shadow&t=

	/*
	██╗███╗   ██╗██╗   ██╗███████╗███╗   ██╗████████╗ ██████╗ ██████╗ ██╗   ██╗
	██║████╗  ██║██║   ██║██╔════╝████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗╚██╗ ██╔╝
	██║██╔██╗ ██║██║   ██║█████╗  ██╔██╗ ██║   ██║   ██║   ██║██████╔╝ ╚████╔╝
	██║██║╚██╗██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║   ██║   ██║   ██║██╔══██╗  ╚██╔╝
	██║██║ ╚████║ ╚████╔╝ ███████╗██║ ╚████║   ██║   ╚██████╔╝██║  ██║   ██║
	╚═╝╚═╝  ╚═══╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝   ╚═╝ */


	ItemContainer inventory = null;

	public Item[] getInventoryItems()
	{
		inventory = client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null)
		{
			return null;
		}
		return inventory.getItems();
	}

	public boolean isInventoryFull()
	{
		int amount = 0;
		for (Item item : getInventoryItems())
		{
			if (item == null || item.getId() == -1)
			{
				return false;
			}
			amount++;
		}
		return amount == 28;
	}

	public boolean doesInventoryContain(String itemName)
	{
		for (Item item : getInventoryItems())
		{
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			ItemDefinition itemDefinition = client.getItemDefinition(item.getId());
			if (itemDefinition.getName().toLowerCase().equalsIgnoreCase(itemName))
			{
				return true;
			}
		}
		return false;
	}

	public boolean doesInventoryContain(int itemId)
	{
		for (Item item : getInventoryItems())
		{
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			if (item.getId() == itemId)
			{
				return true;
			}
		}
		return false;
	}

	public String[] getInventoryItemActions(String itemName)
	{
		for (Item item : getInventoryItems())
		{
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			ItemDefinition itemDefinition = client.getItemDefinition(item.getId());
			if (itemDefinition.getName().toLowerCase().equalsIgnoreCase(itemName))
			{
				return client.getItemDefinition(item.getId()).getInventoryActions();
			}
		}
		return null;
	}

	public String[] getInventoryItemActions(int itemId)
	{
		for (Item item : getInventoryItems())
		{
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			if (item.getId() == itemId)
			{
				return client.getItemDefinition(item.getId()).getInventoryActions();
			}
		}
		return null;
	}

	public List<Integer> getInventorySlotsThatContain(String itemName)
	{
		int i = 0;
		List<Integer> slots = new ArrayList<>();
		for (Item item : getInventoryItems())
		{
			i++;
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			ItemDefinition itemDefinition = client.getItemDefinition(item.getId());
			if (itemDefinition.getName().toLowerCase().equalsIgnoreCase(itemName))
			{
				slots.add(i);
			}
		}
		return slots;
	}

	public List<Integer> getInventorySlotsThatContain(int itemId)
	{
		int i = 0;
		List<Integer> slots = new ArrayList<>();
		for (Item item : getInventoryItems())
		{
			i++;
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			if (item.getId() == itemId)
			{
				slots.add(i);
			}
		}
		return slots;
	}











	public Item[] getBankItems()
	{
		ItemContainer bank = client.getItemContainer(InventoryID.BANK);
		if (bank == null)
		{
			return null;
		}
		return bank.getItems();
	}

	public int[] getIndicatorLocation(String string)
	{
		int[] location = {0,0,5,5};
		if (string.isEmpty())
		{
			return location;
		}
		String[] parts = removeWhiteSpaces(string).split(":");
		for (int i = 0; i < 4; i++)
		{
			String part = removeCharactersFromString(parts[i]);
			if (part.isEmpty())
			{
				break;
			}
			location[i] = Integer.parseInt(part);
		}
		return location;
	}

	public String removeCharactersFromString(String string)
	{
		return string.toLowerCase().replaceAll("\\D", "");
	}

	public String removeNumbersFromString(String string)
	{
		return string.toLowerCase().replaceAll("[0-9]", "");
	}

	public String removeWhiteSpaces(String string)
	{
		return string.toLowerCase().replaceAll("\\s+", "");
	}

	public int getRandomNumber(int min, int max)
	{
		return (int) ((Math.random() * ((max - min) + 1)) + min);
	}

	public void log(String string)
	{
		System.out.println(string);
	}

	@Nullable
	public GameObject findNearestGameObject(int... ids)
	{
		return new GameObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	@Nullable
	public WallObject findNearestWallObject(int... ids)
	{
		return new WallObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	@Nullable
	public DecorativeObject findNearestDecorObject(int... ids)
	{
		return new DecorativeObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	@Nullable
	public GroundObject findNearestGroundObject(int... ids)
	{
		return new GroundObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	public List<GameObject> getGameObjects(int... ids)
	{
		return new GameObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	public List<WallObject> getWallObjects(int... ids)
	{
		return new WallObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	public List<DecorativeObject> getDecorObjects(int... ids)
	{
		return new DecorativeObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	public List<GroundObject> getGroundObjects(int... ids)
	{
		return new GroundObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	@Nullable
	public TileObject findNearestObject(int... ids)
	{
		GameObject gameObject = findNearestGameObject(ids);

		if (gameObject != null)
		{
			return gameObject;
		}

		WallObject wallObject = findNearestWallObject(ids);

		if (wallObject != null)
		{
			return wallObject;
		}
		DecorativeObject decorativeObject = findNearestDecorObject(ids);

		if (decorativeObject != null)
		{
			return decorativeObject;
		}

		return findNearestGroundObject(ids);
	}

	public boolean isPlayerWithinArea(Player player, int x1, int y2, int x2, int y1, int z)
	{
		WorldPoint playerLocation = player.getWorldLocation();
		return playerLocation.getX() >= x1
				&& playerLocation.getX() <= x2
				&& playerLocation.getY() >= y1
				&& playerLocation.getY() <= y2
				&& playerLocation.getPlane() == z;
	}

	public boolean isPlayerLocation(int playerLocation, boolean greaterThan, int i)
	{
		if (greaterThan)
		{
			return playerLocation >= i;
		}
		else
		{
			return playerLocation <= i;
		}
	}

	public boolean doesTileObjectExistAtLocation(TileObject t, WorldPoint c)
	{
		if (t == null)
		{
			return false;
		}
		WorldPoint tWP = t.getWorldLocation();
		if (tWP == null || c == null)
		{
			return false;
		}
		return tWP.equals(c);
	}

	public boolean isGameObjectWithinArea(GameObject gameObject, int x1, int y2, int x2, int y1, int z)
	{
		if (gameObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = gameObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isGroundObjectWithinArea(GroundObject groundObject, int x1, int y2, int x2, int y1, int z)
	{
		if (groundObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = groundObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isWallObjectWithinArea(WallObject wallObject, int x1, int y2, int x2, int y1, int z)
	{
		if (wallObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = wallObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isDecorativeObjectWithinArea(DecorativeObject decorativeObject, int x1, int y2, int x2, int y1, int z)
	{
		if (decorativeObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = decorativeObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isTileObjectWithinArea(TileObject tileObject, int x1, int y2, int x2, int y1, int z)
	{
		if (tileObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = tileObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public void renderCentrePoint(Graphics2D graphics, Rectangle bounds, Color color, int boxSize)
	{
		if (bounds == null)
		{
			return;
		}
		int x = (int) bounds.getCenterX() - boxSize / 2;
		int y = (int) bounds.getCenterY() - boxSize / 2;
		graphics.setColor(color);
		graphics.fillRect(x, y, boxSize, boxSize);
	}

	public void renderTileCentrePoint(Graphics2D graphics, WorldPoint worldPoint, Color color, int boxSize)
	{
		LocalPoint lp = LocalPoint.fromWorld(client, worldPoint);
		if (lp == null)
		{
			return;
		}
		Polygon polygon = Perspective.getCanvasTilePoly(client, lp);
		if (polygon == null)
		{
			return;
		}
		Rectangle bounds = polygon.getBounds();
		if (bounds == null)
		{
			return;
		}
		renderCentrePoint(graphics, bounds, color, boxSize);
	}


}
