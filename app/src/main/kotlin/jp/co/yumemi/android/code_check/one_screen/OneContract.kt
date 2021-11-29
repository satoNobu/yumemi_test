package jp.co.yumemi.android.code_check.one_screen

import android.widget.TextView
import jp.co.yumemi.android.code_check.model.entity.Item
import jp.co.yumemi.android.code_check.one_screen.recycler_view.CustomAdapter

interface OneContract {
    interface View {
        fun gotoRepositoryFragment(item: Item)
        fun submitList(list: List<Item>)
    }
    interface Presenter {
        fun serAdapter(): CustomAdapter
        fun getEditorActionResult(editText:TextView, action:Int): Boolean
    }
}