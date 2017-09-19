package com.androidcat.utilities;
import android.content.Context;

// TODO: Auto-generated Javadoc

/**
 * **********************************************************************************<br>
 * 工程名称: <br>
 * 模块功能：<br>
 * 修改作 者: 张明<br>
 * 单 位：武汉天喻信息 研发中心 <br>
 * 开发日期：2015-08-20 上午10:38:19 <br>
 * **********************************************************************************<br>.
 */
public class ResourceManager {
	
	/**
	 * Gets the id by name.
	 *
	 * @param context the context
	 * @param className the class name
	 * @param name the name
	 * @return the id by name
	 */
	public static int getIdByName(Context context, String className, String name) {
		String packageName = context.getPackageName();
		Class r = null;
		int id = 0;
		try {
			r = Class.forName(packageName + ".R");

			Class[] classes = r.getClasses();
			Class desireClass = null;

			for (int i = 0; i < classes.length; ++i) {
				if (classes[i].getName().split("\\$")[1].equals(className)) {
					desireClass = classes[i];
					break;
				}
			}

			if (desireClass != null)
				id = desireClass.getField(name).getInt(desireClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return id;
	}
	
	/**
	 * Gets the ids by name.
	 *
	 * @param context the context
	 * @param className the class name
	 * @param name the name
	 * @return the ids by name
	 */
	public static int[] getIdsByName(Context context, String className, String name) {
	    String packageName = context.getPackageName();
	    Class r = null;
	    int[] ids = null;
	    try {
	      r = Class.forName(packageName + ".R");

	      Class[] classes = r.getClasses();
	      Class desireClass = null;

	      for (int i = 0; i < classes.length; ++i) {
	        if (classes[i].getName().split("\\$")[1].equals(className)) {
	          desireClass = classes[i];
	          break;
	        }
	      }

	      if ((desireClass != null) && (desireClass.getField(name).get(desireClass) != null) && (desireClass.getField(name).get(desireClass).getClass().isArray()))
	        ids = (int[])desireClass.getField(name).get(desireClass);
	    }
	    catch (ClassNotFoundException e) {
	    		e.printStackTrace();
	    	} catch (IllegalArgumentException e) {
	    		e.printStackTrace();
	    } catch (SecurityException e) {
	    		e.printStackTrace();
	    } catch (IllegalAccessException e) {
	    		e.printStackTrace();
	    } catch (NoSuchFieldException e) {
	    		e.printStackTrace();
	    }

	    return ids;
	  }
}








