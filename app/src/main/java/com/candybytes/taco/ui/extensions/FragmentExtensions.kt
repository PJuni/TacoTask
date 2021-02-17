package com.candybytes.taco.ui.extensions

import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment


fun Fragment.showDialogCancel(
    @StringRes titleResId: Int,
    @StringRes messageResId: Int,
    @StringRes textPositiveResId: Int,
    @StringRes textCancelResId: Int,
    onCancel: () -> Unit = {},
    onPositive: () -> Unit
): AlertDialog = AlertDialog.Builder(requireContext())
    .setTitle(titleResId)
    .setPositiveButton(textPositiveResId) { _, _ -> onPositive.invoke() }
    .setNegativeButton(textCancelResId) { dialog, _ ->
        onCancel.invoke()
        dialog.dismiss()
    }
    .setCancelable(false)
    .setMessage(messageResId)
    .show()