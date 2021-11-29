package jp.co.yumemi.android.code_check.two_screen

import jp.co.yumemi.android.code_check.model.entity.Item

class TwoPresenter(private val view: TwoContract.View): TwoContract.Presenter {
    override fun setData(item: Item) {
        view.setValueOnScreen(item)
    }
}