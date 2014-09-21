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

package com.liferay.tasks.callback;

import android.widget.Toast;

import com.liferay.mobile.android.task.callback.typed.GenericAsyncTaskCallback;
import com.liferay.tasks.activity.MainActivity;
import com.liferay.tasks.ui.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class GetTasksCallback
	extends GenericAsyncTaskCallback<List<TaskModel>> {

	public GetTasksCallback(MainActivity activity) {
		_activity = activity;
	}

	@Override
	public void onFailure(Exception e) {
		String message = "Couldn't get tasks " + e.getMessage();

		Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSuccess(List<TaskModel> tasks) {
		_activity.reloadTasks(tasks);
	}

	@Override
	public List<TaskModel> transform(Object obj) throws Exception {
		List<TaskModel> tasks = new ArrayList<TaskModel>();

		JSONArray jsonArray = (JSONArray)obj;

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);

			tasks.add(new TaskModel(json));
		}

		return tasks;
	}

	private MainActivity _activity;

}