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

package com.liferay.tasks.ui.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liferay.tasks.ui.R;
import com.liferay.tasks.ui.model.TaskModel;

import java.util.List;

/**
 * @author Silvio Santos
 */
public class TasksAdapter extends ArrayAdapter<TaskModel> {

	public TasksAdapter(Context context, List<TaskModel> tasks) {
		super(context, 0, tasks);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;

		LayoutInflater inflater = LayoutInflater.from(getContext());

		if (view == null) {
			view = inflater.inflate(R.layout.item_task, parent, false);

			holder = new ViewHolder();
			holder.modifiedDate = (TextView)
				view.findViewById(R.id.modified_date);

			holder.progress = (ImageView)view.findViewById(R.id.progress);
			holder.title = (TextView)view.findViewById(R.id.title);

			view.setTag(holder);
		}
		else {
			holder = (ViewHolder)view.getTag();
		}

		TaskModel task = getItem(position);
		int level = getStatusLevel(task.getStatus());

		holder.modifiedDate.setText(task.getModifiedDate());
		holder.progress.getDrawable().setLevel(level);
		holder.title.setText(task.getTitle());

		return view;
	}

	public void remove(List<TaskModel> tasks) {
		setNotifyOnChange(false);

		for (TaskModel task : tasks) {
			remove(task);
		}

		notifyDataSetChanged();
	}

	protected int getStatusLevel(int status) {
		return ((status * 2000) - 2000);
	}

	class ViewHolder {

		TextView modifiedDate;
		ImageView progress;
		TextView title;
	}

}