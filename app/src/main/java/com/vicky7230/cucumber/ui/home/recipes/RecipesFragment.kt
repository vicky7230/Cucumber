package com.vicky7230.cucumber.ui.home.recipes

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vicky7230.cucumber.R
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.base.BaseFragment
import com.vicky7230.cucumber.ui.home.ItemOffsetDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.android.synthetic.main.ingredients_dialog_view.view.*
import kotlinx.android.synthetic.main.recipes_list_view_footer.view.*
import javax.inject.Inject


class RecipesFragment : BaseFragment(), RecipesMvpView, RecipesAdapter.Callback {
    @Inject
    lateinit var presenter: RecipesMvpPresenter<RecipesMvpView>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var itemOffsetDecoration: ItemOffsetDecoration

    @Inject
    lateinit var recipesItemAnimator: RecipesItemAnimator

    @Inject
    lateinit var recipesAdapter: RecipesAdapter

    @Inject
    lateinit var customTabsIntent: CustomTabsIntent
    var isLoading = false

    companion object {

        fun newInstance() = RecipesFragment()

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
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        presenter.onAttach(this)
        recipesAdapter.setCallback(this)
        return view
    }

    override fun setUp(view: View) {
        //linearLayoutManager.isItemPrefetchEnabled = false
        recipeList.layoutManager = linearLayoutManager
        recipeList.addItemDecoration(itemOffsetDecoration)
        //(recipesItemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recipeList.itemAnimator = recipesItemAnimator
        recipeList.adapter = recipesAdapter

        recipeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
                    recipesAdapter.addItem(
                            Recipe(
                                    type = "LOADING"
                            )
                    )
                    presenter.getRecipes()
                    isLoading = true
                }
            }
        })
        showLoading()
        presenter.getRecipes()
    }

    override fun updateRecipeList(recipes: MutableList<Recipe>) {
        if (recipesAdapter.itemCount > 0)
            recipesAdapter.removeItem()
        recipesAdapter.addItems(recipes)
        isLoading = false
    }

    override fun onLikeRecipeClick(position: Int) {
        presenter.saveRecipe(recipesAdapter.getItem(position))
    }

    override fun onRetryClick() {
        presenter.getRecipes()
    }

    override fun onShareClick(sourceUrl: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, sourceUrl)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Share using..."))
    }

    override fun onIngredientsClick(recipeId: String) {
        presenter.getRecipe(recipeId)
    }

    override fun onSingleClick(sourceUrl: String) {
        customTabsIntent.launchUrl(context!!, Uri.parse(sourceUrl))
    }

    override fun showIngredients(ingredients: List<String>?) {
        val layoutInflater =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.ingredients_dialog_view, null, false)
        view.title.setText(R.string.ingredients)
        view.ingredients_list.adapter =
                ArrayAdapter(context!!, R.layout.ingredients_list_item, ingredients!!)

        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.window?.attributes?.windowAnimations = R.style.DialogTheme
        dialog.show()
    }

    override fun showErrorInRecyclerView() {
        val loadingMoreViewHolder =
                recipeList.findViewHolderForAdapterPosition(recipesAdapter.itemCount - 1) as RecipesAdapter.LoadingMoreViewHolder?
        if (loadingMoreViewHolder != null) {
            loadingMoreViewHolder.itemView.loading.visibility = View.GONE
            loadingMoreViewHolder.itemView.retry_button.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}
