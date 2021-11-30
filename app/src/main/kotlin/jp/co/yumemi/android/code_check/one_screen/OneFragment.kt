/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.one_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.model.entity.Item
import jp.co.yumemi.android.code_check.model.repository.db.OneViewModel
import jp.co.yumemi.android.code_check.one_screen.recycler_view.CustomAdapter

class OneFragment: Fragment(R.layout.fragment_one), OneContract.View {

    private val presenter by lazy {
        OnePresenter(this, OneViewModel())
    }

    lateinit var _adapter: CustomAdapter
    private var binding: FragmentOneBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOneBinding.bind(view)

        _adapter = presenter.serAdapter()

        _binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            presenter.getEditorActionResult(editText, action)
        }

        val _layoutManager = LinearLayoutManager(this.context)
        val _dividerItemDecoration = DividerItemDecoration(this.context, _layoutManager.orientation)
        _binding.recyclerView.also {
            it.layoutManager = _layoutManager
            it.addItemDecoration(_dividerItemDecoration)
            it.adapter = _adapter
        }
    }

    override fun submitList(list: List<Item>) {
        _adapter.submitList(list)
    }
    override fun gotoRepositoryFragment(item: Item) {
        val _action = OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(_action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
