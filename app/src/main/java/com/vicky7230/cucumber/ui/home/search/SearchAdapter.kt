package com.vicky7230.cucumber.ui.home.search

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.vicky7230.cucumber.R
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.home.RecipeDiffUtilCallback
import com.vicky7230.cucumber.utils.GlideApp
import com.vicky7230.cucumber.utils.NetworkUtils
import kotlinx.android.synthetic.main.recipes_list_view_footer.view.*
import kotlinx.android.synthetic.main.search_list_item.view.*

/**
 * Created by vicky on 17/2/18.
 */
class SearchAdapter(private val recipeList: MutableList<Recipe>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_LOADING = -1
    private val TYPE_RECIPE = 1

    interface Callback {
        fun onLikeRecipeClick(position: Int)

        fun onRetryClick()

        fun onSingleClick(sourceUrl: String)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun getItem(position: Int): Recipe? {
        return if (position != RecyclerView.NO_POSITION)
            recipeList?.get(position)
        else
            null
    }

    fun addItem(recipe: Recipe?) {
        recipeList?.add(recipe!!)
        notifyItemInserted(recipeList!!.size - 1)
    }

    fun removeItem() {
        recipeList?.removeAt(recipeList.size - 1)
        notifyItemRemoved(recipeList!!.size)
    }

    fun refreshItems(recipeList: MutableList<Recipe>?) {

        val newRecipeList = ArrayList<Recipe>()
        newRecipeList.addAll(recipeList!!)

        val diffResult =
            DiffUtil.calculateDiff(
                RecipeDiffUtilCallback(
                    this.recipeList,
                    newRecipeList
                )
            )
        this.recipeList?.clear()
        this.recipeList?.addAll(recipeList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItems(recipeList: MutableList<Recipe>?) {

        val newRecipeList = ArrayList<Recipe>()
        newRecipeList.addAll(this.recipeList!!)
        newRecipeList.addAll(recipeList!!)

        val diffResult =
            DiffUtil.calculateDiff(
                RecipeDiffUtilCallback(
                    this.recipeList,
                    newRecipeList
                )
            )
        this.recipeList.addAll(recipeList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return if (recipeList?.get(position)?.type == "LOADING") TYPE_LOADING else TYPE_RECIPE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_RECIPE ->
                createSearchItemViewHolder(parent)
            else ->
                createLoadingItemViewHolder(parent)
        }
    }

    private fun createSearchItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.search_list_item,
                parent,
                false
            )
        )

        searchItemViewHolder.itemView.search_item.setOnClickListener({
            val recipe = getItem(searchItemViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onSingleClick(recipe.sourceUrl)
            }
        })

        searchItemViewHolder.itemView.search_like_button.setOnClickListener({
            val recipe = getItem(searchItemViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onLikeRecipeClick(searchItemViewHolder.adapterPosition)
                recipe.liked = true
                notifyItemChanged(searchItemViewHolder.adapterPosition)
            }
        })

        return searchItemViewHolder
    }

    private fun createLoadingItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val loadingMoreViewHolder = LoadingItemViewHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.recipes_list_view_footer,
                parent,
                false
            )
        )

        loadingMoreViewHolder.itemView.retry_button.setOnClickListener({
            loadingMoreViewHolder.itemView.loading.visibility = View.VISIBLE
            loadingMoreViewHolder.itemView.retry_button.visibility = View.GONE
            callback?.onRetryClick()
        })

        return loadingMoreViewHolder
    }

    override fun getItemCount(): Int {
        return recipeList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_RECIPE -> (holder as SearchItemViewHolder).onBind(recipeList?.get(position))
            TYPE_LOADING -> (holder as LoadingItemViewHolder).onBind()
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is LoadingItemViewHolder) {
            if (!NetworkUtils.isNetworkConnected(holder.itemView.context)) {
                holder.itemView.loading.visibility = View.GONE
                holder.itemView.retry_button.visibility = View.VISIBLE
            }
        }
    }

    class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(recipe: Recipe?) {
            GlideApp
                .with(itemView.context)
                .load(recipe?.imageUrl)
                .transition(withCrossFade())
                .centerCrop()
                .into(itemView.recipe_image)
            itemView.recipe_title.text = recipe?.title
            if (recipe?.liked!!)
                itemView.search_like_button.setImageResource(R.drawable.ic_favorite_higlighted_24dp)
            else
                itemView.search_like_button.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }
    }

    class LoadingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind() {
        }
    }
}