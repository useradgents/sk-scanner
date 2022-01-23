package tech.skot.libraries.scanner

import androidx.camera.view.PreviewView
import androidx.fragment.app.Fragment
import tech.skot.core.components.SKActivity
import tech.skot.core.components.SKComponentViewProxy

class SKScannerViewProxy(
    override val formats: Set<SKScannerVC.Format>,
    override val onScanCode: (code: String) -> Unit,
    scanningInitial: Boolean
) : SKComponentViewProxy<PreviewView>(), SKScannerVC {

    override fun saveState() {
    }

    override var scanning: Boolean = scanningInitial

    override fun bindTo(
        activity: SKActivity,
        fragment: Fragment?,
        binding: PreviewView
    ): SKScannerView = SKScannerView(this, activity, fragment, binding)
}