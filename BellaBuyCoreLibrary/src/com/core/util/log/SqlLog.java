package com.core.util.log;

import java.util.UUID;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午3:48:03
 * @Description: sql执行日志
 */
public class SqlLog {
	
	public static Object[] emptyObjects = new Object[]{};

	/**
	 * 查询日志输出
	 * @param db
	 * @param tableName
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sort
	 * @return
	 */
	public static java.lang.StringBuilder buildSqlLog(SQLiteDatabase db,
			String tableName, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		sb.append("starting query, database is ");
		if (db != null) {
			sb.append("not ");
		}
		sb.append("null; ");
		sb.append("tables is ");
		if (tableName == null) {
			sb.append("null; ");
		} else if (tableName.length() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(tableName).append("; ");
		}
		if (projection == null) {
			sb.append("projection is null; ");
		} else if (projection.length == 0) {
			sb.append("projection is empty; ");
		} else {
			for (int i = 0; i < projection.length; ++i) {
				sb.append("projection[");
				sb.append(i);
				sb.append("] is ");
				sb.append(projection[i]);
				sb.append("; ");
			}
		}
		sb.append("selection is ");
		sb.append(selection);
		sb.append("; ");
		if (selectionArgs == null) {
			sb.append("selectionArgs is null; ");
		} else if (selectionArgs.length == 0) {
			sb.append("selectionArgs is empty; ");
		} else {
			for (int i = 0; i < selectionArgs.length; ++i) {
				sb.append("selectionArgs[");
				sb.append(i);
				sb.append("] is ");
				sb.append(selectionArgs[i]);
				sb.append("; ");
			}
		}
		sb.append("sort is ");
		sb.append(sort);
		sb.append(".");
		return sb;
	}

	/**
	 * 查询日志输出
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public static java.lang.StringBuilder buildQuerySqlLog(SQLiteDatabase db,
			String sql, String[] selectionArgs) {
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		sb.append("starting rawQuery, sql is ");
		if (db != null) {
			sb.append("not ");
		}
		sb.append("null; ");
		sb.append("sql is ");
		if (sql == null) {
			sb.append("null; ");
		} else if (sql.length() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(sql).append("; ");
		}
		if (selectionArgs == null) {
			sb.append("selectionArgs is null; ");
		} else if (selectionArgs.length == 0) {
			sb.append("selectionArgs is empty; ");
		} else {
			for (int i = 0; i < selectionArgs.length; ++i) {
				sb.append("selectionArgs[");
				sb.append(i);
				sb.append("] is ");
				sb.append(selectionArgs[i]);
				sb.append("; ");
			}
		}
		return sb;
	}

	/**
	 * 删除日志
	 * @param db
	 * @param tableName
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sort
	 * @return
	 */
	public static java.lang.StringBuilder buildDeleteSqlLog(SQLiteDatabase db,
			String tableName, final String whereClause, final String[] whereArgs) {
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		sb.append("starting delete, database is ");
		if (db != null) {
			sb.append("not ");
		}
		sb.append("null; ");
		sb.append("tables is ");
		if (tableName == null) {
			sb.append("null; ");
		} else if (tableName.length() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(tableName).append("; ");
		}
		sb.append("whereClause is ");
		sb.append(whereClause);
		sb.append("; ");
		if (whereArgs == null) {
			sb.append("whereArgs is null; ");
		} else if (whereArgs.length == 0) {
			sb.append("whereArgs is empty; ");
		} else {
			for (int i = 0; i < whereArgs.length; ++i) {
				sb.append("whereArgs[");
				sb.append(i);
				sb.append("] is ");
				sb.append(whereArgs[i]);
				sb.append("; ");
			}
		}
		return sb;
	}

	/**
	 * 执行Sql日志
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public static java.lang.StringBuilder buildSqlLog(SQLiteDatabase db,
			String sql, Object... bindArgs) {
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		sb.append("starting execSQL, db is ");
		if (db != null) {
			sb.append("not ");
		}
		sb.append("null; ");
		sb.append("sql is ");
		if (sql == null) {
			sb.append("null; ");
		} else if (sql.length() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(sql).append("; ");
		}
		if (bindArgs == null) {
			sb.append("bindArgs is null; ");
		} else if (bindArgs.length == 0) {
			sb.append("selectionArgs is empty; ");
		} else {
			for (int i = 0; i < bindArgs.length; ++i) {
				sb.append("selectionArgs[");
				sb.append(i);
				sb.append("] is ");
				sb.append(bindArgs[i]);
				sb.append("; ");
			}
		}
		return sb;
	}

	/**
	 * 插入日志
	 * @param db
	 * @param tableName
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sort
	 * @return
	 */
	public static java.lang.StringBuilder buildSqlLog(final SQLiteDatabase db,
			final String tableName, final String nullColumnHack,
			final ContentValues values) {
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		sb.append("starting insert, database is ");
		if (db != null) {
			sb.append("not ");
		}
		sb.append("null; ");
		sb.append("tables is ");
		if (tableName == null) {
			sb.append("null; ");
		} else if (tableName.length() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(tableName).append("; ");
		}
		sb.append("ContentValues is ");
		if (values == null) {
			sb.append("null; ");
		} else if (values.size() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(values.toString()).append("; ");
		}
		sb.append("nullColumnHack is ");
		sb.append(nullColumnHack);
		sb.append("; ");
		return sb;
	}

	/**
	 * 修改日志
	 * @param db
	 * @param tableName
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sort
	 * @return
	 */
	public static java.lang.StringBuilder buildSqlLog(SQLiteDatabase db,
			final String tableName, final ContentValues values,
			final String whereClause, final String[] whereArgs) {
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		sb.append("starting update, database is ");
		if (db != null) {
			sb.append("not ");
		}
		sb.append("null; ");
		sb.append("tables is ");
		if (tableName == null) {
			sb.append("null; ");
		} else if (tableName.length() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(tableName).append("; ");
		}
		sb.append("ContentValues is ");
		if (values == null) {
			sb.append("null; ");
		} else if (values.size() == 0) {
			sb.append("empty; ");
		} else {
			sb.append(values.toString()).append("; ");
		}
		sb.append("whereClause is ");
		sb.append(whereClause);
		sb.append("; ");
		if (whereArgs == null) {
			sb.append("whereArgs is null; ");
		} else if (whereArgs.length == 0) {
			sb.append("whereArgs is empty; ");
		} else {
			for (int i = 0; i < whereArgs.length; ++i) {
				sb.append("whereArgs[");
				sb.append(i);
				sb.append("] is ");
				sb.append(whereArgs[i]);
				sb.append("; ");
			}
		}
		return sb;
	}

	/**
	 * 数据库ID生成方法
	 * @return
	 */
	public static String generateId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}
