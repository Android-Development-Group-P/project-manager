package com.example.projectmanager.ui.createProject

import android.app.Notification
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.StartNotificationViewModelFactory
import com.example.projectmanager.ui.start.StartNotificationAdapter
import com.example.projectmanager.ui.start.StartNotificationViewModel
import kotlinx.android.synthetic.main.fragment_start_notification.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class StartNotificationFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = StartNotificationFragment()
        const val ACTIVITY_TITLE = "Notifications"
        lateinit var adapter: StartNotificationAdapter
    }

    override val kodein by kodein()
    private  val factory: StartNotificationViewModelFactory by instance()

    private  lateinit var viewModel: StartNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.notification_title)
        return inflater.inflate(R.layout.fragment_start_notification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(StartNotificationViewModel::class.java)

        viewModel.loadNotifications()

        adapter = StartNotificationAdapter(mutableListOf())
        notificationRecyclerView.adapter = adapter
        notificationRecyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.getNotifications().observe(viewLifecycleOwner, Observer { result ->
            if (result.error != null) {
                Log.d("getNotifications", "Error: ${result.error.localizedMessage}")
            } else {
                adapter.updateNotificationList(result.data!!)
            }
        })
    }
}
