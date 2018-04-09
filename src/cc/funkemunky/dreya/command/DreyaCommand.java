package cc.funkemunky.dreya.command;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DreyaCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Dreya : Created By " + ChatColor.AQUA + "[Mr_JaVa_ , funkemunky]");
        } else if(args[0].equalsIgnoreCase("toggle")) {
            if(!sender.hasPermission("dreya.admin")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            if(args.length == 2) {
                Check checkName = Dreya.getInstance().getDataManager().getCheckByName(args[1]);

                if(checkName == null) {
                    sender.sendMessage(ChatColor.RED + "That check is not a thing!");
                    return true;
                }

                checkName.setEnabled(!checkName.isEnabled());
                sender.sendMessage(ChatColor.GREEN + "Set check's state to " + checkName.isEnabled());
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Invalid arguments.");
            return true;
        }

        return true;
    }
}
