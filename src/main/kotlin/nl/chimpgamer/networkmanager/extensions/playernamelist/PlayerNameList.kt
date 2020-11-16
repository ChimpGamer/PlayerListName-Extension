package nl.chimpgamer.networkmanager.extensions.playernamelist

import nl.chimpgamer.networkmanager.api.extensions.NMExtension
import nl.chimpgamer.networkmanager.api.utils.PlatformType
import nl.chimpgamer.networkmanager.extensions.playernamelist.configrations.Formats
import nl.chimpgamer.networkmanager.extensions.playernamelist.listeners.PlayerListener
import nl.chimpgamer.networkmanager.extensions.playernamelist.tasks.PlayerNameListUpdateTask
import org.bukkit.event.HandlerList

class PlayerNameList : NMExtension() {

    // Configurations
    val formats = Formats(this)

    // Listeners
    private val playerListener = PlayerListener(this)

    override fun onEnable() {
        if (networkManager.platformType !== PlatformType.BUKKIT) {
            logger.severe("Hey, this NetworkManager extension is for Spigot only!")
            return
        }
        formats.initialize()

        networkManager.registerListener(playerListener)
        networkManager.scheduler.runRepeating(PlayerNameListUpdateTask(this), formats.updateInterval)
    }

    override fun onConfigsReload() {
        formats.reload()
    }

    override fun onDisable() {
        HandlerList.unregisterAll(playerListener)
    }
}