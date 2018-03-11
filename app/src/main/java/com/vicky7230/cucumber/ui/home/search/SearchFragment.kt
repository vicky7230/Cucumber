package com.vicky7230.cucumber.ui.home.search

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vicky7230.cucumber.R
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.recipes_list_view_footer.view.*
import javax.inject.Inject


class SearchFragment : BaseFragment(), SearchMvpView, SearchAdapter.Callback {
    @Inject
    lateinit var presenter: SearchMvpPresenter<SearchMvpView>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    @Inject
    lateinit var searchAdapter: SearchAdapter
    @Inject
    lateinit var customTabsIntent: CustomTabsIntent

    var isLoading = false

    companion object {

        fun newInstance() = SearchFragment()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        presenter.onAttach(this)
        searchAdapter.setCallback(this)
        return view
    }

    override fun setUp(view: View) {
        searchList.layoutManager = linearLayoutManager
        searchList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        searchList.adapter = searchAdapter

        clear_search.setOnClickListener({ _ ->
            search_edit_text.text.clear()
            searchAdapter.refreshItems(ArrayList())
        })

        searchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
                    searchAdapter.addItem(
                        Recipe(
                            type = "LOADING"
                        )
                    )
                    presenter.getNextPage()
                    isLoading = true
                }
            }
        })

        val subject = PublishSubject.create<String>()

        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                subject.onNext(s.toString())
                if (s.toString().isEmpty())
                    searchAdapter.refreshItems(ArrayList())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        presenter.search(subject)
    }

    override fun refreshSearchList(recipes: MutableList<Recipe>) {
        searchAdapter.refreshItems(recipes)
        isLoading = false
    }

    override fun updateRecipeList(recipes: MutableList<Recipe>) {
        if (searchAdapter.itemCount > 0)
            searchAdapter.removeItem()
        searchAdapter.addItems(recipes)
        if (recipes.size > 0)
            isLoading = false
    }

    override fun onLikeRecipeClick(position: Int) {
        presenter.saveRecipe(searchAdapter.getItem(position))
    }

    override fun onRetryClick() {
        presenter.getNextPage()
    }

    override fun onSingleClick(sourceUrl: String) {
        customTabsIntent.launchUrl(activity, Uri.parse(sourceUrl))
    }

    override fun showErrorInRecyclerView() {
        val loadingMoreViewHolder =
            searchList.findViewHolderForAdapterPosition(searchAdapter.itemCount - 1) as SearchAdapter.LoadingItemViewHolder?
        if (loadingMoreViewHolder != null) {
            loadingMoreViewHolder.itemView.loading.visibility = View.GONE
            loadingMoreViewHolder.itemView.retry_button.visibility = View.VISIBLE
        }
    }
}
