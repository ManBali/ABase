package com.core.db;

import static com.core.util.log.SqlLog.buildDeleteSqlLog;
import static com.core.util.log.SqlLog.buildQuerySqlLog;
import static com.core.util.log.SqlLog.buildSqlLog;
import static com.core.util.log.SqlLog.emptyObjects;

import com.core.util.constants.CoreConstant;
import com.core.util.log.CoreLog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public abstract class BaseDbAgent {
	
	private static String TAG = "--sql-->:";

	protected SQLiteDatabase mDatabase;

	protected Cursor query(String tableName, String[] projection, String selection,
			String[] selectionArgs, String groupBy, String having, String sort) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(tableName);
		if (CoreConstant.IS_TEST_FLAG) {
			StringBuilder sb = buildSqlLog(mDatabase, tableName, projection,selection, selectionArgs, sort);
			CoreLog.i(sb.insert(0, TAG).toString());
		}
		Cursor ret = qb.query(mDatabase, projection, selection, selectionArgs, groupBy,
				having, sort);
		if (ret != null) {
			ret = new ReadOnlyCursorWrapper(ret);
		}
		return ret;

	}

	protected long insert(String tableName,String nullColumnHack, ContentValues values){
		if (CoreConstant.IS_TEST_FLAG) {
			StringBuilder sb = buildSqlLog(mDatabase, tableName, nullColumnHack, values);
			CoreLog.i(sb.insert(0, TAG).toString());
		}
		return mDatabase.insert(tableName, nullColumnHack, values);
	}
	
	protected int delete(String tableName,final String whereClause, final String[] whereArgs) {
		if (CoreConstant.IS_TEST_FLAG) {
			StringBuilder sb = buildDeleteSqlLog(mDatabase,tableName, whereClause, whereArgs);
			CoreLog.i(sb.insert(0, TAG).toString());
		}
		return mDatabase.delete(tableName, whereClause, whereArgs);
	}

	protected int update(String tableName,final ContentValues values, final String whereClause,
			final String[] whereArgs) {
		if (CoreConstant.IS_TEST_FLAG) {
			StringBuilder sb = buildSqlLog(mDatabase,tableName, values, whereClause, whereArgs);
			CoreLog.i(sb.insert(0, TAG).toString());
		}
		return mDatabase.update(tableName, values, whereClause, whereArgs);
	}

	protected Cursor rawQuery(String sql, String... selectionArgs) {
		if (CoreConstant.IS_TEST_FLAG) {
			StringBuilder sb = buildQuerySqlLog(mDatabase, sql, selectionArgs);
			CoreLog.i(sb.insert(0, TAG).toString());
		}
		return mDatabase.rawQuery(sql, selectionArgs);
	}

	protected boolean execSQL(String sql) {
		return execSQL(sql, emptyObjects);
	}

	protected boolean execSQL(String sql, Object... bindArgs) {
		boolean success = false;
		try {
			if (CoreConstant.IS_TEST_FLAG) {
				StringBuilder sb = buildSqlLog(mDatabase, sql, bindArgs);
				CoreLog.i(sb.insert(0, TAG).toString());
			}
			mDatabase.execSQL(sql, bindArgs);
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

}
