package com.candybytes.taco.ui.fragments.food

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentFoodDetailBinding
import com.candybytes.taco.ui.extensions.*
import com.candybytes.taco.ui.fragments.BaseFragment
import com.candybytes.taco.ui.fragments.food.adapter.FoodDetailAdapter
import com.candybytes.taco.ui.vm.MainViewModel
import com.candybytes.taco.ui.vm.food.FoodDetailViewModel
import com.candybytes.taco.vo.Nutrient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FoodDetailFragment : BaseFragment<FragmentFoodDetailBinding>() {

    private val args by navArgs<FoodDetailFragmentArgs>()
    private val viewModel: FoodDetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val pickPictureIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data ?: return@registerForActivityResult
        binding?.imageFoodDetailPlaceholder?.setImageBitmap(context?.getBitmapBy(uri))
    }

    private val foodListObserver: Observer<List<Pair<String, Nutrient>>> by lazy {
        Observer<List<Pair<String, Nutrient>>> { nutrients ->
            Timber.i("Nutrients: $nutrients")
            binding?.recyclerFoodDetail?.apply {
                adapter = FoodDetailAdapter(nutrients)
                applyDivider(requireContext())
            }
        }
    }
    private val imageClickedObserver: Observer<Boolean> by lazy {
        Observer<Boolean> { clicked ->
            Timber.i("Image clicked: $clicked")
            if(clicked) checkPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                onPermissionGranted = {
                    val intent = Intent(Intent.ACTION_PICK).apply {
                        type = "image/*"
                    }
                    val chooserPickImage = Intent.createChooser(intent, null)

                    pickPictureIntent.launch(chooserPickImage)
                },
                onPermissionDenied = {
                    if (it.isPermanentlyDenied) showDialogCancel(
                        R.string.permissions_gallery_title,
                        R.string.permissions_gallery_desc,
                        R.string.permissions_general_ok,
                        R.string.permissions_general_cancel,
                    ) { openSettings() }
                },
                onPermissionRationaleShouldBeShown = { _, permissionToken ->
                    permissionToken.continuePermissionRequest()
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentFoodDetailBinding.inflate(layoutInflater, container, false).apply {
        viewModel = this@FoodDetailFragment.viewModel
        lifecycleOwner = this@FoodDetailFragment
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backgroundFoodDetailBrown?.setOnClickListener {
            viewModel.chooseImageClicked.postValue(true)
        }

        mainViewModel.showBottomBar.postValue(false)

        viewModel.getFoodBy(args.foodId).reObserve(this, foodListObserver)
        viewModel.chooseImageClicked.reObserve(this, imageClickedObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.showBottomBar.postValue(true)
    }

}