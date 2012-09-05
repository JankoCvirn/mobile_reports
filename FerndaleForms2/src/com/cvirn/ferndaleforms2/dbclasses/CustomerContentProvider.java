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

public class CustomerContentProvider extends ContentProvider {

	private DbHelper dbHelper;
	private static HashMap<String, String> CUSTOMER_PROJECTION_MAP;
	private static final String TABLE_NAME = "customer";
	private static final String AUTHORITY = "com.cvirn.ferndaleforms2.dbclasses.customercontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/id");
	public static final Uri NAME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/name");
	public static final Uri STREET_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/street");
	public static final Uri CITY_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/city");
	public static final Uri STATE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/state");
	public static final Uri CONTACT_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/contact");

	public static final String DEFAULT_SORT_ORDER = "id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int CUSTOMER = 1;
	private static final int CUSTOMER_ID = 2;
	private static final int CUSTOMER_NAME = 3;
	private static final int CUSTOMER_STREET = 4;
	private static final int CUSTOMER_CITY = 5;
	private static final int CUSTOMER_STATE = 6;
	private static final int CUSTOMER_CONTACT = 7;

	// Content values keys (using column names)
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String STREET = "street";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String CONTACT = "contact";

	public boolean onCreate() {
		dbHelper = new DbHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case CUSTOMER:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(CUSTOMER_PROJECTION_MAP);
			break;
		case CUSTOMER_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("id='" + url.getPathSegments().get(2) + "'");
			break;
		case CUSTOMER_NAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("name='" + url.getPathSegments().get(2) + "'");
			break;
		case CUSTOMER_STREET:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("street='" + url.getPathSegments().get(2) + "'");
			break;
		case CUSTOMER_CITY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("city='" + url.getPathSegments().get(2) + "'");
			break;
		case CUSTOMER_STATE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("state='" + url.getPathSegments().get(2) + "'");
			break;
		case CUSTOMER_CONTACT:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("contact='" + url.getPathSegments().get(2) + "'");
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
		case CUSTOMER:
			return "vnd.android.cursor.dir/vnd.com.cvirn.ferndaleforms2.dbclasses.customer";
		case CUSTOMER_ID:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.customer";
		case CUSTOMER_NAME:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.customer";
		case CUSTOMER_STREET:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.customer";
		case CUSTOMER_CITY:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.customer";
		case CUSTOMER_STATE:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.customer";
		case CUSTOMER_CONTACT:
			return "vnd.android.cursor.item/vnd.com.cvirn.ferndaleforms2.dbclasses.customer";

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
		if (URL_MATCHER.match(url) != CUSTOMER) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("customer", "customer", values);
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
		case CUSTOMER:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case CUSTOMER_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_STREET:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"street="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_CITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"city="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_STATE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"state="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_CONTACT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"contact="
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
		case CUSTOMER:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case CUSTOMER_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_STREET:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"street="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_CITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"city="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_STATE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"state="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case CUSTOMER_CONTACT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"contact="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), CUSTOMER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/id" + "/*",
				CUSTOMER_ID);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/name" + "/*", CUSTOMER_NAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/street"
				+ "/*", CUSTOMER_STREET);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/city" + "/*", CUSTOMER_CITY);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/state"
				+ "/*", CUSTOMER_STATE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/contact"
				+ "/*", CUSTOMER_CONTACT);

		CUSTOMER_PROJECTION_MAP = new HashMap<String, String>();
		CUSTOMER_PROJECTION_MAP.put(ID, "id");
		CUSTOMER_PROJECTION_MAP.put(NAME, "name");
		CUSTOMER_PROJECTION_MAP.put(STREET, "street");
		CUSTOMER_PROJECTION_MAP.put(CITY, "city");
		CUSTOMER_PROJECTION_MAP.put(STATE, "state");
		CUSTOMER_PROJECTION_MAP.put(CONTACT, "contact");

	}
}
