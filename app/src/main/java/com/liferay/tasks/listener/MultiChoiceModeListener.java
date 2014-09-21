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

package com.liferay.tasks.listener;

import android.util.SparseBooleanArray;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.AbsListView;
import android.widget.ListView;

import com.liferay.tasks.R;
import com.liferay.tasks.activity.MainActivity;
import com.liferay.tasks.ui.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Santos
 */
public class MultiChoiceModeListener
	implements AbsListView.MultiChoiceModeListener {

	public MultiChoiceModeListener(MainActivity activity, ListView listView) {
		_activity = activity;
		_listView = listView;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.delete:
				_activity.deleteTasks(getSelectedItems());

				mode.finish();

				return true;

			default:
				return false;
		}
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();

		inflater.inflate(R.menu.menu_context, menu);

		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
	}

	@Override
	public void onItemCheckedStateChanged(
		ActionMode mode, int position, long id, boolean checked) {

		int count = _listView.getCheckedItemCount();
		mode.setTitle(count + " Selected");
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	protected List<TaskModel> getSelectedItems() {
		List<TaskModel> tasks = new ArrayList<TaskModel>();
		SparseBooleanArray checked = _listView.getCheckedItemPositions();

		for (int i = 0; i < checked.size(); i++) {
			if (checked.valueAt(i) == true) {
				int position = checked.keyAt(i);

				TaskModel task = (TaskModel)_listView.getItemAtPosition(
					position);

				tasks.add(task);
			}
		}

		return tasks;
	}

	private MainActivity _activity;
	private ListView _listView;

}