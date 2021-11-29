package jp.co.yumemi.android.code_check.two_screen

import jp.co.yumemi.android.code_check.model.entity.Item

interface TwoContract {
    interface View {
        fun setValueOnScreen(item: Item)
    }
    interface Presenter {
        fun setData(item: Item)
    }
}