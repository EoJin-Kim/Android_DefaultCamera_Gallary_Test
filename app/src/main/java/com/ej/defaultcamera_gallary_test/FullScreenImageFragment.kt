package com.ej.defaultcamera_gallary_test

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class FullScreenImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable<Bitmap>(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen_image, container, false)
    }

    companion object {
        val ARG_PARAM1 = "ARG_PARAM1"
        @JvmStatic
        fun newInstance(image: Bitmap) =
            FullScreenImageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, image)
                }
            }
    }
}