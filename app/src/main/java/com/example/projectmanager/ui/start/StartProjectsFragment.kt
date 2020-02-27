package com.example.projectmanager.ui.createProject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.StartProjectsViewModelFactory
import com.example.projectmanager.ui.project.CreateProjectActivity
import com.example.projectmanager.ui.project.JoinProjectActivity
import com.example.projectmanager.ui.project.JoinProjectViewModel
import com.example.projectmanager.ui.start.StartProjectsAdapter
import com.example.projectmanager.ui.start.StartProjectsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_start_projects.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class StartProjectsFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = StartProjectsFragment()
        const val ACTIVITY_TITLE = "Projects"
        const val REQUEST_CREATE_PROJECT_CODE = 1
        const val REQUEST_JOIN_PROJECT_CODE = 2
    }

    override val kodein by kodein()
    private val factory: StartProjectsViewModelFactory by instance()

    private lateinit var viewModel: StartProjectsViewModel
    private lateinit var floatingMenu: FloatingMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = ACTIVITY_TITLE

        return inflater.inflate(R.layout.fragment_start_projects, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(StartProjectsViewModel::class.java)

        // Create the custom floating menu
        floatingMenu = createFloatingMenu()

        // Setup FloatingActionButton listener
        fab_main.setOnClickListener {
            if (!floatingMenu.isExpanded)
                floatingMenu.expand()
            else
                floatingMenu.dismiss()

            floatingMenu.isExpanded = !floatingMenu.isExpanded
        }

        // Initialize layout manager for recycler view
        var adapter = StartProjectsAdapter(listOf())

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(activity)

        viewModel.getProjects().observe(viewLifecycleOwner, Observer {
            adapter.setProjects(it.data!!)
            swipe_layout.isRefreshing = false
        })

        swipe_layout.setOnRefreshListener {
            viewModel.loadProjects()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CREATE_PROJECT_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //viewModel.loadProjects()
                }

                Activity.RESULT_CANCELED -> {

                }
            }
        }
    }

    private fun createFloatingMenu() : FloatingMenu {
        val menuItemAdd = FloatingMenuItem(menu_add_project,
            menu_add_project.findViewById(R.id.fab_item) as FloatingActionButton,
            menu_add_project.findViewById(R.id.text_view) as TextView,
            -resources.getDimension(R.dimen.standard_100)
        )

        menuItemAdd.fab.setOnClickListener {
            val intent = Intent(activity, CreateProjectActivity::class.java)
            startActivityForResult(intent, REQUEST_CREATE_PROJECT_CODE)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        val menuItemJoin = FloatingMenuItem(menu_join_project,
            menu_join_project.findViewById(R.id.fab_item) as FloatingActionButton,
            menu_join_project.findViewById(R.id.text_view) as TextView,
            -resources.getDimension(R.dimen.standard_55)
        )

        menuItemJoin.fab.setOnClickListener {
            val intent = Intent(activity, JoinProjectActivity::class.java)
            startActivityForResult(intent, REQUEST_JOIN_PROJECT_CODE)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        menuItemAdd.text_view!!.text = "Create new project"
        menuItemAdd.fab.setImageResource(R.drawable.ic_create_white_24dp)

        menuItemJoin.text_view!!.text = "Find and join project"
        menuItemJoin.fab.setImageResource(R.drawable.ic_search_white_24dp)

        return FloatingMenu(fab_main, listOf(menuItemAdd, menuItemJoin))
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