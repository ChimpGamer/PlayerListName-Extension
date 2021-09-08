package nl.chimpgamer.networkmanager.extensions.playernamelist.configrations

import nl.chimpgamer.networkmanager.api.utils.FileUtils
import nl.chimpgamer.networkmanager.extensions.playernamelist.PlayerNameList
import nl.chimpgamer.networkmanager.extensions.playernamelist.models.Format
import org.bukkit.entity.Player

class Formats(private val playerNameList: PlayerNameList) : FileUtils(playerNameList.dataFolder.absolutePath, "formats.yml") {
    private var formats: MutableMap<String, Format> = HashMap()

    fun initialize() {
        setupFile(playerNameList.getResource("formats.yml"))
        formats = HashMap()
        loadData()
    }

    private fun loadData() {
        val formatsSection = config.getConfigurationSection("formats") ?: return
        for (key in formatsSection.getKeys(false)) {
            val prefix = formatsSection.getString("$key.prefix")
            val name = formatsSection.getString("$key.name")
            val suffix = formatsSection.getString("$key.suffix")
            val priority = formatsSection.getInt("$key.priority", 100)

            formats[key] = Format(prefix, name, suffix, priority)
        }
    }

    val updateInterval: Int
        get() = getInt("updateInterval", 10)

    fun getFormat(player: Player): Format? {
        val formats = ArrayList<Format>()
        for ((key, format) in this.formats.entries) {
            if (player.hasPermission("playernamelist.format.$key")) {
                formats.add(format)
            }
        }
        val format = this.formats["default"]
        if (format != null) {
            formats.add(format)
        }
        if (formats.isEmpty()) {
            playerNameList.logger.warning("There are no formats available. Please add atleast one format to the formats.yml!")
        }
        return formats.maxByOrNull { it.priority }
    }

    override fun reload() {
        super.reload()
        formats.clear()
        loadData()
    }
}