package com.levi.rokiu.domain.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "apps", strict = false)
data class AppsResponse(
    @field:ElementList(inline = true, required = false)
    var apps: List<App>? = null
)