package com.vicky7230.cucumber.ui.home.likes

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import com.vicky7230.cucumber.R
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.base.BaseFragment
import com.vicky7230.cucumber.ui.home.ItemOffsetDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_likes.*
import kotlinx.android.synthetic.main.ingredients_dialog_view.view.*
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent


class LikesFragment : BaseFragment(), LikesMvpView, LikesAdapter.Callback {
    @Inject
    lateinit var presenter: LikesMvpPresenter<LikesMvpView>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var itemOffsetDecoration: ItemOffsetDecoration
    @Inject
    lateinit var likesAdapter: LikesAdapter
    @Inject
    lateinit var customTabsIntent: CustomTabsIntent

    companion object {

        fun newInstance() = LikesFragment()
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
        val view = inflater.inflate(R.layout.fragment_likes, container, false)
        presenter.onAttach(this)
        likesAdapter.setCallback(this)
        return view
    }

    override fun setUp(view: View) {

        likesList.layoutManager = linearLayoutManager
        likesList.addItemDecoration(itemOffsetDecoration)
        likesList.adapter = likesAdapter

        presenter.getRecipes()
    }

    override fun updateLikeList(recipes: MutableList<Recipe>?) {
        likesAdapter.addItems(recipes)
    }

    override fun onRemoveRecipeClick(recipe: Recipe) {
        presenter.removeRecipe(recipe)
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
        customTabsIntent.launchUrl(activity, Uri.parse(sourceUrl))
    }

    override fun showIngredients(ingredients: List<String>?) {
        val layoutInflater =
            activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.ingredients_dialog_view, null, false)
        view.title.setText(R.string.ingredients)
        view.ingredients_list.adapter =
                ArrayAdapter(activity, R.layout.ingredients_list_item, ingredients)

        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.window.attributes.windowAnimations = R.style.DialogTheme
        dialog.show()
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}
