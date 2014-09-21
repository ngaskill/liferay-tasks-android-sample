/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.tasks.activity;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v621.tasksentry.TasksentryService;
import com.liferay.tasks.R;
import com.liferay.tasks.callback.DeleteTasksCallback;
import com.liferay.tasks.callback.GetTasksCallback;
import com.liferay.tasks.listener.MultiChoiceModeListener;
import com.liferay.tasks.ui.adapter.TasksAdapter;
import com.liferay.tasks.ui.model.TaskModel;
import com.liferay.tasks.util.PrefsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Santos
 */
public class MainActivity extends Activity
	implements AdapterView.OnItemClickListener {

	public void deleteTasks(List<TaskModel> tasks) {
		BatchSessionImpl session = new BatchSessionImpl(PrefsUtil.getSession());
		session.setCallback(new DeleteTasksCallback(this, tasks));

		TasksentryService service = new TasksentryService(session);

		try {
			for (TaskModel task : tasks) {
				service.deleteTasksEntry(task.getId());
			}

			session.invoke();
		}
		catch (Exception e) {
			String message = "Coudn't delete tasks: " + e.getMessage();

			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onItemClick(
		AdapterView<?> parent, View view, int position, long id) {

		TaskModel task = (TaskModel)parent.getItemAtPosition(position);

		Intent intent = new Intent(this, AddTaskActivity.class);
		intent.putExtra(AddTaskActivity.EXTRA_UPDATE, true);
		intent.putExtra(AddTaskActivity.EXTRA_TASK, task);

		startActivity(intent);
	}

	@Override
	public boolean onMenuItemSelected(int id, MenuItem item) {
		if (item.getItemId() == R.id.add) {
			Intent intent = new Intent(this, AddTaskActivity.class);

			startActivity(intent);
		}

		return super.onMenuItemSelected(id, item);
	}

	public void onTaskDeleted(List<TaskModel> tasks) {
		_adapter.remove(tasks);
	}

	public void reloadTasks(List<TaskModel> tasks) {
		_tasks.clear();
		_tasks.addAll(tasks);

		_adapter.notifyDataSetChanged();
	}

	protected void getTasks() {
		Session session = PrefsUtil.getSession();

		GetTasksCallback callback = new GetTasksCallback(this);
		session.setCallback(callback);

		TasksentryService service = new TasksentryService(session);

		try {
			service.getUserTasksEntries(PrefsUtil.getUserId(), -1, -1);
		}
		catch (Exception e) {
			String message = "Couldn't get tasks " + e.getMessage();

			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);

		setContentView(R.layout.activity_main);

		_tasks = new ArrayList<TaskModel>();
		_adapter = new TasksAdapter(this, _tasks);

		ListView listView = (ListView)findViewById(R.id.list);
		listView.setAdapter(_adapter);
		listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setOnItemClickListener(this);
		listView.setMultiChoiceModeListener(
			new MultiChoiceModeListener(this, listView));
	}

	@Override
	protected void onResume() {
		super.onResume();

		getTasks();
	}

	private TasksAdapter _adapter;
	private List<TaskModel> _tasks;

}