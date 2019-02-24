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
        super( "botfilter", null, "bf", "antibot", "bot" );
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
            sender.sendMessage( "§bBotFilter (maythiwat's build) §c" + Settings.IMP.BOT_FILTER_VERSION);
            sender.sendMessage( "§8> §7botfilter stat §8- §7Show statistics" );
            sender.sendMessage( "§8> §7botfilter reload §8- §7Reload Configuration" );
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
        sender.sendMessage( "§bBotFilter (maythiwat's build) §c" + Settings.IMP.BOT_FILTER_VERSION);
        sender.sendMessage( "§8> §7Github: §ehttps://github.com/maythiwat/BungeeCord-BotFilter/" );
        sender.sendMessage( "§8> §7Attack detected: " + ( botFilter.isUnderAttack() ? "§cYes" : "§aNo" ) );
        sender.sendMessage( "§8> §7Checking Player(s): §e" + botFilter.getOnlineOnFilter() );
        sender.sendMessage( "§8> §7Non-Bot Player(s): §e" + botFilter.getUsersCount() );
    }
}
