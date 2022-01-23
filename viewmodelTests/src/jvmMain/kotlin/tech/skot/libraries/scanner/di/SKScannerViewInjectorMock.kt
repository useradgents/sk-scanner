package tech.skot.libraries.scanner.di

import tech.skot.core.di.module
import tech.skot.core.di.InjectorMock
import tech.skot.libraries.scanner.SKScannerVC
import tech.skot.libraries.scanner.SKScannerViewMock

class SKScannerViewInjectorMock : SKScannerViewInjector {
    override fun sKScanner(
        formats: Set<SKScannerVC.Format>,
        onScanCode: (String) -> Unit,
        scanningInitial: Boolean
    ) = SKScannerViewMock(formats, onScanCode, scanningInitial)
}

val skscannerModuleMock = module<InjectorMock> {
    single<SKScannerViewInjector> { SKScannerViewInjectorMock() }
}