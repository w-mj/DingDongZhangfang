package com.dingdonginc.zhangfang.services

import androidx.fragment.app.DialogFragment

class DialogDismissService {
    private val dialogs = HashMap<String, DialogFragment>()
    fun <T: DialogFragment> register(dialog: T) {
        dialogs[dialog::class.java.simpleName] = dialog
    }

    fun <T: DialogFragment> dismiss(cls: Class<T>) {
        dialogs.remove(cls.simpleName)?.dismiss()
    }

    fun <T: DialogFragment> unregister(dialog: T) {
        dialogs.remove(dialog::class.java.simpleName, dialog)
    }}