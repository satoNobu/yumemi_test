package jp.co.yumemi.android.code_check.one_screen

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import jp.co.yumemi.android.code_check.model.entity.Item
import jp.co.yumemi.android.code_check.model.repository.db.OneViewModel
import jp.co.yumemi.android.code_check.one_screen.recycler_view.CustomAdapter

class OnePresenter(private val view: OneContract.View, private val model: OneViewModel): OneContract.Presenter {
    override fun serAdapter(): CustomAdapter{
        return CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: Item) {
                view.gotoRepositoryFragment(item = item)
            }
        })
    }

    override fun getEditorActionResult(editText: TextView, action: Int): Boolean {
        if (action == EditorInfo.IME_ACTION_SEARCH) {
            editText.text.toString().let {
                model.searchResults(it).apply {
                    view.submitList(this)
                }
            }
            return true
        }
        return false
    }

}