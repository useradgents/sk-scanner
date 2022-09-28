package tech.skot.libraries.scanner

import android.annotation.SuppressLint
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import tech.skot.core.SKLog
import tech.skot.core.components.SKActivity
import tech.skot.core.components.SKComponentView
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
class SKScannerView(
    override val proxy: SKScannerViewProxy,
    activity: SKActivity,
    fragment: Fragment?,
    binding: PreviewView
) : SKComponentView<PreviewView>(proxy, activity, fragment, binding) {

    fun SKScannerVC.Format.toAndroidFormat() =
        when (this) {
            SKScannerVC.Format.EAN8 -> Barcode.FORMAT_EAN_8
            SKScannerVC.Format.EAN13 -> Barcode.FORMAT_EAN_13
            SKScannerVC.Format.Code128 -> Barcode.FORMAT_CODE_128
            SKScannerVC.Format.QRCode -> Barcode.FORMAT_QR_CODE
        }

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private var cameraProvider: ProcessCameraProvider? = null
    private var barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .apply {

                when (proxy.formats.size) {
                    0 -> {}
                    1 -> setBarcodeFormats(proxy.formats.first().toAndroidFormat())
                    else -> {
                        val formatsList = proxy.formats.toList()
                        val moreFormats =
                            formatsList.drop(1).map { it.toAndroidFormat() }.toIntArray()
                        setBarcodeFormats(formatsList.first().toAndroidFormat(), *moreFormats)
                    }
                }
            }.build()
    )

    init {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder()
                    .build()
                preview.setSurfaceProvider(binding.surfaceProvider)


                val analysisUseCase = ImageAnalysis.Builder().build()
                analysisUseCase.setAnalyzer(
                    cameraExecutor,
                    ImageAnalysis.Analyzer { imageProxy ->
                        if (proxy.scanning) {
                            imageProxy.image?.let { image ->
                                val inputImage =
                                    InputImage.fromMediaImage(
                                        image,
                                        imageProxy.imageInfo.rotationDegrees
                                    )


                                barcodeScanner.process(inputImage)
                                    .addOnSuccessListener { barcodes ->
                                        barcodes.firstOrNull()?.rawValue?.let { code ->
                                            proxy.onScanCode(code)
                                        }
                                    }
                                    .addOnFailureListener {
                                        SKLog.e(it, it.message ?: "error")
                                    }.addOnCompleteListener {
                                        // When the image is from CameraX analysis use case, must call image.close() on received
                                        // images when finished using them. Otherwise, new images may not be received or the camera
                                        // may stall.
                                        imageProxy.close()
                                    }
                            }
                        }

                    }
                )
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    fragment?.viewLifecycleOwner ?: activity,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    analysisUseCase
                )

            } catch (eex: ExecutionException) {
                SKLog.e(eex, "problem with camera")
            } catch (intex: InterruptedException) {
                SKLog.e(intex, "problem with camera")
            }

        }, ContextCompat.getMainExecutor(context))


    }


}