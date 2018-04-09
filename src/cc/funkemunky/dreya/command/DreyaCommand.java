package cc.funkemunky.dreya.command;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.util.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DreyaCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Dreya v" + Dreya.getInstance().getDescription().getVersion() + " : Created By " + ChatColor.AQUA + "[Mr_JaVa_ , funkemunky]");
        } else if(args[0].equalsIgnoreCase("toggle")) {
            if(!sender.hasPermission("dreya.admin")
                    && !sender.hasPermission("dreya.toggle")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            if(args.length == 2) {
                Check checkName = Dreya.getInstance().getDataManager().getCheckByName(args[1]);

                if(checkName == null) {
                    sender.sendMessage(ChatColor.RED + "Check '" + args[1] + "' does not exist!");
                    return true;
                }

                checkName.setEnabled(!checkName.isEnabled());
                sender.sendMessage(Config.getPrefix() + ChatColor.GRAY + "Set check's state to: " + (checkName.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + checkName.isEnabled());
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Invalid arguments.");
            return true;
        } else if(args[0].equalsIgnoreCase("status")) {
            if(!sender.hasPermission("dreya.admin")
                    && !sender.hasPermission("dreya.status")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            List<String> checkNames = new ArrayList<>();

            for(Check checkLoop : Dreya.getInstance().getDataManager().getChecks()) {
                checkNames.add((checkLoop.isEnabled() ? ChatColor.GREEN + checkLoop.getName() : ChatColor.RED + checkLoop.getName()) + ChatColor.GRAY);
            }

            sender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------------");
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Dreya Status");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GRAY + "Checks: " + checkNames.toString());
            sender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------------");
        }

        return true;
    }
}
