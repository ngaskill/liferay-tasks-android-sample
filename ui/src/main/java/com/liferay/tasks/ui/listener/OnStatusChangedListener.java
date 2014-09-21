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

package com.liferay.tasks.ui.listener;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author Silvio Santos
 */
public class OnStatusChangedListener
	implements SeekBar.OnSeekBarChangeListener {

	public OnStatusChangedListener(TextView textView) {
		_textView = textView;
	}

	@Override
	public void onProgressChanged(
		SeekBar seekBar, int progress, boolean fromUser) {

		if (!fromUser) {
			return;
		}

		int percentage = progress * 20;

		_textView.setText(String.valueOf(percentage) + "%");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	private TextView _textView;

}