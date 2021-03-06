package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.IssuesViewModelFactory
import com.example.projectmanager.ui.project.ProjectActivity
import kotlinx.android.synthetic.main.issues_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class IssuesFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = IssuesFragment()

        lateinit var adapter: IssuesAdapter
    }

    override val kodein by kodein()
    private val factory: IssuesViewModelFactory by instance()

    private lateinit var viewModel: IssuesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issues_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(IssuesViewModel::class.java)

        val members = ProjectActivity.currentProject?.members!!
        viewModel.loadUsers(members)

        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            ProjectActivity.members = it.data!!
        })

        val adapter = ViewPageAdapter(childFragmentManager)
        adapter.fragments.add(CreatedFragment())
        adapter.titles.add(getString(R.string.issue_created))

        adapter.fragments.add(StartedFragment())
        adapter.titles.add(getString(R.string.issue_started))

        adapter.fragments.add(FinishedFragment())
        adapter.titles.add(getString(R.string.issue_finished))

        viewPager.adapter = adapter

        tabLayoutIssues.setupWithViewPager(viewPager)

        addIssueButton.setOnClickListener {viewButton ->
            (viewButton.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_project, CreateIssueFragment()
            ).addToBackStack("toCreateIssue").commit()
        }

    }
}
