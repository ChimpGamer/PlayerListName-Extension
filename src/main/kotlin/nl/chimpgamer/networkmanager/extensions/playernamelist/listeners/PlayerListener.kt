package nl.chimpgamer.networkmanager.extensions.playernamelist.listeners

import nl.chimpgamer.networkmanager.extensions.playernamelist.PlayerNameList
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener(private val playerNameList: PlayerNameList) : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        playerNameList.applyPlayerListName(event.player)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerChangedWorld(event: PlayerChangedWorldEvent) {
        playerNameList.applyPlayerListName(event.player)
    }
}