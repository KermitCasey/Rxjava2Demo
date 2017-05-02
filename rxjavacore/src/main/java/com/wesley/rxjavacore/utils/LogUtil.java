package com.wesley.rxjavacore.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log工具，类似android.util.Log。 tag自动产生，格式:
 * customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * <p/>
 * Author: wyouflf Date: 13-7-24 Time: 下午12:23
 */
@SuppressLint("SimpleDateFormat")
public class LogUtil {

	public static String customTagPrefix = "";
	private static boolean bDebug = true;

	private LogUtil() {
	}

	public static boolean allowD = true;
	public static boolean allowE = true;
	public static boolean allowI = true;
	public static boolean allowV = true;
	public static boolean allowW = true;
	public static boolean allowWtf = true;

	/**
	 * 设置debug模式，true 打印信息 false 不打印任何信息
	 * 
	 * @param isDebug
	 *            是否打开debug模式
	 */
	public static void setDebug(boolean isDebug) {
		bDebug = isDebug;
	}

	public static StackTraceElement getCallerStackTraceElement() {
		return Thread.currentThread().getStackTrace()[4];
	}

	private static String generateTag(StackTraceElement caller) {
		String tag = "%s.%s(L:%d)";
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName
				.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, caller.getMethodName(),
				caller.getLineNumber());
		tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":"
				+ tag;
		return tag;
	}

	public static CustomLogger customLogger;

	public interface CustomLogger {
		void d(String tag, String content);

		void d(String tag, String content, Throwable tr);

		void e(String tag, String content);

		void e(String tag, String content, Throwable tr);

		void i(String tag, String content);

		void i(String tag, String content, Throwable tr);

		void v(String tag, String content);

		void v(String tag, String content, Throwable tr);

		void w(String tag, String content);

		void w(String tag, String content, Throwable tr);

		void w(String tag, Throwable tr);

		void wtf(String tag, String content);

		void wtf(String tag, String content, Throwable tr);

		void wtf(String tag, Throwable tr);
	}

	public static void d(String content) {
		if (!bDebug) {
			return;
		}

		if (!allowD) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.d(tag, content);
		} else {
			Log.d(tag, content);
		}
	}

	public static void d(String content, Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowD) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.d(tag, content, tr);
		} else {
			Log.d(tag, content, tr);
		}
	}

	public static void e(String content) {
		if (!bDebug) {
			return;
		}

		if (!allowE) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.e(tag, content);
		} else {
			Log.e(tag, content);
		}
	}

	public static void e(String content, Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowE) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.e(tag, content, tr);
		} else {
			Log.e(tag, content, tr);
		}
	}

	public static void i(String content) {
		if (!bDebug) {
			return;
		}

		if (!allowI) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.i(tag, content);
		} else {
			Log.i(tag, content);
		}
	}

	public static void i(String content, Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowI) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.i(tag, content, tr);
		} else {
			Log.i(tag, content, tr);
		}
	}

	public static void v(String content) {
		if (!bDebug) {
			return;
		}

		if (!allowV) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.v(tag, content);
		} else {
			Log.v(tag, content);
		}
	}

	public static void v(String content, Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowV) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.v(tag, content, tr);
		} else {
			Log.v(tag, content, tr);
		}
	}

	public static void w(String content) {
		if (!bDebug) {
			return;
		}

		if (!allowW) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.w(tag, content);
		} else {
			Log.w(tag, content);
		}
	}

	public static void w(String content, Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowW) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.w(tag, content, tr);
		} else {
			Log.w(tag, content, tr);
		}
	}

	public static void w(Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowW) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.w(tag, tr);
		} else {
			Log.w(tag, tr);
		}
	}

	public static void wtf(String content) {
		if (!bDebug) {
			return;
		}

		if (!allowWtf) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.wtf(tag, content);
		} else {
			Log.wtf(tag, content);
		}
	}

	public static void wtf(String content, Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowWtf) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.wtf(tag, content, tr);
		} else {
			Log.wtf(tag, content, tr);
		}
	}

	public static void wtf(Throwable tr) {
		if (!bDebug) {
			return;
		}

		if (!allowWtf) {
			return;
		}
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);

		if (customLogger != null) {
			customLogger.wtf(tag, tr);
		} else {
			Log.wtf(tag, tr);
		}
	}

	public static String getStackInfo(Throwable arg1) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw);
		pw.close();
		String error = writer.toString();
		return error;
	}

	public static void writeLog(Throwable arg) {
		writeLog(getStackInfo(arg));
	}

	@SuppressWarnings("unused")
	public static void writeLog(String msg) {

		if (msg == null) {
			return;
		}

		Log.d("QHTAPP", msg);
		if (msg != null) {
			return;
		}
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/QHTLog/");
			if (!file.exists()) {
				file.mkdir();
			}

			file = new File(Environment.getExternalStorageDirectory()
					+ "/QHTLog/YHT.log");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fo = new FileOutputStream(file, true);

			Date curDate = new Date(System.currentTimeMillis());
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"[yyyy-MM-dd HH:mm:ss] ");
			String strDate = sDateFormat.format(curDate);

			fo.write(strDate.getBytes());
			fo.write(msg.getBytes());
			fo.write("\r\n".getBytes());

			fo.flush();
			fo.close();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		}
	}

}
