package tech.skot.libraries.scanner

import androidx.camera.view.PreviewView
import androidx.fragment.app.Fragment
import tech.skot.core.components.SKActivity
import tech.skot.core.components.SKComponentViewProxy
import tech.skot.libraries.skscanner.viewlegacy.R

class SKScannerViewProxy(
    override val formats: Set<SKScannerVC.Format>,
    override val onScanCode: (code: String) -> Unit,
    scanningInitial: Boolean
) : SKComponentViewProxy<PreviewView>(), SKScannerVC {

    companion object {
        var LAYOUT_ID: Int? = null
    }

    override val layoutId: Int
        get() = LAYOUT_ID ?: R.layout.sk_scanner

    override fun saveState() {
    }

    override var scanning: Boolean = scanningInitial

    override fun bindTo(
        activity: SKActivity,
        fragment: Fragment?,
        binding: PreviewView
    ): SKScannerView = SKScannerView(this, activity, fragment, binding)
}