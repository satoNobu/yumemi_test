/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        val item = args.item

        val binding = FragmentTwoBinding.bind(view)
        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text = String.format(getString(R.string.written_language), item.language)
        binding.starsView.text = String.format(getString(R.string.stargazers_count), item.stargazersCount)
        binding.watchersView.text = String.format(getString(R.string.watchers_count), item.watchersCount)
        binding.forksView.text = String.format(getString(R.string.forks_count), item.forksCount)
        binding.openIssuesView.text = String.format(getString(R.string.open_issues_count), item.openIssuesCount)
    }
}
