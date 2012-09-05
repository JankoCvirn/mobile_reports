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

public class WorkerContentProvider extends ContentProvider {

	private DbHelper dbHelper;
	private static HashMap<String, String> WORKER_PROJECTION_MAP;
	private static final String TABLE_NAME = "worker";
	private static final String AUTHORITY = "com.cvirn.ferndaleforms2.dbclasses.workercontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/id");
	public static final Uri FNAME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/fname");
	public static final Uri LNAME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/lname");
	public static final Uri PROFFESSION_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/proffession");
	public static final Uri NOTES_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/notes");

	public static final String DEFAULT_SORT_ORDER = "id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int WORKER = 1;
	private static final int WORKER_ID = 2;
	private static final int WORKER_FNAME = 3;
	private static final int WORKER_LNAME = 4;
	private static final int WORKER_PROFFESSION = 5;
	private static final int WORKER_NOTES = 6;

	// Content values keys (using column names)
	public static final String ID = "id";
	public static final String FNAME = "fname";
	public static final String LNAME = "lname";
	public static final String PROFFESSION = "proffession";
	public static final String NOTES = "notes";

	public boolean onCreate() {
		dbHelper = new DbHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case WORKER:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(WORKER_PROJECTION_MAP);
			break;
		case WORKER_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("id='" + url.getPathSegments().get(2) + "'");
			break;
		case WORKER_FNAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("fname='" + url.getPathSegments().get(2) + "'");
			break;
		case WORKER_LNAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("lname='" + url.getPathSegments().get(2) + "'");
			break;
		case WORKER_PROFFESSION:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("proffession='" + url.getPathSegments().get(2) + "'");
			break;
		case WORKER_NOTES:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("notes='" + url.getPathSegments().get(2) + "'");
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
		case WORKER:
			return "vnd.android.cursor.dir/vnd.com.cvirn.ferndaleforms2.dbclasses.worker";
		case WORKER_ID:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.worker";
		case WORKER_FNAME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.worker";
		case WORKER_LNAME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.worker";
		case WORKER_PROFFESSION:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.worker";
		case WORKER_NOTES:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.worker";

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
		if (URL_MATCHER.match(url) != WORKER) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("worker", "worker", values);
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
		case WORKER:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case WORKER_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_FNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"fname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_LNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"lname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_PROFFESSION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"proffession="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_NOTES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"notes="
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
		case WORKER:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case WORKER_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_FNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"fname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_LNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"lname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_PROFFESSION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"proffession="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WORKER_NOTES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"notes="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), WORKER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/id" + "/*",
				WORKER_ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/fname"
				+ "/*", WORKER_FNAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/lname"
				+ "/*", WORKER_LNAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/proffession"
				+ "/*", WORKER_PROFFESSION);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/notes"
				+ "/*", WORKER_NOTES);

		WORKER_PROJECTION_MAP = new HashMap<String, String>();
		WORKER_PROJECTION_MAP.put(ID, "id");
		WORKER_PROJECTION_MAP.put(FNAME, "fname");
		WORKER_PROJECTION_MAP.put(LNAME, "lname");
		WORKER_PROJECTION_MAP.put(PROFFESSION, "proffession");
		WORKER_PROJECTION_MAP.put(NOTES, "notes");

	}
}
