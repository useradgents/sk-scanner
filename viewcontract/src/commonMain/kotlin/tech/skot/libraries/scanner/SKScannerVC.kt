package tech.skot.libraries.scanner

import tech.skot.core.components.SKComponentVC
import tech.skot.core.components.SKLayoutIsSimpleView

@SKLayoutIsSimpleView
interface SKScannerVC : SKComponentVC {
    val formats: Set<Format>

    sealed class Format {
        object EAN8 : Format()
        object EAN13 : Format()
        object Code128 : Format()
        object QRCode : Format()
    }

    var scanning: Boolean
    val onScanCode: (code: String) -> Unit
}