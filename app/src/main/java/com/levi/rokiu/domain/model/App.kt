package com.levi.rokiu.domain.model

import com.levi.rokiu.R
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text


@Root(name = "app", strict = false)
data class App(
    @field:Attribute(name = "id", required = false)
    var id: String = "",

    @field:Attribute(name = "type", required = false)
    var type: String = "",

    @field:Attribute(name = "version", required = false)
    var version: String = "",

    @field:Text(required = false)
    var name: String = ""
)

fun App.toChannel(): Channel {
    return Channel(
        id = id,
        name = name,
        logoResId = R.drawable.tv_24px
    )
}

