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

package com.liferay.tasks.ui.model;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class TaskModel implements Serializable {

	public TaskModel(JSONObject json) throws JSONException {
		_modifiedDate = json.getLong(_MODIFIED_DATE);
		_status = json.getInt(_STATUS);
		_id = json.getLong(_TASK_ENTRY_ID);
		_title = json.getString(_TITLE);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskModel)) {
			return false;
		}

		TaskModel task = (TaskModel)obj;

		return (_id == task.getId());
	}

	public long getId() {
		return _id;
	}

	public String getModifiedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(
			"MMM d, HH:mm", Locale.getDefault());

		return formatter.format(new Date(_modifiedDate));
	}

	public int getStatus() {
		return _status;
	}

	public String getTitle() {
		return _title;
	}

	private static final String _MODIFIED_DATE = "modifiedDate";

	private static final String _STATUS = "status";

	private static final String _TASK_ENTRY_ID = "tasksEntryId";

	private static final String _TITLE = "title";

	private long _id;
	private long _modifiedDate;
	private int _status;
	private String _title;

}