package me.pineacle.luckofthesea.guis.menus.augments;

import lombok.Getter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.api.augments.Augment;
import me.pineacle.luckofthesea.guis.BaseButton;
import me.pineacle.luckofthesea.utils.ItemBuilder;
import me.pineacle.luckofthesea.utils.StringUtils;

public class AugmentButton extends BaseButton {

    @Getter private final Augment augment;

    public AugmentButton(final LuckOfTheSeaPlugin plugin, final Augment augment) {
        super(plugin, new ItemBuilder(augment.getType()).build());
        this.augment = augment;
        setDisplayName(StringUtils.format(augment.getDisplayName()));
        setLore(StringUtils.color(augment.getDescription()));

    }

}
