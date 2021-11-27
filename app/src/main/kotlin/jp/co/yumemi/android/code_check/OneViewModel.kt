/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel : ViewModel() {

    // 検索結果
    fun searchResults(inputText: String): List<Item> = runBlocking {
        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {
            val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }

            val jsonBody = JSONObject(response.receive<String>())

            val items = mutableListOf<Item>()
            // アイテムの個数分ループする
            jsonBody.optJSONArray("items")?.let {
                for (i in 0 until it.length()) {
                    it.optJSONObject(i)?.let { jsonItem ->
                        items.add(
                            Item(
                                name = jsonItem.optString("full_name"),
                                ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: "",
                                language = jsonItem.optString("language"),
                                stargazersCount = jsonItem.optLong("stargazers_count"),
                                watchersCount = jsonItem.optLong("watchers_count"),
                                forksCount = jsonItem.optLong("forks_conut"),
                                openIssuesCount = jsonItem.optLong("open_issues_count")
                            )
                        )
                    }
                }
            }

            lastSearchDate = Date()

            return@async items.toList()
        }.await()
    }
}

@Parcelize
data class Item(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable