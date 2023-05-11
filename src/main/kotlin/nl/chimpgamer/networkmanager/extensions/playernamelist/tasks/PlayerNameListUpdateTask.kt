package nl.chimpgamer.networkmanager.extensions.playernamelist.tasks

import nl.chimpgamer.networkmanager.extensions.playernamelist.PlayerNameList
import org.bukkit.Bukkit

class PlayerNameListUpdateTask(private val playerNameList: PlayerNameList) : Runnable {
    override fun run() {
        Bukkit.getOnlinePlayers().forEach(playerNameList::applyPlayerListName)
    }
}