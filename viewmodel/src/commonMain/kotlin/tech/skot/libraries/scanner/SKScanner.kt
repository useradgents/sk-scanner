package tech.skot.libraries.scanner

import tech.skot.core.components.SKComponent
import tech.skot.libraries.scanner.di.skscannerViewInjector


class SKScanner(
    formats: Set<SKScannerVC.Format>,
    scanningInitial: Boolean = true,
    onScanCode: (code: String) -> Unit
) : SKComponent<SKScannerVC>() {
    final override val view: SKScannerVC = skscannerViewInjector.sKScanner(
        formats = formats,
        onScanCode = onScanCode,
        scanningInitial = scanningInitial,
    )

    var scanning: Boolean
        get() = view.scanning
        set(value) {
            view.scanning = value
        }
}
