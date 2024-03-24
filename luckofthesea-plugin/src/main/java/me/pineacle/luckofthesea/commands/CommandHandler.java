package me.pineacle.luckofthesea.commands;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.commands.subs.AugmentCommand;
import me.pineacle.luckofthesea.commands.subs.CodexCommand;
import me.pineacle.luckofthesea.commands.subs.HelpCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final LuckOfTheSeaPlugin plugin;

    private final List<SubCommand> subCommands;
    private final Map<Class<? extends SubCommand>, SubCommand> subCommandsClazz;

    public CommandHandler(final LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
        subCommands = new ArrayList<>();
        subCommandsClazz = new HashMap<>();

        registerSubCommand(new HelpCommand(plugin));
        registerSubCommand(new CodexCommand(plugin));
        registerSubCommand(new AugmentCommand(plugin));

    }

    public void registerSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
        subCommandsClazz.put(subCommand.getClass(), subCommand);
    }

    public List<SubCommand> getSubCommands() {
        return new ArrayList<>(subCommands);
    }

    public SubCommand getSubCommand(Class<? extends SubCommand> subCommandClass) {
        return subCommandsClazz.get(subCommandClass);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (args.length == 0) {
            getSubCommand(HelpCommand.class).execute(sender, label, args);
            return true;
        }

        for (SubCommand subCommand : subCommands) {

            if (!subCommand.getConsoleCompatible()) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "This command is not available for console.");
                    return true;
                }
            }

            if (subCommand.isValidTrigger(args[0])) {

                if (!subCommand.hasPermission(sender)) {
                      sender.sendMessage(plugin.getLanguage().get("no-permission"));
                    return true;
                }

                if (args.length - 1 >= subCommand.getMinimumArguments()) {
                    try {
                        subCommand.execute(sender, label, Arrays.copyOfRange(args, 1, args.length));
                    } catch (CommandException e) {
                        sender.sendMessage(ChatColor.RED + e.getMessage());
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + subCommand.getName() + " " + subCommand.getPossibleArguments());
                }

                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Unknown sub-command. Type \"/" + label + " help\" for a list of commands.");
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> commands = new ArrayList<>();

        for (SubCommand subCommand : subCommands) {
            commands.add(subCommand.getName());
        }

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], commands, completions);
        return completions;
    }
}
