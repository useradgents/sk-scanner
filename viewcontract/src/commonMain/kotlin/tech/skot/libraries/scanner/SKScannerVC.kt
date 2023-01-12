package tech.skot.libraries.scanner

import tech.skot.core.components.SKComponentVC
import tech.skot.core.components.SKLayoutIsSimpleView

@SKLayoutIsSimpleView
interface SKScannerVC : SKComponentVC {
    val formats: Set<Format>

    sealed class Format {
        object FormatUnknown : Format()
        object FormatAllFormats : Format()
        object FormatCode128 : Format()
        object FormatCode39 : Format()
        object FormatCode93 : Format()
        object FormatCodabar : Format()
        object FormatDataMatrix : Format()
        object FormatEan13 : Format()
        object FormatEan8 : Format()
        object FormatItf : Format()
        object FormatQrCode : Format()
        object FormatUpcA : Format()
        object FormatUpcE : Format()
        object FormatPdf417 : Format()
        object FormatAztec : Format()
        object TypeUnknown : Format()
        object TypeContactInfo : Format()
        object TypeEmail : Format()
        object TypeIsbn : Format()
        object TypePhone : Format()
        object TypeProduct : Format()
        object TypeSms : Format()
        object TypeText : Format()
        object TypeUrl : Format()
        object TypeWifi : Format()
        object TypeGeo : Format()
        object TypeCalendarEvent : Format()
        object TypeDriverLicense : Format()
    }

    var scanning: Boolean
    val onScanCode: (code: String) -> Unit
}