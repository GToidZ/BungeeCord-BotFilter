package ru.leymooo.botfilter.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Settings extends Config
{

    @Ignore
    public static final Settings IMP = new Settings();
    
    @Final
    public String BOT_FILTER_VERSION = "4.0.0";

    @Comment(
            {
                "BungeeCord-BotFiler (maythiwat's build)", 
                "https://github.com/maythiwat/BungeeCord-BotFilter", 
                "Any bugs or feature request, Please submit an issue in github."
            })

    @Final
    public final String ISSUES = "https://github.com/maythiwat/BungeeCord-BotFilter/issues";
    @Final
    public final String HELP = "https://github.com/maythiwat/BungeeCord-BotFilter";

    @Comment("Original BotFilter (RU)")
    @Final
    public final String ISSUES_RU = "https://github.com/Leymooo/BungeeCord/issues";
    @Final
    public final String HELP_RU = "http://www.rubukkit.org/threads/137038/";

    @Create
    public MESSAGES MESSAGES;

    @Comment("Do not use '\\ n', use %nl%")
    public static class MESSAGES
    {

        public String PREFIX = "&b&lBot&d&lFilter";
        public String CHECKING = "%prefix%&7>> &aОPlease wait...";
        public String CHECKING_CAPTCHA = "%prefix%&7>> &aEnter the number from the image in the chat.";
        public String CHECKING_CAPTCHA_WRONG = "%prefix%&7>> &cYou entered the captcha incorrectly, please try again. У вас &a%s &c%s";
        public String SUCCESSFULLY = "%prefix%&7>> &aCheck passed, enjoy the game.";
        public String KICK_MANY_CHECKS = "%prefix%%nl%%nl%&cSuspicious activity noticed from your IP Address%nl%%nl%&6Try again in 10 minutes.";
        public String KICK_NOT_PLAYER = "%prefix%%nl%%nl%&cYou did not pass the test, maybe you are a bot%nl%&7&oIf it is not, please try again.";
        public String KICK_COUNTRY = "%prefix%%nl%%nl%&cYour country is banned on the server.";
        public String KICK_BIG_PING = "%prefix%%nl%%nl%&cYou have a very high ping, most likely you are a bot.";
        @Comment(
                {
                    "Title%nl%Subtitle", "Leave blank to disable ( edit: CHECKING_TITLE = \"\" )",
                    "Turning off titles can slightly improve performance."
                })
        public String CHECKING_TITLE = "&r&lBot&b&lFilter%nl%&aChecking...";
        public String CHECKING_TITLE_SUS = "&rCheck passed%nl%&Have a nice game";
        public String CHECKING_TITLE_CAPTCHA = " %nl%&rEnter captcha in chat!";
    }

    @Create
    public GEO_IP GEO_IP;

    @Comment("Enable or Disable GeoIp")
    public static class GEO_IP
    {

        @Comment(
                {
                    "When verification is working",
                    "0 - Always",
                    "1 - Only during the bot attack",
                    "2 - Never"
                })
        public int MODE = 1;
        @Comment(
        {
            "How exactly does GeoIp work",
            "0 - White list(Only those countries in the list can enter)",
            "1 - Black list(Only countries that are not in the list can enter)"
        })
        public int TYPE = 0;
        @Comment(
                {
                    "URL to download GEOIP",
                    "Change the link if it does not working.",
                    "The filename must end with .mmdb or be packed in .tar.gz"
                })
        public String GEOIP_DOWNLOAD_URL = "http://geolite.maxmind.com/download/geoip/database/GeoLite2-Country.tar.gz";
        @Comment("Allowed country (default: Asean)")
        public List<String> ALLOWED_COUNTRIES = Arrays.asList( "TH", "LA", "MM", "KH", "VN", "PH", "MY", "SG", "ID", "BN" );
    }

    @Create
    public PING_CHECK PING_CHECK;

    @Comment("Enable or disable high ping checking")
    public static class PING_CHECK
    {

        @Comment(
                {
                    "When verification is working",
                    "0 - Always",
                    "1 - Only during the bot attack",
                    "2 - Never"
                })
        public int MODE = 1;
        @Comment("Maximum allowed ping")
        public int MAX_PING = 350;
    }

    @Create
    public SERVER_PING_CHECK SERVER_PING_CHECK;

    @Comment("Enable or disable check for direct connection")
    public static class SERVER_PING_CHECK
    {

        @Comment(
                {
                    "When verification is working",
                    "0 - Always",
                    "1 - Only during the bot attack",
                    "2 - Never",
                    "Disabled by default, since it is not very stable, during strong attacks."
                })
        public int MODE = 2;
        @Comment("How long delay to connect server after receiving the motd server")
        public int CACHE_TIME = 12;
        public List<String> KICK_MESSAGE = new ArrayList()
        {
            {
                add( "%nl%" );
                add( "%nl%" );
                add( "&cYou were kicked! Do not use direct connection." );
                add( "%nl%" );
                add( "%nl%" );
                add( "&bTo login to the server:" );
                add( "%nl%" );
                add( "&71) &rAdd server to server list." );
                add( "%nl%" );
                add( "&lOur Server IP &8>> &b&lmc.example.org" );
                add( "%nl%" );
                add( "%nl%" );
                add( "&72) &rUpdate server list. " );
                add( "%nl%" );
                add( "&oTo update it, click &c&lRefresh" );
                add( "%nl%" );
                add( "%nl%" );
                add( "&73) &rWait &c1-3&r seconds then join the server again." );

            }
        };
    }

    @Create
    public PROTECTION PROTECTION;

    @Comment(
            {
                "Setting how protection will work",
                "0 - Only check with captcha",
                "1 - Drop check + captcha",
                "2 - Drop check, if failed, then captcha"
            })
    public static class PROTECTION
    {

        @Comment("Operation mode when no attack")
        public int NORMAL = 2;
        @Comment("Operation mode during the attack")
        public int ON_ATTACK = 1;
        /*
        @Comment(
                {
                    "Когда работают дополнительные проверки по протоколу",
                    "    (Пакеты на которые клиент должен всегда отвечать)",
                    "0 - Всегда",
                    "1 - Только во время бот атаки",
                    "2 - Отключить"
                })
        public int ADDITIONAL_CHECKS = 1;
         */
    }

    @Create
    public SQL SQL;

    @Comment("Database Setup")
    public static class SQL
    {

        @Comment("Database type. sqlite or mysql")
        public String STORAGE_TYPE = "sqlite";
        @Comment("After how many days to remove players from the database, which have been tested and no longer entered. 0 or less to disable")
        public int PURGE_TIME = 14;
        @Comment("Settings for mysql")
        public String HOSTNAME = "127.0.0.1";
        public int PORT = 3306;
        public String USER = "user";
        public String PASSWORD = "password";
        public String DATABASE = "database";
    }

    @Comment(
            {
                "How many players / bots should go in 1 minute for protection to be activated",
                "Recommended options when there is no advertising: ",
                "Up to 150 online - 25, up to 250 - 30, up to 350 - 35, up to 550 - 40.45, above - adjust to yourself ",
                "It is recommended to increase these values ​​during an advertisement or when a current is flowing."
            })
    public int PROTECTION_THRESHOLD = 30;

    @Comment("How long is automatic protection active? In milliseconds. 1 second = 1000")
    public int PROTECTION_TIME = 120000;

    @Comment("Check whether the bot on entering the server during a bot attack, regardless of whether the check passed or not")
    public boolean FORCE_CHECK_ON_ATTACK = true;

    @Comment("Show online with filter")
    public boolean SHOW_ONLINE = true;

    @Comment("How much time does the player have to go through the defense. In milliseconds. 1 second = 1000")
    public int TIME_OUT = 12700;

    @Comment(
            {
                "Enable / Disable compatibility with old plugins that use ScoreBoard on a bungee?",
                "Set to false if there are problems with new plugins."
            })
    public boolean FIX_SCOREBOARDS = true;

    @Comment("Fix for: 'Team 'xxx' already exist in this scoreboard'")
    public boolean FIX_SCOREBOARD_TEAMS = true;

    public void reload(File file)
    {
        load( file );
        save( file );
    }
}
