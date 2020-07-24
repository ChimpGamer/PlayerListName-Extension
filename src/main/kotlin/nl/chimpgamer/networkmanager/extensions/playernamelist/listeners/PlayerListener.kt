package nl.chimpgamer.networkmanager.extensions.playernamelist.listeners

import nl.chimpgamer.networkmanager.api.utils.Placeholders
import nl.chimpgamer.networkmanager.extensions.playernamelist.PlayerNameList
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener(private val playerNameList: PlayerNameList) : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        applyPlayerListName(event.player)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerChangedWorld(event: PlayerChangedWorldEvent) {
        applyPlayerListName(event.player)
    }

    private fun applyPlayerListName(player: Player) {
        val format = playerNameList.formats.getFormat(player) ?: return
        val nmPlayer = playerNameList.networkManager.getPlayer(player.uniqueId) ?: return
        player.playerListName = Placeholders.setPlaceholders(nmPlayer, format.prefix + format.name + format.suffix, true, true)
    }
}