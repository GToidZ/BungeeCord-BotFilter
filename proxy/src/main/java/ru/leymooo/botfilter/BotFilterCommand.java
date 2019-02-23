package ru.leymooo.botfilter;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.leymooo.botfilter.config.Settings;

public class BotFilterCommand extends Command
{

    public BotFilterCommand()
    {
        super( "botfilter", null, "bf", "antibot", "gg" );
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if ( sender instanceof ProxiedPlayer )
        {
            sendStat( sender );
            return;
        }
        if ( args.length == 0 )
        {
            sender.sendMessage( "§r--------------- §bBotFilter §cv" + Settings.IMP.BOT_FILTER_VERSION + "§r-----------------" );
            sender.sendMessage( "§r> §lbotfilter reload §6- §aReload Configuration" );
            sender.sendMessage( "§r> §lbotfilter stat §6- §aShow statistics" );
            sender.sendMessage( "§r--------------- §bBotFilter §r-----------------" );
        } else if ( args[0].equalsIgnoreCase( "reload" ) )
        {
            BungeeCord.getInstance().getBotFilter().disable();
            BungeeCord.getInstance().setBotFilter( new BotFilter( false ) );
            sender.sendMessage( "§aReloaded Successfully." );
        } else if ( args[0].equalsIgnoreCase( "stat" ) || args[0].equalsIgnoreCase( "stats" ) || args[0].equalsIgnoreCase( "info" ) )
        {
            sendStat( sender );
        }
    }

    private void sendStat(CommandSender sender)
    {
        BotFilter botFilter = BungeeCord.getInstance().getBotFilter();
        sender.sendMessage( "§r----------------- §bBotFilter (maythiwat's build) §cv" + Settings.IMP.BOT_FILTER_VERSION + " §r-----------------" );
        sender.sendMessage( "§r> §lAttack detected: " + ( botFilter.isUnderAttack() ? "Yes" : "§aNo" ) );
        sender.sendMessage( "§r> §lChecking Player(s): " + botFilter.getOnlineOnFilter() );
        sender.sendMessage( "§r> §lNon-Bot Player(s): " + botFilter.getUsersCount() );
        sender.sendMessage( "§r> §lSource Code: https://github.com/maythiwat/BungeeCord-BotFilter/" );
        sender.sendMessage( "§r> §lOriginal Author: http://www.rubukkit.org/threads/137038/" );
    }
}
