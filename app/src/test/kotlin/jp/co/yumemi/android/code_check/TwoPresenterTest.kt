package jp.co.yumemi.android.code_check

import com.nhaarman.mockitokotlin2.mock
import jp.co.yumemi.android.code_check.model.entity.Item
import jp.co.yumemi.android.code_check.two_screen.TwoContract
import jp.co.yumemi.android.code_check.two_screen.TwoPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class TwoPresenterTest {
    private lateinit var target: TwoPresenter
    private val view = mock<TwoContract.View>()

    @Before
    fun setUp() {
        target = TwoPresenter(view)
    }

    @Test
    fun setDataTest() {
        val testItem = Item(
            name = "full_name",
            ownerIconUrl = "avatar_url" ,
            language = "language",
            stargazersCount = 1,
            watchersCount = 2,
            forksCount = 3,
            openIssuesCount = 4
        )
        target.setData(item = testItem)
        verify(view).setValueOnScreen(testItem)
    }
}