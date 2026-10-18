package ru.kre4.chad.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Long,
    val from: String,
    val to: String,
    val data: MessageContent,
    val time: Long,
)

@Serializable
data class MessageContent(
    @SerialName("Text")
    val text: TextContent? = null,
    @SerialName("Image")
    val image: ImageContent? = null,
)

@Serializable
data class TextContent(
    val text: String,
)

@Serializable
data class ImageContent(
    val link: String,
)
