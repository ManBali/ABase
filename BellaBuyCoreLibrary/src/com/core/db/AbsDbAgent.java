package com.core.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbsDbAgent {

	private final Lock mDbLock = new ReentrantLock();

	public abstract class SqlTask<T> extends BaseDbAgent {
		
		private List<Cursor> cursors = new ArrayList<Cursor>();

		protected abstract T doExecute();

		/**
		 * @param isWritable 是否打开可写数据库
		 * @return
		 */
		public T execute(boolean isWritable) {
			T t = null;
			mDbLock.lock();
			try {
				mDatabase = openSQLiteDatabase(isWritable);
				t = doExecute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					closeResources();
				} catch (Exception e) {
					e.printStackTrace();
				}

				mDbLock.unlock();
			}
			return t;
		}

		@Override
		protected Cursor rawQuery(String sql, String... selectionArgs) {
			Cursor cursor = super.rawQuery(sql, selectionArgs);
			cursors.add(cursor);
			return cursor;
		}

		@Override
		protected Cursor query(String tableName, String[] projection,
				String selection, String[] selectionArgs, String groupBy,
				String having, String sort) {
			Cursor cursor = super.query(tableName, projection, selection,selectionArgs, groupBy, having, sort);
			cursors.add(cursor);
			return cursor;
		}

		private void closeResources() {
			// 1.关闭所有Cursor资源
			for (Iterator<Cursor> iterator = cursors.iterator(); iterator
					.hasNext();) {
				Cursor cursor = (Cursor) iterator.next();
				cursor.close();
				iterator.remove();
			}
			// 2.关闭数据库
			closeDb();
		}

		private void closeDb() {
			if (mDatabase != null) {
				mDatabase.close();
			}
		}
	}

	/**
	 * 子类中指定SQLiteDatabase
	 * @return
	 */
	protected abstract SQLiteDatabase openSQLiteDatabase(boolean isWritable);

}
