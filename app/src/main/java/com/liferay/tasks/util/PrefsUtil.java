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

package com.liferay.tasks.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;

/**
 * @author Silvio Santos
 */
public class PrefsUtil {

	public static long getGroupId() {
		return 10181;
	}

	public static Session getSession() {
		return new SessionImpl(_SERVER, _LOGIN, _PASSWORD);
	}

	public static long getUserId() {
		return 10434;
	}

	private static final String _LOGIN = "test@liferay.com";

	private static final String _PASSWORD = "test";

	private static final String _SERVER = "http://10.0.2.2:8080";

}