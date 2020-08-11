package net.runelite.client.plugins.inventoryindicators;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("inventoryindicators")
public interface IIConfig extends Config {

    @ConfigTitleSection(
            keyName = "firstTitle",
            name = "Inventory Indicators",
            description = "",
            position = 1
    )
    default Title clickLogTitle() { return new Title(); }

    @ConfigItem(
            keyName = "inventoryEnum",
            name = "",
            description = "",
            position = 2,
            titleSection = "firstTitle"
    )
    default InventoryEnum inventoryEnum() { return InventoryEnum.FULL; }

    @ConfigItem(
            keyName = "displayFull",
            name = "Enable indicator",
            description = "",
            position = 3,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default boolean displayFull() { return false; }

    @ConfigItem(
            keyName = "fullLocation",
            name = "Indicator location",
            description = "Format: X:Y:WIDTH:HEIGHT | \"10:10:20:20\"",
            position = 4,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default String fullLocation() { return "100:0:5:5"; }

    @ConfigItem(
            keyName = "fullColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 5,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default Color fullColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayContain",
            name = "Enable indicator",
            description = "",
            position = 6,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default boolean displayContain() { return false; }

    @ConfigItem(
            keyName = "containNames",
            name = "Items and amount",
            description = "Format: NAME:AMOUNT:HEXCOLOR | \"Coins:1000:00FF00, Ash,\"",
            position = 7,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default String containName() { return "Coins:1000, \nAsh,"; }

    @ConfigItem(
            keyName = "containLocation",
            name = "Indicator location",
            description = "Format: X:Y:WIDTH:HEIGHT | \"10:10:20:20\"",
            position = 8,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default String containLocation() { return "105:0:5:5"; }

    @ConfigItem(
            keyName = "containColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 9,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default Color containColor() { return Color.RED; }

    @ConfigItem(
            keyName = "highlightItems",
            name = "Highlight Items",
            description = "Highlight items based on a color, default: RED.",
            position = 10,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default boolean highlightItems() { return false; }

}