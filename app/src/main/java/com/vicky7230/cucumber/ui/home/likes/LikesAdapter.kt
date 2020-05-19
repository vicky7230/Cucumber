package com.vicky7230.cucumber.ui.home.likes

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.vicky7230.cucumber.R
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.home.RecipeDiffUtilCallback
import com.vicky7230.cucumber.utils.GlideApp
import kotlinx.android.synthetic.main.recipe_list_item_saved.view.*

/**
 * Created by vicky on 17/2/18.
 */
class LikesAdapter(private val likeList: MutableList<Recipe>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Callback {
        fun onRemoveRecipeClick(recipe: Recipe)

        fun onShareClick(sourceUrl: String)

        fun onIngredientsClick(recipeId: String)

        fun onSingleClick(sourceUrl: String)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun addItems(recipeList: MutableList<Recipe>?) {

        val newLikeList = ArrayList<Recipe>()
        newLikeList.addAll(recipeList!!)
        val diffResult =
            DiffUtil.calculateDiff(
                RecipeDiffUtilCallback(
                    this.likeList,
                    newLikeList
                )
            )
        this.likeList?.clear()
        this.likeList?.addAll(recipeList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    public fun getItem(position: Int): Recipe? {
        return if (position != RecyclerView.NO_POSITION)
            likeList?.get(position)
        else
            null
    }

    override fun getItemCount(): Int {
        return likeList?.size ?: 0
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LikeViewHolder).onBind(likeList?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createRecipeViewHolder(parent)
    }

    private fun createRecipeViewHolder(parent: ViewGroup?): LikeViewHolder {
        val likeViewHolder = LikeViewHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.recipe_list_item_saved,
                parent,
                false
            )
        )

        likeViewHolder.itemView.like_image_card.setOnClickListener({
            val recipe = getItem(likeViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onSingleClick(recipe.sourceUrl)
            }
        })

        likeViewHolder.itemView.delete_button.setOnClickListener({
            val recipe = getItem(likeViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onRemoveRecipeClick(recipe)
            }
        })

        likeViewHolder.itemView.share_button.setOnClickListener({
            val recipe = getItem(likeViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onShareClick(recipe.sourceUrl)
            }
        })

        likeViewHolder.itemView.ingredients_button.setOnClickListener({
            val recipe = getItem(likeViewHolder.adapterPosition)
            if (recipe != null) {
                callback?.onIngredientsClick(recipe.recipeId)
            }
        })

        return likeViewHolder
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is LikeViewHolder) {
            holder.itemView.recipe_title.text = ""
            holder.itemView.recipe_image.setImageDrawable(null)
        }
    }

    class LikeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(recipe: Recipe?) {
            GlideApp
                .with(itemView.context)
                .load(recipe?.imageUrl)
                .transition(withCrossFade())
                .centerCrop()
                .into(itemView.recipe_image)
            itemView.recipe_title.text = recipe?.title
        }
    }
}