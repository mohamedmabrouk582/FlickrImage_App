package com.mabrouk.flickrimageapp.viewModels.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.mabrouk.flickrimageapp.R
import com.mabrouk.flickrimageapp.callBacks.BaseCallBack
import com.mabrouk.flickrimageapp.data.models.FilckrResponse
import com.mabrouk.flickrimageapp.utils.network.CheckNetwork
import com.mabrouk.flickrimageapp.utils.network.RequestCoroutines
import com.mabrouk.flickrimageapp.utils.network.RequestListener
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

open class BaseViewModel<v : BaseCallBack>(application: Application) : AndroidViewModel(application), BaseVmodel<v> , RequestCoroutines{

    lateinit var view: v
    override fun attachView(view: v) {
        this.view = view
    }
}
