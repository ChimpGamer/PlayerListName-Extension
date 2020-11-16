package nl.chimpgamer.networkmanager.extensions.playernamelist.tasks

import nl.chimpgamer.networkmanager.api.utils.Placeholders
import nl.chimpgamer.networkmanager.extensions.playernamelist.PlayerNameList
import org.bukkit.Bukkit

class PlayerNameListUpdateTask(private val playerNameList: PlayerNameList) : Runnable {
    override fun run() {
        Bukkit.getOnlinePlayers().forEach { player ->
            val format = playerNameList.formats.getFormat(player) ?: return
            playerNameList.networkManager.cacheManager.cachedPlayers.getIfLoaded(player.uniqueId).let { nmPlayer ->
                player.playerListName = Placeholders.setPlaceholders(nmPlayer, format.prefix + format.name + format.suffix,
                    formatText = true,
                    usePAPI = true
                )
            }
        }
    }
}