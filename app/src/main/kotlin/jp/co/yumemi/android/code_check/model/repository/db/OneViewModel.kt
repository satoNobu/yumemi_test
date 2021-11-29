/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.model.repository.db

import android.util.Log
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.model.entity.Item
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.lang.Exception
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel : ViewModel() {

    // 検索結果
    fun searchResults(inputText: String): List<Item> = runBlocking {
        val client = HttpClient(Android)

        val items = mutableListOf<Item>()
        return@runBlocking GlobalScope.async {
            try {
                val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }

                val jsonBody = JSONObject(response.receive<String>())

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
            } catch (e: Exception) {
                Log.i("ERROR: ", e.toString())
                return@async items.toList()
            }
        }.await()
    }
}