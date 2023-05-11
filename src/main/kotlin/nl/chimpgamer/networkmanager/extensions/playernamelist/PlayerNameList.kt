package nl.chimpgamer.networkmanager.extensions.playernamelist

import nl.chimpgamer.networkmanager.api.extensions.NMExtension
import nl.chimpgamer.networkmanager.api.utils.Placeholders
import nl.chimpgamer.networkmanager.api.utils.PlatformType
import nl.chimpgamer.networkmanager.api.utils.formatColorCodes
import nl.chimpgamer.networkmanager.extensions.playernamelist.configrations.Formats
import nl.chimpgamer.networkmanager.extensions.playernamelist.listeners.PlayerListener
import nl.chimpgamer.networkmanager.extensions.playernamelist.tasks.PlayerNameListUpdateTask
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList

class PlayerNameList : NMExtension() {

    // Configurations
    private val formats = Formats(this)

    // Listeners
    private val playerListener = PlayerListener(this)

    // Tasks
    private var updateTaskId = -1

    override fun onEnable() {
        if (networkManager.platformType !== PlatformType.BUKKIT) {
            logger.severe("Hey, this NetworkManager extension is for Spigot only!")
            return
        }
        formats.initialize()

        networkManager.registerListener(playerListener)
        updateTaskId = scheduler.runRepeating(PlayerNameListUpdateTask(this), formats.updateInterval)
    }

    override fun onConfigsReload() {
        formats.reload()
    }

    override fun onDisable() {
        HandlerList.unregisterAll(playerListener)
        if (updateTaskId != -1) {
            scheduler.stopRepeating(updateTaskId)
        }
    }

    fun applyPlayerListName(player: Player) {
        val format = formats.getFormat(player) ?: return
        networkManager.cacheManager.cachedPlayers.getIfLoaded(player.uniqueId).let { nmPlayer ->
            player.playerListName = Placeholders.setPlaceholders(nmPlayer, format.prefix + format.name + format.suffix).formatColorCodes()
        }
    }
}