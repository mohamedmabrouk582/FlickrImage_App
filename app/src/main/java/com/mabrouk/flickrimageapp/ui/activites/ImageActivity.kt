package com.mabrouk.flickrimageapp.ui.activites

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.mabrouk.flickrimageapp.R
import com.mabrouk.flickrimageapp.app.MyApp
import com.mabrouk.flickrimageapp.callBacks.ImageListCallBack
import com.mabrouk.flickrimageapp.data.models.ImageModel
import com.mabrouk.flickrimageapp.databinding.ImageListLayoutBinding
import com.mabrouk.flickrimageapp.ui.adapters.ImagesPageAdapter
import com.mabrouk.flickrimageapp.ui.base.BaseActivity
import com.mabrouk.flickrimageapp.utils.SharedManager
import com.mabrouk.flickrimageapp.utils.TEST
import com.mabrouk.flickrimageapp.utils.network.RequestCoroutines
import com.mabrouk.flickrimageapp.viewModels.ImageListViewModel
import com.mabrouk.flickrimageapp.viewModels.base.BaseViewModel
import com.mabrouk.flickrimageapp.viewModels.base.BaseViewModelFactory
import com.novoda.merlin.Merlin
import kotlinx.android.synthetic.main.image_list_layout.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent


/*
* Created By mabrouk on 28/08/19
* FlickrImage App
*/

class ImageActivity : BaseActivity() , ImageListCallBack, ImagesPageAdapter.ImagesCallBack ,RequestCoroutines ,
    KoinComponent {
    lateinit var layoutBinding: ImageListLayoutBinding
    val factory:BaseViewModelFactory by inject<BaseViewModelFactory>()
    lateinit var viewModel: ImageListViewModel<ImageListCallBack>
    val merlin:Merlin by lazy {
        Merlin.Builder().withConnectableCallbacks().build(this)
    }
    val adpter:ImagesPageAdapter by lazy {
        ImagesPageAdapter(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this, R.layout.image_list_layout)
        inject()
        initView()
    }


    override fun inject() {
    }

    override fun onResume() {
        super.onResume()
        merlin.bind()
    }

    override fun onPause() {
        super.onPause()
        merlin.unbind()
    }


    override fun initView() {
     viewModel=getViewModel(this,factory)
     viewModel.attachView(this)
     images_rcv.layoutManager= GridLayoutManager(this,calculateNoOfColumns(200f),GridLayoutManager.VERTICAL,false)
     images_rcv.adapter=adpter
     layoutBinding.imagevm=viewModel
     viewModel.loadImages()
        merlin.apply {
            registerConnectable {
               // viewModel.loadImages()
            }
        }
    }

    private inline fun <reified T : BaseViewModel<ImageListCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory): T {
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }

    override fun loadImages(data: PagedList<ImageModel>) {
      adpter.submitList(data)
    }

    override fun onclickImage(item: ImageModel) {

    }

    override fun getImageActivity(): ImageActivity = this


    fun calculateNoOfColumns(columnWidthDp: Float): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        val searchView:SearchView=menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(viewModel.searchListner)
        return true
    }


}