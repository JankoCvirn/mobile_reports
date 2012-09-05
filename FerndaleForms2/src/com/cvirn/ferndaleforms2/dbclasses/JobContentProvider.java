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

public class JobContentProvider extends ContentProvider {

	private DbHelper dbHelper;
	private static HashMap<String, String> JOB_PROJECTION_MAP;
	private static final String TABLE_NAME = "job";
	private static final String AUTHORITY = "com.cvirn.ferndaleforms2.dbclasses.jobcontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri CUSTOMERNR_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/customernr");
	public static final Uri FECMANAGER_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/fecmanager");
	public static final Uri JOBNUMBER_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/jobnumber");
	public static final Uri DATE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/date");
	public static final Uri WORKPERFORMED_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/workperformed");
	public static final Uri JOBLOCATION_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/joblocation");
	public static final Uri JOBNAME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/jobname");
	public static final Uri CUSTOMER_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/customer");
	public static final Uri ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/id");

	public static final String DEFAULT_SORT_ORDER = "id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int JOB = 1;
	private static final int JOB_CUSTOMERNR = 2;
	private static final int JOB_FECMANAGER = 3;
	private static final int JOB_JOBNUMBER = 4;
	private static final int JOB_DATE = 5;
	private static final int JOB_WORKPERFORMED = 6;
	private static final int JOB_JOBLOCATION = 7;
	private static final int JOB_JOBNAME = 8;
	private static final int JOB_CUSTOMER = 9;
	private static final int JOB_ID = 10;

	// Content values keys (using column names)
	public static final String CUSTOMERNR = "customernr";
	public static final String FECMANAGER = "fecmanager";
	public static final String JOBNUMBER = "jobnumber";
	public static final String DATE = "date";
	public static final String WORKPERFORMED = "workperformed";
	public static final String JOBLOCATION = "joblocation";
	public static final String JOBNAME = "jobname";
	public static final String CUSTOMER = "customer";
	public static final String ID = "id";

	public boolean onCreate() {
		dbHelper = new DbHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case JOB:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(JOB_PROJECTION_MAP);
			break;
		case JOB_CUSTOMERNR:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("customernr='" + url.getPathSegments().get(2) + "'");
			break;
		case JOB_FECMANAGER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("fecmanager='" + url.getPathSegments().get(2) + "'");
			break;
		case JOB_JOBNUMBER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("jobnumber='" + url.getPathSegments().get(2) + "'");
			break;
		case JOB_DATE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("date='" + url.getPathSegments().get(2) + "'");
			break;
		case JOB_WORKPERFORMED:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("workperformed='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case JOB_JOBLOCATION:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("joblocation='" + url.getPathSegments().get(2) + "'");
			break;
		case JOB_JOBNAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("jobname='" + url.getPathSegments().get(2) + "'");
			break;
		case JOB_CUSTOMER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("customer='" + url.getPathSegments().get(2) + "'");
			break;
		case JOB_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("id='" + url.getPathSegments().get(2) + "'");
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
		case JOB:
			return "vnd.android.cursor.dir/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_CUSTOMERNR:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_FECMANAGER:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_JOBNUMBER:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_DATE:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_WORKPERFORMED:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_JOBLOCATION:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_JOBNAME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_CUSTOMER:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";
		case JOB_ID:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.job";

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
		if (URL_MATCHER.match(url) != JOB) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("job", "job", values);
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
		case JOB:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case JOB_CUSTOMERNR:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"customernr="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_FECMANAGER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"fecmanager="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_JOBNUMBER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"jobnumber="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_DATE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"date="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_WORKPERFORMED:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"workperformed="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_JOBLOCATION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"joblocation="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_JOBNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"jobname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_CUSTOMER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"customer="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"id="
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
		case JOB:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case JOB_CUSTOMERNR:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"customernr="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_FECMANAGER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"fecmanager="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_JOBNUMBER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"jobnumber="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_DATE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"date="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_WORKPERFORMED:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"workperformed="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_JOBLOCATION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"joblocation="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_JOBNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"jobname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_CUSTOMER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"customer="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case JOB_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"id="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), JOB);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/customernr"
				+ "/*", JOB_CUSTOMERNR);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/fecmanager"
				+ "/*", JOB_FECMANAGER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/jobnumber"
				+ "/*", JOB_JOBNUMBER);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/date" + "/*", JOB_DATE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/workperformed" + "/*", JOB_WORKPERFORMED);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/joblocation"
				+ "/*", JOB_JOBLOCATION);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/jobname"
				+ "/*", JOB_JOBNAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/customer"
				+ "/*", JOB_CUSTOMER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/id" + "/*",
				JOB_ID);

		JOB_PROJECTION_MAP = new HashMap<String, String>();
		JOB_PROJECTION_MAP.put(CUSTOMERNR, "customernr");
		JOB_PROJECTION_MAP.put(FECMANAGER, "fecmanager");
		JOB_PROJECTION_MAP.put(JOBNUMBER, "jobnumber");
		JOB_PROJECTION_MAP.put(DATE, "date");
		JOB_PROJECTION_MAP.put(WORKPERFORMED, "workperformed");
		JOB_PROJECTION_MAP.put(JOBLOCATION, "joblocation");
		JOB_PROJECTION_MAP.put(JOBNAME, "jobname");
		JOB_PROJECTION_MAP.put(CUSTOMER, "customer");
		JOB_PROJECTION_MAP.put(ID, "id");

	}
}
