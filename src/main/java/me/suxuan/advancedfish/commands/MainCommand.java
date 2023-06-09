package me.suxuan.advancedfish.commands;

import me.suxuan.advancedfish.main;
import me.suxuan.advancedfish.manager.BaitManager;
import me.suxuan.advancedfish.manager.ConfigManager;
import me.suxuan.advancedfish.manager.FishManager;
import me.suxuan.advancedfish.manager.RegisterManager;
import me.suxuan.advancedfish.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author: CBer_SuXuan
 * @project: AdvancedFish
 * @className: MainCommand
 * @date: 2023/4/5 21:13
 * @description: Main command
 */
public class MainCommand implements TabExecutor {

    private static final Plugin plugin = main.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(" ");
            sender.sendMessage(CC.translate("&e此服务器正在使用 Advanced Fish 插件。 版本: " +
                    plugin.getDescription().getVersion() + ", 作者: CBer_SuXuan & xiaoyueyouqwq"));
            sender.sendMessage(" ");
            return false;
        }

        if (!player.hasPermission(ConfigManager.getAdvancedFishYaml().getString("ADMIN-PERM"))) {
            sender.sendMessage(" ");
            sender.sendMessage(CC.translate("&e此服务器正在使用 Advanced Fish 插件。 版本: " +
                    plugin.getDescription().getVersion() + ", 作者: CBer_SuXuan & xiaoyueyouqwq"));
            sender.sendMessage(" ");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&6&lAdvanced Fish &8- &7版本: " + plugin.getDescription().getVersion() + " &8- &f指令帮助"));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &6/af getFishList &7- &7&o获取所有的已加载鱼类."));
            player.sendMessage(CC.translate("&e • &6/af getFishItem <鱼类文件名> <自定义的钓手名> &7- &7&o获取指定鱼类的物品."));
            player.sendMessage(CC.translate("&e • &6/af getFurnaceFishItem <鱼类文件名> <自定义的钓手名> &7- &7&o获取指定鱼类烹饪后的物品."));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &6/af getBaitList &7- &7&o获取所有的已加载鱼饵."));
            player.sendMessage(CC.translate("&e • &6/af getBaitItem <鱼饵文件名> <自定义的制作者名> &7- &7&o获取指定鱼饵的物品."));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &6/af reload &7- &7&o重新注册所有内容 (自动重载内容请详细查看鱼类配置文件)"));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &6/area &7- &7&o鱼域设置."));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &7/getEnchantment &7- &7&o辅助指令, 获取手上物品的附魔以供配置文件参考."));
            player.sendMessage(CC.translate("&e • &7/getPotionEffect &7- &7&o辅助指令, 获取手上物品的药水以供配置文件参考."));
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &6您可以查看 Wiki 获取插件的基础教程内容: https://github.com/xiaoyueyoqwq/AdvancedFish/wiki/Welcome"));
            player.sendMessage(CC.CHAT_BAR);
            return true;
        }

        if (args[0].toLowerCase(Locale.ROOT).equals("getfishlist")) {
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &6目前已加载的鱼类有: " + RegisterManager.getRegisterFish().toString()));
            player.sendMessage(CC.CHAT_BAR);
            return true;
        }

        if (args[0].toLowerCase(Locale.ROOT).equals("getfishitem")) {
            if (args.length == 1) {
                player.sendMessage(CC.translate("&c您需要一个鱼的名字才能获得所对应的物品!"));
                return false;
            }

            if (args.length == 2) {
                player.sendMessage(CC.translate("&c您需要自定义一个钓手名才能获取!"));
                return false;
            }

            player.getInventory().addItem(FishManager.getFishItem(args[1], args[2]));
            return true;
        }

        if (args[0].toLowerCase(Locale.ROOT).equals("getfurnacefishitem")) {
            if (args.length == 1) {
                player.sendMessage(CC.translate("&c您需要一个鱼的名字才能获得所对应的物品!"));
                return false;
            }

            if (args.length == 2) {
                player.sendMessage(CC.translate("&c您需要自定义一个钓手名才能获取!"));
                return false;
            }

            if (!FishManager.getFishEnableFurnaceRecipe(args[1])) {
                player.sendMessage(CC.translate("&c此鱼并没有设置其可烹饪，您无法获取!"));
                return false;
            }

            player.getInventory().addItem(FishManager.fishItemToFurnaceItem(FishManager.getFishItem(args[1], player.getName()), args[2]));
            return true;
        }

        if (args[0].toLowerCase(Locale.ROOT).equals("getbaitlist")) {
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(CC.translate("&e • &6目前已加载的鱼饵有: " + RegisterManager.getRegisterBait().toString()));
            player.sendMessage(CC.CHAT_BAR);
            return true;
        }

        if (args[0].toLowerCase(Locale.ROOT).equals("getbaititem")) {
            if (args.length == 1) {
                player.sendMessage(CC.translate("&c您需要一个鱼饵的名字才能获得所对应的物品!"));
                return false;
            }

            if (args.length == 2) {
                player.sendMessage(CC.translate("&c您需要自定义一个制作人名才能获取!"));
                return false;
            }

            player.getInventory().addItem(BaitManager.getBaitItem(args[1], args[2]));
            return true;
        }

        if (args[0].toLowerCase(Locale.ROOT).equals("reload")) {
            // Unload existing recipes and download in case reload plugin cause recipes repeat error
            RegisterManager.getRegisterRecipe().forEach(key -> {
                if (main.getServerVersion() >= 1014) Bukkit.removeRecipe(key);
                if (main.getServerVersion() >= 1013) Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.undiscoverRecipe(key));
            });

            RegisterManager.registerAllFish();
            RegisterManager.registerAllBait();

            player.sendMessage(CC.translate(ConfigManager.getMessageYaml().getString("RELOAD")));
            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(ConfigManager.getAdvancedFishYaml().getString("ADMIN-PERM"))) return null;

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0],
                    Arrays.asList("getFishList", "getFishItem", "getFurnaceFishItem", "getBaitList", "getBaitItem", "reload"), new ArrayList<>());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("getFishItem") || args[0].equalsIgnoreCase("getFurnaceFishItem")) {
                List<String> result = new ArrayList<>(RegisterManager.getRegisterFish());
                return StringUtil.copyPartialMatches(args[1], result, new ArrayList<>());
            } else if (args[0].equalsIgnoreCase("getBaitItem")) {
                List<String> result = new ArrayList<>(RegisterManager.getRegisterBait());
                return StringUtil.copyPartialMatches(args[1], result, new ArrayList<>());
            } else {
                return new ArrayList<>();
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("getFishItem") || args[0].equalsIgnoreCase("getFurnaceFishItem") ||
                    args[0].equalsIgnoreCase("getBaitItem")) {
                List<String> names = new ArrayList<>();
                for (Player target : Bukkit.getOnlinePlayers()) {
                    names.add(target.getName());
                }
                return StringUtil.copyPartialMatches(args[2], names, new ArrayList<>());
            } else {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }
}
