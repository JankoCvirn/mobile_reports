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

public class LaborContentProvider extends ContentProvider {

	private DbHelper dbHelper;
	private static HashMap<String, String> LABOR_PROJECTION_MAP;
	private static final String TABLE_NAME = "labor";
	private static final String AUTHORITY = "com.cvirn.ferndaleforms2.dbclasses.laborcontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/id");
	public static final Uri JOBNR_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/jobnr");
	public static final Uri WORKER_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/worker");
	public static final Uri STIME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/stime");
	public static final Uri HTIME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/htime");
	public static final Uri DTIME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/dtime");

	public static final String DEFAULT_SORT_ORDER = "id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int LABOR = 1;
	private static final int LABOR_ID = 2;
	private static final int LABOR_JOBNR = 3;
	private static final int LABOR_WORKER = 4;
	private static final int LABOR_STIME = 5;
	private static final int LABOR_HTIME = 6;
	private static final int LABOR_DTIME = 7;

	// Content values keys (using column names)
	public static final String ID = "id";
	public static final String JOBNR = "jobnr";
	public static final String WORKER = "worker";
	public static final String STIME = "stime";
	public static final String HTIME = "htime";
	public static final String DTIME = "dtime";

	public boolean onCreate() {
		dbHelper = new DbHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case LABOR:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(LABOR_PROJECTION_MAP);
			break;
		case LABOR_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("id='" + url.getPathSegments().get(2) + "'");
			break;
		case LABOR_JOBNR:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("jobnr='" + url.getPathSegments().get(2) + "'");
			break;
		case LABOR_WORKER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("worker='" + url.getPathSegments().get(2) + "'");
			break;
		case LABOR_STIME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("stime='" + url.getPathSegments().get(2) + "'");
			break;
		case LABOR_HTIME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("htime='" + url.getPathSegments().get(2) + "'");
			break;
		case LABOR_DTIME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("dtime='" + url.getPathSegments().get(2) + "'");
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
		case LABOR:
			return "vnd.android.cursor.dir/vnd.com.cvirn.ferndaleforms2.dbclasses.labor";
		case LABOR_ID:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.labor";
		case LABOR_JOBNR:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.labor";
		case LABOR_WORKER:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.labor";
		case LABOR_STIME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.labor";
		case LABOR_HTIME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.labor";
		case LABOR_DTIME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.labor";

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
		if (URL_MATCHER.match(url) != LABOR) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("labor", "labor", values);
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
		case LABOR:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case LABOR_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_JOBNR:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"jobnr="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_WORKER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"worker="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_STIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"stime="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_HTIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"htime="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_DTIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"dtime="
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
		case LABOR:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case LABOR_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_JOBNR:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"jobnr="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_WORKER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"worker="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_STIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"stime="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_HTIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"htime="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LABOR_DTIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"dtime="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), LABOR);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/id" + "/*",
				LABOR_ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/jobnr"
				+ "/*", LABOR_JOBNR);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/worker"
				+ "/*", LABOR_WORKER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/stime"
				+ "/*", LABOR_STIME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/htime"
				+ "/*", LABOR_HTIME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/dtime"
				+ "/*", LABOR_DTIME);

		LABOR_PROJECTION_MAP = new HashMap<String, String>();
		LABOR_PROJECTION_MAP.put(ID, "id");
		LABOR_PROJECTION_MAP.put(JOBNR, "jobnr");
		LABOR_PROJECTION_MAP.put(WORKER, "worker");
		LABOR_PROJECTION_MAP.put(STIME, "stime");
		LABOR_PROJECTION_MAP.put(HTIME, "htime");
		LABOR_PROJECTION_MAP.put(DTIME, "dtime");

	}
}
