package com.vicky7230.cucumber.ui.home.recipes

import android.animation.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.DecelerateInterpolator
import com.vicky7230.cucumber.R
import kotlinx.android.synthetic.main.recipe_list_item.view.*


/**
 * Created by vicky on 15/2/18.
 */
class RecipesItemAnimator : DefaultItemAnimator() {
    private val DECELERATE_INTERPOLATOR = DecelerateInterpolator()
    private val ANTICIPATE_OVERSHOOT_INTERPOLATOR = AnticipateOvershootInterpolator(1.0f)

    override fun canReuseUpdatedViewHolder(@NonNull viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    @NonNull
    override fun recordPreLayoutInformation(
            @NonNull state: RecyclerView.State, @NonNull viewHolder: RecyclerView.ViewHolder,
            changeFlags: Int, @NonNull payloads: List<Any>
    ): RecyclerView.ItemAnimator.ItemHolderInfo {
        if (changeFlags == RecyclerView.ItemAnimator.FLAG_CHANGED) {
            for (payload in payloads) {
                if (payload is String) {
                    return RecipesItemHolderInfo(payload)
                }
            }
        }

        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    override fun animateChange(
            @NonNull oldHolder: RecyclerView.ViewHolder,
            @NonNull newHolder: RecyclerView.ViewHolder,
            @NonNull preInfo: RecyclerView.ItemAnimator.ItemHolderInfo,
            @NonNull postInfo: RecyclerView.ItemAnimator.ItemHolderInfo
    ): Boolean {

        if (preInfo is RecipesItemHolderInfo) {
            val holder = newHolder as RecipesAdapter.RecipeViewHolder

            animateHeartButton(holder)
            if (RecipesAdapter.ACTION_LIKE_IMAGE_DOUBLE_CLICKED == preInfo.updateAction) {
                animatePhotoLike(holder)
            }
        }

        return false
    }


    private fun animateHeartButton(holder: RecipesAdapter.RecipeViewHolder) {
        val animatorSet = AnimatorSet()

        val rotation = ObjectAnimator.ofFloat(holder.itemView.like_button, "rotation", 0.0f, 360.0f)
        rotation.duration = 800
        rotation.interpolator = ANTICIPATE_OVERSHOOT_INTERPOLATOR
        val scaleX = ObjectAnimator.ofFloat(holder.itemView.like_button, "scaleX", 1.0f, 1.5f, 1.0f)
        scaleX.duration = 800
        scaleX.interpolator = ANTICIPATE_OVERSHOOT_INTERPOLATOR
        val scaleY = ObjectAnimator.ofFloat(holder.itemView.like_button, "scaleY", 1.0f, 1.5f, 1.0f)
        scaleY.duration = 800
        scaleY.interpolator = ANTICIPATE_OVERSHOOT_INTERPOLATOR

        rotation.addUpdateListener { valueAnimator ->
            if (valueAnimator.currentPlayTime >= 500) {
                holder.itemView.like_button.setImageResource(R.drawable.ic_favorite_higlighted_24dp)
            }
        }

        animatorSet.playTogether(rotation, scaleX, scaleY)

        animatorSet.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animator: Animator) {
                dispatchAnimationFinished(holder)
            }

        })

        animatorSet.start()

    }

    private fun animatePhotoLike(holder: RecipesAdapter.RecipeViewHolder) {
        holder.itemView.image_view_like.visibility = View.VISIBLE

        holder.itemView.image_view_like.scaleY = 0.0f
        holder.itemView.image_view_like.scaleX = 0.0f

        val animatorSet = AnimatorSet()

        val scaleLikeIcon = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView.image_view_like,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 2.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 2.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f, 0.0f)
        )
        scaleLikeIcon.interpolator = DECELERATE_INTERPOLATOR
        scaleLikeIcon.duration = 1000

        val scaleLikeBackground = ObjectAnimator.ofPropertyValuesHolder(
            holder.itemView.recipe_image_card,
            PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.95f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.95f, 1.0f)
        )
        scaleLikeBackground.interpolator = DECELERATE_INTERPOLATOR
        scaleLikeBackground.duration = 600

        animatorSet.playTogether(scaleLikeIcon, scaleLikeBackground)


        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                resetLikeAnimationState(holder)
                dispatchAnimationFinished(holder)
            }
        })
        animatorSet.start()
    }

    private fun resetLikeAnimationState(holder: RecipesAdapter.RecipeViewHolder) {
        holder.itemView.image_view_like.visibility = View.INVISIBLE
    }

    class RecipesItemHolderInfo(var updateAction: String) :
        RecyclerView.ItemAnimator.ItemHolderInfo()
}