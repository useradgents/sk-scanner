package tech.skot.libraries.scanner.di

import tech.skot.core.di.BaseInjector
import tech.skot.core.di.module
import tech.skot.libraries.scanner.SKScannerVC
import tech.skot.libraries.scanner.SKScannerViewProxy

class SKScannerViewInjectorImpl : SKScannerViewInjector {
    override fun sKScanner(
        formats: Set<SKScannerVC.Format>,
        onScanCode: Function1<String, Unit>,
        scanningInitial: Boolean
    ): SKScannerVC = SKScannerViewProxy(formats, onScanCode, scanningInitial)
}

val skscannerModule = module<BaseInjector> {
    single { SKScannerViewInjectorImpl() as SKScannerViewInjector }
}