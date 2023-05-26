package com.ej.defaultcamera_gallary_test

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.ej.defaultcamera_gallary_test.databinding.FragmentFullScreenImageBinding


class FullScreenImageFragment : Fragment() {
    lateinit var binding : FragmentFullScreenImageBinding

    private var uploadImage: Bitmap? = null

    private lateinit var matrix: Matrix
    private lateinit var savedMatrix: Matrix
    private lateinit var startPoint: PointF

    private val NONE = 0
    private val DRAG = 1
    private val ZOOM = 2

    private var mode = NONE
    private var lastScaleFactor = 0f
    private var scaleFactor = 1f

    private lateinit var scaleGestureDetector: ScaleGestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uploadImage = it.getParcelable<Bitmap>(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_full_screen_image,container,false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uploadImage.setImageBitmap(uploadImage)


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