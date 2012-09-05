/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */
package com.cvirn.ferndaleforms2.dbclasses;

import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

import com.cvirn.ferndaleforms2.dbhelper.*;

public class SubconContentProvider extends ContentProvider {

	private DbHelper dbHelper;
	private static HashMap<String, String> SUBCON_PROJECTION_MAP;
	private static final String TABLE_NAME = "subcon";
	private static final String AUTHORITY = "com.cvirn.ferndaleforms2.dbclasses.subconcontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/id");
	public static final Uri NAME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/name");

	public static final String DEFAULT_SORT_ORDER = "id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int SUBCON = 1;
	private static final int SUBCON_ID = 2;
	private static final int SUBCON_NAME = 3;

	// Content values keys (using column names)
	public static final String ID = "id";
	public static final String NAME = "NAME";

	public boolean onCreate() {
		dbHelper = new DbHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case SUBCON:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(SUBCON_PROJECTION_MAP);
			break;
		case SUBCON_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("id='" + url.getPathSegments().get(2) + "'");
			break;
		case SUBCON_NAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("name='" + url.getPathSegments().get(2) + "'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		String orderBy = "";
		if (TextUtils.isEmpty(sort)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sort;
		}
		Cursor c = qb.query(mDB, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	public String getType(Uri url) {
		switch (URL_MATCHER.match(url)) {
		case SUBCON:
			return "vnd.android.cursor.dir/vnd.com.cvirn.ferndaleforms2.dbclasses.subcon";
		case SUBCON_ID:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.subcon";
		case SUBCON_NAME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.subcon";

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	public Uri insert(Uri url, ContentValues initialValues) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		long rowID;
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		if (URL_MATCHER.match(url) != SUBCON) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("subcon", "subcon", values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + url);
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case SUBCON:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case SUBCON_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SUBCON_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case SUBCON:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case SUBCON_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SUBCON_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	static {
		URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), SUBCON);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/id" + "/*",
				SUBCON_ID);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/name" + "/*", SUBCON_NAME);

		SUBCON_PROJECTION_MAP = new HashMap<String, String>();
		SUBCON_PROJECTION_MAP.put(ID, "id");
		SUBCON_PROJECTION_MAP.put(NAME, "name");

	}
}
