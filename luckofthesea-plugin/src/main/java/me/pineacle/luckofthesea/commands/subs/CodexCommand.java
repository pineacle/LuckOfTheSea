package me.pineacle.luckofthesea.commands.subs;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.commands.SubCommand;
import me.pineacle.luckofthesea.guis.menus.codex.FDMainMenu;
import me.pineacle.luckofthesea.utils.Permissions;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CodexCommand extends SubCommand {

    private final LuckOfTheSeaPlugin plugin;

    public CodexCommand(LuckOfTheSeaPlugin plugin) {
        super("codex");
        this.plugin = plugin;
        setPermission(Permissions.CODEX);
    }

    @Override
    public boolean getConsoleCompatible() {
        return false;
    }

    @Override
    public String getPossibleArguments() {
        return "";
    }

    @Override
    public int getMinimumArguments() {
        return 0;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) throws CommandException {
        Player player = (Player) sender;
        plugin.getMenuListener().addGui(player,
                        new FDMainMenu(plugin, plugin.getMenuConfig().get("guis.codex_main_menu.title"),
                                plugin.getMenuConfig().getMenus().getInt("guis.codex_main_menu.rows")),
                        false).open(player);
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("Shows all available fish to catch.");
    }

}
