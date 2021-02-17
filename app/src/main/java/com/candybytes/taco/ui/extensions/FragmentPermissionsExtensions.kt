package com.candybytes.taco.ui.extensions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

val doNothing = Unit

fun Fragment.checkPermissions(
    permission: String,
    onPermissionGranted: (PermissionGrantedResponse) -> Unit = {},
    onPermissionDenied: (PermissionDeniedResponse) -> Unit = {},
    onPermissionRationaleShouldBeShown: (PermissionRequest, PermissionToken) -> Unit = { _, _ -> },
) = Dexter.withContext(requireContext())
    .withPermission(permission)
    .withListener(object : PermissionListener {
        override fun onPermissionGranted(response: PermissionGrantedResponse) {
            onPermissionGranted.invoke(response)
        }

        override fun onPermissionDenied(response: PermissionDeniedResponse) {
            onPermissionDenied.invoke(response)
        }

        override fun onPermissionRationaleShouldBeShown(
            permission: PermissionRequest,
            token: PermissionToken
        ) {
            onPermissionRationaleShouldBeShown.invoke(permission, token)
        }
    }).check()

fun Fragment.openSettings() {
    val openSettingsIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { doNothing }
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", requireContext().packageName, null)
    }
    openSettingsIntent.launch(intent)
}