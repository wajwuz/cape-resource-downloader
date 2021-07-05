package net.michal.basic

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.michal.helpers.downloadFile
import net.michal.helpers.downloadText
import java.io.File

private lateinit var ENDPOINT: String
private val USERS_ENDPOINT by lazy { "$ENDPOINT/users" }
private val ITEMS_ENDPOINT by lazy { "$ENDPOINT/items" }

@Serializable
data class Item(val type: String)

@Serializable
data class User(val items: List<Item>)

val json = Json { ignoreUnknownKeys = true }

suspend fun downloadUser(nickname: String): User {
    val response = downloadText("$USERS_ENDPOINT/$nickname.cfg")

    return json.decodeFromString(response)
}

suspend fun downloadModel(name: String, destination: File) =
    downloadFile("$ITEMS_ENDPOINT/$name/model.cfg", destination)

suspend fun downloadTexture(nickname: String, name: String, destination: File) =
    downloadFile("$ITEMS_ENDPOINT/$name/users/$nickname.png", destination)

private val destinationDir = File("downloaded")

operator fun File.get(parent: String) = File(this, parent)

suspend fun main(arguments: Array<String>) {
    ENDPOINT = arguments.getOrNull(0)
        ?: throw IllegalArgumentException("Please provide endpoint domain!")
    val username = arguments.getOrNull(1)
        ?: throw IllegalArgumentException("Please provide username!")

    val user = downloadUser(username)

    user.items.forEach {
        val modelDir = destinationDir[it.type]
        val texturesDir = modelDir["textures"]

        if (!texturesDir.exists()) texturesDir.mkdirs()

        val modelFile = modelDir["model.cfg"]
        if (!modelFile.exists()) downloadModel(it.type, modelFile)

        val textureFile = texturesDir["$username.jpg"]
        if (!textureFile.exists()) downloadTexture(username, it.type, textureFile)
    }
}
