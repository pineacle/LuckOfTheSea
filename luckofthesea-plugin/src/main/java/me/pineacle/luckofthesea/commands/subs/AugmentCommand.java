package me.pineacle.luckofthesea.commands.subs;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.commands.SubCommand;
import me.pineacle.luckofthesea.guis.menus.augments.AugmentMenu;
import me.pineacle.luckofthesea.guis.menus.codex.FDMainMenu;
import me.pineacle.luckofthesea.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AugmentCommand extends SubCommand {


    private final LuckOfTheSeaPlugin plugin;

    public AugmentCommand(LuckOfTheSeaPlugin plugin) {
        super("augments");
        this.plugin = plugin;
        setPermission(Permissions.AUGMENT);
    }

    @Override
    public boolean getConsoleCompatible() {
        return true;
    }

    @Override
    public String getPossibleArguments() {
        return "[add | remove]";
    }

    @Override
    public int getMinimumArguments() {
        return 0;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) throws CommandException {

        // Console
        if(!(sender instanceof Player player)) {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.RED + "You must be in-game to list augments");
            }
        } else {
            plugin.getMenuListener().addGui(player,
                    new AugmentMenu(plugin,plugin.getMenuConfig().get("guis.augments_list.title"),
                            plugin.getMenuConfig().getMenus().getInt("guis.augments_list.rows")),
                    true).open(player);
        }
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("Shows all available fish to catch.");
    }

}
