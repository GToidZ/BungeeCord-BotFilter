package ru.leymooo.botfilter;

import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import net.md_5.bungee.BungeeCord;
import ru.leymooo.botfilter.BotFilter.CheckState;
import ru.leymooo.botfilter.caching.PacketsPosition;
import ru.leymooo.botfilter.caching.PacketUtils;
import ru.leymooo.botfilter.caching.PacketUtils.KickType;
import ru.leymooo.botfilter.config.Settings;
import ru.leymooo.botfilter.utils.ManyChecksUtils;

/**
 *
 * @author Leymooo
 */
public class BotFilterThread
{

    private static Thread thread;
    private static final HashSet<String> TO_REMOVE_SET = new HashSet<>();
    private static BungeeCord bungee = BungeeCord.getInstance();
    

    public static void start()
    {
        ( thread = new Thread( () ->
        {
            while ( !Thread.currentThread().isInterrupted() && sleep(1000) )
            {
                try
                {
                    long currTime = System.currentTimeMillis();
                    BotFilter botFilter = bungee.getBotFilter();
                    for ( Map.Entry<String, Connector> entryset : bungee.getBotFilter().getConnectedUsersSet().entrySet() )
                    {
                        Connector connector = entryset.getValue();
                        if ( !connector.isConnected() )
                        {
                            TO_REMOVE_SET.add( entryset.getKey() );
                            continue;
                        }
                        CheckState state = connector.getState();
                        switch ( state )
                        {
                            case SUCCESSFULLY:
                            case FAILED:
                                TO_REMOVE_SET.add( entryset.getKey() );
                                continue;
                            default:
                                if ( ( currTime - connector.getJoinTime() ) >= Settings.IMP.TIME_OUT )
                                {
                                    connector.failed( KickType.NOTPLAYER, state == BotFilter.CheckState.CAPTCHA_ON_POSITION_FAILED
                                            ? "Too long fall check" : "Captcha not entered" );
                                    TO_REMOVE_SET.add( entryset.getKey() );
                                    continue;
                                } else if ( state == BotFilter.CheckState.CAPTCHA_ON_POSITION_FAILED || state == BotFilter.CheckState.ONLY_POSITION )
                                {
                                    connector.getChannel().writeAndFlush( PacketUtils.getChachedPacket( PacketsPosition.CHECKING ).get( connector.getVersion() ) );
                                } else
                                {
                                    connector.getChannel().writeAndFlush( PacketUtils.getChachedPacket( PacketsPosition.CHECKING_CAPTCHA ).get( connector.getVersion() ) );
                                }
                                connector.sendPing();
                        }
                    }

                } catch ( Exception e )
                {
                    bungee.getLogger().log( Level.WARNING, "[BotFilter] An exception is occurred. Please report it to the developer!", e );
                } finally
                {
                    if ( !TO_REMOVE_SET.isEmpty() )
                    {
                        for ( String remove : TO_REMOVE_SET )
                        {
                            bungee.getBotFilter().removeConnection( remove, null );
                        }
                        TO_REMOVE_SET.clear();
                    }
                }
            }

        }, "BotFilter thread" ) ).start();
    }

    public static void stop()
    {
        if ( thread != null )
        {
            thread.interrupt();
        }
    }

    private static boolean sleep(long time)
    {
        try
        {
            Thread.sleep( time );
        } catch ( InterruptedException ex )
        {
            return false;
        }
        return true;
    }

    public static void startCleanUpThread()
    {
        new Thread( () ->
        {
            while ( !Thread.interrupted() && sleep(BotFilter.ONE_MIN) )
            {
                ManyChecksUtils.cleanUP();
                if ( bungee.getConnectionThrottle() != null )
                {
                    bungee.getConnectionThrottle().cleanUP();
                }
                if ( bungee.getBotFilter() != null )
                {
                    BotFilter botFilter = bungee.getBotFilter();
                    if ( botFilter.getServerPingUtils() != null )
                    {
                        botFilter.getServerPingUtils().cleanUP();
                    }
                    if ( botFilter.getSql() != null )
                    {
                        botFilter.getSql().tryCleanUP();
                    }
                    if (botFilter.getGeoIp() != null) {
                        botFilter.getGeoIp().tryClenUP();
                    }
                }
            }
        }, "CleanUp thread" ).start();

    }
}
