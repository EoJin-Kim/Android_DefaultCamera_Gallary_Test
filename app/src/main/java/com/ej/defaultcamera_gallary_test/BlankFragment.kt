package com.ej.defaultcamera_gallary_test

import android.R.attr
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ej.defaultcamera_gallary_test.databinding.FragmentBlankBinding


class BlankFragment : Fragment() {
    lateinit var binding : FragmentBlankBinding

    private val REQUEST_IMAGE_CODE = 101
    private val REQUEST_GALLERY = 102
    private var image : Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_blank,container,false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gallery.setOnClickListener {
            getImageFromGallery()
        }

        binding.camera.setOnClickListener {
            takePicture()
        }
        binding.imageView.setOnClickListener {
            val transition = parentFragmentManager.beginTransaction()
            val fragment = BlankFragment.newInstance()
            transition.replace(R.id.fragmentContainerView, fragment)
            transition.commit()
        }
    }
    fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,REQUEST_GALLERY)
    }
    //사진찍기
    fun takePicture() {
        val imageTakeIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (imageTakeIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE)
        }
    }

    //결과값 가져오기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CODE -> {
                    val extras: Bundle = data!!.extras!!
                    val imageBitmap = extras["data"] as Bitmap?
                    binding.imageView.setImageBitmap(imageBitmap)
                    image = imageBitmap
                }
                REQUEST_GALLERY ->{
                        var currentImageUri = data?.data
                        try{
                            currentImageUri?.let {
                                if(Build.VERSION.SDK_INT < 28) {
                                    val bitmap = MediaStore.Images.Media.getBitmap(
                                        requireActivity().contentResolver,
                                        currentImageUri
                                    )
                                    binding.imageView.setImageBitmap(bitmap)
                                    image = bitmap
                                } else {
                                    val source = ImageDecoder.createSource(requireActivity().contentResolver, currentImageUri)
                                    val bitmap = ImageDecoder.decodeBitmap(source)
                                    binding.imageView.setImageBitmap(bitmap)
                                    image = bitmap
                                }
                            }
                        }catch( e : Exception) {
                            e.printStackTrace()
                        }

                }

            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(requireActivity(), "사진 선택 취소", Toast.LENGTH_LONG).show();
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BlankFragment()
    }
}