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

import android.view.View;

import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v621.tasksentry.TasksentryService;
import com.liferay.tasks.R;
import com.liferay.tasks.callback.AddTaskCallback;
import com.liferay.tasks.callback.UpdateTaskCallback;
import com.liferay.tasks.ui.listener.OnStatusChangedListener;
import com.liferay.tasks.ui.model.TaskModel;
import com.liferay.tasks.util.PrefsUtil;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class AddTaskActivity extends Activity implements View.OnClickListener {

	public static final String EXTRA_TASK = "task";

	public static final String EXTRA_UPDATE = "update";

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.save) {
			String title = _titleView.getText().toString();
			int status = _seekBar.getProgress() + 1;

			if (_update) {
				updateTask(_task.getId(), title, status);
			}
			else {
				addTask(title);
			}
		}
	}

	protected void addTask(String task) {
		Session session = PrefsUtil.getSession();

		AddTaskCallback callback = new AddTaskCallback(this);
		session.setCallback(callback);

		TasksentryService service = new TasksentryService(session);

		try {
			JSONObject serviceContext = new JSONObject();
			serviceContext.put("scopeGroupId", PrefsUtil.getGroupId());

			JSONObjectWrapper wrapper = new JSONObjectWrapper(serviceContext);

			service.addTasksEntry(task, 0, 0, 0, 0, 0, 0, 0, false, wrapper);
		}
		catch (Exception e) {
			String message = "Couldn't add task: " + e.getMessage();

			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}
	}

	protected void initialize(Bundle extras) {
		if (extras == null) {
			return;
		}

		_update = extras.getBoolean(EXTRA_UPDATE, false);
		_task = (TaskModel)extras.getSerializable(EXTRA_TASK);

		if (!_update) {
			return;
		}

		View container = findViewById(R.id.status_bar_container);
		container.setVisibility(View.VISIBLE);

		int progress = _task.getStatus() - 1;
		TextView percentageView = (TextView)findViewById(R.id.percentage);
		percentageView.setText(String.valueOf(progress * 20) + "%");

		OnStatusChangedListener listener = new OnStatusChangedListener(
			percentageView);

		_titleView.setText(_task.getTitle());
		_seekBar.setProgress(progress);
		_seekBar.setOnSeekBarChangeListener(listener);
	}

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);

		setContentView(R.layout.activity_add_task);

		_titleView = (EditText)findViewById(R.id.task);

		_seekBar = (SeekBar)findViewById(R.id.status_bar);
		_seekBar.setMax(5);

		View saveView = findViewById(R.id.save);
		saveView.setOnClickListener(this);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		initialize(extras);
	}

	protected void updateTask(long taskId, String title, int status) {
		Session session = PrefsUtil.getSession();

		UpdateTaskCallback callback = new UpdateTaskCallback(this);
		session.setCallback(callback);

		TasksentryService service = new TasksentryService(session);

		try {
			JSONObject serviceContext = new JSONObject();
			serviceContext.put("scopeGroupId", PrefsUtil.getGroupId());

			serviceContext.put("userId", PrefsUtil.getUserId());

			JSONObjectWrapper wrapper = new JSONObjectWrapper(serviceContext);

			service.updateTasksEntry(
				taskId, title, 0, 0, 0, 0, 0, 0, 0, 0, false, status, wrapper);
		}
		catch (Exception e) {
			String message = "Couldn't update task: " + e.getMessage();

			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}
	}

	private SeekBar _seekBar;
	private TaskModel _task;
	private EditText _titleView;
	private boolean _update;

}