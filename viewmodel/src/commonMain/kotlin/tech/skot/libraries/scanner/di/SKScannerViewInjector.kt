package tech.skot.libraries.scanner.di

import tech.skot.core.di.get
import tech.skot.libraries.scanner.SKScannerVC

interface SKScannerViewInjector {
    fun sKScanner(
        formats: Set<SKScannerVC.Format>,
        onScanCode: Function1<String, Unit>,
        scanningInitial: Boolean
    ): SKScannerVC
}

val skscannerViewInjector: SKScannerViewInjector = get()