package nl.dykam.dev.dnslogin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class DnsLoginVerifier implements Listener {
  DnsLoginPlugin plugin;
  public DnsLoginVerifier(DnsLoginPlugin plugin) {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
    plugin.getLogger().info("Initialized Dns Login Verifier");
  }

  @EventHandler
  private void onPlayerLogin(PlayerLoginEvent ple) {
    try {
      String keyName = "users." + ple.getPlayer().getName();
      String requiredKey = plugin.getConfig().getString(keyName);
      if(requiredKey != null && ple.getHostname().startsWith(requiredKey))
        return;
      plugin.getLogger().info("Login attempt with username " + ple.getPlayer().getName() + " disallowed. DnsLogin key was not found");
      ple.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Player not whitelisted.");
    } catch (Exception e) {
      ple.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Player not whitelisted");
    }
  }
}
