package tech.skot.libraries.scanner

import tech.skot.core.components.SKComponentViewMock

class SKScannerViewMock(
    override val formats: Set<SKScannerVC.Format>,
    override val onScanCode: (code: String) -> Unit,
    scanningInitial: Boolean
) : SKComponentViewMock(), SKScannerVC {
    override var scanning: Boolean = scanningInitial
}