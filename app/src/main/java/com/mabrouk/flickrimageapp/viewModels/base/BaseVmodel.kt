package com.mabrouk.flickrimageapp.viewModels.base

import com.mabrouk.flickrimageapp.callBacks.BaseCallBack


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface BaseVmodel<v : BaseCallBack> {
    fun attachView(view: v)
}
