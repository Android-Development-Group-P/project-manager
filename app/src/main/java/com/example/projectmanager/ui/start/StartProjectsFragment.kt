package com.example.projectmanager.ui.createProject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.projectmanager.R
import com.example.projectmanager.ui.project_new.CreateProjectActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_start_projects.*

class StartProjectsFragment : Fragment() {

    companion object {
        val ACTIVITY_TITLE = "Projects"
    }

    private lateinit var floatingMenu: FloatingMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = ACTIVITY_TITLE
        return inflater.inflate(R.layout.fragment_start_projects, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        floatingMenu = createFloatingMenu()

        fab_main.setOnClickListener {
            if (!floatingMenu.isExpanded)
                floatingMenu.expand()
            else
                floatingMenu.dismiss()

            floatingMenu.isExpanded = !floatingMenu.isExpanded
        }

        super.onActivityCreated(savedInstanceState)
    }

    private fun createFloatingMenu() : FloatingMenu {
        val menu_item_add = FloatingMenuItem(menu_add_project,
            menu_add_project.findViewById(R.id.fab_item) as FloatingActionButton,
            menu_add_project.findViewById(R.id.text_view) as TextView,
            -resources.getDimension(R.dimen.standard_100)
        )

        menu_item_add.fab.setOnClickListener {
            val intent = Intent(activity, CreateProjectActivity::class.java)
            startActivity(intent)
            floatingMenu.dismiss()
        }

        val menu_item_join = FloatingMenuItem(menu_join_project,
            menu_join_project.findViewById(R.id.fab_item) as FloatingActionButton,
            menu_join_project.findViewById(R.id.text_view) as TextView,
            -resources.getDimension(R.dimen.standard_55)
        )

        menu_item_add.text_view!!.text = "Create new project"
        menu_item_add.fab.setImageResource(R.drawable.ic_create_white_24dp)

        menu_item_join.text_view!!.text = "Find and join project"
        menu_item_join.fab.setImageResource(R.drawable.ic_search_white_24dp)

        return FloatingMenu(fab_main, listOf(menu_item_add, menu_item_join))
    }

    class FloatingMenu (
        val mainButton: FloatingActionButton,
        val items: List<FloatingMenuItem>,
        var isExpanded: Boolean = false
    ) {
        fun expand() {
            mainButton.animate().rotation(135f)
            for (item in items)
                item.animateHorizontal(FloatingMenuItem.AnimateTo.Visible)
        }

        fun dismiss() {
            mainButton.animate().rotation(0f)
            for (item in items)
                item.animateHorizontal(FloatingMenuItem.AnimateTo.Invisible)
        }
    }

    class FloatingMenuItem (
        val parent: View?,
        val fab: FloatingActionButton,
        val text_view: TextView?,
        val distanceToMove: Float
    ) {
        init {
            fab.isVisible = true
            fab.alpha = 0f
            text_view?.visibility = View.VISIBLE
            text_view?.alpha = 0f
        }

        fun animateHorizontal(animateTo: AnimateTo) {

            val alpha = if (animateTo == AnimateTo.Visible) 1.0f else 0.0f

            when (animateTo) {
                AnimateTo.Visible -> {
                    parent?.animate()
                        ?.translationY(distanceToMove)

                    fab.animate()
                        .alpha(alpha).start()

                    text_view?.animate()
                        ?.alpha(alpha)
                        ?.duration = 1500
                }
                AnimateTo.Invisible -> {
                    text_view?.animate()
                        ?.alpha(alpha)
                        ?.duration = 500

                    parent?.animate()
                        ?.translationY(0f)
                        ?.duration = 500

                    fab.animate()
                        .alpha(alpha)
                        .duration = 500
                }
            }

        }

        enum class AnimateTo {
            Visible,
            Invisible
        }
    }
}