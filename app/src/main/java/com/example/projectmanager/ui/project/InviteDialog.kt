package com.example.projectmanager.ui.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.example.projectmanager.R
import com.example.projectmanager.util.QRGenerator
import kotlinx.android.synthetic.main.fragment_invite_dialog.*

class InviteDialog : DialogFragment() {

    private lateinit var qr: QRGenerator.QRObject

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        qr = arguments!!.getSerializable(QR_TAG) as QRGenerator.QRObject

        return inflater.inflate(R.layout.fragment_invite_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image_view.setImageBitmap(QRGenerator.generate(qr))
        invite_code_text_view.text = qr.content
    }

    companion object {

        val QR_TAG = "QR"

        /**
         * Create a new instance of CustomDialogFragment, providing "num" as an
         * argument.
         */
        fun newInstance(qr: QRGenerator.QRObject): InviteDialog {
            val fragment =
                InviteDialog()

            val args = Bundle()
            args.putSerializable(QR_TAG, qr)
            fragment.arguments = args

            return fragment
        }
    }
}
