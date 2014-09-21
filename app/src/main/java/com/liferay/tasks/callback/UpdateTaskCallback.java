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

import android.app.Activity;

import android.widget.Toast;

import com.liferay.mobile.android.task.callback.typed.JSONObjectAsyncTaskCallback;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class UpdateTaskCallback extends JSONObjectAsyncTaskCallback {

	public UpdateTaskCallback(Activity activity) {
		_activity = activity;
	}

	@Override
	public void onFailure(Exception e) {
		String message = "Couldn't update task: " + e.getMessage();

		Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSuccess(JSONObject jsonObject) {
		_activity.finish();
	}

	private Activity _activity;

}