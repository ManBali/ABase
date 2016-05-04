package com.core.db;

import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.CursorWrapper;

public class ReadOnlyCursorWrapper extends CursorWrapper implements CrossProcessCursor {
	
	public ReadOnlyCursorWrapper(Cursor cursor) {
		super(cursor);
		mCursor = (CrossProcessCursor) cursor;
	}

	public boolean deleteRow() {
		throw new SecurityException("Travel manager cursors are read-only");
	}

	public boolean commitUpdates() {
		throw new SecurityException("Travel manager cursors are read-only");
	}

	public void fillWindow(int pos, CursorWindow window) {
		mCursor.fillWindow(pos, window);
	}

	public CursorWindow getWindow() {
		return mCursor.getWindow();
	}

	public boolean onMove(int oldPosition, int newPosition) {
		return mCursor.onMove(oldPosition, newPosition);
	}

	private CrossProcessCursor mCursor;
	
}