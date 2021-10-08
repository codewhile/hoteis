package com.viniciusmello.fragments.commom

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AboutDialogFragment :
    DialogFragment(), DialogInterface.OnClickListener {

    override fun onClick(dialog: DialogInterface?, which: Int) {

        if (which == Dialog.BUTTON_NEGATIVE)
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.jw.org")))

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setTitle("Acessar site Jw.Org")
            .setMessage("Aqui você vai encontrar os melhores sites da regiao")
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton("Acessar Jw.Org", this)
            .create();

    }

}