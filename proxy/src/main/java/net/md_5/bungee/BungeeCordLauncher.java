package net.md_5.bungee;

import java.security.Security;
import java.util.Arrays;
import java.util.logging.Level;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.command.ConsoleCommandSender;

public class BungeeCordLauncher
{

    public static void main(String[] args) throws Exception
    {
        Security.setProperty( "networkaddress.cache.ttl", "30" );
        Security.setProperty( "networkaddress.cache.negative.ttl", "10" );

        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        parser.acceptsAll( Arrays.asList( "v", "version" ) );
        parser.acceptsAll( Arrays.asList( "noconsole" ) );

        OptionSet options = parser.parse( args );

        if ( options.has( "version" ) )
        {
            System.out.println( BungeeCord.class.getPackage().getImplementationVersion() );
            return;
        }

        BungeeCord bungee = new BungeeCord();
        ProxyServer.setInstance( bungee );
        bungee.getLogger().log( Level.WARNING, "Starting BungeeCord-BotFilter (maythiwat\'s build)");
        bungee.getLogger().log( Level.WARNING, String.format("Based on BungeeCord %s", bungee.getGameVersion()) );
        bungee.getLogger().log( Level.WARNING, "Jenkins: http://ci.demza.info/job/BungeeCord-BotFilter/" );
        bungee.getLogger().log( Level.WARNING, "Source Code: https://github.com/maythiwat/BungeeCord-BotFilter" );
        bungee.start();

        if ( !options.has( "noconsole" ) )
        {
            String line;
            while ( bungee.isRunning && ( line = bungee.getConsoleReader().readLine( ">" ) ) != null )
            {
                if ( !bungee.getPluginManager().dispatchCommand( ConsoleCommandSender.getInstance(), line ) )
                {
                    bungee.getConsole().sendMessage( new ComponentBuilder( "Command not found :(" ).color( ChatColor.RED ).create() );
                }
            }
        }
    }
}
